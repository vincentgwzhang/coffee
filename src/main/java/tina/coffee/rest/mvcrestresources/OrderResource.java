package tina.coffee.rest.mvcrestresources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;
import tina.coffee.rest.dto.OrderDTO;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.ACCEPTED;

@RequestMapping("/order")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService service;
    private OrderItemService orderItemService;

    public OrderResource(OrderService service, OrderItemService orderItemService) {
        this.service = service;
        this.orderItemService = orderItemService;
    }

    @PostMapping("{desktopNumber}")
    public ResponseEntity<OrderDTO> createNewOrder(@NotNull @PathVariable("desktopNumber") Integer desktopNumber) {
        OrderDTO dto = service.createNewOrder(desktopNumber);
        return new ResponseEntity<>(dto, OK);
    }

    @PutMapping("{oldDesktopNumber}/{newDesktopNumber}")
    public ResponseEntity customerMoveToNewTable(@NotNull @PathVariable("oldDesktopNumber") Integer oldDesktopNumber,
                                           @NotNull @PathVariable("newDesktopNumber") Integer newDesktopNumber){
        service.customerMoveToNewTable(oldDesktopNumber, newDesktopNumber);
        return new ResponseEntity(ACCEPTED);
    }

    @GetMapping("{desktopNumber}")
    public ResponseEntity<OrderDTO> getOrder(@NotNull @PathVariable("desktopNumber") Integer desktopNumber) {
        OrderDTO orderDTO = service.getOrderByDesktop(desktopNumber);
        return new ResponseEntity<>(orderDTO, OK);
    }

    @GetMapping("bydate")
    public ResponseEntity<List<OrderDTO>> getOrders(@NotNull @RequestParam("date") String date) {
        List<OrderDTO> orderDTOS = service.findOrdersByCalendarDay(date);
        return new ResponseEntity<>(orderDTOS, OK);
    }

    @GetMapping("bydaterange")
    public ResponseEntity<List<OrderDTO>> getOrders(@NotNull @RequestParam("startDate") String startDate,
                              @NotNull @RequestParam("startDate") String endDate) {
        List<OrderDTO> orderDTOS = service.findOrdersByCalendarDay(startDate, endDate);
        return new ResponseEntity<>(orderDTOS, OK);
    }

    @PutMapping("lost/{desktopNumber}")
    public ResponseEntity markLost(@NotNull @PathVariable("desktopNumber") Integer desktopNumber) {
        service.markLost(desktopNumber);
        return new ResponseEntity(ACCEPTED);
    }

    /**
     * 注意：这个Endpoint 是不做close 的，也不算实际的钱的，因为在另外一个endpoint 进行计算
     */
    @PutMapping("clear/{desktopNumber}")
    public ResponseEntity<OrderDTO> clearOrder(@NotNull @PathVariable("desktopNumber") Integer desktopNumber) {
        OrderDTO orderDTO = service.clearOrder(desktopNumber);
        return new ResponseEntity(ACCEPTED);
    }

    /**
     * 注意： 这个endpoint 才是close 的
     */
    @PutMapping("close/{desktopNumber}/actualPaid/{actualPaid:.+}")
    public ResponseEntity closeOrder(@NotNull @PathVariable("desktopNumber") Integer desktopNumber,
                               @NotNull @PathVariable("actualPaid")BigDecimal actualPaid) {
        service.closeOrder(desktopNumber, actualPaid);
        return new ResponseEntity(ACCEPTED);
    }
}
