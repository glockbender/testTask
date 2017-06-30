package jaxb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

/**
 * Created by prvz on 29.06.2017.
 */

@XmlRootElement(name = "Envelope")
@XmlAccessorType(XmlAccessType.FIELD)
public class Envelope {

    public final static Logger LOG = LogManager.getLogger(Envelope.class);

    @XmlRootElement(name = "Body")
    static class Body{

        private SendPayment sendPayment;

        @XmlElement(namespace = "wsapi:Payment")
        public SendPayment getSendPayment() {
            return sendPayment;
        }

        public void setSendPayment(SendPayment sendPayment) {
            this.sendPayment = sendPayment;
        }
    }

    @XmlElement(name = "Body")
    private Body body;

    public static Envelope parseXmlString(String xml){
        try (StringReader stringReader = new StringReader(xml)){
            JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Envelope) jaxbUnmarshaller.unmarshal(stringReader);
        }catch (JAXBException e){
            LOG.error("An error occurred when try to parse xml string to Envelope instance!",e);
            return null;
        }
    }
}
