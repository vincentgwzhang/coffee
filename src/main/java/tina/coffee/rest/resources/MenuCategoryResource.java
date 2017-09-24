package tina.coffee.rest.resources;

import org.springframework.web.bind.annotation.RequestBody;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.rest.dto.MenuCategoryDTO;

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
import java.util.Set;

@Path("/menuCategory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuCategoryResource {

    private MenuCategoryService service;

    public MenuCategoryResource(MenuCategoryService service) {
        this.service = service;
    }

    @GET
    public Response listCategories() {
        List<MenuCategoryDTO> categories = service.listAllCategories();
        return Response.ok(categories).build();
    }

    @GET
    @Path("enabled")
    public Response listEnabledCategories() {
        List<MenuCategoryDTO> categories = service.listEnabledCategories();
        return Response.ok(categories).build();
    }

    @GET
    @Path("{id}")
    public Response getCategoryById(@NotNull @PathParam("id") Integer id) {
        MenuCategoryDTO dto = service.getMenuCategoryById(id);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCategoryById(@NotNull @PathParam("id") Integer id) {
        service.deleteMenuCategoryById(id);
        return Response.accepted().build();
    }

    @DELETE
    public Response deleteCategoryByCollection(@NotNull @QueryParam("id") Set<Integer> ids) {
        service.deleteMenuCategoryByCollection(ids);
        return Response.accepted().build();
    }

    @POST
    public Response addCategory(@NotNull @Valid @RequestBody MenuCategoryDTO menuCategoryDTO) {
        MenuCategoryDTO dto = service.addCategory(menuCategoryDTO);
        if(Objects.nonNull(dto)) {
            return Response.ok(dto).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response updateCategory(@NotNull @Valid @RequestBody MenuCategoryDTO menuCategoryDTO) {
        MenuCategoryDTO dto = service.updateCategory(menuCategoryDTO);
        if(Objects.nonNull(dto)) {
            return Response.ok(dto).build();
        } else {
            return Response.serverError().build();
        }
    }


}
