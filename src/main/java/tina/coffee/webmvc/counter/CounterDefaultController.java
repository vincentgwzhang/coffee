package tina.coffee.webmvc.counter;

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
@RequestMapping("counter")
@Secured("ROLE_COUNTER")
public class CounterDefaultController extends AbsController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String REDIRECT_CLOSE_ORDER = "redirect:/counter/closeOrder/{0}";

    @Autowired
    private DesktopService service;

    @Autowired
    private OrderService orderService;

    @ModelAttribute("counter_desktops")
    public List<DesktopDTO> findAllDesktop() {
        // desktop number can not less than 1, otherwise it shows very strange
        return service.findAllOnSiteDesktop();
    }

    @GetMapping
    public String showPage() {
        logger.debug("tina.coffee.webmvc.counter.CounterDefaultController.showPage triggered");
        return "/counter/desktop";
    }

    @GetMapping("/closeDesk/{desknum}")
    public ModelAndView closeDesk(@PathVariable(name="desknum", required=true) Integer deskNumber) {
        logger.debug("tina.coffee.webmvc.counter.CounterDefaultController.closeDesk triggered");
        OrderDTO orderDTO = orderService.retrieveOpenOrderByDesktopNumber(deskNumber);
        return new ModelAndView(MessageFormat.format(REDIRECT_CLOSE_ORDER, orderDTO.getOrderId()));
    }

}
