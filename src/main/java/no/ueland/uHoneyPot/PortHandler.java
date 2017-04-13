package no.ueland.uHoneyPot;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ueland on 4/13/17.
 */
class PortHandler implements Runnable {

    private final int port;

    public PortHandler(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(this.port);
            try {
                while (true) {
                    Socket socket = listener.accept();
                    new Thread(new SocketHandler(socket)).start();
                }
            } finally {
                listener.close();
            }
        }catch(Exception ex) {
            Util.die(ex);
        }
    }
}
