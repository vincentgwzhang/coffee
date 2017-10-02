package tina.coffee.webmvc.admin;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tina.coffee.business.MenuCategoryService;
import tina.coffee.business.OrderAdjustmentOverviewService;
import tina.coffee.function.CalFunction;
import tina.coffee.rest.dto.MenuCategoryDTO;
import tina.coffee.rest.dto.OrderAdjustmentOverviewWrapper;
import tina.coffee.system.exceptions.weberror.ParameterErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
@Secured("ROLE_ADMIN")
public class OrderAdjustmentCalendarController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderAdjustmentOverviewService orderAdjustmentOverviewService;

    @GetMapping("statistics")
    public String orderStatisticsPage() {
        return "/admin/order_statistics";
    }

    @ModelAttribute("dates")
    public List<Integer> monthDatesModel(@RequestParam(name="date", required=false) String yearMonth) throws ParameterErrorException {
        return CalFunction.initMonthDateSeed(yearMonth);
    }

    @ModelAttribute("overviewWrapper")
    public OrderAdjustmentOverviewWrapper overviewSummary(@RequestParam(name="date", required=false) String yearMonth) {
        Calendar calendar = Calendar.getInstance();
        if(!Strings.isNullOrEmpty(yearMonth)) {
            String[] dates = yearMonth.split("_");
            calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
        }
        return orderAdjustmentOverviewService.getOrderAdjustmentOverviewListInSpecificMonth(calendar);
    }

    @GetMapping("calendar")
    public String orderCalendarPage(@RequestParam(name="date", required=false) String yearMonth, Model map) {
        return "admin/order_calendar";
    }
}
