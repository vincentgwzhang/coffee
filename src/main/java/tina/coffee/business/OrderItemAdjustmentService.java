package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tina.coffee.data.repository.OrderItemAdjustmentRepository;
import tina.coffee.dozer.DozerMapper;

@Service
public class OrderItemAdjustmentService {

    private final DozerMapper mapper;

    private final OrderItemAdjustmentRepository repository;

    @Autowired
    public OrderItemAdjustmentService(DozerMapper mapper,
                                      OrderItemAdjustmentRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }
}
