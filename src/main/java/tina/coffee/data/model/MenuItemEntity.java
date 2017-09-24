package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="menu_item")
public class MenuItemEntity {

    @Id
    @Column(name = "mi_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int miId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mi_mc_id", nullable=false)
    private MenuCategoryEntity menuCategoryEntity;

    @Column(name="mi_pic", nullable = false)
    private String miPic;

    @Column(name="mi_enable", nullable=false)
    private boolean miEnable;

    @Column(name="mi_price", nullable = false)
    private BigDecimal miPrice = BigDecimal.ZERO;

    @Column(name="mi_to_chief", nullable=false)
    private boolean toChief = true;

    @ManyToMany
    @JoinTable(name="menu_item_to_import_product",
            joinColumns=@JoinColumn(name="mitip_mi_id", referencedColumnName="mi_id"),
            inverseJoinColumns=@JoinColumn(name="mitip_ip_id", referencedColumnName="ip_id"))
    private List<ImportProductEntity> importProducts;

    public Set<MenuItemLanguageEntity> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<MenuItemLanguageEntity> languages) {
        if( this.languages == null ) {
            this.languages = Sets.newHashSet();
        }
        this.languages.addAll(languages);
        this.languages.stream().forEach(lan -> lan.setMenuItemEntity(this));
    }

    @ElementCollection(fetch= FetchType.EAGER)
    @OneToMany(mappedBy="menuItemEntity", fetch= FetchType.EAGER)
    private Set<MenuItemLanguageEntity> languages;

    public int getMiId() {
        return miId;
    }

    public void setMiId(int miId) {
        this.miId = miId;
    }

    public MenuCategoryEntity getMenuCategoryEntity() {
        return menuCategoryEntity;
    }

    public void setMenuCategoryEntity(MenuCategoryEntity menuCategoryEntity) {
        this.menuCategoryEntity = menuCategoryEntity;
    }

    public String getMiPic() {
        return miPic;
    }

    public void setMiPic(String miPic) {
        this.miPic = miPic;
    }

    public boolean isMiEnable() {
        return miEnable;
    }

    public void setMiEnable(boolean miEnable) {
        this.miEnable = miEnable;
    }

    public BigDecimal getMiPrice() {
        return miPrice;
    }

    public void setMiPrice(BigDecimal miPrice) {
        this.miPrice = miPrice;
    }

    public boolean isToChief() {
        return toChief;
    }

    public void setToChief(boolean toChief) {
        this.toChief = toChief;
    }

    public List<ImportProductEntity> getImportProducts() {
        return importProducts;
    }

    public void setImportProducts(List<ImportProductEntity> importProducts) {
        this.importProducts = importProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemEntity that = (MenuItemEntity) o;
        return getMiId() == that.getMiId() &&
                isMiEnable() == that.isMiEnable() &&
                isToChief() == that.isToChief() &&
                Objects.equal(getMenuCategoryEntity(), that.getMenuCategoryEntity()) &&
                Objects.equal(getMiPic(), that.getMiPic()) &&
                Objects.equal(getMiPrice(), that.getMiPrice()) &&
                Objects.equal(getImportProducts(), that.getImportProducts()) &&
                Objects.equal(getLanguages(), that.getLanguages());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMiId(), getMenuCategoryEntity(), getMiPic(), isMiEnable(), getMiPrice(), isToChief(), getImportProducts(), getLanguages());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("miId", miId)
                .add("menuCategoryEntity", menuCategoryEntity)
                .add("miPic", miPic)
                .add("miEnable", miEnable)
                .add("miPrice", miPrice)
                .add("toChief", toChief)
                .add("importProducts", importProducts)
                .add("languages", languages)
                .toString();
    }
}
