package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.ImportProductCountEntity;
import tina.coffee.data.model.ImportProductEntity;

import java.util.Optional;


public interface ImportProductCountRepository extends JpaRepository<ImportProductCountEntity, Integer> {

    Optional<ImportProductCountEntity> findByImportProduct(ImportProductEntity importProduct);

}