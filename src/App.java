import java.io.IOException;

import mailtools.MailSender;

public class App {
    public static void main(String[] args) {
        if (sendMessageTest()) {
            System.out.println("Works! :)");
        } else {
            System.out.println("Doesn't work :C");
        }
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