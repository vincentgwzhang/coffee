package tina.coffee.rest.dto;

import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "ImportHistoryDTO",
        propOrder = {
                "ihId",
                "ihIhsId",
                "ihIpId",
                "ihPrice",
                "count"
        }
)
@XmlRootElement(name="ImportHistoryDTO")
public class ImportHistoryDTO {

    private int ihId;

    private int ihIhsId;

    private int ihIpId;

    private BigDecimal ihPrice;

    private int count = 0;

    public int getIhId() {
        return ihId;
    }

    public void setIhId(int ihId) {
        this.ihId = ihId;
    }

    public int getIhIhsId() {
        return ihIhsId;
    }

    public void setIhIhsId(int ihIhsId) {
        this.ihIhsId = ihIhsId;
    }

    public int getIhIpId() {
        return ihIpId;
    }

    public void setIhIpId(int ihIpId) {
        this.ihIpId = ihIpId;
    }

    public BigDecimal getIhPrice() {
        return ihPrice;
    }

    public void setIhPrice(BigDecimal ihPrice) {
        this.ihPrice = ihPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
