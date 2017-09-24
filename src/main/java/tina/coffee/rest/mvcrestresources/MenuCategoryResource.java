package tina.coffee.rest.mvcrestresources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.rest.dto.MenuCategoryDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/menuCategory")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuCategoryResource {

    private MenuCategoryService service;

    public MenuCategoryResource(MenuCategoryService service) {
        this.service = service;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<MenuCategoryDTO>> listCategories() {
        List<MenuCategoryDTO> categories = service.listAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("enabled")
    public ResponseEntity<List<MenuCategoryDTO>> listEnabledCategories() {
        List<MenuCategoryDTO> categories = service.listEnabledCategories();
        return new ResponseEntity<>(categories, OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MenuCategoryDTO> getCategoryById(@NotNull @PathVariable("id") Integer id) {
        MenuCategoryDTO dto = service.getMenuCategoryById(id);
        return new ResponseEntity<>(dto, OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCategoryById(@NotNull @PathVariable("id") Integer id) {
        service.deleteMenuCategoryById(id);
        return new ResponseEntity(ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity deleteCategoryByCollection(@NotNull @RequestParam("id") Set<Integer> ids) {
        service.deleteMenuCategoryByCollection(ids);
        return new ResponseEntity(ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<MenuCategoryDTO> addCategory(@NotNull @Valid @RequestBody MenuCategoryDTO menuCategoryDTO) {
        MenuCategoryDTO dto = service.addCategory(menuCategoryDTO);
        return new ResponseEntity<>(dto, OK);
    }

    @PutMapping
    public ResponseEntity<MenuCategoryDTO> updateCategory(@NotNull @Valid @RequestBody MenuCategoryDTO menuCategoryDTO) {
        MenuCategoryDTO dto = service.updateCategory(menuCategoryDTO);
        return new ResponseEntity<>(dto, OK);
    }
}
