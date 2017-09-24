package tina.coffee.rest.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrderItemDTO",
        propOrder = {
                "orderItemId",
                "orderId",
                "descEN",
                "descSP",
                "descCN",
                "price",
                "count",
                "status",
        }
)
@XmlRootElement(name="OrderItemDTO")
public class OrderItemDTO {

    private int orderItemId;

    private int orderId;

    private String descEN;

    private String descSP;

    private String descCN;

    private BigDecimal price;

    private int count;

    private String status;

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDescEN() {
        return descEN;
    }

    public void setDescEN(String descEN) {
        this.descEN = descEN;
    }

    public String getDescSP() {
        return descSP;
    }

    public void setDescSP(String descSP) {
        this.descSP = descSP;
    }

    public String getDescCN() {
        return descCN;
    }

    public void setDescCN(String descCN) {
        this.descCN = descCN;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
