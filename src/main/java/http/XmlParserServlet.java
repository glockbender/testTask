package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import jaxb.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tcp.TcpClient;
import tcp.TcpServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by prvz on 29.06.2017.
 */
public class XmlParserServlet extends HttpServlet {

    private final static Logger LOG = LogManager.getLogger(XmlParserServlet.class);

    private final int tcpDestPort;
    private final String tcpDestIp;
    private final String magicSeq = "FFBBCCDD";

    public XmlParserServlet(int tcpDestPort, String tcpDestIp) {
        this.tcpDestPort = tcpDestPort;
        this.tcpDestIp = tcpDestIp;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<form id=\"form1\" name=\"form1\" method=\"post\">");
        out.println("<p>Post your XML here:</p>");
        out.println("<p><textarea rows=\"10\" cols=\"40\" form=\"form1\" name=\"xml\"></textarea></p>");
        out.println("<input type=\"submit\" value=\"Submit\"></p>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String xml = req.getParameter("xml");

        Envelope envelope;

        if((envelope = Envelope.parseXmlString(xml))==null){
            //error
        }else {
            convertToJsonAndReceiveBySocket(envelope);
        }
    }

    private void convertToJsonAndReceiveBySocket(Envelope envelope){
        TcpServer server = new TcpServer(tcpDestPort,magicSeq);
        server.start();

        TcpClient client = new TcpClient(tcpDestPort,tcpDestIp);
        final int buffSize = 1024;
        try (Socket socket = client.initSocket();
             BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream(),buffSize);
             InputStream in = socket.getInputStream();
             StringWriter writer = new StringWriter()){

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer,envelope);
            writer.flush();

            final String s = writer.toString();
            out.write(magicSeq.getBytes());
            out.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putShort((short) s.length()).array());
            out.write(s.getBytes("utf-16"));
            out.flush();

//            StringBuilder response = new StringBuilder();

//            while (true){
//
//                int readByte;
//                while ((readByte = in.read())!=-1){
//                    response.append((char)readByte);
//                }
//                if (response.length()>0){
//                    LOG.info("GET RESPONSE FROM SERVER: "+response.toString());
//                    break;
//                }
//            }

        }catch (IOException e){
            LOG.error(e);
        }
    }
}
