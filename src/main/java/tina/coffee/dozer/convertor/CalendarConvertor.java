package tina.coffee.dozer.convertor;

import org.dozer.DozerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static tina.coffee.system.config.SystemConstant.LONG_DATE_FORMAT;

public class CalendarConvertor extends DozerConverter<Calendar, String>{

    private Logger logger = LoggerFactory.getLogger(getClass());

    public CalendarConvertor() {
        super(Calendar.class, String.class);
    }

    @Override
    public String convertTo(Calendar source, String destination) {
        DateFormat dateFormat = new SimpleDateFormat(LONG_DATE_FORMAT);
        return dateFormat.format(source.getTime());
    }

    @Override
    public Calendar convertFrom(String source, Calendar destination) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(LONG_DATE_FORMAT);
        try {
            cal.setTime(dateFormat.parse(source));
        } catch (ParseException e) {
            logger.error(String.format("Invalid value [%s] for Calendar", source));
            cal = null;
        }
        return cal;
    }
}