package tina.coffee.function;

import tina.coffee.rest.dto.ImportHistorySummaryDTO;

import java.util.Calendar;
import java.util.Date;

public class ImportHistoryOverviewFunction {

    public static Integer getImportDate(ImportHistorySummaryDTO dto) {
        Date date = dto.getIhsTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

}
