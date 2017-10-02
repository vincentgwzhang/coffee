package tina.coffee.rest.dto;

import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CloseTakeAwayDTO",
        propOrder = {
                "orderItemDTOList",
                "actualPrice"
        }
)
@XmlRootElement(name="CloseTakeAwayDTO")
public class CloseTakeAwayDTO {

    private List<CloseOrderItemDTO> orderItemDTOList;

    private BigDecimal actualPrice;

    public List<CloseOrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<CloseOrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
