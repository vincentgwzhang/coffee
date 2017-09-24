package tina.coffee.dozer.providers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.OrderAdjustmentOverviewEntity;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.dozer.convertor.DateConvertor;
import tina.coffee.rest.dto.OrderAdjustmentOverviewDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.wildcard;

@Component
public class OrderAdjustmentOverviewMappingProvider implements MappingProvider {

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(dtoToEntityMapping());
    }

    private BeanMappingBuilder dtoToEntityMapping() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(OrderAdjustmentOverviewDTO.class, OrderAdjustmentOverviewEntity.class, wildcard(false))
                .fields("overviewId", "overviewId")
                .fields("adjustDate", "adjustDate", customConverter(DateConvertor.class))
                .fields("oriTotal", "oriTotal", customConverter(BigDecimalConverter.class))
                .fields("adjTotal", "adjTotal", customConverter(BigDecimalConverter.class))
                ;
            }
        };
    }
}
