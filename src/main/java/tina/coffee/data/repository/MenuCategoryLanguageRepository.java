package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.MenuCategoryEntity;
import tina.coffee.data.model.MenuCategoryLanguageEntity;

import java.util.List;

public interface MenuCategoryLanguageRepository extends JpaRepository<MenuCategoryLanguageEntity, Integer> {

    List<MenuCategoryLanguageEntity> findByMclDescriptionAndMenuCategoryEntityNot(String description, MenuCategoryEntity entity);

    List<MenuCategoryLanguageEntity> findByMclDescription(String description);

}
