package com.kpanchal.mailtools;

import java.util.LinkedHashMap;

public class Login {
    private static final LinkedHashMap<String, Integer> providersAndPorts = new LinkedHashMap<>();
    static {
        providersAndPorts.put("smtp.aol.com", 465);
        providersAndPorts.put("smtp.gmail.com", 587);
        providersAndPorts.put("smtp.live.com", 465);
        providersAndPorts.put("smtp.mail.me.com", 587);
        providersAndPorts.put("smtp-mail.outlook.com", 587);
        providersAndPorts.put("smtp.mail.yahoo.com", 587);
    }

    public static String getSMTPAddress(int index) {
        return "" + Login.providersAndPorts.keySet().toArray()[index];
    }

    public static int getPort(String address) {
        return Login.providersAndPorts.getOrDefault(address, 587);
    }
}
