package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.MenuCategoryEntity;
import tina.coffee.data.model.MenuItemEntity;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {

    List<MenuItemEntity> getMenuItemEntitiesByMenuCategoryEntity(MenuCategoryEntity entity);
    List<MenuItemEntity> getMenuItemEntitiesByMenuCategoryEntityMcId(int mcId);

    void deleteByMenuCategoryEntity(MenuCategoryEntity entity);

    Optional<MenuItemEntity> findByMiId(int id);

}
