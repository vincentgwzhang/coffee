package tina.coffee.webmvc.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tina.coffee.business.DesktopService;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.business.OrderItemService;
import tina.coffee.business.OrderService;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.rest.dto.DesktopDTO;
import tina.coffee.rest.dto.MenuCategoryWithMenuItems;
import tina.coffee.rest.dto.OrderDTO;
import tina.coffee.rest.dto.OrderItemDTO;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个旨意是给bar台用的，原意是直接外带结合，点菜直接就结帐，与操作员是同样的
 */
@Controller
@RequestMapping("counter")
@Secured("ROLE_COUNTER")
public class BarController extends AbsController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService service;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MenuCategoryService menuCategoryService;

    /**
     * 能选择到的都是当即不需要去到厨房的，因此流程是：
     * 1, 直接出不需要经过厨房的东西
     * 2, 选择，然后下面有结帐按钮。一旦结帐，做以下的事情：
     *      2.1）创建订单
     *      2.2）创建订单项
     *      2.3）结帐，直接认定该客户会给钱（不给的话后台可以删除的）
     */
    @GetMapping("bar")
    public String showPage(Model model) {
        logger.debug("tina.coffee.webmvc.operator.OrderController.showPage triggered");
        List<MenuCategoryWithMenuItems> items = menuCategoryService.showMenu();
        model.addAttribute("menu", items);

        return "counter/bar";
    }

}
