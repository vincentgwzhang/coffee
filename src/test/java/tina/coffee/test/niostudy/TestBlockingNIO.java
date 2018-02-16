package tina.coffee.test.niostudy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 阻塞式
 */
public class TestBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException{
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));//1. 获取通道

        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);

        ByteBuffer buf = ByteBuffer.allocate(1024);//2. 分配指定大小的缓冲区

        while(inChannel.read(buf) != -1){//3. 读取本地文件，并发送到服务端
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        inChannel.close();
        sChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException{
        ServerSocketChannel ssChannel = ServerSocketChannel.open();//1. 获取通道
        FileChannel outChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ssChannel.bind(new InetSocketAddress(9898));//2. 绑定连接
        SocketChannel sChannel = ssChannel.accept();//3. 获取客户端连接的通道
        ByteBuffer buf = ByteBuffer.allocate(1024);//4. 分配指定大小的缓冲区

        //5. 接收客户端的数据，并保存到本地
        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        sChannel.close();//6. 关闭通道
        outChannel.close();
        ssChannel.close();
    }

}