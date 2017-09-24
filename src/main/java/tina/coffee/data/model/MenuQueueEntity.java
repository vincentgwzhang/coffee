package tina.coffee.data.model;

import javax.persistence.*;

@Entity
@Table(name="menu_queue")
public class MenuQueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mqId;

    @OneToOne
    @JoinColumn(name="mq_oi_id", nullable=false, unique=true)
    private OrderItemEntity orderItemEntity;

    public int getMqId() {
        return mqId;
    }

    public void setMqId(int mqId) {
        this.mqId = mqId;
    }

    public OrderItemEntity getOrderItemEntity() {
        return orderItemEntity;
    }

    public void setOrderItemEntity(OrderItemEntity orderItemEntity) {
        this.orderItemEntity = orderItemEntity;
    }
}
