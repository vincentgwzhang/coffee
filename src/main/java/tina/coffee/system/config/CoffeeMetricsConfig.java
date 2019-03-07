package tina.coffee.system.config;

import com.codahale.metrics.Counter;
import com.codahale.metrics.jmx.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tina.coffee.system.monitoring.metrics.MetricRegistryBuilder;
import tina.coffee.system.monitoring.metrics.MetricSender;

/**
 * 关于 Metrics 的方案一，使用 ServletContextAttributeExporter
 */
@Configuration
public class CoffeeMetricsConfig {

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistryBuilder().defaultJVMMetrics().build();
    }

    @Bean(initMethod="start")
    public JmxReporter jmxReporter() {
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry()).build();
        return reporter;
    }

    @Bean
    public MetricSender metricSender() {
        return new MetricSender(metricRegistry());
    }

    @Bean(name="findAllOnSiteDesktopCounter")
    public Counter findAllOnSiteDesktopCounter() {
        return metricRegistry().counter("FindAllOnSiteDesktopCounter");
    }

}
