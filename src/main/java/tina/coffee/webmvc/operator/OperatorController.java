package tina.coffee.webmvc.operator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tina.coffee.business.DesktopService;
import tina.coffee.business.OrderService;
import tina.coffee.rest.dto.DesktopDTO;
import tina.coffee.rest.dto.OrderDTO;

import java.text.MessageFormat;
import java.util.List;

@Controller
@RequestMapping("operator")
@Secured("ROLE_OPERATOR")
public class OperatorController extends AbsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DesktopService service;

    @Autowired
    private OrderService orderService;

    private String REDIRECT_NEW_ORDER = "redirect:/operator/order/{0}";

    @ModelAttribute("operator_desktops")
    public List<DesktopDTO> findAllDesktop() {
        // desktop number can not less than 1, otherwise it shows very strange
        return service.findAllOnSiteDesktop();
    }

    @GetMapping("/showdesktops")
    public String operator() {
        logger.debug("tina.coffee.webmvc.operator.OperatorController.operator triggered");
        return "operator/desktop";
    }

    @GetMapping("/openDesk/{desknum}")
    public ModelAndView openNewDesk(@PathVariable(name="desknum", required=true) Integer deskNumber) {
        logger.debug("tina.coffee.webmvc.operator.OperatorController.openNewDesk triggered");
        OrderDTO orderDTO = orderService.createNewOrder(deskNumber);
        return new ModelAndView(MessageFormat.format(REDIRECT_NEW_ORDER, orderDTO.getOrderId()));
    }

    @GetMapping("/intoDesk/{desknum}")
    public ModelAndView intoNewDesk(@PathVariable(name="desknum", required=true) Integer deskNumber) {
        logger.debug("tina.coffee.webmvc.operator.OperatorController.intoNewDesk triggered");
        OrderDTO orderDTO = orderService.retrieveOpenOrderByDesktopNumber(deskNumber);
        return new ModelAndView(MessageFormat.format(REDIRECT_NEW_ORDER, orderDTO.getOrderId()));
    }



}
