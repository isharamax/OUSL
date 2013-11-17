/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.util;

/**
 *
 * @author mfh684
 */
import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailClient {

    private static final String SMTP_MAIL = "smtp";
    private String smtpHost = "smtp.gmail.com",
            user = "ousl.duty.roster@gmail.com", //Proxy login & password... This can be empty ("") ;
            password = "ishara123";

//    public MailClient(String smtpHost, String user, String password) {
//        this.smtpHost = smtpHost;
//        this.user = user;
//        this.password = password;
//    }

    public void sendMsg(String from, String subject, String message, String[] toAddress, String[] attachments) throws Exception {


        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        Session session = Session.getDefaultInstance(props, null);



        Message newMessage = new MimeMessage(session);
        newMessage.setFrom(new InternetAddress(from));
        newMessage.setSubject(subject);


        Object content = message;
        String debugText = "Subject: " + subject;
        log("Sending Text message (" + debugText + ")");
        newMessage.setText((String) content);


// Send Message
        try {
            Transport transport = session.getTransport(SMTP_MAIL);
            transport.connect(smtpHost, user, password);
            transport.sendMessage(newMessage, new Address[]{new InternetAddress(toAddress[0])});
        } catch (Exception e) {
            log(e.toString());
        }
    }

    private InternetAddress[] getInternetAddress(String[] toAddresses) throws Exception {
        InternetAddress[] inetAddr = null;
        try {
            inetAddr = new InternetAddress[toAddresses.length + 1];
            for (int i = 0; i < toAddresses.length; i++) {
                inetAddr[i] = new InternetAddress(toAddresses[i]);
            }
        } catch (Exception e) {
            log(e.toString());
        }
        return inetAddr;
    }

    private void log(String s) {
        System.out.println(new Date() + " " + s);
    }
}
