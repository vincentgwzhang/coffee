package tina.coffee.rest.dto;

import org.hibernate.validator.constraints.NotBlank;
import tina.coffee.function.JsonFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "MenuCategoryDTO",
        propOrder = {
                "mcId",
                "mcName",
                "mcEnable",
                "descEN",
                "descSP",
                "descCN"
        }
)
@XmlRootElement(name="MenuCategoryDTO")
public class MenuCategoryDTO {

    private int mcId;

    private String mcName;

    private boolean mcEnable;

    @NotBlank
    private String descEN;

    @NotBlank
    private String descSP;

    @NotBlank
    private String descCN;

    public int getMcId() {
        return mcId;
    }

    public void setMcId(int mcId) {
        this.mcId = mcId;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public boolean isMcEnable() {
        return mcEnable;
    }

    public void setMcEnable(boolean mcEnable) {
        this.mcEnable = mcEnable;
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

    @Override
    public String toString() {
        return JsonFunction.toJson(this);
    }
}
