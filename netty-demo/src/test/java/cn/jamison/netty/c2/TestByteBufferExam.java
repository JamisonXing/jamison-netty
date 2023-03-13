package cn.jamison.netty.c2;

import java.nio.ByteBuffer;

import static cn.jamison.netty.c2.ByteBufferUtil.debugAll;

public class TestByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        //                     11            24
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);

        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for(int i = 0; i < source.limit(); i++) {
            //找到一条完整消息
            if(source.get(i) == '\n') {
                int length = i - source.position() + 1;
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
    }
}
