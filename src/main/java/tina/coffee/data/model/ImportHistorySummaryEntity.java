package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="import_history_summary")
public class ImportHistorySummaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ihsId;

    @Column(name="ihs_price", nullable = false)
    private BigDecimal ihsPrice = BigDecimal.ZERO;

    @Column(name="ihs_time", nullable = false)
    private Date ihsTime;

    @ElementCollection(fetch= FetchType.LAZY)
    @OneToMany(mappedBy="importHistorySummary", fetch= FetchType.LAZY)
    private Set<ImportHistoryEntity> importHistoryEntities;

    public int getIhsId() {
        return ihsId;
    }

    public void setIhsId(int ihsId) {
        this.ihsId = ihsId;
    }

    public BigDecimal getIhsPrice() {
        return ihsPrice;
    }

    public void setIhsPrice(BigDecimal ihsPrice) {
        this.ihsPrice = ihsPrice;
    }

    public Date getIhsTime() {
        return ihsTime;
    }

    public void setIhsTime(Date ihsTime) {
        this.ihsTime = ihsTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportHistorySummaryEntity that = (ImportHistorySummaryEntity) o;
        return ihsId == that.ihsId &&
                Objects.equal(ihsPrice, that.ihsPrice) &&
                Objects.equal(ihsTime, that.ihsTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ihsId, ihsPrice, ihsTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ihsId", ihsId)
                .add("ihsPrice", ihsPrice)
                .add("ihsTime", ihsTime)
                .toString();
    }
}
