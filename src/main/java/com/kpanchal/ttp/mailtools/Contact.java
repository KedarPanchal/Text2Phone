package com.kpanchal.ttp.mailtools;

public class Contact {

    private static final String[] providers = {"mms.alltelwireless.com", "mms.att.net", "myboostmobile.com", "mailmymobile.net", "mms.cricketwireless.net", "msg.fi.google.com", "mymetropcs.com", "mypixmessages.com", "pm.sprint.com", "tmomail.net", "mms.uscc.net", "vwpix.com", "vmpix.com", "mypixmessages.com"};

    public static String createContact(String number, int carrierIndex) {
        return number + "@" + providers[carrierIndex];
    }

    public static String parseNumber(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}