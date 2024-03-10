package mailtools;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private String host;
    private String sender;
    private Session session;

    public MailSender(String sender, String password) throws IOException {
        this.host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.trust", this.host);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.host", this.host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        this.session = Session.getDefaultInstance(properties, 
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }    
            });
    }

    public boolean sendMessage(String to, String text) {
        try {
            MimeMessage message = new MimeMessage(this.session);

            message.setFrom(new InternetAddress(this.sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test");
            message.setText(text);

            Transport.send(message);
            
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
