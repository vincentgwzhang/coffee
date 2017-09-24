package tina.coffee.dozer.providers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.dozer.convertor.EnumConvertor;
import tina.coffee.dozer.convertor.MenuItemLanguageConvertor;
import tina.coffee.rest.dto.OrderItemDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.*;

@Component
public class OrderItemMappingProvider implements MappingProvider {

    public static final String MAPPING_ENTITY_TO_DTO = "MAPPING_ENTITY_TO_DTO";

    private String FIELD_LANGUAGE = "menuItem.languages";

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(
                orderItemEntityToOrderItemDTO()
        );
    }

    private BeanMappingBuilder orderItemEntityToOrderItemDTO() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(OrderItemEntity.class, OrderItemDTO.class, mapId(MAPPING_ENTITY_TO_DTO), oneWay(), mapNull(false))
                .fields("orderItemId", "orderItemId")
                .fields("order.orderId", "orderId")
                .fields(FIELD_LANGUAGE, "descEN", customConverter(MenuItemLanguageConvertor.class, LanguageType.ENGLISH.name()))
                .fields(FIELD_LANGUAGE, "descCN", customConverter(MenuItemLanguageConvertor.class, LanguageType.CHINESE.name()))
                .fields(FIELD_LANGUAGE, "descSP", customConverter(MenuItemLanguageConvertor.class, LanguageType.SPANISH.name()))
                .fields("menuItem.miPrice", "price")
                .fields("count", "count")
                .fields("status", "status", customConverter(EnumConvertor.class))
                ;
            }
        };
    }
}
