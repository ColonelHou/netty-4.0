package io.netty.example.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

public class NioTest {
    public static void main(String[] args) {

    }

    public void testChannel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("");
        FileChannel channel = fileInputStream.getChannel();
        // -----------------读
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        channel.read(byteBuffer);
        byteBuffer.flip();
        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println(b);
        }
        fileInputStream.close();
        // ----------------写
        FileOutputStream outputStream = new FileOutputStream("");
        FileChannel outChannel = outputStream.getChannel();
        ByteBuffer byteBufferWrite = ByteBuffer.allocate(512);
        byte[] msg = "Hello world".getBytes();
        for (int i = 0; i < msg.length; i ++) {
            byteBufferWrite.put(msg[i]);
            System.out.println("position: " + byteBufferWrite.position());
            System.out.println("limit : " + byteBufferWrite.limit());
            System.out.println("capacity: " + byteBufferWrite.capacity());
        }
        byteBufferWrite.flip();
        outChannel.write(byteBufferWrite);
        outputStream.close();
    }

    public void testBuffer() {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i ++) {
            int r = new SecureRandom().nextInt(20);
            buffer.put(r);
        }
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
