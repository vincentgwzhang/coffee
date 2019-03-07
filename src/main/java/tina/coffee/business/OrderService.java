package tina.coffee.business;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.DesktopVerifier;
import tina.coffee.Verifier.OrderVerifier;
import tina.coffee.data.model.DesktopEntity;
import tina.coffee.data.model.ImportProductCountEntity;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.data.model.OrderType;
import tina.coffee.data.repository.DesktopRepository;
import tina.coffee.data.repository.ImportProductCountRepository;
import tina.coffee.data.repository.OrderItemRepository;
import tina.coffee.data.repository.OrderRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.function.CalFunction;
import tina.coffee.function.MenuItemFunction;
import tina.coffee.function.OrderFunction;
import tina.coffee.function.print.PrintItem;
import tina.coffee.function.print.PrintItemFooterBuilder;
import tina.coffee.function.print.PrintItemHeaderBuilder;
import tina.coffee.function.print.PrinterFunction;
import tina.coffee.rest.dto.CloseBillDTO;
import tina.coffee.rest.dto.CloseOrderItemDTO;
import tina.coffee.rest.dto.CloseTakeAwayDTO;
import tina.coffee.rest.dto.OrderDTO;
import tina.coffee.rest.dto.OrderStatisticsWrapper;
import tina.coffee.system.exceptions.order.OrderCreateException;
import tina.coffee.system.prop.UsbPrinterConfig;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static tina.coffee.system.config.SystemConstant.SHORT_DATE_FORMAT;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final DozerMapper mapper;

    private final OrderItemRepository orderItemRepository;

    private final OrderRepository repository;

    private final DesktopRepository desktopRepository;

    private final DesktopService desktopService;

    private final MenuItemService menuItemService;

    private final MenuQueueService menuQueueService;

    private final ImportProductCountRepository importProductCountRepository;

    private final UsbPrinterConfig usbPrinterConfig;

    private static final int TAKE_AWAY_ORDER_DESKTOP_NUMBER = -1;

    @Autowired
    public OrderService(DozerMapper mapper,
                        UsbPrinterConfig usbPrinterConfig,
                        DesktopService desktopService,
                        MenuQueueService menuQueueService,
                        DesktopRepository desktopRepository,
                        OrderItemRepository orderItemRepository,
                        ImportProductCountRepository importProductCountRepository,
                        MenuItemService menuItemService,
                        OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
        this.usbPrinterConfig = usbPrinterConfig;
        this.orderItemRepository = orderItemRepository;
        this.menuItemService = menuItemService;
        this.desktopRepository = desktopRepository;
        this.importProductCountRepository = importProductCountRepository;
        this.desktopService = desktopService;
        this.menuQueueService = menuQueueService;
    }

    @Transactional
    public OrderStatisticsWrapper statisticsIncomeByDateInSpecMonth(String yearMonth) {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd   = Calendar.getInstance();

        if(!Strings.isNullOrEmpty(yearMonth)) {
            String[] dates = yearMonth.split("_");
            calendarStart.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            calendarStart.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);

            calendarEnd.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            calendarEnd.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
        }

        calendarStart.set(Calendar.DAY_OF_MONTH, 1);
        calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DATE));

        List<OrderEntity> entities = repository.findByEndTimeBetweenAndOrderTypeOrderByOrderId(calendarStart, calendarEnd, OrderType.CLOSE);
        Map<Integer, List<OrderEntity>> orderByDate = entities.stream().collect(Collectors.groupingBy(entity -> CalFunction.calendarToDayOfMonth(entity.getStartTime())));
        List<OrderStatisticsWrapper.StatisticsDTO> statisticsDTOS = orderByDate.entrySet().stream()
                .map(entry -> new OrderStatisticsWrapper.StatisticsDTO(entry.getKey(), accumulateOrdersIncomeInOneDay(entry.getValue())))
                .collect(Collectors.toList());

        return new OrderStatisticsWrapper(calendarStart.getActualMaximum(Calendar.DATE), statisticsDTOS);
    }

    private BigDecimal accumulateOrdersIncomeInOneDay(List<OrderEntity> entities) {
        return entities.stream().map(OrderEntity::getActualPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public OrderDTO retrieveOpenOrderByDesktopNumber(Integer desktopNumber) {
        Optional<DesktopEntity> desktopEntity = desktopRepository.findByDeskNo(desktopNumber);
        DesktopVerifier.verifyIfDesktopNotExistAndThrow(desktopEntity, desktopNumber);
        Optional<OrderEntity> orderEntity = repository.findByDesktopEntityDeskNoAndOrderType(desktopEntity.get().getDeskNo(), OrderType.OPEN);
        return mapper.map(orderEntity.get(), OrderDTO.class);
    }

    @Transactional
    public OrderDTO createNewOrder(Integer desktopNumber) {
        OrderEntity orderEntity;
        OrderDTO orderDTO = null;
        Optional<DesktopEntity> desktopEntity = desktopRepository.findByDeskNo(desktopNumber);

        if(!isTakeAwayOrder(desktopNumber)) {
            DesktopVerifier.newCreateOrderProcedure(desktopEntity, desktopNumber);
        }

        if(desktopEntity.isPresent()) {
            //Create new order in table
            orderEntity = OrderFunction.initOrderEntity(desktopEntity.get());
            orderEntity = repository.save(orderEntity);
            orderDTO = mapper.map(orderEntity, OrderDTO.class);

            //Update desktop to be occupied
            desktopService.updateDesktopOccupiedStatus(desktopEntity.get().getDeskNo(), true);
        }

        return Optional.ofNullable(orderDTO).orElseThrow(OrderCreateException.newOrderCreateException(desktopNumber));
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findOrdersByCalendarDay(String strDate) {
        Calendar startTime = CalFunction.stringToCalendar(SHORT_DATE_FORMAT, strDate);
        Calendar endTime = CalFunction.stringToCalendar(SHORT_DATE_FORMAT, strDate);
        endTime.add(Calendar.DATE, 1);
        return findOrdersByCalendarDay(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findOrdersByCalendarDay(String strStartDate, String strEndDate) {
        Calendar startTime = CalFunction.stringToCalendar(SHORT_DATE_FORMAT, strStartDate);
        Calendar endTime = CalFunction.stringToCalendar(SHORT_DATE_FORMAT, strEndDate);
        return findOrdersByCalendarDay(startTime, endTime);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findOrdersByCalendarDay(Calendar startTime, Calendar endTime) {
        List<OrderEntity> orderEntities = repository.findByEndTimeBetweenAndOrderTypeOrderByOrderId(startTime, endTime, OrderType.CLOSE);
        return mapper.map(orderEntities, OrderDTO.class);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderByDesktop(Integer desktopNumber) {
        Optional<DesktopEntity> desktopEntity = desktopRepository.findByDeskNo(desktopNumber);
        Optional<OrderEntity> orderEntity = repository.findByDesktopEntityDeskNoAndOrderType(desktopEntity.get().getDeskNo(), OrderType.OPEN);
        OrderVerifier.verifiyIfOrderExistOrThrow(orderEntity);
        return mapper.map(orderEntity.get(), OrderDTO.class);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderByID(Integer orderId) {
        Optional<OrderEntity> orderEntity = repository.findByOrderId(orderId);
        OrderVerifier.verifiyIfOrderExistOrThrow(orderEntity);
        return mapper.map(orderEntity.get(), OrderDTO.class);
    }

    /**
     * Cancel 的意思是说客人点了东西的情况下突然说要走了，于是取消
     * @param desktopNumber
     */
    @Transactional
    public void markLost(Integer desktopNumber) {
        Optional<DesktopEntity> desktopEntity = desktopRepository.findByDeskNo(desktopNumber);
        Optional<OrderEntity> orderEntity = repository.findByDesktopEntityDeskNoAndOrderType(desktopEntity.get().getDeskNo(), OrderType.OPEN);
        orderEntity.orElseThrow(() -> new RuntimeException("It is fine, order not found!"));
        OrderEntity entity = orderEntity.get();
        Set<OrderItemEntity> orderItemEntitySet = entity.getItems();

        BigDecimal totalLost = BigDecimal.ZERO;
        for(OrderItemEntity orderItemEntity : orderItemEntitySet) {
            //menuQueueService.removeInChiefMonitor(orderItemEntity);
            if(orderItemEntity.getStatus() == OrderItemStatus.OPEN || orderItemEntity.getStatus() == OrderItemStatus.PROGRESS) {
                orderItemEntity.setStatus(OrderItemStatus.CANCELED);
                orderItemEntity.setEndTime(Calendar.getInstance());
                orderItemRepository.save(orderItemEntity);
            }
            totalLost = totalLost.add(CalFunction.murtiplyByBigDecimal(orderItemEntity.getMenuItem().getMiPrice(), BigDecimal.valueOf(orderItemEntity.getCount())));
        }

        entity.setTotalPrice(totalLost);
        entity.setActualPrice(BigDecimal.ZERO);//Has not paid
        entity.setEndTime(Calendar.getInstance());
        entity.setOrderType(OrderType.LOST);
        repository.save(entity);

        DesktopEntity dskEntity = desktopEntity.get();
        dskEntity.setDeskOccupied(false);
        desktopRepository.save(dskEntity);
    }

    /**
     *
     * 其实当初设计有 clear 这个动作的目的是让厨师取消菜单的，但是现在不需要了，因为厨师没有屏幕
     * 但这个函数依然保留下来，并且核心的代码将会被屏蔽，以适应给 Tina 的项目
     *
     */
    @Transactional
    public OrderDTO clearOrder(Integer desktopNumber) {
        Optional<DesktopEntity> desktopEntityOptional = desktopRepository.findByDeskNo(desktopNumber);
        DesktopVerifier.closeOrderProcedure(desktopEntityOptional, desktopNumber);

        Optional<OrderEntity> orderEntityOptional = repository.findByDesktopEntityDeskNoAndOrderType(desktopEntityOptional.get().getDeskNo(), OrderType.OPEN);
        OrderVerifier.closeOrderProcedure(orderEntityOptional, desktopNumber);

        OrderEntity orderEntity = orderEntityOptional.get();
        Set<OrderItemEntity> orderItemEntities = orderEntity.getItems();


        /**
         * 这里开始是简化版，直接把所有的列为要给钱
         */
        for(OrderItemEntity entity : orderItemEntities) {
            entity.setEndTime(Calendar.getInstance());//无法得知是什么时候做好的
            entity.setStatus(OrderItemStatus.DONE);//直接从 OPEN -> DONE
            orderItemRepository.save(entity);
        }
        Optional<BigDecimal> totalPrice = orderItemEntities.stream()
                .map(entity -> CalFunction.murtiplyByBigDecimal(entity.getMenuItem().getMiPrice(), BigDecimal.valueOf(entity.getCount())))
                .reduce(BigDecimal::add);
        /**
         * 这里是简化版结束
         */

        /**
         * 这里开始是完整版，直接有跟厨师互动
         */
//        Set<OrderItemEntity> openOrderItemEntities       = orderItemEntities.stream().filter(en -> en.getStatus() == OrderItemStatus.OPEN).collect(Collectors.toSet());
//        Set<OrderItemEntity> processingOrderItemEntities = orderItemEntities.stream().filter(en -> en.getStatus() == OrderItemStatus.PROGRESS).collect(Collectors.toSet());
//
//        for(OrderItemEntity entity : openOrderItemEntities) {
//            entity.setEndTime(Calendar.getInstance());
//            entity.setStatus(OrderItemStatus.CANCELED);
//            orderItemRepository.save(entity);
//            menuQueueService.removeInChiefMonitor(entity);
//        }
//
//        for(OrderItemEntity entity : processingOrderItemEntities) {
//            entity.setEndTime(Calendar.getInstance());
//            entity.setStatus(OrderItemStatus.DONE);
//            orderItemRepository.save(entity);
//            menuQueueService.removeInChiefMonitor(entity);
//        }
//
//        Optional<BigDecimal> totalPrice = orderEntity.getItems().stream().filter(en -> en.getStatus() == OrderItemStatus.DONE)
//                .map(en -> CalFunction.murtiplyByBigDecimal(en.getMenuItem().getMiPrice(), BigDecimal.valueOf(en.getCount()))).reduce(BigDecimal::add);
        /**
         * 这里是完整版的结束
         */

        orderEntity.setEndTime(null);
        orderEntity.setOrderType(OrderType.OPEN);
        orderEntity.setActualPrice(BigDecimal.ZERO);
        orderEntity.setTotalPrice(totalPrice.orElse(BigDecimal.ZERO));
        repository.save(orderEntity);
        return mapper.map(orderEntity, OrderDTO.class);
    }

    public void closeOrder(Integer desktopNumber, CloseBillDTO closeBillDTO) {
        OrderEntity orderEntity = closeOrder(desktopNumber, closeBillDTO.getActualPrice());
        printOrder(orderEntity, closeBillDTO);
    }

    private void printOrder(OrderEntity orderEntity, CloseBillDTO closeBillDTO) {
        List<PrintItem> printItems = Lists.newArrayList();
        Set<OrderItemEntity> orderItemEntities = orderEntity.getItems();
        for(OrderItemEntity entity : orderItemEntities) {
            String pi_name = entity.getMenuItem().getLanguages().stream().filter(mil-> mil.getLanguageType()==LanguageType.SPANISH).findFirst().get().getMilDescription();
            String pi_unit = entity.getMenuItem().getMiPrice().toString();
            String pi_count = String.valueOf(entity.getCount());
            String pi_total = entity.getMenuItem().getMiPrice().multiply(BigDecimal.valueOf(Long.valueOf(String.valueOf(entity.getCount())))).toString();
            printItems.add(new PrintItem(pi_name, pi_unit, pi_count, pi_total));
        }

        PrinterFunction.print(
                PrintItemHeaderBuilder.buildHeader(),
                PrintItemFooterBuilder.buildFooter(orderEntity.getOrderId(), orderEntity.getDesktopEntity().getDeskNo()),
                printItems,
                usbPrinterConfig.getName(),
                orderEntity.getTotalPrice()
        );
    }

    @Transactional
    public OrderEntity closeOrder(Integer desktopNumber, BigDecimal actualPaid) {
        OrderEntity orderEntity = closeOrderInDB(desktopNumber, actualPaid);
        desktopService.updateDesktopOccupiedStatus(desktopNumber, false);
        return orderEntity;
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
    public void closeTakeAway(CloseTakeAwayDTO closeTakeAwayDTO) {
        OrderDTO orderDTO = createNewOrder(TAKE_AWAY_ORDER_DESKTOP_NUMBER);
        OrderEntity orderEntity = repository.findById(orderDTO.getOrderId()).get();

        List<CloseOrderItemDTO> closeOrderItemDTOS = closeTakeAwayDTO.getOrderItemDTOList();
        Set<OrderItemEntity> orderItemEntities = new HashSet<>();
        List<PrintItem> printItems = Lists.newArrayList();

        for(CloseOrderItemDTO closeOrderItemDTO : closeOrderItemDTOS) {
            MenuItemEntity menuItemEntity = menuItemService.getMenuItemEntityById(closeOrderItemDTO.getMenuItemId());
            int menuItemCount = closeOrderItemDTO.getCount();
            OrderItemEntity orderItemEntity = MenuItemFunction.generateOrderItemEntity(orderEntity, menuItemEntity, menuItemCount);
            orderItemRepository.save(orderItemEntity);

            //update relative menu item relate
            if(!menuItemEntity.isToChief()) {
                updateImportProductCount(menuItemEntity, menuItemCount);
            }
            orderItemEntities.add(orderItemEntity);


            String pi_name = menuItemEntity.getLanguages().stream().filter(mil -> mil.getLanguageType() == LanguageType.SPANISH).findFirst().get().getMilDescription();
            String pi_unit = menuItemEntity.getMiPrice().toString();
            String pi_count = String.valueOf(closeOrderItemDTO.getCount());
            String pi_total = menuItemEntity.getMiPrice().multiply(BigDecimal.valueOf(Long.valueOf(String.valueOf(closeOrderItemDTO.getCount())))).toString();
            printItems.add(new PrintItem(pi_name, pi_unit, pi_count, pi_total));
        }

        orderEntity.setItems(orderItemEntities);
        orderEntity = repository.save(orderEntity);


        clearOrder(TAKE_AWAY_ORDER_DESKTOP_NUMBER);
        closeOrder(TAKE_AWAY_ORDER_DESKTOP_NUMBER, closeTakeAwayDTO.getActualPrice());

        PrinterFunction.print(
                PrintItemHeaderBuilder.buildHeader(),
                PrintItemFooterBuilder.buildFooter(orderEntity.getOrderId(), TAKE_AWAY_ORDER_DESKTOP_NUMBER),
                printItems,
                usbPrinterConfig.getName(),
                orderEntity.getTotalPrice()
        );
    }

    @Transactional
    public OrderEntity closeOrderInDB(Integer desktopNumber, BigDecimal actualPaid) {
        Optional<DesktopEntity> desktopEntityOptional = desktopRepository.findByDeskNo(desktopNumber);
        DesktopVerifier.closeOrderProcedure(desktopEntityOptional, desktopNumber);

        Optional<OrderEntity> orderEntityOptional = repository.findByDesktopEntityDeskNoAndOrderType(desktopEntityOptional.get().getDeskNo(), OrderType.OPEN);
        OrderVerifier.closeOrderProcedure(orderEntityOptional, desktopNumber);

        OrderEntity orderEntity = orderEntityOptional.get();
        orderEntity.setActualPrice(actualPaid);
        orderEntity.setEndTime(Calendar.getInstance());
        orderEntity.setOrderType(OrderType.CLOSE);
        return repository.save(orderEntity);
    }

    @Transactional
    public void customerMoveToNewTable(Integer oldDesktopNumber, Integer newDesktopNumber) {
        //Check old and new desktop
        Optional<DesktopEntity> oldDesktop = desktopRepository.findByDeskNo(oldDesktopNumber);
        Optional<DesktopEntity> newDesktop = desktopRepository.findByDeskNo(newDesktopNumber);
        DesktopVerifier.verifyIfDesktopNotExistAndThrow(oldDesktop, oldDesktopNumber);
        DesktopVerifier.newCreateOrderProcedure(newDesktop, newDesktopNumber);

        //Move all order to new table
        Optional<OrderEntity> orderEntity = repository.findByDesktopEntityDeskNoAndOrderType(oldDesktop.get().getDeskNo(), OrderType.OPEN);
        OrderEntity entity = orderEntity.get();
        entity.setDesktopEntity(newDesktop.get());
        repository.save(entity);

        //Update old desktop to avail for customer
        desktopService.updateDesktopOccupiedStatus(oldDesktop.get().getDeskNo(), false);
        //Update new desktop to be occupied
        desktopService.updateDesktopOccupiedStatus(newDesktop.get().getDeskNo(), true);
    }

    private boolean isTakeAwayOrder(int desktopID) {
        return desktopID == TAKE_AWAY_ORDER_DESKTOP_NUMBER;
    }

    public void refreshOrderPrice(OrderEntity entity) {
        Optional<BigDecimal> totalPrice = entity.getItems().stream()
                .filter(en -> en.getStatus() != OrderItemStatus.CANCELED)
                .map(en -> CalFunction.murtiplyByBigDecimal(en.getMenuItem().getMiPrice(), BigDecimal.valueOf(en.getCount()))).reduce(BigDecimal::add);
        entity.setTotalPrice(totalPrice.get());
        repository.save(entity);
    }
}
