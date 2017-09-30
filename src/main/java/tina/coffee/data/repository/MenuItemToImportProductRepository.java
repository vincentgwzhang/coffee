package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.model.MenuItemToImportProductEntity;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vincent Zhang
 * Created on 2017/9/30 - 1:44
 * Create this class only for study
 * Source from:
 */
public interface MenuItemToImportProductRepository extends JpaRepository<MenuItemToImportProductEntity, Integer> {

    List<MenuItemToImportProductEntity> findByMenuItemEntity(MenuItemEntity menuItemEntity);

    List<MenuItemToImportProductEntity> findByImportProductEntity(ImportProductEntity importProductEntity);

    Optional<MenuItemToImportProductEntity> findById(Integer id);

}
