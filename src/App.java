import java.io.IOException;

import com.beust.jcommander.JCommander;

import argtools.InitialArguments;
import mailtools.MailSender;

public class App {
    public static void main(String[] args) {
        System.out.println(args[0]);

        InitialArguments initialArgs = new InitialArguments();
        JCommander.newBuilder()
            .addObject(initialArgs)
            .build()
            .parse(args);
        System.out.println(initialArgs.getLogin());
    }

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