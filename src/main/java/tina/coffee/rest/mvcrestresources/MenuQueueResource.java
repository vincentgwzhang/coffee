package tina.coffee.rest.mvcrestresources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tina.coffee.business.MenuQueueService;
import tina.coffee.rest.dto.MenuQueueDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * 保留查询厨师的工作状态，但是这个resource 暂时是不需要的
 */
@RequestMapping("/queue")
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuQueueResource {

    private MenuQueueService service;

    public MenuQueueResource(MenuQueueService service) {
        this.service = service;
    }

    /**
     * 对于厨师，
     * 1）可以查看当即的队列
     * 2) 可以更新状态为正在做
     * 3）可以更新状态为完成
     */
    @GetMapping
    public ResponseEntity<List<MenuQueueDTO>> getAllItemsInQueue() {
        List<MenuQueueDTO> menuQueueDTOList = service.displayToChiefMonitor();
        return new ResponseEntity<>(menuQueueDTOList, OK);
    }

}
