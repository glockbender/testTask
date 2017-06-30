package jaxb;

import javax.xml.bind.annotation.*;

/**
 * Created by prvz on 29.06.2017.
 */
@XmlRootElement(namespace = "wsapi:Utils")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

    @XmlEnum
    enum Type {source,destination}

    @XmlAttribute(name = "type")
    private Type type;

    @XmlValue
    private String value;
}
