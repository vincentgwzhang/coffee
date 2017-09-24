package tina.coffee.rest.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "MenuQueueDTO",
        propOrder = {
                "mqId",
                "descEN",
                "descSP",
                "descCN",
                "desktopNumber",
                "count",
                "status",
        }
)
@XmlRootElement(name="MenuQueueDTO")
public class MenuQueueDTO {

    private int mqId;

    private String descEN;

    private String descSP;

    private String descCN;

    private int desktopNumber;

    private int count;

    private String status;//Order item status

    public int getMqId() {
        return mqId;
    }

    public void setMqId(int mqId) {
        this.mqId = mqId;
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

    public int getDesktopNumber() {
        return desktopNumber;
    }

    public void setDesktopNumber(int desktopNumber) {
        this.desktopNumber = desktopNumber;
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
