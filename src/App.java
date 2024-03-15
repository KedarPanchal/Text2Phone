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
    public static String CONFIG_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config";
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
                System.out.println(initialArgs.getSendInfo());
                sendMessage(initialArgs.getSendInfo().get(0), initialArgs.getSendInfo().get(1));
            } catch (IOException e) {
                System.err.println("Error: Unable to initialize message sender:\n" + e.getMessage());
            }
        }
    }

    public static void setLogin() throws IOException {
        new File(CONFIG_PATH).mkdir();
        String propertiesPath = CONFIG_PATH + File.separator + "login.properties";

        if (!new File(propertiesPath).exists()) {
            LoginArguments loginArgs = new LoginArguments();
            JCommander.newBuilder()
                .addObject(loginArgs)
                .build()
                .parse("Email Address", "Password"); // Hardcoded arguments lol

            Properties login = new Properties();
            login.setProperty("EmailAddress", loginArgs.getEmailAddress());
            login.setProperty("Password", loginArgs.getPassword());
            login.store(new FileOutputStream(propertiesPath), "Stored login email and password");
        } else {
            System.out.println("Error: You're already logged in. Run the program with the --logout flag to log out");
        }    
    }

    public static void logout() throws IOException {
        String propertiesPath = CONFIG_PATH + File.separator + "login.properties";
        if (new File(propertiesPath).exists()) {
            new File(propertiesPath).delete();
        } else {
            System.err.println("Error: You're already logged out. Run the program with the --login flag to log in");
        }
    }

    public static void addDevice() throws IOException {
        new File(CONFIG_PATH).mkdir();
        String propertiesPath = CONFIG_PATH + File.separator + "deviceinfo.properties";

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
        String propertiesPath = CONFIG_PATH + File.separator + "deviceInfo.properties";

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
        }
    }

    public static void listDevices() {
        String propertiesPath = CONFIG_PATH + File.separator + "deviceInfo.properties";

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

    public static void sendMessage(String deviceName, String filePath) throws IOException {
        String loginPath = CONFIG_PATH + File.separator + "login.properties";
        Properties loginInfo = new Properties();
        try {
            loginInfo.load(new FileInputStream(loginPath));
        } catch (IOException e) {
            System.err.println("Error: Unable to find login information. Run the program with the --login flag to log in");
            return;
        }
        MailSender sender = new MailSender(loginInfo.getProperty("EmailAddress"), loginInfo.getProperty("Password"));
        
        String devicePath = CONFIG_PATH + File.separator + "deviceinfo.properties";
        Properties deviceInfo = new Properties();
        try {
            deviceInfo.load(new FileInputStream(devicePath));
        } catch (IOException e) {
            System.err.println("Error: Unable to find device information. Run the program with --add-device flag to add a device");
            return;
        }
        
        String deviceEmail = deviceInfo.getProperty(deviceName);
        System.out.println(deviceEmail);
        if (!sender.sendMessage(deviceEmail, filePath)) {
            System.err.println("Error: Unable to send message");
        }
    }
}