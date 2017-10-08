package tina.coffee.system.prop;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:socket_printer.properties")
public class SocketPrinterConfig {

    @Value("${printer_ip}")
    private String printerIP = null;

    @Value("${printer_port}")
    private int printerPort  = 0;

    public String getPrinterIP() {
        return printerIP;
    }

    public void setPrinterIP(String printerIP) {
        this.printerIP = printerIP;
    }

    public int getPrinterPort() {
        return printerPort;
    }

    public void setPrinterPort(int printerPort) {
        this.printerPort = printerPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketPrinterConfig that = (SocketPrinterConfig) o;
        return getPrinterPort() == that.getPrinterPort() &&
                Objects.equal(getPrinterIP(), that.getPrinterIP());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPrinterIP(), getPrinterPort());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("printerIP", printerIP)
                .add("printerPort", printerPort)
                .toString();
    }
}
