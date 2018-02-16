package tina.coffee.test.niostudy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException{
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));//1. 获取通道
        sChannel.configureBlocking(false);//2. 切换非阻塞模式-----------------------> 就多这一步
        ByteBuffer buf = ByteBuffer.allocate(1024);//3. 分配指定大小的缓冲区
        Scanner scan = new Scanner(System.in);//4. 发送数据给服务端
        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + ":" + str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
        sChannel.close();//5. 关闭通道
    }

    //服务端
    @Test
    public void server() throws IOException{
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        ssChannel.bind(new InetSocketAddress(9898));
        Selector selector = Selector.open();
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);//表示要监听的是接受状态
        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey sk = it.next();//8. 获取准备“就绪”的是事件
                if(sk.isAcceptable()){//9. 判断具体是什么事件准备就绪
                    SocketChannel sChannel = ssChannel.accept();//10. 若“接收就绪”，获取客户端连接
                    sChannel.configureBlocking(false);//11. 切换非阻塞模式
                    sChannel.register(selector, SelectionKey.OP_READ);//12. 将该通道注册到选择器上
                }else if(sk.isReadable()){
                    SocketChannel sChannel = (SocketChannel) sk.channel();//13. 获取当前选择器上“读就绪”状态的通道
                    ByteBuffer buf = ByteBuffer.allocate(1024);//14. 读取数据
                    int len = 0;
                    while((len = sChannel.read(buf)) > 0 ){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                it.remove();
            }
        }
    }
}