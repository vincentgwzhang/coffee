package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="import_product_count")
public class ImportProductCountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ipcId;

    @OneToOne
    @JoinColumn(name="ipc_ip_id", nullable=false, unique=true)
    private ImportProductEntity importProduct;

    @Column(name="ipc_count", nullable=false)
    private int count;

    public int getIpcId() {
        return ipcId;
    }

    public void setIpcId(int ipcId) {
        this.ipcId = ipcId;
    }

    public ImportProductEntity getImportProduct() {
        return importProduct;
    }

    public void setImportProduct(ImportProductEntity importProduct) {
        this.importProduct = importProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportProductCountEntity that = (ImportProductCountEntity) o;
        return getIpcId() == that.getIpcId() &&
                getCount() == that.getCount() &&
                Objects.equal(getImportProduct(), that.getImportProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIpcId(), getImportProduct(), getCount());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ipcId", ipcId)
                .add("importProduct", importProduct)
                .add("count", count)
                .toString();
    }
}
