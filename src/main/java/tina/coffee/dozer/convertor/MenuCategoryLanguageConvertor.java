package tina.coffee.dozer.convertor;

import com.google.common.collect.Sets;
import org.dozer.DozerConverter;
import tina.coffee.data.model.LanguageType;
import tina.coffee.data.model.MenuCategoryLanguageEntity;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class MenuCategoryLanguageConvertor extends DozerConverter<Set<MenuCategoryLanguageEntity>, String> {

    public MenuCategoryLanguageConvertor() {
        super((Class<Set<MenuCategoryLanguageEntity>>)(Object)Set.class, String.class);
    }

    @Override
    public String convertTo(Set<MenuCategoryLanguageEntity> source, String destination) {
        Optional<MenuCategoryLanguageEntity> menuCategoryLanguageEntity = source.stream().filter(entity -> entity.getLanguageType()==LanguageType.valueOf(getParameter())).findFirst();
        return getDescription(menuCategoryLanguageEntity.orElse(null));
    }

    @Override
    public Set<MenuCategoryLanguageEntity> convertFrom(String source, Set<MenuCategoryLanguageEntity> destination) {
        MenuCategoryLanguageEntity entity = new MenuCategoryLanguageEntity();
        entity.setMclDescription(source);
        entity.setLanguageType(LanguageType.valueOf(getParameter()));
        return Sets.newHashSet(entity);
    }

    private String getDescription(MenuCategoryLanguageEntity entity) {
        String result = "";
        if(Objects.nonNull(entity)) {
            result = entity.getMclDescription();
        }
        return result;
    }
}
