package tina.coffee.system.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {CoffeeServiceConfig.SERVICE_PACKAGE, CoffeeServiceConfig.SYSTEM_DEFAULT})
public class CoffeeServiceConfig {

    public static final String SERVICE_PACKAGE = "tina.coffee.business";

    public static final String SYSTEM_DEFAULT  = "tina.coffee.system";

}