package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.data.model.OrderAdjustmentOverviewEntity;
import tina.coffee.data.repository.OrderAdjustmentOverviewRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.function.CalFunction;
import tina.coffee.rest.dto.OrderAdjustmentOverviewDTO;
import tina.coffee.rest.dto.OrderAdjustmentOverviewWrapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderAdjustmentOverviewService {

    private final DozerMapper mapper;

    private final OrderAdjustmentOverviewRepository repository;

    @Autowired
    public OrderAdjustmentOverviewService(DozerMapper mapper, OrderAdjustmentOverviewRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public OrderAdjustmentOverviewWrapper getOrderAdjustmentOverviewListInSpecificMonth(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        Date monthStartDate = new Date(calendar.getTimeInMillis());

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date monthEndDate   = new Date(calendar.getTimeInMillis());

        List<OrderAdjustmentOverviewEntity> orderAdjustmentOverviewEntities = repository.findByAdjustDateBetween(monthStartDate, monthEndDate);
        List<OrderAdjustmentOverviewDTO> orderAdjustmentOverviewDTOList = mapper.map(orderAdjustmentOverviewEntities, OrderAdjustmentOverviewDTO.class);
        return new OrderAdjustmentOverviewWrapper(calendar.getActualMaximum(Calendar.DATE), orderAdjustmentOverviewDTOList);
    }

    @Transactional
    public void newOrUptOrderAdjustment(OrderAdjustmentOverviewDTO overviewDTO) {

        OrderAdjustmentOverviewEntity entity;
        Date retrieveDate = new Date(overviewDTO.getAdjustDate().getTime());
        Optional<OrderAdjustmentOverviewEntity> entityOptional = repository.findByAdjustDate(retrieveDate);

        if(entityOptional.isPresent()) {
            entity = entityOptional.get();
            entity.setAdjTotal(CalFunction.stringToBigDecimal(overviewDTO.getAdjTotal()));
            entity.setOriTotal(CalFunction.stringToBigDecimal(overviewDTO.getOriTotal()));
        } else {
            entity = mapper.map(overviewDTO, OrderAdjustmentOverviewEntity.class);
        }

        repository.save(entity);
    }
}
