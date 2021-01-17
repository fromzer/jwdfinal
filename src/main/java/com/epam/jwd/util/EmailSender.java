package com.epam.jwd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailSender {
    private static Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private EmailSender() {
    }

    public static void send(String topic, String text, String from) {
        Properties properties = new Properties();
        try {
            properties.load(EmailSender.class.getResourceAsStream("/mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String user = properties.getProperty("mail.smtp.user");
        String password = properties.getProperty("mail.smtp.password");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        session.setDebug(false);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(user);
            message.setSubject(topic);
            message.setText(text, "UTF-8");
            message.setRecipients(Message.RecipientType.TO, from);
            message.setSentDate(new Date());
            Transport.send(message);
        } catch (MessagingException e) {
            logger.warn("Error", e);
        }
    }
}
