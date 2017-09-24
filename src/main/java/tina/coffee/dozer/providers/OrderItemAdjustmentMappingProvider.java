package tina.coffee.dozer.providers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.OrderItemAdjustmentEntity;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.rest.dto.OrderItemAdjustmentDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.wildcard;

@Component
public class OrderItemAdjustmentMappingProvider implements MappingProvider {

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(dtoToEntityMapping(), entityToDTOMapping());
    }

    private BeanMappingBuilder dtoToEntityMapping() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(OrderItemAdjustmentDTO.class, OrderItemAdjustmentEntity.class, oneWay(), wildcard(false))
                .fields("oiaId", "oiaId")
                .fields("oriItemPrice", "oriItemPrice", customConverter(BigDecimalConverter.class))
                .fields("adjItemPrice", "adjItemPrice", customConverter(BigDecimalConverter.class))
                .fields("oriItemCount", "oriItemCount")
                .fields("adjItemCount", "adjItemCount")
                ;
            }
        };
    }

    private BeanMappingBuilder entityToDTOMapping() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(OrderItemAdjustmentEntity.class, OrderItemAdjustmentDTO.class, oneWay(), wildcard(false))
                        .fields("oiaId", "oiaId")
                        .fields("oriItemPrice", "oriItemPrice", customConverter(BigDecimalConverter.class))
                        .fields("adjItemPrice", "adjItemPrice", customConverter(BigDecimalConverter.class))
                        .fields("oriItemCount", "oriItemCount")
                        .fields("adjItemCount", "adjItemCount")
                        .fields("orderItemEntity.orderItemId", "orderItemId")
                        .fields("orderAdjustmentEntity.oaId", "orderAdjustmentId")
                ;
            }
        };
    }
}
