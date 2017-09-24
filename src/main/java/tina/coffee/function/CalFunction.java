package tina.coffee.function;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalFunction {

    public static BigDecimal murtiplyByBigDecimal(BigDecimal valueA, BigDecimal valueB) {
        return valueA.multiply(valueB);
    }

    public static List<Integer> initMonthDateSeed(String yearMonth) {
        Calendar calendar = Calendar.getInstance();
        if(!Strings.isNullOrEmpty(yearMonth)) {
            String[] dates = yearMonth.split("_");
            calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        Integer maxDate = calendar.getActualMaximum(Calendar.DATE);
        List<Integer> list = new ArrayList<>();
        for( int index = 1; index <= maxDate; index ++ ) {
            list.add(index);
        }

        return list;
    }

    public static Date stringToDate(String template, String dateString) {
        return new Date(stringToCalendar(template, dateString).getTimeInMillis());
    }

    public static Calendar stringToCalendar(String template, String dateString) {
        DateFormat df = new SimpleDateFormat(template);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(df.parse(dateString));
        } catch (ParseException e) {
            return null;
        }
        return calendar;
    }

    public static BigDecimal stringToBigDecimal(String strValue) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        try {
            return (BigDecimal) decimalFormat.parse(strValue);
        } catch(Exception e) {
            return BigDecimal.ZERO;
        }

    }

    public static Integer calendarToDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
