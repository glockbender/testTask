package jaxb;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by prvz on 29.06.2017.
 */
@XmlRootElement(namespace = "wsapi:Payment")
@XmlAccessorType(XmlAccessType.FIELD)
public class SendPayment {

    @XmlEnum
    enum Currency {RUB}

    @XmlElement
    private int token;
    @XmlElement
    private String cardNumber;
    @XmlElement
    private String requestId;
    @XmlElement
    private double amount;
    @XmlElement
    private Currency currency;

    @XmlElement(name = "account", namespace = "wsapi:Utils")
    private List<Account> accounts;

    private int page;

    @XmlElement(name="field")
    private List<Field> fields;

    public int getToken() {
        return token;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getRequestId() {
        return requestId;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public int getPage() {
        return page;
    }

    public List<Field> getFields() {
        return fields;
    }
}
