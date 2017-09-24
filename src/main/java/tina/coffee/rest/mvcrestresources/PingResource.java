package tina.coffee.rest.mvcrestresources;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.rest.dto.PingDto;

import javax.ws.rs.Produces;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/ping")
@RestController
public class PingResource {

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)//if no such thing, then it will turn back xml format
    public ResponseEntity<PingDto> ping(){
        PingDto dto = new PingDto();
        dto.setKey("key");
        dto.setValue("value");

        return new ResponseEntity<>(dto, OK);
    }
}
