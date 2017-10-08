package tina.coffee.function.print;

import com.google.common.collect.Lists;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private BigDecimal totalPrice;

    private final double IVA_RATE = 0.1d;
    private final Font bigFont = new Font("宋体", Font.BOLD, 15);
    private final Font font    = new Font("宋体", Font.BOLD, 10);

    private final Integer RIGHT_PIX = 180;

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setFont(font);
        g2.setColor(Color.black);
        g2.setClip(0, 0, 500, 500);

        double x = 20d; // init x coordinator
        double y = 0d;  // init y coordinator
        float heigth = font.getSize2D();
        float line = heigth;

        g2.setFont(bigFont);
        g2.drawString("RELAX", (float) x + 60, (float) y + line);line += 2 * heigth;
        g2.setFont(font);

        line += 5;

        for(int index = 0 ; index < header.size() ; index ++) {
            g2.drawString( header.get(index), (float) x + 40, (float) y + line );
            line += heigth;
        }

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + RIGHT_PIX, (int) (y + line));
        line += heigth;



        // Set title
        g2.drawString("CAN.", (float) x, (float) y + line);
        g2.drawString("DESCRIPTION", (float) x + 30, (float) y + line);
        g2.drawString("PRE.", (float) x + 120, (float) y + line);
        g2.drawString("SUMA", (float) x + 150, (float) y + line);
        line += heigth;

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + RIGHT_PIX, (int) (y + line));
        line += heigth;

        // Iterate goods
        if (printItems != null && printItems.size() > 0) {
            for (PrintItem gdf : printItems) {
                g2.drawString(gdf.getCount(), (float) x, (float) y + line);
                g2.drawString(gdf.getName(), (float) x + 30, (float) y + line);
                g2.drawString(gdf.getUnitPrice(), (float) x + 120, (float) y + line);
                g2.drawString(gdf.getTotalPrice(), (float) x + 150, (float) y + line);
                line += heigth;
            }
        }

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + RIGHT_PIX, (int) (y + line));line += heigth;

        line += heigth;

        g2.setFont(bigFont);
        g2.drawString("Total: " + totalPrice.toString() + " Euro", (float) x + 25, (float) y + line);line += 2 * heigth;
        g2.setFont(font);

        g2.drawString("IVA INCLUIDO", (float) x + 40, (float) y + line);line += heigth;
        g2.drawString("GRACIAS POR SU VISITA", (float) x + 15, (float) y + line);line += heigth;

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + RIGHT_PIX, (int) (y + line));
        line += heigth;

        g2.drawString("Imponible", (float) x + 0, (float) y + line);
        g2.drawString("IVA%", (float) x + 60, (float) y + line);
        g2.drawString("IVA", (float) x + 120, (float) y + line);
        g2.drawString("Total", (float) x + 150, (float) y + line);
        line += heigth;

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + RIGHT_PIX, (int) (y + line));
        line += heigth;

        BigDecimal beforeTax = totalPrice.divide(BigDecimal.valueOf(1.0d + IVA_RATE), 2, RoundingMode.HALF_UP);
        BigDecimal taxAmount = beforeTax.multiply(BigDecimal.valueOf(IVA_RATE)).setScale(2, RoundingMode.HALF_UP);

        g2.drawString(beforeTax.toString(), (float) x + 0, (float) y + line);
        g2.drawString(String.valueOf(IVA_RATE) + "%", (float) x + 60, (float) y + line);
        g2.drawString(taxAmount.toString(), (float) x + 120, (float) y + line);
        g2.drawString(totalPrice.toString(), (float) x + 150, (float) y + line);
        line += heigth;

        // Set line
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, new float[] { 4.0f }, 0.0f));
        g2.drawLine((int) x, (int) (y + line), (int) x + RIGHT_PIX, (int) (y + line));
        line += heigth;

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

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
