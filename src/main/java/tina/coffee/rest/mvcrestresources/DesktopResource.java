package tina.coffee.rest.mvcrestresources;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.DesktopService;
import tina.coffee.rest.dto.DesktopDTO;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/desktop")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DesktopResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private DesktopService service;

    public DesktopResource(DesktopService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DesktopDTO>> findAllDesktop() {
        logger.debug("{} function triggered", "tina.coffee.rest.mvcrestresources.DesktopResource.findAllDesktop");
        return new ResponseEntity<>(service.findAllDesktop(), OK);
    }

    @GetMapping("nontakeaway")
    public ResponseEntity<List<DesktopDTO>> findAllDesktopAndNotTakeAway() {
        logger.debug("{} function triggered", "tina.coffee.rest.mvcrestresources.DesktopResource.findAllDesktopAndNotTakeAway");
        return new ResponseEntity<>(service.findAllOnSiteDesktop(), OK);
    }

    @GetMapping("availDesks")
    public ResponseEntity<List<DesktopDTO>> findAllAvailDesktops() {
        List<DesktopDTO> dtos = service.findAllAvailableDesktops();
        return new ResponseEntity<>(dtos, OK);
    }

    @GetMapping("enable/{enable}")
    public ResponseEntity<List<DesktopDTO>> findAllDesktopByEnable(@PathVariable("enable") boolean isEnable) {
        List<DesktopDTO> dtos = service.findAllDesktopByEnable(isEnable);
        return new ResponseEntity<>(dtos, OK);
    }

    @GetMapping("occupy/{occupy}")
    public ResponseEntity<List<DesktopDTO>> findAllDesktopByOccupy(@PathVariable("occupy") boolean isOccupied) {
        List<DesktopDTO> dtos = service.findAllDesktopByOccupied(isOccupied);
        return new ResponseEntity<>(dtos, OK);
    }

    @PutMapping
    public ResponseEntity<DesktopDTO> updateDesktop(@NotNull @RequestBody DesktopDTO desktopDTO) {
        logger.debug("{} function triggered", "tina.coffee.rest.mvcrestresources.DesktopResource.updateDesktop");
        logger.debug("parameter :{}", desktopDTO.toString());
        DesktopDTO dto = service.updateDesktop(desktopDTO);
        return new ResponseEntity<>(dto, OK);
    }

    @DeleteMapping
    public ResponseEntity deleteDesktopBySet(@RequestParam("number") Set<Integer> desktopNumbers) {
        service.deleteDesktop(desktopNumbers);
        return new ResponseEntity(ACCEPTED);
    }

    @DeleteMapping("{deskNumber}")
    public ResponseEntity deleteDesktopByDesktopNumber(@PathVariable("deskNumber") Integer desktopNumber) {
        logger.debug("{} function triggered", "tina.coffee.rest.mvcrestresources.DesktopResource.deleteDesktopByDesktopNumber");
        service.deleteDesktop(Sets.newHashSet(desktopNumber));
        return new ResponseEntity(ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<DesktopDTO> addDesktop(@NotNull @RequestBody DesktopDTO desktopDTO) {
        logger.debug("{} function triggered", "tina.coffee.rest.mvcrestresources.DesktopResource.addDesktop");
        logger.debug("Parameters: {}", desktopDTO.toString());
        DesktopDTO dto = service.addNewDesktop(desktopDTO);
        return new ResponseEntity<>(dto, OK);
    }
}
