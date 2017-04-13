package no.ueland.uHoneyPot;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ueland on 3/13/17.
 */
class SocketHandler implements Runnable {

    private static final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-mm-dd");
    private static final SimpleDateFormat fullDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    private final Socket socket;

    public SocketHandler(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        File logFile = getLogFile();
        String remoteAddr = this.socket.getRemoteSocketAddress().toString();
        int port = this.socket.getLocalPort();
        System.out.println("Logging connection to #"+port+" to log file "+logFile.getAbsolutePath());

        try {
            String preLog = "============ New connection on #"+port+" from "+remoteAddr+" @ "+fullDate.format(new Date())+" ============\n";
            writeToLogFile(logFile, preLog);
            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String str;
            while ((str = br.readLine()) != null) {
                writeToLogFile(logFile, str+'\n');
            }
            br.close();
            this.socket.close();

        } catch(Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                // Not important to log
            }
            String sufLog = "============ Closed connection on #"+port+" from "+remoteAddr+" @ "+fullDate.format(new Date())+" ============\n";
            try {
                writeToLogFile(logFile, sufLog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void writeToLogFile(File logFile, String preLog) throws IOException {
        FileUtils.writeStringToFile(logFile, preLog, true);
    }

    private File getLogFile() {
        File f = new File("logs/"+socket.getLocalPort()+"/"+simpleDate.format(new Date())+".log");
        f.getParentFile().mkdirs();
        return f;
    }
}
