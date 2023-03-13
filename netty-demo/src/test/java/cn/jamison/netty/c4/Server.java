package cn.jamison.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.jamison.netty.c2.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //使用nio来理解阻塞模式
        //0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        //1. 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式

        //2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        //3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while(true) {
            //4. accept 建立与客户端的连接，SocketChannel用来与客户端之间通信
            SocketChannel sc = ssc.accept();//非阻塞方法，线程还会继续，如果没有连接建立，但sc是null
            if(sc != null) {
                log.debug("connected...{}", sc);
                sc.configureBlocking(false);//非阻塞模式
                channels.add(sc);
            }
            for(SocketChannel channel : channels) {
                //5. 接受客户端发送的数据
                int read = channel.read(buffer);//非阻塞方法，线程还会继续运行，如果没有得到数据，read返回0
                if(read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }
        }
    }
}
