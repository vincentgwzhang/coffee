package tina.coffee.function.print;

import com.google.common.collect.Lists;
import tina.coffee.function.CalFunction;
import tina.coffee.system.config.SystemConstant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PrintItemFooterBuilder {

    public static List<String> buildFooter(Integer orderId, Integer desktopNumber) {
        List<String> result = Lists.newArrayList();

        result.add("Número de orden:" + orderId);

        if(desktopNumber != -1) {
            result.add("Mesa:" + desktopNumber);
        } else {
            result.add("Mesa: Para llevar");
        }
        result.add("Timpo:" + CalFunction.getCurrentTime(SystemConstant.LONG_DATE_FORMAT  ));

        return result;
    }

}
