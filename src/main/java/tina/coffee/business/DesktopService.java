package tina.coffee.business;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tina.coffee.Verifier.DesktopVerifier;
import tina.coffee.data.model.DesktopEntity;
import tina.coffee.data.repository.DesktopRepository;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.rest.dto.DesktopDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DesktopService {

    private final DesktopRepository repository;

    private final DozerMapper mapper;

    private final Counter counter;

    @Autowired
    public DesktopService(DesktopRepository repository, DozerMapper mapper, @Qualifier("findAllOnSiteDesktopCounter") Counter counter) {
        this.repository = repository;
        this.mapper = mapper;
        this.counter = counter;
    }

    @Transactional(readOnly = true)
    public List<DesktopDTO> findAllDesktop() {
        List<DesktopEntity> entities = repository.findAll();
        return mapper.map(entities, DesktopDTO.class);
    }

    /**
     * 非外卖的
     */
    @Transactional(readOnly = true)
    public List<DesktopDTO> findAllOnSiteDesktop() {
        counter.inc();
        List<DesktopEntity> entities = repository.findAll();
        entities = entities.stream().filter(entity -> entity.getDeskNo() > 0).collect(Collectors.toList());
        return mapper.map(entities, DesktopDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DesktopDTO> findAllDesktopByEnable(boolean isEnable) {
        List<DesktopEntity> entities = repository.findAllByDeskEnable(isEnable);
        return mapper.map(entities, DesktopDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DesktopDTO> findAllAvailableDesktops() {
        List<DesktopEntity> entities = repository.findAllByDeskEnableAndDeskOccupied(true, false);
        entities = entities.stream().filter(entity -> entity.getDeskNo() > 0).collect(Collectors.toList());
        return mapper.map(entities, DesktopDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DesktopDTO> findAllDesktopByOccupied(boolean isOccupied) {
        List<DesktopEntity> entities = repository.findAllByDeskOccupied(isOccupied);
        return mapper.map(entities, DesktopDTO.class);
    }

    @Transactional
    public DesktopDTO addNewDesktop(final DesktopDTO desktopDTO) {
        Optional<DesktopEntity> desktopEntity = repository.findByDeskNo(desktopDTO.getDeskNo());
        DesktopVerifier.verifyIfDesktopExistAndThrow(desktopEntity);
        DesktopEntity entity = mapper.map(desktopDTO, DesktopEntity.class);
        entity = repository.save(entity);
        return mapper.map(entity, DesktopDTO.class);
    }

    @Transactional
    public DesktopDTO updateDesktop(final DesktopDTO desktopDTO) {
        Optional<DesktopEntity> desktopEntity = repository.findByDeskNo(desktopDTO.getDeskNo());
        DesktopVerifier.verifyIfDesktopNotExistAndThrow(desktopEntity, desktopDTO.getDeskNo());
        DesktopEntity entity = mapper.map(desktopDTO, DesktopEntity.class);
        entity = repository.save(entity);
        return mapper.map(entity, DesktopDTO.class);
    }

    @Transactional
    public void deleteDesktop(Set<Integer> desktopNumbers) {
        repository.deleteByDeskNoIn(desktopNumbers);
    }

    @Transactional
    public void updateDesktopOccupiedStatus(Integer desktopNumber, boolean isOccupied) {
        Optional<DesktopEntity> desktopEntity = repository.findByDeskNo(desktopNumber);
        DesktopVerifier.verifyIfDesktopNotExistAndThrow(desktopEntity, desktopNumber);
        if(desktopEntity.isPresent()) {
            DesktopEntity entity = desktopEntity.get();
            entity.setDeskOccupied(isOccupied);
            repository.save(entity);
        }
    }

}
