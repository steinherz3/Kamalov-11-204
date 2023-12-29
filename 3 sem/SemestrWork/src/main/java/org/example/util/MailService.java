package org.example.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {

    public static void sendMessage(String Recipient, String title, String text){
        String hostname = "smtp.yandex.ru";
        String username = "hakaton.project.test";
        String password = "6R0OXYqh2#B1@z3SHK";

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.host", hostname);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom("hakaton.project.test@yandex.ru");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Recipient));
            message.setSubject(title);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}