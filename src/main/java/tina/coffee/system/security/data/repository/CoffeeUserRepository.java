package tina.coffee.system.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.system.security.data.model.CoffeeUserEntity;

import java.util.Optional;

public interface CoffeeUserRepository extends JpaRepository<CoffeeUserEntity, Integer> {

    Optional<CoffeeUserEntity> findByUsername(String username);

    void removeCoffeeUserEntitiesByUsername(String username);
}
