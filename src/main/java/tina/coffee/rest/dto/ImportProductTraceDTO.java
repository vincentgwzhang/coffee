package tina.coffee.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
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
        name = "ImportProductTraceDTO",
        propOrder = {
                "date",
                "ihPrice",
                "count"
        }
)
@XmlRootElement(name="ImportProductTraceDTO")
public class ImportProductTraceDTO {

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= SHORT_DATE_FORMAT)
    @DateTimeFormat(pattern = SHORT_DATE_FORMAT)
    private Date date;

    private BigDecimal ihPrice;

    private int count;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
