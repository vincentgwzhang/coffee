package tina.coffee.system.monitoring.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CPUMetricsSet implements MetricSet {

    private final MBeanServer mBeanServer;

    public CPUMetricsSet() {
        this(ManagementFactory.getPlatformMBeanServer());
    }

    public CPUMetricsSet(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        final Map<String, Metric> gauges = new HashMap<>();

        gauges.put("ProcessCpuLoad", getOSAttributeGaugeDouble("ProcessCpuLoad"));
        gauges.put("ProcessCpuTime", getOSAttributeGaugeLong("ProcessCpuTime"));

        return Collections.unmodifiableMap(gauges);
    }

    private Gauge<Long> getOSAttributeGaugeLong(String attribute) {
        return () -> {
            try {
                return getOSAttribute(attribute);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private Gauge<Double> getOSAttributeGaugeDouble(String attribute) {
        return () -> {
            try {
                Double value = getOSAttribute(attribute);

                if (value > 0) {
                    value = ((int) (value * 1000) / 10.0);
                }

                return value;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private <T extends Number> T getOSAttribute(String attribute) throws Exception {
        T result = null;

        ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mBeanServer.getAttributes(name, new String[]{attribute});

        if (!list.isEmpty()) {
            Attribute att = (Attribute) list.get(0);
            result = (T) att.getValue();

        }

        return result;
    }

}