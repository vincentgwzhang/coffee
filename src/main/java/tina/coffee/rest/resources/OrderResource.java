package tina.coffee.rest.resources;

import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;
import tina.coffee.rest.dto.OrderDTO;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService service;
    private OrderItemService orderItemService;

    public OrderResource(OrderService service, OrderItemService orderItemService) {
        this.service = service;
        this.orderItemService = orderItemService;
    }

    @POST
    @Path("{desktopNumber}")
    public Response createNewOrder(@NotNull @PathParam("desktopNumber") Integer desktopNumber) {
        return Response.ok(service.createNewOrder(desktopNumber)).build();
    }

    @PUT
    @Path("{oldDesktopNumber}/{newDesktopNumber}")
    public Response customerMoveToNewTable(@NotNull @PathParam("oldDesktopNumber") Integer oldDesktopNumber,
                                           @NotNull @PathParam("newDesktopNumber") Integer newDesktopNumber){
        service.customerMoveToNewTable(oldDesktopNumber, newDesktopNumber);
        return Response.accepted().build();
    }

    @GET
    @Path("{desktopNumber}")
    public Response getOrder(@NotNull @PathParam("desktopNumber") Integer desktopNumber) {
        OrderDTO orderDTO = service.getOrderByDesktop(desktopNumber);
        return Response.ok(orderDTO).build();
    }

    @GET
    @Path("bydate")
    public Response getOrders(@NotNull @QueryParam("date") String date) {
        List<OrderDTO> orderDTOS = service.findOrdersByCalendarDay(date);
        return Response.ok(orderDTOS).build();
    }

    @GET
    @Path("bydaterange")
    public Response getOrders(@NotNull @QueryParam("startDate") String startDate,
                              @NotNull @QueryParam("startDate") String endDate) {
        List<OrderDTO> orderDTOS = service.findOrdersByCalendarDay(startDate, endDate);
        return Response.ok(orderDTOS).build();
    }

    @PUT
    @Path("lost/{desktopNumber}")
    public Response markLost(@NotNull @PathParam("desktopNumber") Integer desktopNumber) {
        service.markLost(desktopNumber);
        return Response.accepted().build();
    }

    /**
     * 注意：这个Endpoint 是不做close 的，也不算实际的钱的，因为在另外一个endpoint 进行计算
     */
    @PUT
    @Path("clear/{desktopNumber}")
    public Response clearOrder(@NotNull @PathParam("desktopNumber") Integer desktopNumber) {
        OrderDTO orderDTO = service.clearOrder(desktopNumber);
        return Response.ok(orderDTO).build();
    }

    /**
     * 注意： 这个endpoint 才是close 的
     */
    @PUT
    @Path("close/{desktopNumber}/actualPaid/{actualPaid}")
    public Response closeOrder(@NotNull @PathParam("desktopNumber") Integer desktopNumber,
                               @NotNull @PathParam("actualPaid")BigDecimal actualPaid) {
        service.closeOrder(desktopNumber, actualPaid);
        return Response.ok().build();
    }
}
