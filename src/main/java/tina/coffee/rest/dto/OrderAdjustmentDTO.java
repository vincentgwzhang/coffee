package tina.coffee.rest.dto;

import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrderAdjustmentDTO",
        propOrder = {
                "oaId",
                "overviewId",
                "oriOrderId",
                "oriOrderPrice",
                "adjOrderPrice"
        }
)
@XmlRootElement(name="OrderAdjustmentDTO")
public class OrderAdjustmentDTO {

    private Integer oaId;

    private Integer overviewId;

    private Integer oriOrderId;

    private String oriOrderPrice;

    private String adjOrderPrice;

    public Integer getOaId() {
        return oaId;
    }

    public void setOaId(Integer oaId) {
        this.oaId = oaId;
    }

    public Integer getOverviewId() {
        return overviewId;
    }

    public void setOverviewId(Integer overviewId) {
        this.overviewId = overviewId;
    }

    public Integer getOriOrderId() {
        return oriOrderId;
    }

    public void setOriOrderId(Integer oriOrderId) {
        this.oriOrderId = oriOrderId;
    }

    public String getOriOrderPrice() {
        return oriOrderPrice;
    }

    public void setOriOrderPrice(String oriOrderPrice) {
        this.oriOrderPrice = oriOrderPrice;
    }

    public String getAdjOrderPrice() {
        return adjOrderPrice;
    }

    public void setAdjOrderPrice(String adjOrderPrice) {
        this.adjOrderPrice = adjOrderPrice;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
