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
import tina.coffee.business.OrderService;
import tina.coffee.function.CalFunction;
import tina.coffee.rest.dto.OrderStatisticsWrapper;
import tina.coffee.system.exceptions.weberror.ParameterErrorException;

import java.util.List;

@Controller
@RequestMapping("/admin/statistics/orders")
@Secured("ROLE_ADMIN")
public class IncomeStatisticsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService service;

    @ModelAttribute("ordersByDay")
    public OrderStatisticsWrapper statisticsByDay(@RequestParam(name="date", required=false) String yearMonth) {
        return service.statisticsIncomeByDateInSpecMonth(yearMonth);
    }

    @ModelAttribute("dates")
    public List<Integer> monthDatesModel(@RequestParam(name="date", required=false) String yearMonth) throws ParameterErrorException {
        return CalFunction.initMonthDateSeed(yearMonth);
    }

    @GetMapping
    public String showPage() {
        return "admin/order_statistics";
    }

}
