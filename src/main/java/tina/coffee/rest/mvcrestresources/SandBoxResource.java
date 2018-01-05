package tina.coffee.rest.mvcrestresources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.rest.dto.PingDto;
import tina.coffee.system.monitoring.selfmetrics.CustomMetrics;

import javax.management.AttributeNotFoundException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Produces;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/sanbox")
@RestController
public class SandBoxResource {

    @Autowired
    private CustomMetrics customMetrics;

    @GetMapping("cutomMetrics1/{metrics:.+}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PingDto>> customeMetrics(@NotNull @PathVariable("metrics") Double metrics) throws AttributeNotFoundException {
        String KEY_HITS    = "sandbox.customMetrics" + ".hits";
        String KEY_AVERAGE = "sandbox.customMetrics" + ".average";
        String KEY_MIN     = "sandbox.customMetrics" + ".min";
        String KEY_MAX     = "sandbox.customMetrics" + ".max";
        String KEY_SUM     = "sandbox.customMetrics" + ".sum";
        String KEY_LAST    = "sandbox.customMetrics" + ".last";

        customMetrics.addValue("sandbox.customMetrics", metrics);

        List<PingDto> pingDtoList = new ArrayList<>();
        PingDto dto;

        dto = new PingDto();
        dto.setKey(KEY_HITS);
        dto.setValue(customMetrics.getAttribute(KEY_HITS).toString());
        pingDtoList.add(dto);

        dto = new PingDto();
        dto.setKey(KEY_AVERAGE);
        dto.setValue(customMetrics.getAttribute(KEY_AVERAGE).toString());
        pingDtoList.add(dto);

        dto = new PingDto();
        dto.setKey(KEY_MIN);
        dto.setValue(customMetrics.getAttribute(KEY_MIN).toString());
        pingDtoList.add(dto);

        dto = new PingDto();
        dto.setKey(KEY_MAX);
        dto.setValue(customMetrics.getAttribute(KEY_MAX).toString());
        pingDtoList.add(dto);

        dto = new PingDto();
        dto.setKey(KEY_SUM);
        dto.setValue(customMetrics.getAttribute(KEY_SUM).toString());
        pingDtoList.add(dto);

        dto = new PingDto();
        dto.setKey(KEY_LAST);
        dto.setValue(customMetrics.getAttribute(KEY_LAST).toString());
        pingDtoList.add(dto);

        return new ResponseEntity<>(pingDtoList, OK);
    }
}
