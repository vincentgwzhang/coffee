package tina.coffee.function.print;

/**
 * Created by Vincent Zhang
 * Created on 2017/10/7 - 19:15
 * Create this class only for study
 * Source from:
 */
public class PrintItem {

    private String name;
    private String unitPrice;
    private String count;
    private String totalPrice;

    public PrintItem(String name, String unitPrice, String count, String totalPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
