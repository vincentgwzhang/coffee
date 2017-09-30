package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Vincent Zhang
 * Created on 2017/9/30 - 1:45
 * Create this class only for study
 * Source from:
 */
@Entity
@Table(name="menu_item_to_import_product")
public class MenuItemToImportProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="mitip_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mitip_ip_id", nullable=false)
    private ImportProductEntity importProductEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mitip_mi_id", nullable=false)
    private MenuItemEntity menuItemEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImportProductEntity getImportProductEntity() {
        return importProductEntity;
    }

    public void setImportProductEntity(ImportProductEntity importProductEntity) {
        this.importProductEntity = importProductEntity;
    }

    public MenuItemEntity getMenuItemEntity() {
        return menuItemEntity;
    }

    public void setMenuItemEntity(MenuItemEntity menuItemEntity) {
        this.menuItemEntity = menuItemEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemToImportProductEntity that = (MenuItemToImportProductEntity) o;
        return getId() == that.getId() &&
                Objects.equal(getImportProductEntity(), that.getImportProductEntity()) &&
                Objects.equal(getMenuItemEntity(), that.getMenuItemEntity());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getImportProductEntity(), getMenuItemEntity());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("importProductEntity", importProductEntity)
                .add("menuItemEntity", menuItemEntity)
                .toString();
    }
}
