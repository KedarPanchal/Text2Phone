package com.kpanchal.ttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.beust.jcommander.JCommander;
import com.kpanchal.ttp.argtools.AddDeviceArguments;
import com.kpanchal.ttp.argtools.InitialArguments;
import com.kpanchal.ttp.argtools.LoginArguments;
import com.kpanchal.ttp.argtools.RemoveDeviceArguments;
import com.kpanchal.ttp.mailtools.Contact;
import com.kpanchal.ttp.mailtools.Login;
import com.kpanchal.ttp.mailtools.MailSender;

public class App {
    private static final String VERSION = "1.3.1";
    public static void main(String[] args) {
        InitialArguments initialArgs = new InitialArguments();
        JCommander jc = JCommander.newBuilder()
            .addObject(initialArgs)
            .build();
        jc.parse(args);
        
        if (initialArgs.getLogin()) {
                setLogin();
        } else if (initialArgs.getLogout()) {
                logout();
        } else if (initialArgs.getAddDevice()) {
                addDevice();
        } else if (initialArgs.getRemoveDevice() != null) {
                removeDevice(initialArgs.getRemoveDevice());
        } else if (initialArgs.getListDevices()) {
            listDevices();
        } else if (initialArgs.getSendInfo() != null) {
                sendMessage(initialArgs.getSendInfo().get(0), initialArgs.getSendInfo().get(1));
        } else if (initialArgs.getVersion()) {
            printVersion();
        } else {
            jc.usage();
        }
    }

    public static void setLogin() {
        new File("config").mkdir();
        String propertiesPath = "config" + File.separator + "login.properties";

        if (!new File(propertiesPath).exists()) {
            LoginArguments loginArgs = new LoginArguments();
            JCommander.newBuilder()
                .addObject(loginArgs)
                .build()
                .parse("Email Address", "Password", "Email Provider"); // Hardcoded arguments lol
            try {
                (new File(propertiesPath)).createNewFile();
            Properties login = new Properties();
            String SMTPProvider = Login.getSMTPAddress(loginArgs.getSMTPProviderIndex() - 1);
            login.setProperty("EmailAddress", loginArgs.getEmailAddress());
            login.setProperty("Password", loginArgs.getPassword());
            login.setProperty("SMTPProvider", SMTPProvider);
            login.setProperty("Port", Integer.toString(Login.getPort(SMTPProvider)));
            login.store(new FileOutputStream(propertiesPath), "Stored login email and password");
            System.out.println("Successfully logged in");
            } catch (IOException e) {
                System.err.println("Error: Unable to generate login configuration:\n" + e.getMessage());
            }           
        } else {
            System.out.println("Error: You're already logged in. Run the program with the --logout flag to log out");
        }    
    }

    public static void logout() {
        String propertiesPath = "config" + File.separator + "login.properties";
        if (new File(propertiesPath).exists()) {
            new File(propertiesPath).delete();
            System.out.println("Successfully logged out");
        } else {
            System.err.println("Error: You're already logged out. Run the program with the --login flag to log in");
        }
    }

    public static void addDevice() {
        new File("config").mkdir();
        String propertiesPath = "config" + File.separator + "deviceinfo.properties";

        AddDeviceArguments deviceArgs = new AddDeviceArguments();
        JCommander.newBuilder()
            .addObject(deviceArgs)
            .build()
            .parse("Device Name", "Phone Number", "Cell Service Provider"); // More hardcoded arguments rofl

        try {
        new File(propertiesPath).createNewFile();
        Properties deviceInfo = new Properties();
        deviceInfo.load(new FileInputStream(propertiesPath));
        deviceInfo.setProperty(deviceArgs.getDeviceName(), Contact.createContact(deviceArgs.getPhoneNumber(), deviceArgs.getCellProviderIndex() - 1));
        deviceInfo.store(new FileOutputStream(propertiesPath), "Added device " + deviceArgs.getDeviceName());
        System.out.println("Added device " + deviceArgs.getDeviceName());
        } catch (IOException e) {
            System.err.println("Error: Unable to add device:\n" + e.getMessage());
        }
    }

    public static void removeDevice(String device) {
        String propertiesPath = "config" + File.separator + "deviceinfo.properties";

        Properties deviceInfo = new Properties();
        try {
        deviceInfo.load(new FileInputStream(propertiesPath));
        if (deviceInfo.remove(device) == null) {
            System.err.println("Error: No device with name " + device + " found");
        } else {
            deviceInfo.store(new FileOutputStream(propertiesPath), "Removed device " + device);
            System.out.println("Removed device " + device);
        }
        } catch (IOException e) {
            System.err.println("Error: Unable to remove device:\n" + e.getMessage());
        }
        
    }

    public static void listDevices() {
        String propertiesPath = "config" + File.separator + "deviceInfo.properties";

        Properties deviceInfo = new Properties();
        try {
            deviceInfo.load(new FileInputStream(propertiesPath));

            for (String s : deviceInfo.stringPropertyNames()) {
                System.out.println(s + ": " + Contact.parseNumber(deviceInfo.getProperty(s)));
            }
        } catch (IOException e) {
            System.err.println("Error: No devices found. Run the program with the --add-device flag to add a device");
        }
    }

    public static void printVersion() {
        System.out.println("Text2Phone v" + VERSION + " by Kedar Panchal");
    }

    public static void sendMessage(String deviceName, String filePath) {
        String loginPath = "config" + File.separator + "login.properties";
        Properties loginInfo = new Properties();
        try {
            loginInfo.load(new FileInputStream(loginPath));
        } catch (IOException e) {
            System.err.println("Error: Unable to find login information. Run the program with the --login flag to log in");
            return;
        }
        MailSender sender;
        try {
            sender = new MailSender(loginInfo.getProperty("SMTPProvider"), loginInfo.getProperty("Port"), loginInfo.getProperty("EmailAddress"), loginInfo.getProperty("Password"));
        } catch (IOException e) {
            System.err.println("Error: Unable to initialize message sender:\n" + e.getMessage());
            return;
        }
        
        String devicePath = "config" + File.separator + "deviceinfo.properties";
        Properties deviceInfo = new Properties();
        try {
            deviceInfo.load(new FileInputStream(devicePath));
        } catch (IOException e) {
            System.err.println("Error: Unable to find device information. Run the program with --add-device flag to add a device");
            return;
        }
        
        String deviceEmail = deviceInfo.getProperty(deviceName);
        if (!sender.sendMessage(deviceEmail, filePath)) {
            System.err.println("Error: Unable to send file");
        } else {
            System.out.println("Successfully sent to device " + deviceName);
        }
    }
}