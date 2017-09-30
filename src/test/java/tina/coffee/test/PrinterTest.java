package tina.coffee.test;

import org.junit.Test;
import tina.coffee.function.PrinterFunction;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Vincent Zhang
 * Created on 2017/9/25 - 1:46
 * Create this class only for study
 * Source from:
 */
public class PrinterTest {

    @Test
    public void testLookupService() {
        PrinterFunction.print();
    }

    public void getPrinter(DocFlavor psInFormat) {
        FileInputStream psStream = null;
        try {
            psStream = new FileInputStream(new File("C:\\Download\\1.png"));
        } catch (FileNotFoundException ffne) {
            ffne.printStackTrace();
        }

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(1));

        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);

        for (int i = 0; i < services.length; i++) {
            String svcName = services[i].toString();
            System.out.println(svcName);
        }
    }

}
