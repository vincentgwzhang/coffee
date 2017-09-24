package tina.coffee.rest.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orderitem")
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
    @POST
    @Path("{desktopNumber}/{menuitemID}/{count}")
    public Response orderNewItem(
            @NotNull @PathParam("desktopNumber") Integer desktopNumber,
            @NotNull @PathParam("menuitemID") Integer menuitemId,
            @NotNull @PathParam("count") Integer count) {
        service.orderNewItem(desktopNumber, menuitemId, count);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{orderItemId}")
    public Response cancelOrderItem(@NotNull @PathParam("orderItemId") Integer orderItemId) {
        service.cancelOrderItem(orderItemId);
        return Response.accepted().build();
    }

    @PUT
    @Path("{orderItemId}/{count}")
    public Response updateOrderItem(@NotNull @PathParam("orderItemId") Integer orderItemId,
                                    @NotNull @PathParam("count") Integer count) {
        service.updateOrderItem(orderItemId, count);
        return Response.accepted().build();
    }

    @PUT
    @Path("menuQueueId/{menuQueueId}/status/{status}")
    public Response chiefUpdateOrderItemStatus(@NotNull @PathParam("menuQueueId") Integer menuQueueId,
                                          @NotNull @PathParam("status") String status) {
        service.chiefUpdateOrderItemStatus(menuQueueId, status);
        return Response.accepted().build();
    }
}
