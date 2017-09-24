package tina.coffee.dozer.providers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.dozer.convertor.CalendarConvertor;
import tina.coffee.dozer.convertor.EnumConvertor;
import tina.coffee.rest.dto.OrderDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.FieldsMappingOptions.useMapId;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static tina.coffee.dozer.providers.OrderItemMappingProvider.MAPPING_ENTITY_TO_DTO;

@Component
public class OrderMappingProvider implements MappingProvider {

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(
                orderEntityToOrderDTO()
        );
    }

    private BeanMappingBuilder orderEntityToOrderDTO() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(OrderEntity.class, OrderDTO.class, oneWay(), mapNull(false))
                        .fields("orderId", "orderId")
                        .fields("desktopEntity.deskNo", "desktopNumber")
                        .fields("startTime", "startTime", customConverter(CalendarConvertor.class))
                        .fields("endTime", "endTime", customConverter(CalendarConvertor.class))
                        .fields("totalPrice", "totalPrice", customConverter(BigDecimalConverter.class))
                        .fields("actualPrice", "actualPrice", customConverter(BigDecimalConverter.class))
                        .fields("orderType", "orderType", customConverter(EnumConvertor.class))
                        .fields("items", "items", useMapId(MAPPING_ENTITY_TO_DTO))
                ;
            }
        };
    }
}
