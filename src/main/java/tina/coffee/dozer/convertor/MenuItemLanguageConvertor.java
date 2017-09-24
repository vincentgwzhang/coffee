package tina.coffee.dozer.convertor;

import com.google.common.collect.Sets;
import org.dozer.DozerConverter;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuItemLanguageEntity;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class MenuItemLanguageConvertor extends DozerConverter<Set<MenuItemLanguageEntity>, String> {

    public MenuItemLanguageConvertor() {
        super((Class<Set<MenuItemLanguageEntity>>)(Object)Set.class, String.class);
    }

    @Override
    public String convertTo(Set<MenuItemLanguageEntity> source, String destination) {
        Optional<MenuItemLanguageEntity> menuCategoryLanguageEntity = source.stream().filter(entity -> entity.getLanguageType()==LanguageType.valueOf(getParameter())).findFirst();
        return getDescription(menuCategoryLanguageEntity.orElse(null));
    }

    @Override
    public Set<MenuItemLanguageEntity> convertFrom(String source, Set<MenuItemLanguageEntity> destination) {
        MenuItemLanguageEntity entity = new MenuItemLanguageEntity();
        entity.setMilDescription(source);
        entity.setLanguageType(LanguageType.valueOf(getParameter()));
        return Sets.newHashSet(entity);
    }

    private String getDescription(MenuItemLanguageEntity entity) {
        String result = "";
        if(Objects.nonNull(entity)) {
            result = entity.getMilDescription();
        }
        return result;
    }
}
