package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.MenuCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategoryEntity, Integer> {
    List<MenuCategoryEntity> findByMcEnable(boolean isEnabled);

    Optional<MenuCategoryEntity> findByMcId(Integer id);

}
