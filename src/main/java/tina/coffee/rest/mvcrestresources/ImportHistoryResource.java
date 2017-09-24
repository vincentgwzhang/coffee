package tina.coffee.rest.mvcrestresources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.ImportHistoryService;
import tina.coffee.business.ImportHistorySummaryService;
import tina.coffee.rest.dto.ImportHistoryDTO;
import tina.coffee.rest.dto.ImportHistorySummaryDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/import/history")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportHistoryResource {

    private ImportHistoryService service;
    private ImportHistorySummaryService summaryService;

    public ImportHistoryResource(ImportHistoryService service, ImportHistorySummaryService summaryService) {
        this.service = service;
        this.summaryService = summaryService;
    }

    @GetMapping("summary")
    public ResponseEntity<List<ImportHistorySummaryDTO>> findAllHistory() {
        List<ImportHistorySummaryDTO> importHistorySummaryDTOS = summaryService.findAll();
        return new ResponseEntity<>(importHistorySummaryDTOS, OK);
    }

    @GetMapping("summary/{importHistorySummaryId}")
    public ResponseEntity<ImportHistorySummaryDTO> getImportHistorySummaryById(@NotNull @PathVariable("importHistorySummaryId") Integer importHistorySummaryId) {
        ImportHistorySummaryDTO dto = summaryService.getHistorySummaryByID(importHistorySummaryId);
        return new ResponseEntity<>(dto, OK);
    }

    @PutMapping("summary")
    public ResponseEntity<ImportHistorySummaryDTO> updateImportHistory(@NotNull @Valid @RequestBody ImportHistorySummaryDTO importHistorySummaryDTO) {
        ImportHistorySummaryDTO dto = summaryService.updateImportHistorySummary(importHistorySummaryDTO);
        return new ResponseEntity<>(dto, OK);
    }

    @DeleteMapping("summary/{importHistorySummaryId}")
    public ResponseEntity deleteImportHistorySummaryById(@NotNull @PathVariable("importHistorySummaryId") Integer importHistorySummaryId) {
        summaryService.deleteHistorySummary(importHistorySummaryId);
        return new ResponseEntity<>(ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ImportHistoryDTO>> findAll() {
        List<ImportHistoryDTO> importHistoryDTOS = service.findAll();
        return new ResponseEntity<>(importHistoryDTOS, OK);
    }

    @GetMapping("bysummary/{importHistorySummaryId}")
    public ResponseEntity<List<ImportHistoryDTO>> findAllImportHistoryBySummaryId(@NotNull @PathVariable("importHistorySummaryId") Integer importHistorySummaryId) {
        List<ImportHistoryDTO> importHistoryDTOS = service.findAllBySummaryId(importHistorySummaryId);
        return new ResponseEntity<>(importHistoryDTOS, OK);
    }

    /**
     * This is more or less the same as deleteImportHistorySummaryById
     * the only difference is it would not delete the history summary entry
     */
    @DeleteMapping("bysummary/{importHistorySummaryId}")
    public ResponseEntity deleteAllImportHistoryBySummaryId(@NotNull @PathVariable("importHistorySummaryId") Integer importHistorySummaryId) {
        service.deleteAllBySummaryId(importHistorySummaryId);
        return new ResponseEntity<>(ACCEPTED);
    }

    @GetMapping("{importHistoryId}")
    public ResponseEntity<ImportHistoryDTO> getImportHistoryById(@NotNull @PathVariable("importHistoryId") Integer importHistoryId) {
        ImportHistoryDTO dto = service.getImportHistoryById(importHistoryId);
        return new ResponseEntity<>(dto, OK);
    }

    @PostMapping("date/{date}")
    public ResponseEntity<ImportHistoryDTO> addImportHistory(@NotNull @PathVariable("date") String dateString, @NotNull @Valid @RequestBody ImportHistoryDTO importHistoryDTO) {
        ImportHistoryDTO dto = service.addImportHistoryDTO(dateString, importHistoryDTO);
        return new ResponseEntity<>(dto, OK);
    }

    @PutMapping
    public ResponseEntity<ImportHistoryDTO> updateImportHistory(@NotNull @Valid @RequestBody ImportHistoryDTO importHistoryDTO) {
        ImportHistoryDTO dto = service.updateImportHistory(importHistoryDTO);
        return new ResponseEntity<>(dto, OK);
    }

    @DeleteMapping("{importHistoryId}")
    public ResponseEntity deleteImportHistoryById(@NotNull @PathVariable("importHistoryId") Integer importHistoryId) {
        service.deleteImportHistoryById(importHistoryId);
        return new ResponseEntity<>(ACCEPTED);
    }

}