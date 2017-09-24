package tina.coffee.dozer.convertor;

import com.google.common.collect.Lists;
import org.dozer.DozerConverter;
import tina.coffee.data.model.ImportProductEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MenuItemImportProductConvertor extends DozerConverter<List<ImportProductEntity>, Integer> {

    public MenuItemImportProductConvertor() {
        super((Class<List<ImportProductEntity>>)(Object)Set.class, Integer.class);
    }


    @Override
    public Integer convertTo(List<ImportProductEntity> entities, Integer destination) {
        int result = 0;
        if(!Objects.isNull(entities) && entities.size() != 0) {
            result = entities.get(0).getIpId();
        }
        return result;
    }

    @Override
    public List<ImportProductEntity> convertFrom(Integer source, List<ImportProductEntity> entities) {
        return Lists.newArrayList();
    }
}
