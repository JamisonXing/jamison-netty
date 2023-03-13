package cn.jamison.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        try(RandomAccessFile file = new RandomAccessFile("data.txt", "rw");) {
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            do{
                //向buffer写入
                int len = channel.read(buffer);
                log.debug("读到的字符数：{}", len);
                if(len == -1) {
                    break;
                }
                //切换buffer读模式
                buffer.flip();
                while(buffer.hasRemaining()) {
                    log.debug("实际字节{}", (char)buffer.get());
                }
                //切换buffer为写模式
                buffer.clear();
            }while(true);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
