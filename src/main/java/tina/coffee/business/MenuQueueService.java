package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.data.model.MenuQueueEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.data.repository.MenuQueueRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.rest.dto.MenuQueueDTO;

import java.util.List;

/**
 * 这个类原本是给厨师看屏幕用的，但是暂时不需要了
 */
@Service
public class MenuQueueService {

    private final DozerMapper mapper;

    private final MenuQueueRepository repository;

    @Autowired
    public MenuQueueService(DozerMapper mapper,
                            MenuQueueRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<MenuQueueDTO> displayToChiefMonitor() {
        List<MenuQueueEntity> menuQueueEntities = repository.findAll();
        return mapper.map(menuQueueEntities, MenuQueueDTO.class);
    }

    @Transactional
    public void sendToChiefMonitor(OrderItemEntity orderItemEntity) {
        MenuQueueEntity entity = new MenuQueueEntity();
        entity.setOrderItemEntity(orderItemEntity);
        repository.save(entity);
    }

    @Transactional
    public void removeInChiefMonitor(OrderItemEntity orderItemEntity) {
        repository.deleteByOrderItemEntity(orderItemEntity);
    }

    @Transactional
    public void removeInChiefMonitor(Integer mqId) {
        repository.deleteById(mqId);
    }
}
