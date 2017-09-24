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
@Table(name="order_item_adjustment")
public class OrderItemAdjustmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oiaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="original_order_item_id", nullable=false)
    private OrderItemEntity orderItemEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_adjustment_id", nullable=false)
    private OrderAdjustmentEntity orderAdjustmentEntity;

    @Column(name="original_price", nullable = false)
    private BigDecimal oriItemPrice = BigDecimal.ZERO;

    @Column(name="original_count", nullable = false)
    private int oriItemCount;

    @Column(name="adjust_price", nullable = false)
    private BigDecimal adjItemPrice = BigDecimal.ZERO;

    @Column(name="adjust_count", nullable = false)
    private int adjItemCount;

    public int getOiaId() {
        return oiaId;
    }

    public void setOiaId(int oiaId) {
        this.oiaId = oiaId;
    }

    public OrderItemEntity getOrderItemEntity() {
        return orderItemEntity;
    }

    public void setOrderItemEntity(OrderItemEntity orderItemEntity) {
        this.orderItemEntity = orderItemEntity;
    }

    public OrderAdjustmentEntity getOrderAdjustmentEntity() {
        return orderAdjustmentEntity;
    }

    public void setOrderAdjustmentEntity(OrderAdjustmentEntity orderAdjustmentEntity) {
        this.orderAdjustmentEntity = orderAdjustmentEntity;
    }

    public BigDecimal getOriItemPrice() {
        return oriItemPrice;
    }

    public void setOriItemPrice(BigDecimal oriItemPrice) {
        this.oriItemPrice = oriItemPrice;
    }

    public int getOriItemCount() {
        return oriItemCount;
    }

    public void setOriItemCount(int oriItemCount) {
        this.oriItemCount = oriItemCount;
    }

    public BigDecimal getAdjItemPrice() {
        return adjItemPrice;
    }

    public void setAdjItemPrice(BigDecimal adjItemPrice) {
        this.adjItemPrice = adjItemPrice;
    }

    public int getAdjItemCount() {
        return adjItemCount;
    }

    public void setAdjItemCount(int adjItemCount) {
        this.adjItemCount = adjItemCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemAdjustmentEntity that = (OrderItemAdjustmentEntity) o;
        return getOiaId() == that.getOiaId() &&
                getOriItemCount() == that.getOriItemCount() &&
                getAdjItemCount() == that.getAdjItemCount() &&
                Objects.equal(getOriItemPrice(), that.getOriItemPrice()) &&
                Objects.equal(getAdjItemPrice(), that.getAdjItemPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOiaId(), getOriItemPrice(), getOriItemCount(), getAdjItemPrice(), getAdjItemCount());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("oiaId", oiaId)
                .add("oriItemPrice", oriItemPrice)
                .add("oriItemCount", oriItemCount)
                .add("adjItemPrice", adjItemPrice)
                .add("adjItemCount", adjItemCount)
                .toString();
    }
}
