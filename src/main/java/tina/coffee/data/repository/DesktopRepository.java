package tina.coffee.data.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.DesktopEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "desktopCache")
public interface DesktopRepository extends JpaRepository<DesktopEntity, Integer> {

    @Cacheable
    List<DesktopEntity> findAllByDeskEnable(boolean isEnable);

    @Cacheable
    List<DesktopEntity> findAllByDeskOccupied(boolean isOccupied);

    @Cacheable
    List<DesktopEntity> findAllByDeskEnableAndDeskOccupied(boolean isEnable, boolean isOccupied);

    @Cacheable
    Optional<DesktopEntity> findByDeskNo(int number);

    void deleteByDeskNoIn(Collection<Integer> deskNumbers);

}
