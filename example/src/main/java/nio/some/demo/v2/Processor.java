package nio.some.demo.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Processor {
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            10, 20,
            10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10)
    );
    private Selector selector;

    public Processor() throws IOException {
        /**
         * 1. 会调用操作系统底层的Selector实现来创建Selector
         * 2. 直接调用SelectorProvider类来获取操作系统底层的Selector来创建Selector。
         */
        this.selector = Selector.open();
        this.selector = SelectorProvider.provider().openSelector();
        start();
    }

    public void addChannel(SocketChannel socketChannel) throws ClosedChannelException {
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void wakeUp() {
        this.selector.wakeup();
    }

    public void start() {}/*
        poolExecutor.submit(() -> {
            while (true) {
                if (selector.select(500) < 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int cnt = socketChannel.read(buffer);
                        if (cnt < 0) {
                            socketChannel.close();
                            key.cancel();
                            continue;
                        }
                    }
                }
            }
        });
    }*/
}
