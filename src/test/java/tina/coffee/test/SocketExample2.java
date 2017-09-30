package tina.coffee.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Vincent Zhang
 * Created on 2017/9/26 - 1:24
 * Create this class only for study
 * Source from:
 */
public class SocketExample2 {

    public static void main(String[] args) throws IOException {

        Socket client=new java.net.Socket();
        OutputStreamWriter socketWriter;
        client.connect(new InetSocketAddress("192.168.1.100" , 9100),1000);

        Socket posSender=new java.net.Socket();
        PrintWriter posWriter;
        posSender.connect(new InetSocketAddress("192.168.1.100" , 4000),1000);

        socketWriter = new OutputStreamWriter(client.getOutputStream(), "GB2312");
        posWriter = new PrintWriter(posSender.getOutputStream());

        socketWriter.write("巧富餐饮软件后厨单据\n");
        socketWriter.write("桌位 14 桌，人数 3 \n");
        socketWriter.write("跺脚鱼头 1 份\n");
        for(int i = 0; i < 3; i++)
        socketWriter.write("\n");
        socketWriter.write(0x1c);

        socketWriter.write(0x1c);
        socketWriter.write(0x21);
        socketWriter.write(4);

        socketWriter.write(0x1c);
        socketWriter.write(0x21);
        socketWriter.write(8);

        for(int i=0;i<10;i++){
            socketWriter.write(" ");// 打印完毕自动走纸
        }
        socketWriter.flush();

        posWriter.write(0x1B69);
        posWriter.flush();

        posWriter.close();
        socketWriter.close();
        client.close();
    }

}
