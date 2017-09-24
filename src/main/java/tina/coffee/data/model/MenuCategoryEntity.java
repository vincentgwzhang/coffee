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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="menu_category")
public class MenuCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mcId;

    @Column(name="mc_name", nullable=false)
    private String mcName;

    @Column(name="mc_enable", nullable=false)
    private boolean mcEnable;

    @ElementCollection(fetch= FetchType.EAGER)
    @OneToMany(mappedBy="menuCategoryEntity", fetch= FetchType.EAGER)
    private Set<MenuCategoryLanguageEntity> languages;

    @PrePersist
    public void generateMcName() {
        mcName = UUID.randomUUID().toString();
    }

    public int getMcId() {
        return mcId;
    }

    public void setMcId(int mcId) {
        this.mcId = mcId;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public boolean isMcEnable() {
        return mcEnable;
    }

    public void setMcEnable(boolean mcEnable) {
        this.mcEnable = mcEnable;
    }

    public Set<MenuCategoryLanguageEntity> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<MenuCategoryLanguageEntity> languages) {
        if(this.languages==null) this.languages = Sets.newHashSet();
        this.languages.addAll(languages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuCategoryEntity that = (MenuCategoryEntity) o;
        return getMcId() == that.getMcId() &&
                isMcEnable() == that.isMcEnable() &&
                Objects.equal(getMcName(), that.getMcName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMcId(), getMcName(), isMcEnable());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mcId", mcId)
                .add("mcName", mcName)
                .add("mcEnable", mcEnable)
                .toString();
    }
}
