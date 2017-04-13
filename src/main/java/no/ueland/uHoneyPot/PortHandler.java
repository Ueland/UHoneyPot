package no.ueland.uHoneyPot;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * Created by Ueland on 4/13/17.
 */
public class PortHandler implements Runnable {

    private static Selector selector = null;
    private final int port;

    public PortHandler(int port) {
        this.port = port;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            Util.die(e);
        }
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(port));
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.isOpen()) {
                selector.select();
                Set readyKeys = selector.selectedKeys();
                for (Object readyKey : readyKeys) {
                    SelectionKey key = (SelectionKey) readyKey;
                    if (key.isAcceptable()) {
                        new Thread(new SocketHandler(server.accept().socket())).start();
                    }
                }
            }

        }catch(Exception ex) {
            Util.die(ex);
        }
    }
}
