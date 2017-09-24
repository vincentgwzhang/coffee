package tina.coffee.dozer.providers;

import org.dozer.classmap.RelationshipType;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;
import tina.coffee.data.model.ImportHistoryEntity;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuItemEntity;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.dozer.convertor.MenuItemLanguageConvertor;
import tina.coffee.rest.dto.ImportHistoryDTO;
import tina.coffee.rest.dto.ImportProductTraceDTO;
import tina.coffee.rest.dto.MenuItemDTO;

import java.util.Arrays;
import java.util.Collection;

import static org.dozer.loader.api.FieldsMappingOptions.collectionStrategy;
import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;
import static org.dozer.loader.api.TypeMappingOptions.wildcard;

@Component
public class ImportHistoryMappingProvider implements MappingProvider {

    @Override
    public Collection<BeanMappingBuilder> getMapperConfigurations() {
        return Arrays.asList(importHistoryEntityToImportHistoryDTO(),importHistoryEntityToImportProductTraceDTO());
    }

    private BeanMappingBuilder importHistoryEntityToImportHistoryDTO() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(ImportHistoryEntity.class, ImportHistoryDTO.class, oneWay(), wildcard(false))
                .fields("ihId", "ihId")
                .fields("importHistorySummary.ihsId", "ihIhsId")
                .fields("importProduct.ipId", "ihIpId")
                .fields("importProduct.ipId", "ihIpId")
                .fields("ihPrice", "ihPrice")
                .fields("count", "count")
                ;
            }
        };
    }

    private BeanMappingBuilder importHistoryEntityToImportProductTraceDTO() {
        return new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(ImportHistoryEntity.class, ImportProductTraceDTO.class, oneWay(), wildcard(false))
                        .fields("importHistorySummary.ihsTime", "date")
                        .fields("ihPrice", "ihPrice")
                        .fields("count", "count")
                ;
            }
        };
    }
}
