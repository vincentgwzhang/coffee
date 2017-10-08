package tina.coffee.webmvc.operator;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tina.coffee.business.DesktopService;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.rest.dto.DesktopDTO;
import tina.coffee.rest.dto.MenuCategoryWithMenuItems;
import tina.coffee.rest.dto.OrderDTO;
import tina.coffee.rest.dto.OrderItemDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("operator")
@Secured("ROLE_OPERATOR")
public class OrderController extends AbsController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuCategoryService menuCategoryService;

    @Autowired
    private DesktopService desktopService;

    @Autowired
    private OrderService service;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/order/{orderNum}")
    public String showPage(@PathVariable(name="orderNum", required=true) Integer orderNum, Model model) {
        logger.debug("tina.coffee.webmvc.operator.OrderController.showPage triggered");
        List<MenuCategoryWithMenuItems> items = menuCategoryService.showMenu();
        model.addAttribute("menu", items);


        List<OrderItemDTO> orderItemDTOS = orderItemService.getOrderItemInStatus(orderNum, new ArrayList(OrderItemStatus.allStatuses()), 100);
        OrderDTO orderDTO = service.getOrderByID(orderNum);
        model.addAttribute("deskNum", orderDTO.getDesktopNumber());
        model.addAttribute("orderNum", orderNum);
        model.addAttribute("orderItemDTOS", orderItemDTOS);
        return "operator/order";
    }
}
