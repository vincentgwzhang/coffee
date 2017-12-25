package tina.coffee.system.config;

import org.perf4j.slf4j.aop.TimingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class CoffeePerf4jConfig {

//    @Bean
//    public TimingAspect timingAspect() {
//        return new TimingAspect();
//    }

}
