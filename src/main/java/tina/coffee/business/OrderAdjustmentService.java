package tina.coffee.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tina.coffee.data.repository.OrderAdjustmentRepository;
import tina.coffee.dozer.DozerMapper;

@Service
public class OrderAdjustmentService {

    private final DozerMapper mapper;

    private final OrderAdjustmentRepository repository;

    @Autowired
    public OrderAdjustmentService(DozerMapper mapper,
                                  OrderAdjustmentRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }
}
