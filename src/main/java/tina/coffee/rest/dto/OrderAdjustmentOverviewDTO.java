package tina.coffee.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

import static tina.coffee.system.config.SystemConstant.SHORT_DATE_FORMAT;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrderAdjustmentOverviewDTO",
        propOrder = {
                "overviewId",
                "adjustDate",
                "oriTotal",
                "adjTotal"
        }
)
@XmlRootElement(name="OrderAdjustmentOverviewDTO")
public class OrderAdjustmentOverviewDTO {

    private Integer overviewId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= SHORT_DATE_FORMAT)
    private Date adjustDate;

    private String oriTotal;

    private String adjTotal;

    public Integer getOverviewId() {
        return overviewId;
    }

    public void setOverviewId(Integer overviewId) {
        this.overviewId = overviewId;
    }

    public Date getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(Date adjustDate) {
        this.adjustDate = adjustDate;
    }

    public String getOriTotal() {
        return oriTotal;
    }

    public void setOriTotal(String oriTotal) {
        this.oriTotal = oriTotal;
    }

    public String getAdjTotal() {
        return adjTotal;
    }

    public void setAdjTotal(String adjTotal) {
        this.adjTotal = adjTotal;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
