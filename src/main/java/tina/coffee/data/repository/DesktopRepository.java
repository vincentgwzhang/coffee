package tina.coffee.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tina.coffee.data.model.DesktopEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface DesktopRepository extends JpaRepository<DesktopEntity, Integer> {

    List<DesktopEntity> findAllByDeskEnable(boolean isEnable);

    List<DesktopEntity> findAllByDeskOccupied(boolean isOccupied);

    List<DesktopEntity> findAllByDeskEnableAndDeskOccupied(boolean isEnable, boolean isOccupied);

    Optional<DesktopEntity> findByDeskNo(int number);

    void deleteByDeskNoIn(Collection<Integer> deskNumbers);

}
