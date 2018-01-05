package tina.coffee.system.monitoring.selfmetrics;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CustomMetric {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Long hits = 0L;
    private Double average = 0D;
    private Double min = 0D;
    private Double max = 0D;
    private Double sum = 0D;
    private Double last = 0D;

    void add(double value) {
        lock.secureWrite(() -> {
            BigDecimal valueBigDecimal = new BigDecimal(value, MathContext.DECIMAL64);
            BigDecimal averageBigDecimal = new BigDecimal(average, MathContext.DECIMAL64);
            BigDecimal sumBigDecimal = new BigDecimal(sum, MathContext.DECIMAL64);

            average = averageBigDecimal.multiply(new BigDecimal(hits)).add(valueBigDecimal).divide(new BigDecimal(hits).add(new BigDecimal(1)), 2, RoundingMode.HALF_UP).doubleValue();
            min = (hits > 0) ? Math.min(min, value) : value;
            max = (hits > 0) ? Math.max(max, value) : value;
            sum = sumBigDecimal.add(valueBigDecimal).doubleValue();
            last = value;
            hits++;
        });
    }

    Long hits() {
        return lock.secureRead(() -> hits);
    }

    Double average() {
        return lock.secureRead(() -> average);
    }

    Double min() {
        return lock.secureRead(() -> min);
    }

    Double max() {
        return lock.secureRead(() -> max);
    }

    Double sum() {
        return lock.secureRead(() -> sum);
    }

    Double last() {
        return lock.secureRead(() -> last);
    }

}
