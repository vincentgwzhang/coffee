package tina.coffee.rest.mvcrestresources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RequestMapping("/orderitem")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderItemResource {

    private OrderService orderService;
    private OrderItemService service;

    Logger logger = LoggerFactory.getLogger(getClass());

    public OrderItemResource(OrderService orderService, OrderItemService service) {
        this.service = service;
        this.orderService = orderService;
    }

    /**
     * 对于服务员，
     * 1) 可以为 order 新增一个 item
     * 2) 可以取消该 item
     * 3) 可以查看所有的item
     */
    @PostMapping("{desktopNumber}/{menuitemID}/{count}")
    public ResponseEntity orderNewItem(
            @NotNull @PathVariable("desktopNumber") Integer desktopNumber,
            @NotNull @PathVariable("menuitemID") Integer menuitemId,
            @NotNull @PathVariable("count") Integer count) {
        service.orderNewItem(desktopNumber, menuitemId, count);
        return new ResponseEntity(ACCEPTED);
    }

    /**
     * 对于处理外卖的单子，不需要有任何桌子号，能发过来的，代表他是直接给厨师的。只要发给厨师就好
     * @param menuitemId
     * @param count
     * @return
     */
    @PostMapping("takeaway/{menuitemID}/{count}")
    public ResponseEntity orderNewTakeAwayItem(@NotNull @PathVariable("menuitemID") Integer menuitemId,
                                               @NotNull @PathVariable("count") Integer count) {
        service.orderTakeAwayItem(menuitemId, count);
        return new ResponseEntity(ACCEPTED);
    }

    @DeleteMapping("{orderItemId}")
    public ResponseEntity cancelOrderItem(@NotNull @PathVariable("orderItemId") Integer orderItemId) {
        service.cancelOrderItem(orderItemId);
        return new ResponseEntity(ACCEPTED);
    }

    @PutMapping("{orderItemId}/{count}")
    public ResponseEntity updateOrderItem(@NotNull @PathVariable("orderItemId") Integer orderItemId,
                                    @NotNull @PathVariable("count") Integer count) {
        service.updateOrderItem(orderItemId, count);
        return new ResponseEntity(ACCEPTED);
    }

    @PutMapping("menuQueueId/{menuQueueId}/status/{status}")
    public ResponseEntity chiefUpdateOrderItemStatus(@NotNull @PathVariable("menuQueueId") Integer menuQueueId,
                                          @NotNull @PathVariable("status") String status) {
        service.chiefUpdateOrderItemStatus(menuQueueId, status);
        return new ResponseEntity(ACCEPTED);
    }
}
