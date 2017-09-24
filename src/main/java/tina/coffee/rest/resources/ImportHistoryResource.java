package tina.coffee.rest.resources;

import org.springframework.web.bind.annotation.RequestBody;
import tina.coffee.business.ImportHistoryService;
import tina.coffee.business.ImportHistorySummaryService;
import tina.coffee.rest.dto.ImportHistoryDTO;
import tina.coffee.rest.dto.ImportHistorySummaryDTO;

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

@Path("/import/history")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportHistoryResource {

    private ImportHistoryService service;
    private ImportHistorySummaryService summaryService;

    public ImportHistoryResource(ImportHistoryService service, ImportHistorySummaryService summaryService) {
        this.service = service;
        this.summaryService = summaryService;
    }

    @GET
    @Path("summary")
    public Response findAllHistory() {
        List<ImportHistorySummaryDTO> importHistorySummaryDTOS = summaryService.findAll();
        return Response.ok(importHistorySummaryDTOS).build();
    }

    @GET
    @Path("summary/{importHistorySummaryId}")
    public Response getImportHistorySummaryById(@NotNull @PathParam("importHistorySummaryId") Integer importHistorySummaryId) {
        ImportHistorySummaryDTO dto = summaryService.getHistorySummaryByID(importHistorySummaryId);
        return Response.ok(dto).build();
    }

    @PUT
    @Path("summary")
    public Response updateImportHistory(@NotNull @Valid @RequestBody ImportHistorySummaryDTO importHistorySummaryDTO) {
        ImportHistorySummaryDTO dto = summaryService.updateImportHistorySummary(importHistorySummaryDTO);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("summary/{importHistorySummaryId}")
    public Response deleteImportHistorySummaryById(@NotNull @PathParam("importHistorySummaryId") Integer importHistorySummaryId) {
        summaryService.deleteHistorySummary(importHistorySummaryId);
        return Response.accepted().build();
    }

    @GET
    public Response findAll() {
        List<ImportHistoryDTO> importHistoryDTOS = service.findAll();
        return Response.ok(importHistoryDTOS).build();
    }

    @GET
    @Path("bysummary/{importHistorySummaryId}")
    public Response findAllImportHistoryBySummaryId(@NotNull @PathParam("importHistorySummaryId") Integer importHistorySummaryId) {
        List<ImportHistoryDTO> importHistoryDTOS = service.findAllBySummaryId(importHistorySummaryId);
        return Response.ok(importHistoryDTOS).build();
    }

    /**
     * This is more or less the same as deleteImportHistorySummaryById
     * the only difference is it would not delete the history summary entry
     */
    @DELETE
    @Path("bysummary/{importHistorySummaryId}")
    public Response deleteAllImportHistoryBySummaryId(@NotNull @PathParam("importHistorySummaryId") Integer importHistorySummaryId) {
        service.deleteAllBySummaryId(importHistorySummaryId);
        return Response.accepted().build();
    }

    @GET
    @Path("{importHistoryId}")
    public Response getImportHistoryById(@NotNull @PathParam("importHistoryId") Integer importHistoryId) {
        ImportHistoryDTO dto = service.getImportHistoryById(importHistoryId);
        return Response.ok(dto).build();
    }

    @POST
    @Path("date/{date}")
    public Response addImportHistory(@NotNull @PathParam("date") String dateString, @NotNull @Valid @RequestBody ImportHistoryDTO importHistoryDTO) {
        ImportHistoryDTO dto = service.addImportHistoryDTO(dateString, importHistoryDTO);
        return Response.ok(dto).build();
    }

    @PUT
    public Response updateImportHistory(@NotNull @Valid @RequestBody ImportHistoryDTO importHistoryDTO) {
        ImportHistoryDTO dto = service.updateImportHistory(importHistoryDTO);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("{importHistoryId}")
    public Response deleteImportHistoryById(@NotNull @PathParam("importHistoryId") Integer importHistoryId) {
        service.deleteImportHistoryById(importHistoryId);
        return Response.accepted().build();
    }



}