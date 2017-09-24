package tina.coffee.dozer.providers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.OrderAdjustmentEntity;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.rest.dto.OrderAdjustmentDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.wildcard;

@Component
public class OrderAdjustmentMappingProvider implements MappingProvider {

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(dtoToEntityMapping(), entityToDTOMapping());
    }

    private BeanMappingBuilder dtoToEntityMapping() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(OrderAdjustmentDTO.class, OrderAdjustmentEntity.class, oneWay(), wildcard(false))
                .fields("oaId", "oaId")
                .fields("oriOrderPrice", "oriOrderPrice", customConverter(BigDecimalConverter.class))
                .fields("adjOrderPrice", "adjOrderPrice", customConverter(BigDecimalConverter.class))
                ;
            }
        };
    }

    private BeanMappingBuilder entityToDTOMapping() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(OrderAdjustmentEntity.class, OrderAdjustmentDTO.class, oneWay(), wildcard(false))
                        .fields("oaId", "oaId")
                        .fields("oriOrderPrice", "oriOrderPrice", customConverter(BigDecimalConverter.class))
                        .fields("adjOrderPrice", "adjOrderPrice", customConverter(BigDecimalConverter.class))
                        .fields("overviewEntity.overviewId", "overviewId")
                        .fields("orderEntity.orderId", "oriOrderId")
                ;
            }
        };
    }
}
