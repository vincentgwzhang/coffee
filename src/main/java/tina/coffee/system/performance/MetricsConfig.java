package tina.coffee.system.performance;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.codahale.metrics.graphite.GraphiteUDP;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class MetricsConfig {

    MetricRegistry metricRegistry = new MetricRegistry();

    private Logger logger = LoggerFactory.getLogger(getClass());

    public MetricsConfig() {
        MetricSet jvmMetrics = () -> {
            Map<String, Metric> metrics = new HashMap<>();
//            metrics.put("gc", new GarbageCollectorMetricSet());
            metrics.put("BufferPoolMetricSet", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
//            metrics.put("file-descriptors", new FileDescriptorRatioGauge());
//            metrics.put("memory-usage", new MemoryUsageGaugeSet());
//            metrics.put("threads", new ThreadStatesGaugeSet());
            return metrics;
        };
        metricRegistry.registerAll(jvmMetrics);
    }

    @Bean
    public JmxReporter jmxReporter() {
        final JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();
        return jmxReporter;
    }

    @Bean
    public Slf4jReporter slf4jReporter() {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry).outputTo(logger).build();
        reporter.start(1, TimeUnit.DAYS);
        return reporter;
    }

    @Bean
    public ConsoleReporter consoleReporter() {
        final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.DAYS);
        return reporter;
    }

    @Bean
    public GraphiteReporter graphiteReporter() {
        final GraphiteSender graphite = new GraphiteUDP(new InetSocketAddress("localhost", 2003));
        GraphiteReporter graphiteReporter = GraphiteReporter
                .forRegistry(metricRegistry)
                .prefixedWith("spring-boot")
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        graphiteReporter.start(1, TimeUnit.DAYS);
        return graphiteReporter;
    }
}
