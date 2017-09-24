package tina.coffee.function;

import tina.coffee.rest.dto.OrderAdjustmentOverviewDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

public class OrderAdjustmentOverviewFunction {

    public static Integer getOverviewDate(OrderAdjustmentOverviewDTO dto) {
        Date date = dto.getAdjustDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

}
