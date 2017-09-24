package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Entity
@Table(name="order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_item_order_id", nullable=false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_item_menu_item_id", nullable=false)
    private MenuItemEntity menuItem;

    @Column(name="order_item_count", nullable=false)
    private int count;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_item_start_time", nullable = false)
    private Calendar startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_item_end_time", nullable = true)
    private Calendar endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="order_item_status", nullable=false)
    private OrderItemStatus status;

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

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public MenuItemEntity getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemEntity menuItem) {
        this.menuItem = menuItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return getOrderItemId() == that.getOrderItemId() &&
                getCount() == that.getCount() &&
                Objects.equal(getOrder(), that.getOrder()) &&
                Objects.equal(getStartTime(), that.getStartTime()) &&
                Objects.equal(getEndTime(), that.getEndTime()) &&
                getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderItemId(), getOrder(), getCount(), getStartTime(), getEndTime(), getStatus());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderItemId", orderItemId)
                .add("order", order)
                .add("count", count)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("status", status)
                .toString();
    }
}
