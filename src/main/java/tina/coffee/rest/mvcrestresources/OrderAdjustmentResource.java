package tina.coffee.rest.mvcrestresources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.OrderAdjustmentOverviewService;
import tina.coffee.rest.dto.OrderAdjustmentOverviewDTO;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RequestMapping("/orderAdjustment")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderAdjustmentResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private OrderAdjustmentOverviewService orderAdjustmentOverviewService;

    public OrderAdjustmentResource(OrderAdjustmentOverviewService orderAdjustmentOverviewService) {
        this.orderAdjustmentOverviewService = orderAdjustmentOverviewService;
    }

    @PostMapping
    public ResponseEntity newOrderAdjustment(@NotNull @RequestBody OrderAdjustmentOverviewDTO overviewDTO) {
        orderAdjustmentOverviewService.newOrUptOrderAdjustment(overviewDTO);
        return new ResponseEntity(ACCEPTED);
    }
}
