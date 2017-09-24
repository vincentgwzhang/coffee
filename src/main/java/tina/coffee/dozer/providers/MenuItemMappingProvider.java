package tina.coffee.dozer.providers;

import org.dozer.classmap.RelationshipType;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.dozer.convertor.MenuItemLanguageConvertor;
import tina.coffee.rest.dto.MenuItemDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.collectionStrategy;
import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.wildcard;

@Component
public class MenuItemMappingProvider implements MappingProvider {

    private String FIELD_LANGUAGE = "languages";

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(
                menuItemEntityToMenuItemDTOMapping(),
                menuItemDTOToMenuItemEntity()
                );
    }

    private BeanMappingBuilder menuItemEntityToMenuItemDTOMapping() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(MenuItemEntity.class, MenuItemDTO.class, oneWay(), wildcard(false))
                .fields("miId", "miId")
                .fields("miPic", "miPic")
                .fields("miEnable", "miEnable")
                .fields("toChief", "toChief")
                .fields("menuCategoryEntity.mcId", "miMcId")
                .fields("miPrice", "miPrice", customConverter(BigDecimalConverter.class))
                .fields(FIELD_LANGUAGE, "descEN", customConverter(MenuItemLanguageConvertor.class, LanguageType.ENGLISH.name()))
                .fields(FIELD_LANGUAGE, "descCN", customConverter(MenuItemLanguageConvertor.class, LanguageType.CHINESE.name()))
                .fields(FIELD_LANGUAGE, "descSP", customConverter(MenuItemLanguageConvertor.class, LanguageType.SPANISH.name()))
                ;
            }
        };
    }

    private BeanMappingBuilder menuItemDTOToMenuItemEntity() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(MenuItemDTO.class, MenuItemEntity.class, oneWay(), mapNull(false))
                        .fields("miId", "miId")
                        .fields("miPic", "miPic")
                        .fields("miEnable", "miEnable")
                        .fields("toChief", "toChief")
                        .fields("miPrice", "miPrice", customConverter(BigDecimalConverter.class))
                        .fields("descEN", FIELD_LANGUAGE, customConverter(MenuItemLanguageConvertor.class, LanguageType.ENGLISH.name()), collectionStrategy(false, RelationshipType.CUMULATIVE))
                        .fields("descSP", FIELD_LANGUAGE, customConverter(MenuItemLanguageConvertor.class, LanguageType.SPANISH.name()), collectionStrategy(false, RelationshipType.CUMULATIVE))
                        .fields("descCN", FIELD_LANGUAGE, customConverter(MenuItemLanguageConvertor.class, LanguageType.CHINESE.name()), collectionStrategy(false, RelationshipType.CUMULATIVE))
                ;
            }
        };
    }
}
