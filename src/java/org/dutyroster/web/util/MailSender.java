/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mfh684
 */
public class MailSender {

    private static Session session;

    static {
        try {
            final Properties properties = new Properties();
            properties.load(MailSender.class.getClassLoader().getResourceAsStream("/WEB-INF/mail-settings.properties"));
            Properties props = new Properties();
            props.put("mail.smtp.host", properties.getProperty("mail.smtp.host"));
            props.put("mail.smtp.socketFactory.port", properties.getProperty("mail.smtp.socketFactory.port"));
            props.put("mail.smtp.socketFactory.class",
                    properties.getProperty("mail.smtp.socketFactory.class"));
            props.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
            props.put("mail.smtp.port", properties.getProperty("mail.smtp.port"));

             session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.username"), properties.getProperty("password"));
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendEmail(String from, String to, String text, String subject) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}