package tina.coffee.dozer.providers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuQueueEntity;
import tina.coffee.dozer.convertor.EnumConvertor;
import tina.coffee.dozer.convertor.MenuItemLanguageConvertor;
import tina.coffee.rest.dto.MenuQueueDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.wildcard;

@Component
public class MenuQueueMappingProvider implements MappingProvider {

    private String FIELD_LANGUAGE = "orderItemEntity.menuItem.languages";

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(
                menuQueueEntityToMenuQueueDTO()
                );
    }

    private BeanMappingBuilder menuQueueEntityToMenuQueueDTO() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(MenuQueueEntity.class, MenuQueueDTO.class, oneWay(), wildcard(false))
                .fields("mqId", "mqId")
                .fields("orderItemEntity.order.desktopEntity.deskNo", "desktopNumber")
                .fields("orderItemEntity.count", "count")
                .fields("orderItemEntity.status", "status", customConverter(EnumConvertor.class))
                .fields(FIELD_LANGUAGE, "descEN", customConverter(MenuItemLanguageConvertor.class, LanguageType.ENGLISH.name()))
                .fields(FIELD_LANGUAGE, "descCN", customConverter(MenuItemLanguageConvertor.class, LanguageType.CHINESE.name()))
                .fields(FIELD_LANGUAGE, "descSP", customConverter(MenuItemLanguageConvertor.class, LanguageType.SPANISH.name()));
            }
        };
    }
}
