package tina.coffee.system.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tina.coffee.dozer.DozerMapper;
import tina.coffee.dozer.providers.MappingProvider;

import java.util.Set;
import java.util.function.Consumer;

@Configuration
@ComponentScan(CoffeeDozerConfig.DOZER_PACKAGE)
public class CoffeeDozerConfig {

    public static final String DOZER_PACKAGE = "tina.coffee.dozer";

    @Bean
    public DozerMapper dozerMapper(Set<MappingProvider> mapperConfigurations) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapperConfigurations.forEach(configureMapper(mapper));

        return new DozerMapper(mapper);
    }

    private Consumer<MappingProvider> configureMapper(DozerBeanMapper mapper) {
        return mapperConfigurationProvider -> mapperConfigurationProvider.getMapperConfigurations()
                .forEach(mapper::addMapping);
    }
}
