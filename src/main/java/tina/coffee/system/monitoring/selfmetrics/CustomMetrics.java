package tina.coffee.system.monitoring.selfmetrics;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 记住，这个是统计用的，因为直接每次都是进行计算
 */
public class CustomMetrics implements DynamicMBean {

    private static final String MBEAN_DESC = "Custom MBean";

    private static final String LONG = "java.lang.Long";
    private static final String DOUBLE = "java.lang.Double";

    private final Map<String, CustomMetric> metricsMap = new ConcurrentHashMap<>();

    @Override
    public Number getAttribute(String metricName) throws AttributeNotFoundException {
        String key = metricName.substring(0, metricName.lastIndexOf("."));
        String suffix = metricName.substring(metricName.lastIndexOf(".") + 1);

        CustomMetric metric = metricsMap.get(key);

        Number result;

        if (metric != null) {
            if (suffix.equalsIgnoreCase("hits")) {
                result = metric.hits();
            } else if (suffix.equalsIgnoreCase("average")) {
                result = metric.average();
            } else if (suffix.equalsIgnoreCase("min")) {
                result = metric.min();
            } else if (suffix.equalsIgnoreCase("max")) {
                result = metric.max();
            } else if (suffix.equalsIgnoreCase("sum")) {
                result = metric.sum();
            } else if (suffix.equalsIgnoreCase("last")) {
                result = metric.last();
            } else {
                throw new AttributeNotFoundException("Attribute " + metricName + " not found");
            }
        } else {
            throw new AttributeNotFoundException("Attribute " + metricName + " not found");
        }
        return result;
    }

    @Override
    public AttributeList getAttributes(String[] metricsNames) {
        return new AttributeList(Arrays.stream(metricsNames).map(metricName -> {
                            try {
                                return new Attribute(metricName, getAttribute(metricName));
                            } catch (AttributeNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                    }).collect(Collectors.toList()));
    }

    @Override
    public void setAttribute(Attribute attribute) {
        //READ ONLY
        return;
    }

    @Override
    public AttributeList setAttributes(AttributeList inAttributeList) {
        //READ ONLY
        return null;
    }

    @Override
    public Object invoke(String name, Object[] args, String[] sig) throws ReflectionException {
        throw new ReflectionException(new NoSuchMethodException(name));
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanAttributeInfo[] attrs = metricsMap.keySet().stream().sorted().flatMap(metricType -> getMetrics(metricType).stream()).collect(Collectors.toList()).toArray(new MBeanAttributeInfo[0]);
        MBeanOperationInfo[] operationInfos = {};
        return new MBeanInfo(getClass().getName(), MBEAN_DESC, attrs, null, operationInfos, null);
    }

    private List<MBeanAttributeInfo> getMetrics(String metricType) {
        List<MBeanAttributeInfo> result = new ArrayList<>();
        result.add(new MBeanAttributeInfo(metricType + ".hits", LONG, metricType + " hits count", true, false, false));
        result.add(new MBeanAttributeInfo(metricType + ".average", DOUBLE, metricType + " average value", true, false, false));
        result.add(new MBeanAttributeInfo(metricType + ".min", DOUBLE, metricType + " min value", true, false, false));
        result.add(new MBeanAttributeInfo(metricType + ".max", DOUBLE, metricType + " max value", true, false, false));
        result.add(new MBeanAttributeInfo(metricType + ".sum", DOUBLE, metricType + " sum value", true, false, false));
        result.add(new MBeanAttributeInfo(metricType + ".last", DOUBLE, metricType + " last value", true, false, false));
        return result;
    }

    @SuppressWarnings("unchecked")
    public void addHit(String metricName) {
        metricsMap.putIfAbsent(metricName, new CustomMetric());
        metricsMap.get(metricName).add(0);
    }

    @SuppressWarnings("unchecked")
    public void addValue(String metricName, double value) {
        metricsMap.putIfAbsent(metricName, new CustomMetric());
        metricsMap.get(metricName).add(value);
    }
}
