package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name="order_adjustment_overview")
public class OrderAdjustmentOverviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Integer overviewId;

    /**
     * 这个不是 Order 发生的日期，而是整个 order 做数的时候发生的日期
     */
    @Column(name="adjust_date", nullable = false)
    private Date adjustDate;

    @Column(name="original_total", nullable = false)
    private BigDecimal oriTotal = BigDecimal.ZERO;

    @Column(name="adjust_total", nullable = false)
    private BigDecimal adjTotal = BigDecimal.ZERO;

    public Integer getOverviewId() {
        return overviewId;
    }

    public void setOverviewId(Integer overviewId) {
        this.overviewId = overviewId;
    }

    public Date getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(Date adjustDate) {
        this.adjustDate = adjustDate;
    }

    public BigDecimal getOriTotal() {
        return oriTotal;
    }

    public void setOriTotal(BigDecimal oriTotal) {
        this.oriTotal = oriTotal;
    }

    public BigDecimal getAdjTotal() {
        return adjTotal;
    }

    public void setAdjTotal(BigDecimal adjTotal) {
        this.adjTotal = adjTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderAdjustmentOverviewEntity that = (OrderAdjustmentOverviewEntity) o;
        return getOverviewId() == that.getOverviewId() &&
                Objects.equal(getAdjustDate(), that.getAdjustDate()) &&
                Objects.equal(getOriTotal(), that.getOriTotal()) &&
                Objects.equal(getAdjTotal(), that.getAdjTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOverviewId(), getAdjustDate(), getOriTotal(), getAdjTotal());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("overviewId", overviewId)
                .add("adjustDate", adjustDate)
                .add("oriTotal", oriTotal)
                .add("adjTotal", adjTotal)
                .toString();
    }
}
