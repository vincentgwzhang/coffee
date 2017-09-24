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
@Table(name="orders_adjustment")
public class OrderAdjustmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="overview_id", nullable=false)
    private OrderAdjustmentOverviewEntity overviewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="original_order_id", nullable=false)
    private OrderEntity orderEntity;

    @Column(name="original_price", nullable = false)
    private BigDecimal oriOrderPrice = BigDecimal.ZERO;

    @Column(name="updated_price", nullable = false)
    private BigDecimal adjOrderPrice = BigDecimal.ZERO;

    public int getOaId() {
        return oaId;
    }

    public void setOaId(int oaId) {
        this.oaId = oaId;
    }

    public OrderAdjustmentOverviewEntity getOverviewEntity() {
        return overviewEntity;
    }

    public void setOverviewEntity(OrderAdjustmentOverviewEntity overviewEntity) {
        this.overviewEntity = overviewEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public BigDecimal getOriOrderPrice() {
        return oriOrderPrice;
    }

    public void setOriOrderPrice(BigDecimal oriOrderPrice) {
        this.oriOrderPrice = oriOrderPrice;
    }

    public BigDecimal getAdjOrderPrice() {
        return adjOrderPrice;
    }

    public void setAdjOrderPrice(BigDecimal adjOrderPrice) {
        this.adjOrderPrice = adjOrderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderAdjustmentEntity that = (OrderAdjustmentEntity) o;
        return getOaId() == that.getOaId() &&
                Objects.equal(getOriOrderPrice(), that.getOriOrderPrice()) &&
                Objects.equal(getAdjOrderPrice(), that.getAdjOrderPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOaId(), getOriOrderPrice(), getAdjOrderPrice());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("oaId", oaId)
                .add("oriOrderPrice", oriOrderPrice)
                .add("adjOrderPrice", adjOrderPrice)
                .toString();
    }
}
