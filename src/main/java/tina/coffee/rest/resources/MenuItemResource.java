package tina.coffee.rest.resources;

import com.google.common.collect.Sets;
import org.springframework.web.bind.annotation.RequestBody;
import tina.coffee.business.MenuItemService;
import tina.coffee.rest.dto.MenuItemDTO;

import javax.validation.Valid;
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
import java.util.List;
import java.util.Objects;

@Path("/menuItem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuItemResource {

    private MenuItemService service;

    public MenuItemResource(MenuItemService service) {
        this.service = service;
    }

    @GET
    public Response listItems() {
        List<MenuItemDTO> categories = service.listAllMenuItems();
        return Response.ok(categories).build();
    }

    @GET
    @Path("/category/{id}")
    public Response listItemsByCategory(@NotNull @PathParam("id") Integer categoryId) {
        List<MenuItemDTO> categories = service.listAllMenuItemsByMenuCategory(categoryId);
        return Response.ok(categories).build();
    }

    @GET
    @Path("{id}")
    public Response getItemById(@NotNull @PathParam("id") Integer id) {
        MenuItemDTO dto = service.getMenuItemById(id);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteItemById(@NotNull @PathParam("id") Integer id) {
        service.deleteMenuItemById(id);
        return Response.accepted().build();
    }

    @DELETE
    public Response deleteItemByIds(@NotNull @QueryParam("id") List<Integer> ids) {
        service.deleteMenuItemsByCollection(Sets.newHashSet(ids));
        return Response.accepted().build();
    }

    @DELETE
    @Path("/category/{id}")
    public Response deleteItemByCategoryId(@NotNull @PathParam("id") Integer categoryId) {
        service.deleteMenuItemByCategoryId(categoryId);
        return Response.accepted().build();
    }

    @POST
    public Response addItem(@NotNull @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO dto = service.addItem(menuItemDTO);
        if(Objects.nonNull(dto)) {
            return Response.ok(dto).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response updateItem(@NotNull @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO dto = service.updateItem(menuItemDTO);
        if(Objects.nonNull(dto)) {
            return Response.ok(dto).build();
        } else {
            return Response.serverError().build();
        }
    }


}
