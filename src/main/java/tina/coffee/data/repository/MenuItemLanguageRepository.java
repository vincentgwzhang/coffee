package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.data.model.MenuItemLanguageEntity;

import java.util.List;

public interface MenuItemLanguageRepository extends JpaRepository<MenuItemLanguageEntity, Integer> {

    List<MenuItemLanguageEntity> findByMilDescriptionAndMenuItemEntityNot(String description, MenuItemEntity entity);

    List<MenuItemLanguageEntity> findByMilDescription(String description);

}
