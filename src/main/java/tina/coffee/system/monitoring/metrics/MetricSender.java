package tina.coffee.system.monitoring.metrics;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Reservoir;
import org.springframework.util.StopWatch;

import java.util.Map;

public class MetricSender {

    private static final String HISTOGRAM_PREFIX = "histogram.";

    private final MetricRegistry registry;

    public MetricSender(MetricRegistry registry) {
        this.registry = registry;
    }

    private void updateHistogram(String metricsName, long elapseTime) {
        metricsName = HISTOGRAM_PREFIX.concat(metricsName);
        Map<String, Histogram> registeredHistograms = registry.getHistograms();
        Histogram registeredHistogram = registeredHistograms.get(metricsName);
        if (registeredHistogram == null) {
            Reservoir reservoir = new ExponentiallyDecayingReservoir();
            registeredHistogram = new Histogram(reservoir);
            registry.register(metricsName, registeredHistogram);
        }
        registeredHistogram.update(elapseTime);
    }

    public StopWatch getStartedStopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    private String computeMetricName(Class<?> clazz, String methodName) {
        return clazz.getName() + '.' + methodName;
    }

    public void stopAndSend(StopWatch stopWatch, Class<?> clazz, String methodName) {
        stopWatch.stop();
        String metricName = computeMetricName(clazz, methodName);
        long elapseTime = stopWatch.getTotalTimeMillis();

        updateHistogram(metricName, elapseTime);
    }

}
