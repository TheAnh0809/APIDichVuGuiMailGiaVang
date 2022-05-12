package com.example.apidichvuguimailgiavang.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private String toEmail;
    private String title;
    private String body;

    public EmailService(String toEmail, String title, String body) {
        this.toEmail = toEmail;
        this.title = title;
        this.body = body;
    }

    public boolean send() {
        final String username = "the.anh.892k01@gmail.com";
        final String password = "gefxkntmjcjafxzp";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.toEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(this.toEmail)
            );
            message.setSubject(this.title);
            message.setText(this.body);

            Transport.send(message);

            System.out.println("Done");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
