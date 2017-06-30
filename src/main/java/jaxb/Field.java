package jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by prvz on 29.06.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String value;

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
