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
import java.math.BigDecimal;

@Entity
@Table(name="import_history")
public class ImportHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ihId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ih_ihs_id", nullable=false)
    private ImportHistorySummaryEntity importHistorySummary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ih_ip_id", nullable=false)
    private ImportProductEntity importProduct;

    @Column(name="ih_price")
    private BigDecimal ihPrice = BigDecimal.ZERO;

    @Column(name="ih_count")
    private int count;

    public int getIhId() {
        return ihId;
    }

    public void setIhId(int ihId) {
        this.ihId = ihId;
    }

    public ImportHistorySummaryEntity getImportHistorySummary() {
        return importHistorySummary;
    }

    public void setImportHistorySummary(ImportHistorySummaryEntity importHistorySummary) {
        this.importHistorySummary = importHistorySummary;
    }

    public ImportProductEntity getImportProduct() {
        return importProduct;
    }

    public void setImportProduct(ImportProductEntity importProduct) {
        this.importProduct = importProduct;
    }

    public BigDecimal getIhPrice() {
        return ihPrice;
    }

    public void setIhPrice(BigDecimal ihPrice) {
        this.ihPrice = ihPrice;
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
        ImportHistoryEntity that = (ImportHistoryEntity) o;
        return ihId == that.ihId &&
                count == that.count &&
                Objects.equal(importHistorySummary, that.importHistorySummary) &&
                Objects.equal(importProduct, that.importProduct) &&
                Objects.equal(ihPrice, that.ihPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ihId, importHistorySummary, importProduct, ihPrice, count);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ihId", ihId)
                .add("importHistorySummary", importHistorySummary)
                .add("importProduct", importProduct)
                .add("ihPrice", ihPrice)
                .add("count", count)
                .toString();
    }
}
