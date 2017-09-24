package tina.coffee.rest.dto;

import com.google.common.base.MoreObjects;
import tina.coffee.function.JsonFunction;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "MenuCategoryDTO",
        propOrder = {
                "deskId",
                "deskNo",
                "deskEnable",
                "deskOccupied"
        }
)
@XmlRootElement(name="DesktopDTO")
public class DesktopDTO {

    private int deskId;

    @NotNull
    private int deskNo;

    @NotNull
    private boolean deskEnable;

    @NotNull
    private boolean deskOccupied;

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public int getDeskNo() {
        return deskNo;
    }

    public void setDeskNo(int deskNo) {
        this.deskNo = deskNo;
    }

    public boolean isDeskEnable() {
        return deskEnable;
    }

    public void setDeskEnable(boolean deskEnable) {
        this.deskEnable = deskEnable;
    }

    public boolean isDeskOccupied() {
        return deskOccupied;
    }

    public void setDeskOccupied(boolean deskOccupied) {
        this.deskOccupied = deskOccupied;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
