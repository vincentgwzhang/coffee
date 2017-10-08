package tina.coffee.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.DesktopVerifier;
import tina.coffee.Verifier.MenuVerifier;
import tina.coffee.Verifier.OrderVerifier;
import tina.coffee.data.model.DesktopEntity;
import tina.coffee.data.model.ImportProductCountEntity;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.model.MenuQueueEntity;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.data.model.OrderType;
import tina.coffee.data.repository.DesktopRepository;
import tina.coffee.data.repository.ImportProductCountRepository;
import tina.coffee.data.repository.MenuItemRepository;
import tina.coffee.data.repository.MenuQueueRepository;
import tina.coffee.data.repository.OrderItemRepository;
import tina.coffee.data.repository.OrderRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.dozer.providers.OrderItemMappingProvider;
import tina.coffee.function.CalFunction;
import tina.coffee.function.MenuItemFunction;
import tina.coffee.function.print.SocketPrinter;
import tina.coffee.rest.dto.OrderDTO;
import tina.coffee.rest.dto.OrderItemDTO;
import tina.coffee.system.config.SystemConstant;
import tina.coffee.system.exceptions.order.OrderNotOpenException;
import tina.coffee.system.prop.SocketPrinterConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final DozerMapper mapper;

    private final OrderItemRepository repository;

    private final OrderRepository orderRepository;

    private final DesktopRepository desktopRepository;

    private final MenuItemRepository menuItemRepository;

    private final MenuQueueRepository menuQueueRepository;

    private final OrderService orderService;

    private final MenuQueueService menuQueueService;

    private final ImportProductCountRepository importProductCountRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private SocketPrinterConfig socketPrinterConfig;

    @Autowired
    public OrderItemService(DozerMapper mapper,
                            OrderItemRepository repository,
                            OrderRepository orderRepository,
                            DesktopRepository desktopRepository,
                            MenuItemRepository menuItemRepository,
                            MenuQueueRepository menuQueueRepository,
                            ImportProductCountRepository importProductCountRepository,
                            MenuQueueService menuQueueService,
                            SocketPrinterConfig socketPrinterConfig,
                            OrderService orderService) {
        this.mapper = mapper;

        this.orderService = orderService;
        this.menuQueueService = menuQueueService;

        this.repository = repository;
        this.orderRepository = orderRepository;
        this.desktopRepository = desktopRepository;
        this.menuItemRepository = menuItemRepository;
        this.menuQueueRepository = menuQueueRepository;
        this.importProductCountRepository = importProductCountRepository;
        this.socketPrinterConfig = socketPrinterConfig;
    }

    @Transactional
    public OrderItemEntity createOrderItem(Integer desktopNumber, Integer menuitemId, Integer count) {
        OrderDTO orderDTO = null;

        Optional<DesktopEntity> desktopEntity = desktopRepository.findByDeskNo(desktopNumber);
        DesktopVerifier.appendExistOrderProcedure(desktopEntity, desktopNumber);

        Optional<OrderEntity> orderEntity = orderRepository.findByDesktopEntityDeskNoAndOrderType(desktopEntity.get().getDeskNo(), OrderType.OPEN);
        orderEntity.orElseThrow(OrderNotOpenException.newOrderNotOpenException(desktopNumber));

        Optional<MenuItemEntity> menuItemEntity = menuItemRepository.findByMiId(menuitemId);
        MenuVerifier.newOrderMenuItemProcedure(menuItemEntity);

        OrderItemEntity orderItemEntity = MenuItemFunction.generateOrderItemEntity(orderEntity.get(), menuItemEntity.get(), count);
        return repository.save(orderItemEntity);
    }

    @Transactional
    public void updateOrderItem(Integer orderItemId, Integer count) {
        Optional<OrderItemEntity> orderItemEntity = repository.findByOrderItemId(orderItemId);
        OrderVerifier.operatorUpdateOrderItemProcedure(orderItemEntity);

        OrderItemEntity entity = orderItemEntity.get();
        entity.setCount(count);
        repository.save(entity);
    }

    @Transactional
    public void chiefUpdateOrderItemStatus(Integer menuQueueId, String status) {
        Optional<MenuQueueEntity> menuQueueEntity = menuQueueRepository.findByMqId(menuQueueId);
        menuQueueEntity.orElseThrow(()-> new RuntimeException("Menu Queue Repository not found"));

        MenuQueueEntity entity = menuQueueEntity.get();

        OrderItemEntity orderItemEntity = entity.getOrderItemEntity();
        OrderItemStatus orderItemStatus = OrderItemStatus.valueOf(status);

        if(orderItemStatus==OrderItemStatus.PROGRESS) {//Update to progress
            OrderVerifier.chiefUpdateOrderItemProcedure(Optional.of(orderItemEntity));
        } else if(orderItemStatus==OrderItemStatus.DONE) {//Update to complete
            OrderVerifier.chiefCompleteOrderItemProcedure(Optional.of(orderItemEntity));

            orderItemEntity.setEndTime(Calendar.getInstance());
            menuQueueService.removeInChiefMonitor(menuQueueId);
        }

        orderItemEntity.setStatus(orderItemStatus);
        repository.save(orderItemEntity);
    }

    @Transactional
    public void orderNewItem(Integer desktopNumber, Integer menuitemId, Integer count) {
        OrderItemEntity entity = createOrderItem(desktopNumber, menuitemId, count);
        orderService.refreshOrderPrice(entity.getOrder());

        if(entity.getMenuItem().isToChief()){
            String menuName = entity.getMenuItem().getLanguages().stream().filter(l -> l.getLanguageType() == LanguageType.CHINESE).map(mi -> mi.getMilDescription()).findFirst().get();
            printToChief(menuName, desktopNumber, count);
        }
        updateImportProductCount(entity.getMenuItem(), count);
    }

    private void printToChief(String menuName, Integer desktopNumber, Integer count) {
        List<String> strList = new ArrayList<>();
        strList.add("");
        strList.add("");
        strList.add("###############################################");
        strList.add("Timpo: " + CalFunction.getCurrentTime(SystemConstant.LONG_DATE_FORMAT));
        strList.add("Nombre: " + menuName);
        if(desktopNumber != -1) {
            strList.add("Número de escritorio: " + desktopNumber);
        } else {
            strList.add("Número de escritorio: Para llevar");
        }

        strList.add("Número de ejemplares: " + count);
        strList.add("################################################");
        strList.add("");
        strList.add("");

        try {
            SocketPrinter printer = new SocketPrinter(socketPrinterConfig.getPrinterIP(), socketPrinterConfig.getPrinterPort());
            printer.setJustification(0);
            for(String content : strList) {
                printer.printlnObj(content);
            }
            printer.feedAndCut();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 判断是不是发给厨师的，对于是发给厨师的，就判断，打印
     */
    @Transactional
    public void orderTakeAwayItem(Integer menuitemId, Integer count) {
        MenuItemEntity entity = menuItemRepository.findOne(menuitemId);

        if( entity.isToChief() ) {
            String menuName = entity.getLanguages().stream().filter(l -> l.getLanguageType() == LanguageType.SPANISH).map(mi -> mi.getMilDescription()).findFirst().get();
            printToChief(menuName, -1, count);

            updateImportProductCount(entity, count);
        }
    }

    @Transactional
    public void updateImportProductCount(MenuItemEntity entity, Integer count) {
        List<ImportProductEntity> importProductEntities = entity.getImportProducts();
        if(importProductEntities.size()!=0) {
            ImportProductEntity importProductEntity = importProductEntities.get(0);
            if(importProductEntity.isIpCountable()){
                Optional<ImportProductCountEntity> importProductCountEntityOptional = importProductCountRepository.findByImportProduct(importProductEntity);
                if(importProductCountEntityOptional.isPresent()) {
                    ImportProductCountEntity importProductCountEntity = importProductCountEntityOptional.get();
                    BigDecimal originalValue = importProductCountEntity.getCount();
                    BigDecimal deductValue   = new BigDecimal(count);
                    importProductCountEntity.setCount(originalValue.subtract(deductValue));
                    importProductCountRepository.save(importProductCountEntity);
                }
            }

        }
    }

    @Transactional
    public void cancelOrderItem(Integer orderItemId) {
        Optional<OrderItemEntity> orderItemEntity = repository.findByOrderItemId(orderItemId);
        OrderVerifier.operatorCancelOrderItemProcedure(orderItemEntity);

        OrderItemEntity entity = orderItemEntity.get();
        entity.setStatus(OrderItemStatus.CANCELED);
        entity.setEndTime(Calendar.getInstance());
        repository.save(entity);
        orderService.refreshOrderPrice(entity.getOrder());

        if(entity.getMenuItem().isToChief()){
            //这里是改为打印的
            //menuQueueService.removeInChiefMonitor(entity);
        }
    }

    @Transactional
    public List<OrderItemDTO> getOrderItemInStatus(Integer orderNumber, List<String> statuses, Integer count) {
        Pageable topCount = new PageRequest(0, count);
        List<OrderItemStatus> orderItemStatuses = statuses.stream().map(OrderItemStatus::valueOf).collect(Collectors.toList());
        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderNumber);
        OrderVerifier.verifiyIfOrderExistOrThrow(orderEntity);
        List<OrderItemEntity> entities = repository.findByOrderAndStatusInOrderByStartTimeDesc(orderEntity.get(), orderItemStatuses, topCount);
        return mapper.map(entities, OrderItemDTO.class, OrderItemMappingProvider.MAPPING_ENTITY_TO_DTO);
    }
}
