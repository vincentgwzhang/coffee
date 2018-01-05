package tina.coffee.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tina.coffee.system.monitoring.selfmetrics.CustomMetrics;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Configuration
public class CoffeeCustomMetricsConfig {

    @Bean
    public CustomMetrics customMetrics() {
        CustomMetrics customMetrics = new CustomMetrics();
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            mbeanServer.registerMBean(customMetrics, new ObjectName("coffee:type=customMetrics"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customMetrics;
    }

}
