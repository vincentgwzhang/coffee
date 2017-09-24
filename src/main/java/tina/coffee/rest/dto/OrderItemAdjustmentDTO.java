package tina.coffee.rest.dto;

import tina.coffee.data.model.OrderAdjustmentEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.function.JsonFunction;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrderItemAdjustmentDTO",
        propOrder = {
                "oiaId",
                "orderItemId",
                "orderAdjustmentId",
                "oriItemPrice",
                "oriItemCount",
                "adjItemPrice",
                "adjItemCount"
        }
)
@XmlRootElement(name="OrderItemAdjustmentDTO")
public class OrderItemAdjustmentDTO {

    private Integer oiaId;

    private Integer orderItemId;

    private Integer orderAdjustmentId;

    private String oriItemPrice;

    private int oriItemCount;

    private String adjItemPrice;

    private int adjItemCount;

    public Integer getOiaId() {
        return oiaId;
    }

    public void setOiaId(Integer oiaId) {
        this.oiaId = oiaId;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderAdjustmentId() {
        return orderAdjustmentId;
    }

    public void setOrderAdjustmentId(Integer orderAdjustmentId) {
        this.orderAdjustmentId = orderAdjustmentId;
    }

    public int getOriItemCount() {
        return oriItemCount;
    }

    public void setOriItemCount(int oriItemCount) {
        this.oriItemCount = oriItemCount;
    }

    public int getAdjItemCount() {
        return adjItemCount;
    }

    public void setAdjItemCount(int adjItemCount) {
        this.adjItemCount = adjItemCount;
    }

    public String getOriItemPrice() {
        return oriItemPrice;
    }

    public void setOriItemPrice(String oriItemPrice) {
        this.oriItemPrice = oriItemPrice;
    }

    public String getAdjItemPrice() {
        return adjItemPrice;
    }

    public void setAdjItemPrice(String adjItemPrice) {
        this.adjItemPrice = adjItemPrice;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
