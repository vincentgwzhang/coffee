package tina.coffee.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

import static tina.coffee.system.config.SystemConstant.SHORT_DATE_FORMAT;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "ImportHistorySummaryDTO",
        propOrder = {
                "ihsId",
                "ihsPrice",
                "ihsTime"
        }
)
@XmlRootElement(name="ImportHistorySummaryDTO")
public class ImportHistorySummaryDTO {

    private int ihsId;

    private BigDecimal ihsPrice;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= SHORT_DATE_FORMAT)
    private Date ihsTime;

    public int getIhsId() {
        return ihsId;
    }

    public void setIhsId(int ihsId) {
        this.ihsId = ihsId;
    }

    public BigDecimal getIhsPrice() {
        return ihsPrice;
    }

    public void setIhsPrice(BigDecimal ihsPrice) {
        this.ihsPrice = ihsPrice;
    }

    public Date getIhsTime() {
        return ihsTime;
    }

    public void setIhsTime(Date ihsTime) {
        this.ihsTime = ihsTime;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
