package tina.coffee.rest.dto;

import org.hibernate.validator.constraints.NotBlank;
import tina.coffee.function.JsonFunction;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "MenuItemDTO",
        propOrder = {
                "miId",
                "miMcId",
                "miPic",
                "miEnable",
                "toChief",
                "miPrice",
                "descEN",
                "descSP",
                "descCN"
        }
)
@XmlRootElement(name="MenuItemDTO")
public class MenuItemDTO {

    private int miId;

    @NotNull
    private int miMcId;

    private String miPic;

    private boolean miEnable;

    private boolean toChief;

    private String miPrice;

    @NotBlank
    private String descEN;

    @NotBlank
    private String descSP;

    @NotBlank
    private String descCN;

    public int getMiId() {
        return miId;
    }

    public void setMiId(int miId) {
        this.miId = miId;
    }

    public int getMiMcId() {
        return miMcId;
    }

    public void setMiMcId(int miMcId) {
        this.miMcId = miMcId;
    }

    public String getMiPic() {
        return miPic;
    }

    public void setMiPic(String miPic) {
        this.miPic = miPic;
    }

    public boolean isMiEnable() {
        return miEnable;
    }

    public void setMiEnable(boolean miEnable) {
        this.miEnable = miEnable;
    }

    public String getMiPrice() {
        return miPrice;
    }

    public void setMiPrice(String miPrice) {
        this.miPrice = miPrice;
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

    public boolean isToChief() {
        return toChief;
    }

    public void setToChief(boolean toChief) {
        this.toChief = toChief;
    }

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
