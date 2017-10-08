package tina.coffee.function.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tina.coffee.system.exceptions.printer.PrinterNotExistException;
import tina.coffee.system.exceptions.printer.PrinterNotWorkException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PrinterFunction {

    private static Logger logger = LoggerFactory.getLogger("PrinterFunction");

    public static boolean print(List<String> header, List<String> footer, List<PrintItem> items, String printerName, BigDecimal totalPrice) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(1));

        Optional<PrintService> printServiceOptional = Arrays.asList(services).stream().filter(service -> service.toString().endsWith(printerName)).findFirst();
        PrintService printService = printServiceOptional.orElseThrow(()-> new PrinterNotExistException());
        DocPrintJob printjob = printService.createPrintJob();

        PrintableBasicTicket newTickets = new PrintableBasicTicket();
        newTickets.setHeader(header);
        newTickets.setFooter(footer);
        newTickets.setPrintItems(items);
        newTickets.setTotalPrice(totalPrice);

        Doc doc=new SimpleDoc(newTickets, DocFlavor.SERVICE_FORMATTED.PRINTABLE,null);

        try {
            printjob.print(doc,aset);
        } catch (PrintException e) {
            logger.error(e.getMessage(), e);
            throw new PrinterNotWorkException();
        }
        return true;
    }

}
