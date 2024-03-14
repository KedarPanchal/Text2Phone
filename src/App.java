import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.beust.jcommander.JCommander;

import argtools.AddDeviceArguments;
import argtools.InitialArguments;
import argtools.LoginArguments;
import argtools.RemoveDeviceArguments;
import mailtools.Contact;
import mailtools.MailSender;

public class App {
    public static void main(String[] args) {
        InitialArguments initialArgs = new InitialArguments();
        JCommander.newBuilder()
            .addObject(initialArgs)
            .build()
            .parse(args);
        
        if (initialArgs.getLogin()) {
            try {
                setLogin();
            } catch (IOException e) {
                System.out.println("Error: Unable to generate login configuration:\n" + e.getMessage());
            }
        } else if (initialArgs.getAddDevice()) {
            try {
                addDevice();
            } catch (IOException e) {
                System.out.println("Error: Unable to add device:\n" + e.getMessage());
            }
        } else if (initialArgs.getRemoveDevice()) {
            try {
                removeDevice();
            } catch (IOException e) {
                System.out.println("Error: Unable to remove device:\n" + e.getMessage());
            }
        } else if (initialArgs.getListDevices()) {
            listDevices();
        }
    }

    public static void setLogin() throws IOException {
        String configDir = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config"; // Gets parent folder.
        new File(configDir).mkdir();
        String propertiesPath = configDir + File.separator + "login.properties";

        if (!new File(propertiesPath).exists()) {
            LoginArguments loginArgs = new LoginArguments();
            JCommander.newBuilder()
                .addObject(loginArgs)
                .build()
                .parse("Email Address", "Password"); // Hardcoded arguments lol

            Properties login = new Properties();
            login.setProperty("Email Address", loginArgs.getEmailAddress());
            login.setProperty("Password", loginArgs.getPassword());
            login.store(new FileOutputStream(propertiesPath), "Stored login email and password");
        } else {
            System.out.println("Error: You're already logged in. Rerun the program with the --logout flag to log out");
        }    
    }

    public static void addDevice() throws IOException {
        String configDir = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config";
        new File(configDir).mkdir();
        String propertiesPath = configDir + File.separator + "deviceinfo.properties";

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
    }

    public static void removeDevice() throws IOException {
        String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config" + File.separator + "deviceInfo.properties";

        RemoveDeviceArguments deviceArgs = new RemoveDeviceArguments();
        JCommander.newBuilder()
            .addObject(deviceArgs)
            .build()
            .parse("Device Name");

        Properties deviceInfo = new Properties();
        deviceInfo.load(new FileInputStream(propertiesPath));
        if (deviceInfo.remove(deviceArgs.getDeviceName()) == null) {
            System.out.println("Error: No device with name " + deviceArgs.getDeviceName() + " found");
        } else {
            deviceInfo.store(new FileOutputStream(propertiesPath), "Removed device " + deviceArgs.getDeviceName());
        }
    }

    public static void listDevices() {
        String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config" + File.separator + "deviceInfo.properties";

        Properties deviceInfo = new Properties();
        try {
            deviceInfo.load(new FileInputStream(propertiesPath));

            for (String s : deviceInfo.stringPropertyNames()) {
                System.out.println(s + ": " + Contact.parseNumber(deviceInfo.getProperty(s)));
            }
        } catch (IOException e) {
            System.out.println("No devices found");
        }
    }

    // Delete this once code workie good
    public static boolean sendMessageTest() {
        try {
            MailSender sender = MailSender.createMailSender(Auth.USER, Auth.PASS);
            return sender.sendMessage(Auth.PHONE_CONTACT, Auth.FILENAME);
        } catch (IOException e) {
            e.printStackTrace(); // *projectile vomits*
            return false;
        }
    }
}