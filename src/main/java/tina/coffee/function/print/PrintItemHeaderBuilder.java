package tina.coffee.function.print;

import com.google.common.collect.Lists;

import java.util.List;

public class PrintItemHeaderBuilder {

    public static List<String> buildHeader() {
        List<String> result = Lists.newArrayList();

        result.add("MIAOMIAO WANG");
        result.add("AV FELIPE II 24");
        result.add("28009 MADRID");
        result.add("CIF: X-9687940-H");
        result.add("TEL: 917864850");

        return result;
    }

}
