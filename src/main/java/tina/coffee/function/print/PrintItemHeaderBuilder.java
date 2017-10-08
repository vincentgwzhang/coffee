package tina.coffee.function.print;

import com.google.common.collect.Lists;

import java.util.List;

public class PrintItemHeaderBuilder {

    public static List<String> buildHeader(Integer orderId) {
        List<String> result = Lists.newArrayList();

        result.add("Número de orden:" + orderId);

        return result;
    }

}
