package com.NLCS.TreeShop.config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

	public static void sendEmail(String recipientEmail, String subject1, String messageBody) throws Exception {

        final String fromEmail = "taikhoanr1@gmail.com";
        // Mat khai email cua ban
        final String password = "Rongcon1@";
        // dia chi email nguoi nhan
        final String toEmail = recipientEmail;

        final String subject = subject1;
        final String body = messageBody;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        message.setSubject(subject);
        message.setSubject("HTML Message");
        String htmlContent = "<h1>Html Tag</h1>";
        message.setContent(htmlContent, "text/html");
        Transport.send(message);
        System.out.println("Gui mail thanh cong");
    }
}

