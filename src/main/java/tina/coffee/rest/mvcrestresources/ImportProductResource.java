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
import tina.coffee.business.ImportProductService;
import tina.coffee.rest.dto.ImportProductDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/import/product")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportProductResource {

    private ImportProductService service;

    public ImportProductResource(ImportProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ImportProductDTO>> findAll() {
        List<ImportProductDTO> importProductDTOS = service.findAll();
        return new ResponseEntity<>(importProductDTOS, OK);
    }

    @PostMapping
    public ResponseEntity<ImportProductDTO> addNewImportProduct(@NotNull @Valid @RequestBody ImportProductDTO productDTO) {
        ImportProductDTO importProductDTO = service.addImportProduct(productDTO);
        return new ResponseEntity<>(importProductDTO, OK);
    }

    @DeleteMapping("{importproductid}")
    public ResponseEntity deleteImportProduct(@NotNull @PathVariable("importproductid") Integer importProductId) {
        service.deleteImportProductById(importProductId);
        return new ResponseEntity(ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<ImportProductDTO> updateImportProduct(@NotNull @Valid @RequestBody ImportProductDTO productDTO) {
        ImportProductDTO importProductDTO = service.updateImportProduct(productDTO);
        return new ResponseEntity<>(importProductDTO, OK);
    }

}