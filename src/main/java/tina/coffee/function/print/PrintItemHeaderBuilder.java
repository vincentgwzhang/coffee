package tina.coffee.function.print;

import com.google.common.collect.Lists;

import java.util.List;

public class PrintItemHeaderBuilder {

    public static List<String> buildHeader(Integer orderId, Integer desktopNumber) {
        List<String> result = Lists.newArrayList();

        result.add("MIAOMIAO WANG");
        result.add("AV FELIPE II 24");
        result.add("28009 MADRID");
        result.add("CIF: X-9687940-H");
        result.add("TEL: 917864850");
        result.add("Número de orden:" + orderId);

        if(desktopNumber != -1) {
            result.add("Mesa:" + desktopNumber);
        } else {
            result.add("Mesa: Para llevar");
        }

        return result;
    }

}
