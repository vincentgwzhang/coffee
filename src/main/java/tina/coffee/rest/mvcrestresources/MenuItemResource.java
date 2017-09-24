package tina.coffee.rest.mvcrestresources;

import com.google.common.collect.Sets;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.MenuItemService;
import tina.coffee.rest.dto.MenuItemDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/menuItem")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuItemResource {

    private MenuItemService service;

    public MenuItemResource(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> listItems() {
        List<MenuItemDTO> categories = service.listAllMenuItems();
        return new ResponseEntity<>(categories, OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<MenuItemDTO>> listItemsByCategory(@NotNull @PathVariable("id") Integer categoryId) {
        List<MenuItemDTO> categories = service.listAllMenuItemsByMenuCategory(categoryId);
        return new ResponseEntity<>(categories, OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MenuItemDTO> getItemById(@NotNull @PathVariable("id") Integer id) {
        MenuItemDTO dto = service.getMenuItemById(id);
        return new ResponseEntity<>(dto, OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteItemById(@NotNull @PathVariable("id") Integer id) {
        service.deleteMenuItemById(id);
        return new ResponseEntity(ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity deleteItemByIds(@NotNull @RequestParam("id") List<Integer> ids) {
        service.deleteMenuItemsByCollection(Sets.newHashSet(ids));
        return new ResponseEntity(ACCEPTED);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity deleteItemByCategoryId(@NotNull @PathVariable("id") Integer categoryId) {
        service.deleteMenuItemByCategoryId(categoryId);
        return new ResponseEntity(ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> addItem(@NotNull @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO dto = service.addItem(menuItemDTO);
        return new ResponseEntity<>(dto, OK);
    }

    @PutMapping
    public ResponseEntity<MenuItemDTO> updateItem(@NotNull @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO dto = service.updateItem(menuItemDTO);
        return new ResponseEntity<>(dto, OK);
    }

}
