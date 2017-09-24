package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="menu_category_language")
public class MenuCategoryLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mcl_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mcl_mc_id", nullable=false)
    private MenuCategoryEntity menuCategoryEntity;

    @Enumerated(EnumType.STRING)
    @Column(name="mcl_language", nullable=false)
    private LanguageType languageType;

    @Column(name="mcl_description", nullable=false)
    private String mclDescription;

    public int getMcl_id() {
        return mcl_id;
    }

    public void setMcl_id(int mcl_id) {
        this.mcl_id = mcl_id;
    }

    public MenuCategoryEntity getMenuCategoryEntity() {
        return menuCategoryEntity;
    }

    public void setMenuCategoryEntity(MenuCategoryEntity menuCategoryEntity) {
        this.menuCategoryEntity = menuCategoryEntity;
    }

    public LanguageType getLanguageType() {
        return languageType;
    }

    public void setLanguageType(LanguageType languageType) {
        this.languageType = languageType;
    }

    public String getMclDescription() {
        return mclDescription;
    }

    public void setMclDescription(String mclDescription) {
        this.mclDescription = mclDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuCategoryLanguageEntity that = (MenuCategoryLanguageEntity) o;
        return getMcl_id() == that.getMcl_id() &&
                Objects.equal(getMenuCategoryEntity(), that.getMenuCategoryEntity()) &&
                getLanguageType() == that.getLanguageType() &&
                Objects.equal(getMclDescription(), that.getMclDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMcl_id(), getMenuCategoryEntity(), getLanguageType(), getMclDescription());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mcl_id", mcl_id)
                .add("menuCategoryEntity", menuCategoryEntity)
                .add("languageType", languageType)
                .add("mclDescription", mclDescription)
                .toString();
    }
}
