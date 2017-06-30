package tcp;

import com.sun.istack.internal.Nullable;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by prvz on 29.06.2017.
 */
public class TcpClient {

    private final int tcpDestPort;
    private final String tcpDestIp;

    public TcpClient(int tcpDestPort, String tcpDestIp) {
        this.tcpDestPort = tcpDestPort;
        this.tcpDestIp = tcpDestIp;
    }

    public Socket initSocket() throws IOException{
        return new Socket(InetAddress.getByName(tcpDestIp),tcpDestPort);
    }


}
