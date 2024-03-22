package com.kpanchal.ttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.beust.jcommander.JCommander;
import com.kpanchal.argtools.AddDeviceArguments;
import com.kpanchal.argtools.InitialArguments;
import com.kpanchal.argtools.LoginArguments;
import com.kpanchal.argtools.RemoveDeviceArguments;
import com.kpanchal.mailtools.Contact;
import com.kpanchal.mailtools.Login;
import com.kpanchal.mailtools.MailSender;

public class App {
    private static final String VERSION = "1.2.0";
    public static void main(String[] args) {
        InitialArguments initialArgs = new InitialArguments();
        JCommander jc = JCommander.newBuilder()
            .addObject(initialArgs)
            .build();
        jc.parse(args);
        
        if (initialArgs.getLogin()) {
            try {
                setLogin();
            } catch (IOException e) {
                System.err.println("Error: Unable to generate login configuration:\n" + e.getMessage());
            }
        } else if (initialArgs.getLogout()) {
            try {
                logout();
            } catch (IOException e) {
                System.err.println("Error: Unable to logout:\n" + e.getMessage());
            }
        } else if (initialArgs.getAddDevice()) {
            try {
                addDevice();
            } catch (IOException e) {
                System.err.println("Error: Unable to add device:\n" + e.getMessage());
            }
        } else if (initialArgs.getRemoveDevice()) {
            try {
                removeDevice();
            } catch (IOException e) {
                System.err.println("Error: Unable to remove device:\n" + e.getMessage());
            }
        } else if (initialArgs.getListDevices()) {
            listDevices();
        } else if (initialArgs.getSendInfo() != null) {
            try {
                sendMessage(initialArgs.getSendInfo().get(0), initialArgs.getSendInfo().get(1));
            } catch (IOException e) {
                System.err.println("Error: Unable to initialize message sender:\n" + e.getMessage());
            }
        } else if (initialArgs.getVersion()) {
            printVersion();
        }else {
            jc.usage();
        }
    }

    public static void setLogin() throws IOException {
        new File("config").mkdir();
        String propertiesPath = "config" + File.separator + "login.properties";

        if (!new File(propertiesPath).exists()) {
            LoginArguments loginArgs = new LoginArguments();
            JCommander.newBuilder()
                .addObject(loginArgs)
                .build()
                .parse("Email Address", "Password"); // Hardcoded arguments lol

            Properties login = new Properties();
            String SMTPProvider = Login.getSMTPAddress(loginArgs.getSMTPProviderIndex());
            login.setProperty("EmailAddress", loginArgs.getEmailAddress());
            login.setProperty("Password", loginArgs.getPassword());
            login.setProperty("SMTP Provider", SMTPProvider);
            login.setProperty("Port", Integer.toString(Login.getPort(SMTPProvider)));
            login.store(new FileOutputStream(propertiesPath), "Stored login email and password");
            System.out.println("Successfully logged in");
        } else {
            System.out.println("Error: You're already logged in. Run the program with the --logout flag to log out");
        }    
    }

    public static void logout() throws IOException {
        String propertiesPath = "config" + File.separator + "login.properties";
        if (new File(propertiesPath).exists()) {
            new File(propertiesPath).delete();
            System.out.println("Successfully logged out");
        } else {
            System.err.println("Error: You're already logged out. Run the program with the --login flag to log in");
        }
    }

    public static void addDevice() throws IOException {
        new File("config").mkdir();
        String propertiesPath = "config" + File.separator + "deviceinfo.properties";

        AddDeviceArguments deviceArgs = new AddDeviceArguments();
        JCommander.newBuilder()
            .addObject(deviceArgs)
            .build()
            .parse("Device Name", "Phone Number", "Cell Service Provider"); // More hardcoded arguments rofl

        new File(propertiesPath).createNewFile();
        Properties deviceInfo = new Properties();
        deviceInfo.load(new FileInputStream(propertiesPath));
        deviceInfo.setProperty(deviceArgs.getDeviceName(), Contact.createContact(deviceArgs.getPhoneNumber(), deviceArgs.getCellProviderIndex() - 1));
        deviceInfo.store(new FileOutputStream(propertiesPath), "Added device " + deviceArgs.getDeviceName());
        System.out.println("Added device " + deviceArgs.getDeviceName());
    }

    public static void removeDevice() throws IOException {
        String propertiesPath = "config" + File.separator + "deviceInfo.properties";

        RemoveDeviceArguments deviceArgs = new RemoveDeviceArguments();
        JCommander.newBuilder()
            .addObject(deviceArgs)
            .build()
            .parse("Device Name");

        Properties deviceInfo = new Properties();
        deviceInfo.load(new FileInputStream(propertiesPath));
        if (deviceInfo.remove(deviceArgs.getDeviceName()) == null) {
            System.err.println("Error: No device with name " + deviceArgs.getDeviceName() + " found");
        } else {
            deviceInfo.store(new FileOutputStream(propertiesPath), "Removed device " + deviceArgs.getDeviceName());
            System.out.println("Removed device " + deviceArgs.getDeviceName());
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

    public static void sendMessage(String deviceName, String filePath) throws IOException {
        String loginPath = "config" + File.separator + "login.properties";
        Properties loginInfo = new Properties();
        try {
            loginInfo.load(new FileInputStream(loginPath));
        } catch (IOException e) {
            System.err.println("Error: Unable to find login information. Run the program with the --login flag to log in");
            return;
        }
        MailSender sender = new MailSender(loginInfo.getProperty("SMTP Provider"), loginInfo.getProperty("Port"), loginInfo.getProperty("EmailAddress"), loginInfo.getProperty("Password"));
        
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