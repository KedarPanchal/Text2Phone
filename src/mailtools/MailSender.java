package mailtools;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    private String host;
    private String sender;
    private Session session;

    public MailSender(String sender, String password) throws IOException {
        this.host = "smtp.gmail.com";
        this.sender = sender;

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

    private MimeMessage initMessage(String to) {
        MimeMessage ret = new MimeMessage(this.session);
        try {
            ret.setFrom(new InternetAddress(this.sender));
            ret.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            return ret;
        } catch (MessagingException e) {
            return null;
        }
    } 

    private boolean sendText(MimeMessage message, String text) {
        try {
            message.setSubject("Error: File not found");
            message.setText(text);
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    private boolean sendFile(MimeMessage message, File file) throws IOException {
        try {
            message.setSubject(file.getName());
            if (!file.isDirectory()) {
                MimeBodyPart attachment = new MimeBodyPart();
                attachment.attachFile(file);

                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(attachment);

                message.setContent(multipart);

                Transport.send(message);

                return true;
            } else {
                File zipFile = new File(file.getAbsolutePath() + ".zip");
                Zipper.zipFolder(file.toPath(), zipFile.toPath());
                zipFile.deleteOnExit();
                return sendFile(message, zipFile);
            }          
        } catch (MessagingException e) {
            return false;
        }
    }

    public boolean sendMessage(String to, String filename) {
        MimeMessage message = this.initMessage(to);
        if (message != null) {
            try {
               return this.sendFile(message, new File(filename));
            } catch (IOException e) {
                System.out.println("Error: File not found. Sending text instead...");
                return this.sendText(message, filename);
            }
        } else {
            return false;
        }
    }
}