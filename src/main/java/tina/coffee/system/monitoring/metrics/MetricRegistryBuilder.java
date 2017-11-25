package tina.coffee.system.monitoring.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;

import java.util.HashMap;
import java.util.Map;

public class MetricRegistryBuilder {

    private static final String JVM = "jvm";
    private static final String MEMORY = JVM + ".mem";
    private static final String CLASSLOADER = JVM + ".class";
    private static final String GC = JVM + ".gc";
    private static final String THREAD = JVM + ".thread";
    private static final String CPU = JVM + ".cpu";

    private Map<String, MetricSet> metrics = new HashMap<>();

    public MetricRegistryBuilder register(String metricName, MetricSet metric) {
        metrics.put(metricName, metric);
        return this;
    }

    public MetricRegistryBuilder defaultJVMMetrics() {
        register(GC, new GarbageCollectorMetricSet());
        register(MEMORY, new MemoryUsageGaugeSet());
        register(CLASSLOADER, new ClassLoadingGaugeSet());
        register(THREAD, new ThreadStatesGaugeSet());
        register(CPU, new CPUMetricsSet());

        return this;
    }

    public MetricRegistry build() {
        MetricRegistry registry = new MetricRegistry();

        metrics.forEach(registry::register);

        return registry;
    }

}
