package tina.coffee.rest.resources;

import com.google.common.collect.Sets;
import tina.coffee.business.DesktopService;
import tina.coffee.rest.dto.DesktopDTO;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/desktop")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DesktopResource {

    private DesktopService service;

    public DesktopResource(DesktopService service) {
        this.service = service;
    }

    @GET
    public Response findAllDesktop() {
        return Response.ok(service.findAllDesktop()).build();
    }

    @GET
    @Path("enable/{enable}")
    public Response findAllDesktopByEnable(@PathParam("enable") boolean isEnable) {
        return Response.ok(service.findAllDesktopByEnable(isEnable)).build();
    }

    @GET
    @Path("occupy/{occupy}")
    public Response findAllDesktopByOccupy(@PathParam("occupy") boolean isOccupied) {
        return Response.ok(service.findAllDesktopByOccupied(isOccupied)).build();
    }

    @PUT
    public Response updateDesktop(@NotNull DesktopDTO desktopDTO) {
        return Response.ok(service.updateDesktop(desktopDTO)).build();
    }

    @DELETE
    public Response deleteDesktopBySet(@QueryParam("number") Set<Integer> desktopNumbers) {
        service.deleteDesktop(desktopNumbers);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{deskNumber}")
    public Response deleteDesktopByDesktopNumber(@PathParam("deskNumber") Integer desktopNumber) {
        service.deleteDesktop(Sets.newHashSet(desktopNumber));
        return Response.accepted().build();
    }

    @POST
    public Response addDesktop(@NotNull DesktopDTO desktopDTO) {
        return Response.ok(service.addNewDesktop(desktopDTO)).build();
    }
}
