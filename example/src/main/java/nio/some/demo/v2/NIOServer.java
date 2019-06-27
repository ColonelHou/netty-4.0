package nio.some.demo.v2;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        int cores = Runtime.getRuntime().availableProcessors();
        Processor p[] = new Processor[cores];
        for (int i = 0; i < p.length; i ++) {
            p[i] = new Processor();
        }

        // 阻塞到至少有一个通道在你注册的事件上就绪了; 有多少通道已经就绪
        int i = 0;
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKey = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKey.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);

                    Processor processor = p[cores % i++];
                    processor.addChannel(channel);
                    processor.wakeUp();
                }
            }
        }

    }
}
