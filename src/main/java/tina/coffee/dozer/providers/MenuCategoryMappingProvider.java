package tina.coffee.dozer.providers;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.FieldsMappingOptions.collectionStrategy;

import org.dozer.classmap.RelationshipType;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuCategoryEntity;
import tina.coffee.dozer.convertor.MenuCategoryLanguageConvertor;
import tina.coffee.rest.dto.MenuCategoryDTO;
import tina.coffee.rest.dto.MenuCategoryWithMenuItems;

import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;

import java.util.Arrays;
import java.util.Collection;

@Component
public class MenuCategoryMappingProvider implements MappingProvider {

    private String FIELD_LANGUAGE = "languages";

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(
                menuCategoryDTOTOMenuCategoryEntityMapping(),
                menuCategoryDTOToMenuCategoryEntity(),
                menuCategoryDTOToMenuCategoryWithMenuItems()
                );
    }

    private BeanMappingBuilder menuCategoryDTOTOMenuCategoryEntityMapping() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(MenuCategoryEntity.class, MenuCategoryDTO.class, oneWay())
                .fields("mcId", "mcId")
                .fields("mcName", "mcName")
                .fields("mcEnable", "mcEnable")
                .fields(FIELD_LANGUAGE, "descEN", customConverter(MenuCategoryLanguageConvertor.class, LanguageType.ENGLISH.name()))
                .fields(FIELD_LANGUAGE, "descCN", customConverter(MenuCategoryLanguageConvertor.class, LanguageType.CHINESE.name()))
                .fields(FIELD_LANGUAGE, "descSP", customConverter(MenuCategoryLanguageConvertor.class, LanguageType.SPANISH.name()));
            }
        };
    }

    private BeanMappingBuilder menuCategoryDTOToMenuCategoryEntity() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(MenuCategoryDTO.class, MenuCategoryEntity.class, oneWay(), mapNull(false))
                        .fields("mcId", "mcId")
                        .fields("mcName", "mcName")
                        .fields("mcEnable", "mcEnable")
                        .fields("descEN", FIELD_LANGUAGE, customConverter(MenuCategoryLanguageConvertor.class, LanguageType.ENGLISH.name()), collectionStrategy(false, RelationshipType.CUMULATIVE))
                        .fields("descSP", FIELD_LANGUAGE, customConverter(MenuCategoryLanguageConvertor.class, LanguageType.SPANISH.name()), collectionStrategy(false, RelationshipType.CUMULATIVE))
                        .fields("descCN", FIELD_LANGUAGE, customConverter(MenuCategoryLanguageConvertor.class, LanguageType.CHINESE.name()), collectionStrategy(false, RelationshipType.CUMULATIVE))
                ;
            }
        };
    }

    private BeanMappingBuilder menuCategoryDTOToMenuCategoryWithMenuItems() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(MenuCategoryDTO.class, MenuCategoryWithMenuItems.class, oneWay(), mapNull(false))
                        .fields("this", "menuCategoryDTO");
            }
        };
    }
}
