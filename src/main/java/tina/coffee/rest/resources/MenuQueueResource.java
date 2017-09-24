package tina.coffee.rest.resources;

import tina.coffee.business.MenuQueueService;
import tina.coffee.rest.dto.MenuQueueDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/queue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuQueueResource {

    private MenuQueueService service;

    public MenuQueueResource(MenuQueueService service) {
        this.service = service;
    }

    /**
     * 对于厨师，
     * 1）可以查看当即的队列
     * 2) 可以更新状态为正在做
     * 3）可以更新状态为完成
     */
    @GET
    public Response getAllItemsInQueue() {
        List<MenuQueueDTO> menuQueueDTOList = service.displayToChiefMonitor();
        return Response.ok(menuQueueDTOList).build();
    }


}
