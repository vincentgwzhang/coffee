package tina.coffee.function.print;

import com.google.common.collect.Lists;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;

/**
 * Created by Vincent Zhang
 * Created on 2017/10/7 - 19:36
 * Create this class only for study
 * Source from:
 */
public class PrintableBasicTicket implements Printable {

    private List<String> header = Lists.newArrayList();
    private List<String> footer = Lists.newArrayList();
    private List<PrintItem> printItems = Lists.newArrayList();

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) graphics;
        Font font = new Font("宋体", Font.BOLD, 10);
        g2.setFont(font);
        g2.setColor(Color.black);
        g2.setClip(0, 0, 500, 500);

        double x = 20d; // init x coordinator
        double y = 0d;  // init y coordinator
        float heigth = font.getSize2D();
        float line = heigth;

        g2.drawString("源辰信息科技有限公司", (float) x + 25, (float) y + line);line += 2 * heigth;

        for(int index = 0 ; index < header.size() ; index ++) {
            g2.drawString( header.get(index), (float) x, (float) y + line );
            line += heigth;
        }

        // Set title
        g2.drawString("名称", (float) x + 20, (float) y + line);
        g2.drawString("单价", (float) x + 60, (float) y + line);
        g2.drawString("数量", (float) x + 90, (float) y + line);
        g2.drawString("小计", (float) x + 120, (float) y + line);
        line += heigth;

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + 158, (int) (y + line));
        line += heigth;

        // Iterate goods
        if (printItems != null && printItems.size() > 0) {
            for (PrintItem gdf : printItems) {
                g2.drawString(gdf.getName(), (float) x + 15, (float) y + line);
                g2.drawString(gdf.getUnitPrice(), (float) x + 60, (float) y + line);
                g2.drawString(gdf.getCount(), (float) x + 95, (float) y + line);
                g2.drawString(gdf.getTotalPrice(), (float) x + 120, (float) y + line);
                line += heigth;
            }
        }

        g2.drawLine((int) x, (int) (y + line), (int) x + 158, (int) (y + line));line += heigth;

        // Set footer
        for(int index = 0 ; index < footer.size() ; index ++) {
            g2.drawString( footer.get(index), (float) x, (float) y + line );
            line += heigth;
        }

        switch (pageIndex) {
            case 0:
                return PAGE_EXISTS; // 0
            default:
                return NO_SUCH_PAGE; // 1
        }
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public void setFooter(List<String> footer) {
        this.footer = footer;
    }

    public void setPrintItems(List<PrintItem> printItems) {
        this.printItems = printItems;
    }
}
