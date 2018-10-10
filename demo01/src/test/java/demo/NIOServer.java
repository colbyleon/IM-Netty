package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 10:49
 */
public class NIOServer {
    static Logger logger = Logger.getLogger("console.log");

    public static void main(String[] args) {

        try  {
            Selector serverSelector = Selector.open();
            Selector clientSelector = Selector.open();

            new Thread(() -> {
                try {
                    ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                    listenerChannel.bind(new InetSocketAddress(8000));
                    listenerChannel.configureBlocking(false);
                    listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                    while (true) {
                        // 监测是否有新的连接，这里的1是阻塞时间1ms
                        if (serverSelector.select(1) > 0) {
                            Set<SelectionKey> keySet = serverSelector.selectedKeys();
                            Iterator<SelectionKey> keyIterator = keySet.iterator();

                            while (keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();
                                if (key.isAcceptable()) {
                                    logger.info("有客户端进入...");
                                    try {
                                        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                        clientChannel.configureBlocking(false);
                                        clientChannel.register(clientSelector, SelectionKey.OP_READ);

                                    } finally {
                                        keyIterator.remove();
                                    }
                                }
                                logger.info("server key loop");
                            }
                        }
                    }
                } catch (IOException ignored) {
                }
            }).start();

            new Thread(() -> {
                try {
                    while (true) {
                        if (clientSelector.select() > 0) {
                            Set<SelectionKey> keySet = clientSelector.selectedKeys();
                            Iterator<SelectionKey> keyIterator = keySet.iterator();

                            logger.info("msg in...");

                            while (keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();
                                if (key.isReadable()) {
                                    logger.info("reading");
                                    try {
                                        SocketChannel clientChannel = (SocketChannel) key.channel();
                                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                                        clientChannel.read(byteBuffer);
                                        byteBuffer.flip();
                                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                                    } finally {
                                        keyIterator.remove();
                                        key.interestOps(SelectionKey.OP_READ);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
