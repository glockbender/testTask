package server;

import http.XmlParserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by prvz on 29.06.2017.
 */
public class Bootstrap {

    private static final Logger LOG = LogManager.getLogger(Bootstrap.class);

    public static final String RESOURCES_PATH = "src/main/resources";
    private static final String PROP_PATH = RESOURCES_PATH+"/config.property";

    public static void main(String[] args){

        Properties properties = new Properties();

        try(FileInputStream fis = new FileInputStream(PROP_PATH)){
            properties.load(fis);

            final int httpPort = Integer.parseInt(properties.getProperty("http.port"));
            final int tcpDestPort = Integer.parseInt(properties.getProperty("tcp.dest.port"));
            final String tcpDestAddr = properties.getProperty("tcp.dest.addr");

            LOG.debug("Property read!\nHttp port: "+httpPort+"\nTCP dest port: "+tcpDestPort+"\nTCP dest addr: "+tcpDestAddr);

            Server server = new Server(httpPort);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

            context.setContextPath("/test");

            server.setHandler(context);

            context.addServlet(new ServletHolder(new XmlParserServlet(tcpDestPort,tcpDestAddr)),"/*");

            server.start();

        }catch (FileNotFoundException e){
            LOG.error("Cannot find property file!",e);
        }catch (NumberFormatException e) {
            LOG.error("Error occurred when try to parse int property values!", e);
        }catch (RuntimeException e){
            LOG.error(e);
        }catch (IOException e) {
            LOG.error("Cannot load property file!", e);
        }catch (Exception e){
            LOG.error("Cannot start server!",e);
        }

    }


}
