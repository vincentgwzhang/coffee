package tina.coffee.system.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.system.security.data.model.CoffeeRoleEntity;

public interface CoffeeRoleRepository extends JpaRepository<CoffeeRoleEntity, Integer> {

    CoffeeRoleEntity findByName(String name);

}
