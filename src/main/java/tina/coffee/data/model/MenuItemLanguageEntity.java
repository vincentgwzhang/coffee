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
@Table(name="menu_item_language")
public class MenuItemLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int milId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mil_mi_id", nullable=false)
    private MenuItemEntity menuItemEntity;

    @Enumerated(EnumType.STRING)
    @Column(name="mil_language", nullable=false)
    private LanguageType languageType;

    @Column(name="mil_description", nullable=false)
    private String milDescription;

    public int getMilId() {
        return milId;
    }

    public void setMilId(int milId) {
        this.milId = milId;
    }

    public MenuItemEntity getMenuItemEntity() {
        return menuItemEntity;
    }

    public void setMenuItemEntity(MenuItemEntity menuItemEntity) {
        this.menuItemEntity = menuItemEntity;
    }

    public LanguageType getLanguageType() {
        return languageType;
    }

    public void setLanguageType(LanguageType languageType) {
        this.languageType = languageType;
    }

    public String getMilDescription() {
        return milDescription;
    }

    public void setMilDescription(String milDescription) {
        this.milDescription = milDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemLanguageEntity that = (MenuItemLanguageEntity) o;
        return getMilId() == that.getMilId() &&
                getLanguageType() == that.getLanguageType() &&
                Objects.equal(getMilDescription(), that.getMilDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMilId(),  getLanguageType(), getMilDescription());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("milId", milId)
                .add("languageType", languageType)
                .add("milDescription", milDescription)
                .toString();
    }
}
