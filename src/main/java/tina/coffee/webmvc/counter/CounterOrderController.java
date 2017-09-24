package tina.coffee.webmvc.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.data.model.OrderType;
import tina.coffee.rest.dto.MenuCategoryWithMenuItems;
import tina.coffee.rest.dto.OrderDTO;
import tina.coffee.rest.dto.OrderItemDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("counter")
@Secured("ROLE_COUNTER")
public class CounterOrderController extends AbsController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService service;

    @GetMapping("closeOrder/{orderNum}")
    public String closeOrder(@PathVariable(name="orderNum", required=true) Integer orderNum, Model model) {
        logger.debug("tina.coffee.webmvc.counter.CounterOrderController.closeOrder triggered");

        OrderDTO orderDTO = service.getOrderByID(orderNum);
        if(orderDTO == null || !orderDTO.getOrderType().equalsIgnoreCase(OrderType.OPEN.name())) {
            return "redirect:/counter";
        }

        List<OrderItemDTO> orderItemDTOS = orderItemService.getOrderItemInStatus(orderNum, new ArrayList(OrderItemStatus.allStatuses()), 100);
        model.addAttribute("deskNum", orderDTO.getDesktopNumber());
        model.addAttribute("orderNum", orderNum);
        model.addAttribute("orderItemDTOS", orderItemDTOS);
        return "/counter/order";
    }

}
