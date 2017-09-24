package tina.coffee.rest.resources;

import org.springframework.web.bind.annotation.RequestBody;
import tina.coffee.business.ImportProductService;
import tina.coffee.rest.dto.ImportProductDTO;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/import/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportProductResource {

    private ImportProductService service;

    public ImportProductResource(ImportProductService service) {
        this.service = service;
    }

    @GET
    public Response findAll() {
        List<ImportProductDTO> importProductDTOS = service.findAll();
        return Response.ok(importProductDTOS).build();
    }

    @POST
    public Response addNewImportProduct(@NotNull @Valid @RequestBody ImportProductDTO productDTO) {
        ImportProductDTO importProductDTO = service.addImportProduct(productDTO);
        return Response.ok(importProductDTO).build();
    }

    @DELETE
    @Path("{importproductid}")
    public Response deleteImportProduct(@NotNull @PathParam("importproductid") Integer importProductId) {
        service.deleteImportProductById(importProductId);
        return Response.accepted().build();
    }

    @PUT
    public Response updateImportProduct(@NotNull @Valid @RequestBody ImportProductDTO productDTO) {
        ImportProductDTO importProductDTO = service.updateImportProduct(productDTO);
        return Response.ok(importProductDTO).build();
    }

}