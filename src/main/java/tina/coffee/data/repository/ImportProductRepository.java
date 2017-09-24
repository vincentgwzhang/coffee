package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.ImportProductEntity;

import java.util.Optional;


public interface ImportProductRepository extends JpaRepository<ImportProductEntity, Integer> {

    Optional<ImportProductEntity> findByIpId(Integer ipId);

    void deleteByIpId(Integer ipId);

}