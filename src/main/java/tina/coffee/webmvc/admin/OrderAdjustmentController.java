package tina.coffee.webmvc.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tina.coffee.business.OrderAdjustmentOverviewService;
import tina.coffee.business.OrderService;
import tina.coffee.function.OrderFunction;
import tina.coffee.rest.dto.OrderDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
@Secured("ROLE_ADMIN")
public class OrderAdjustmentController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderAdjustmentOverviewService orderAdjustmentOverviewService;

    @Autowired
    private OrderService orderService;

    @ModelAttribute("orders")
    public List<OrderDTO> getSpecificDateOrders(@RequestParam(name="date", required=true) String dateString) {
        List<OrderDTO> orderDTOS = orderService.findOrdersByCalendarDay(dateString.replaceAll("_","-"));
        orderDTOS = OrderFunction.filterQualifiedAdjustmentOrder(orderDTOS);
        return orderDTOS;
    }

    @GetMapping("adjustment")
    public String orderAdjustPage() {
        return "admin/order_adjust";
    }

}
