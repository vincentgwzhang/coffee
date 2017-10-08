package tina.coffee.function.print;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PrintItemFooterBuilder {

    public static List<String> buildFooter(BigDecimal actualPrice, BigDecimal customerPay, BigDecimal customerReceive) {
        List<String> result = Lists.newArrayList();

        result.add("precio total:" + actualPrice);
        result.add("pago al cliente:" + customerPay);
        result.add("camio:" + customerReceive);
        result.add("Timpo:" + LocalDateTime.now().toString());

        return result;
    }

}
