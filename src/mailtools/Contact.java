package mailtools;

import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

public class Contact {

    private static final Map<String, String> ret = Contact.createMap();
    
    private final String smsGateway;

    public Contact(String number, String carrier) {
        this.smsGateway = number + "@" + Contact.ret.getOrDefault(carrier, "");
    }

    public Contact(String number, int carrierIndex) {
        this.smsGateway = number + "@" + Contact.ret.keySet().toArray()[carrierIndex - 1];
    }

    private static Map<String, String> createMap() {
        Map<String, String> ret = new LinkedHashMap<>();
        ret.put("Alltel", "mms.alltelwireless.com");
        ret.put("AT&T", "mms.att.net");
        ret.put("Boost Mobile", "myboostmobile.com");
        ret.put("Consumer Cellular", "mailmymobile.net");
        ret.put("Cricket Wirless", "mms.cricketwireless.net");
        ret.put("Google Fi Wireless", "msg.fi.google.com");
        ret.put("MetroPCS", "mymetropcs.com");
        ret.put("Spectrum Mobile", "mypixmessages.com");
        ret.put("Sprint", "pm.sprint.com");
        ret.put("T-Mobile", "tmomail.net");
        ret.put("U.S. Cellular", "mms.uscc.net");
        ret.put("Verizon Wireless", "vwpix.com");
        ret.put("Virgin Mobile", "vmpix.com");
        ret.put("XFinity Mobile", "mypixmessages.com");
        
        return Collections.unmodifiableMap(ret);
    }

    public String getEmail() {
        return this.smsGateway;
    }
}