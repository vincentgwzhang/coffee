package tina.coffee.system.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages={CoffeeJpaConfig.MODEL_PACKAGE, CoffeeJpaConfig.SYSTEM_DEFAULT})
@EnableJpaRepositories(basePackages={CoffeeJpaConfig.REPOSITORY_PACKAGE, CoffeeJpaConfig.SYSTEM_DEFAULT})
public class CoffeeJpaConfig {
    public static final String MODEL_PACKAGE      = "tina.coffee.data.model";
    public static final String REPOSITORY_PACKAGE = "tina.coffee.data.repository";

    public static final String SYSTEM_DEFAULT     = "tina.coffee.system";
}