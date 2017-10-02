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
import tina.coffee.business.ImportHistorySummaryService;
import tina.coffee.function.CalFunction;
import tina.coffee.rest.dto.ImportHistorySummaryWrapper;
import tina.coffee.rest.dto.OrderAdjustmentOverviewWrapper;
import tina.coffee.system.exceptions.weberror.ParameterErrorException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/admin/importproduct/calendar")
@Secured("ROLE_ADMIN")
public class ImportProductCalendarController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImportHistorySummaryService service;

    @ModelAttribute("dates")
    public List<Integer> monthDatesModel(@RequestParam(name="date", required=false) String yearMonth) throws ParameterErrorException {
        return CalFunction.initMonthDateSeed(yearMonth);
    }

    @ModelAttribute("overviewWrapper")
    public ImportHistorySummaryWrapper overviewSummary(@RequestParam(name="date", required=false) String yearMonth) {
        Calendar calendar = Calendar.getInstance();
        if(!Strings.isNullOrEmpty(yearMonth)) {
            String[] dates = yearMonth.split("_");
            calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
        }
        return service.getImportProductOverviewListInSpecificMonth(calendar);
    }


    @GetMapping
    public String orderCalendarPage(@RequestParam(name="date", required=false) String yearMonth, Model map) {
        return "admin/import_calendar";
    }

}
