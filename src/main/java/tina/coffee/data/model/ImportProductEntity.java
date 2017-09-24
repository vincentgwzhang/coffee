package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="import_product")
public class ImportProductEntity {

    @Id
    @Column(name="ip_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ipId;

    @Column(name="ip_countable", nullable=false)
    private boolean ipCountable;

    @Column(name="ip_cn_name", nullable=false)
    private String cnName;

    @Column(name="ip_en_name", nullable=false)
    private String enName;

    @Column(name="ip_sp_name", nullable=false)
    private String spName;

    public int getIpId() {
        return ipId;
    }

    public void setIpId(int ipId) {
        this.ipId = ipId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public boolean isIpCountable() {
        return ipCountable;
    }

    public void setIpCountable(boolean ipCountable) {
        this.ipCountable = ipCountable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportProductEntity entity = (ImportProductEntity) o;
        return getIpId() == entity.getIpId() &&
                isIpCountable() == entity.isIpCountable() &&
                Objects.equal(getCnName(), entity.getCnName()) &&
                Objects.equal(getEnName(), entity.getEnName()) &&
                Objects.equal(getSpName(), entity.getSpName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ipId", ipId)
                .add("ipCountable", ipCountable)
                .add("cnName", cnName)
                .add("enName", enName)
                .add("spName", spName)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIpId(), isIpCountable(), getCnName(), getEnName(), getSpName());
    }
}
