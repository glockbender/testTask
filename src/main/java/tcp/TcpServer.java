package tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by prvz on 30.06.2017.
 */
public class TcpServer extends Thread{

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private final int port;
    private final int bufferSize = 2048;
    private final String magicSequence;

    private boolean isApprovedData(byte[] bytes){
        StringBuilder sb = new StringBuilder(magicSequence.length());
        for (int i=0;i<magicSequence.length();i++){
            sb.append((char)bytes[i]);
        }
        return sb.toString().equals(magicSequence);
    }

    @Override
    public void run() {



        try (ServerSocket serverSocket = getServerSocket();
             Socket socket = getSocket(serverSocket);
             InputStream in = socket.getInputStream();
             BufferedInputStream dataIn = new BufferedInputStream(in);
             OutputStream out = socket.getOutputStream()){

            byte[] bytes = new byte[bufferSize];
            final int magicSeqLen = magicSequence.length();

            while (true) {

                int i = 0;
                int readByte;
                boolean isApproved = false;

                while ((readByte = dataIn.read()) !=-1){

                    if (i==magicSeqLen && !(isApproved = isApprovedData(bytes))){
                        LOG.error("Data isn't approved!");
                        break;
                    } else if (i==bufferSize) {
                        LOG.error("Too much data!");
                        isApproved = false;
                        break;
                    }

                    bytes[i++] = (byte)readByte;
                }

                if (i!=0){
                    String s;
                    if (isApproved){
                        s = "GOOD DATA!";
                    }else {
                        s = "BAD DATA!";
                    }
                    LOG.info(s);
                    out.write(s.getBytes());
                    out.flush();
                    return;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
            LOG.error(e);
        }
    }

    private Socket getSocket(ServerSocket serverSocket) throws IOException{
        Socket result = serverSocket.accept();
        LOG.info("Socket accepted! IP: "+result.getInetAddress());
        return result;
    }

    private ServerSocket getServerSocket() throws IOException{
        ServerSocket result = new ServerSocket(port);
        LOG.info("SERVER STARTED!");
        return result;
    }

    public TcpServer(int port, String magicSequence) {
        this.port = port;
        this.magicSequence = magicSequence;
    }
}
