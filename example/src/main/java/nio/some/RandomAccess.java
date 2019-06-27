package nio.some;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

public class RandomAccess {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(
                "/Users/houningning/Documents/opensource/netty/" +
                        "code/netty-4.0/example/src/main/" +
                        "java/nio/some/a.txt", "rw");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.flip();

        FileChannel inChannel = randomAccessFile.getChannel();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
        socketChannel.close();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 80));
        boolean b = true;
        while (b) {
            SocketChannel socketChannel1 = serverSocketChannel.accept();
        }

        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink(); // 向管道写数据
//        AsynchronousFileChannel asynFileChannel = AsynchronousFileChannel.open(
//                "", StandardOpenOption.READ);

        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        while (true) {
            selector.select();
            Set<SelectionKey> selectorKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectorKeys.iterator();
            while (it.hasNext()) {
                SelectionKey sKey = it.next();
                if (sKey.isAcceptable()) {

                }
                it.remove();
            }

        }


    }
}
