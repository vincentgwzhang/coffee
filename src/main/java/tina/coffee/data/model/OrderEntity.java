package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_desktop_no", referencedColumnName="desk_no", nullable=true)
    private DesktopEntity desktopEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_start_time", nullable = false)
    private Calendar startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_end_time", nullable = true)
    private Calendar endTime;

    @Column(name="order_total_price", nullable = true)
    private BigDecimal totalPrice;

    @Column(name="order_actual_price", nullable = true)
    private BigDecimal actualPrice;

    @Enumerated(EnumType.STRING)
    @Column(name="order_type", nullable=false)
    private OrderType orderType;

    @ElementCollection(fetch= FetchType.LAZY)
    @OneToMany(mappedBy="order", fetch= FetchType.LAZY)
    @OrderBy("orderItemId asc")
    private Set<OrderItemEntity> items;

    public Set<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemEntity> items) {
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public DesktopEntity getDesktopEntity() {
        return desktopEntity;
    }

    public void setDesktopEntity(DesktopEntity desktopEntity) {
        this.desktopEntity = desktopEntity;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return getOrderId() == that.getOrderId() &&
                Objects.equal(getStartTime(), that.getStartTime()) &&
                Objects.equal(getEndTime(), that.getEndTime()) &&
                Objects.equal(getTotalPrice(), that.getTotalPrice()) &&
                Objects.equal(getActualPrice(), that.getActualPrice()) &&
                getOrderType() == that.getOrderType();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderId(), getStartTime(), getEndTime(), getTotalPrice(), getActualPrice(), getOrderType());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("totalPrice", totalPrice)
                .add("actualPrice", actualPrice)
                .add("orderType", orderType)
                .toString();
    }
}
