package tina.coffee.rest.dto;

import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CloseBillDTO",
        propOrder = {
                "actualPrice",
                "customerPay",
                "customerReceive"
        }
)
@XmlRootElement(name="CloseBillDTO")
public class CloseBillDTO {

    private BigDecimal actualPrice;

    private BigDecimal customerPay;

    private BigDecimal customerReceive;

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getCustomerPay() {
        return customerPay;
    }

    public void setCustomerPay(BigDecimal customerPay) {
        this.customerPay = customerPay;
    }

    public BigDecimal getCustomerReceive() {
        return customerReceive;
    }

    public void setCustomerReceive(BigDecimal customerReceive) {
        this.customerReceive = customerReceive;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
