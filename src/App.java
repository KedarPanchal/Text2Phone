import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.beust.jcommander.JCommander;

import argtools.InitialArguments;
import argtools.LoginArguments;
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
                System.out.println("Error: Unable to generate login configuration");
            }
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
                .parse(new String[]{"Email Address", "Password"}); // Hardcoded arguments lol

            Properties login = new Properties();
            login.setProperty("Email Address", loginArgs.getEmailAddress());
            login.setProperty("Password", loginArgs.getPassword());
            login.store(new FileOutputStream(configDir  + File.separator + "login.properties"), "Stored login email and password");
        } else {
            System.out.println("Error: You're already logged in. Rerun the program with the --logout flag to log out");
        }    
    }

    // Delete this once code workie good
    public static boolean sendMessageTest() {
        try {
            MailSender sender = MailSender.createMailSender(Auth.USER, Auth.PASS);
            return sender.sendMessage(Auth.PHONE_CONTACT.getEmail(), Auth.FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}