package tina.coffee.function.print;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketPrinter {

    private OutputStream socketOut = null;
    private OutputStreamWriter writer = null;

    public SocketPrinter(String printerIp, int port) throws IOException {
        Socket socket = new Socket(printerIp, port);
        socket.setSoTimeout(1000);
        socketOut = socket.getOutputStream();
        writer = new OutputStreamWriter(socketOut, "GBK");
        socketOut.write(27);
        socketOut.write(27);
    }

    public void resetToDefault() throws IOException {
        setInverse(false);
        setBold(false);
        setFontDefault();
        setUnderline(0);
        setJustification(0);
        writer.flush();
    }

    public void setBold(Boolean bool) throws IOException {
        writer.write(0x1B);
        writer.write("E");
        writer.write((int) (bool ? 1 : 0));
        writer.flush();
    }

    public void setFontZoomIn() throws IOException {
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(12);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(12);
        writer.flush();
    }

    public void setFontZoomInWidth() throws IOException {
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(4);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(4);
        writer.flush();
    }

    public void setFontZoomInHeight() throws IOException {
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(8);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(8);
        writer.flush();
    }

    public void setFontDefault() throws IOException {

        writer.write(0x1c);
        writer.write(0x21);
        writer.write(1);
        writer.flush();
    }

    public void setInverse(Boolean bool) throws IOException {
        writer.write(0x1D);
        writer.write("B");
        writer.write((int) (bool ? 1 : 0));
        writer.flush();
    }

    public void setUnderline(int val) throws IOException {
        writer.write(0x1B);
        writer.write("-");
        writer.write(val);
        writer.flush();
    }

    public void setJustification(int val) throws IOException {
        writer.write(0x1B);
        writer.write("a");
        writer.write(val);
        writer.flush();
    }

    public void printStr(String str) throws IOException {
        writer.write(str);
        writer.flush();
    }

    public void printlnStr(String str) throws IOException {
        writer.write(str + "\n");
        writer.flush();
    }

    public void printObj(Object obj) throws IOException {
        writer.write(obj.toString());
        writer.flush();
    }

    public void printlnObj(Object obj) throws IOException {
        writer.write(obj.toString() + "\n");
        writer.flush();
    }

    public void printLst(List lst, String separator) throws IOException {
        for (Object o : lst) {
            this.printObj(o);
            this.printStr(separator);
        }
    }

    public void printMap(Map map, String kvSeparator, String itemSeparator) throws IOException {
        for (Object key : map.keySet()) {
            this.printObj(key);
            this.printStr(kvSeparator);
            this.printObj(map.get(key));
            this.printStr(itemSeparator);
        }
    }

    public void openCashbox() throws IOException {
        writer.write(27);
        writer.write(112);
        writer.write(0);
        writer.write(10);
        writer.write(10);

        writer.flush();
    }

    public void feedAndCut() throws IOException {
        feed();
        cut();
        writer.flush();
    }

    public void feed() throws IOException {
        writer.write(27);
        writer.write(100);
        writer.write(4);
        writer.write(10);
        writer.flush();
    }

    public void cut() throws IOException {
        writer.write(0x1D);
        writer.write("V");
        writer.write(48);
        writer.write(0);
        writer.flush();
    }

    public void closeConn() {
        try {
            writer.close();
            socketOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] argus) {
        List<String> strList = new ArrayList<>();
        strList.add("vincent 1");

        try {
            SocketPrinter printer = new SocketPrinter("192.168.1.100", 9100);
            printer.setJustification(0);
            printer.printLst(strList, "\n");
            printer.feedAndCut();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}