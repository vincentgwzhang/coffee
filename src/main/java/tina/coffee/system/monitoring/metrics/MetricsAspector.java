package tina.coffee.system.monitoring.metrics;

import com.codahale.metrics.MetricRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MetricsAspector {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MetricRegistry registry;

    @Autowired
    public MetricSender metricSender;

    @Pointcut("within(tina.coffee.webmvc.*.*)")
    private void isMVCMethod() {}

    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.GetMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.RequestMapping) " +
                    "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)")
    private void containMappingAnnotation() {}

    @Around("isMVCMethod() && containMappingAnnotation()")
    public Object registryMark(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.debug("{} start", joinPoint.getSignature().getName());

        StopWatch stopWatch = metricSender.getStartedStopWatch();

        try {
            return joinPoint.proceed();
        } finally {
            Class<?> clazz = joinPoint.getTarget().getClass();
            String methodName = joinPoint.getSignature().getName();
            metricSender.stopAndSend(stopWatch, clazz, methodName);

            logger.debug("{} end", methodName);
        }

    }

}
