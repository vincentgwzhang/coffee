package tina.coffee.rest.dto;

import com.google.common.collect.Sets;
import tina.coffee.data.model.OrderType;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrderDTO",
        propOrder = {
                "orderId",
                "desktopNumber",
                "startTime",
                "endTime",
                "totalPrice",
                "actualPrice",
                "orderType",
                "items",
        }
)
@XmlRootElement(name="OrderDTO")
public class OrderDTO {

    private int orderId;

    private int desktopNumber;

    private String startTime;

    private String endTime;

    private String totalPrice;

    private String actualPrice;

    private String orderType;

    private Set<OrderItemDTO> items = Sets.newHashSet();

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDesktopNumber() {
        return desktopNumber;
    }

    public void setDesktopNumber(int desktopNumber) {
        this.desktopNumber = desktopNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Set<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDTO> items) {
        this.items = items;
    }
}
