package io.rently.mailerservice.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {
    private final String sender;
    private final String username;
    private final String password;
    private final Properties properties;

    private Mailer(String sender, String username, String password, Properties properties) {
        this.sender = sender;
        this.username = username;
        this.password = password;
        this.properties = properties;
    }

    public void sendMailTo(String recipient, String subject, String content) {
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });
        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setContent(content, "text/html");
            System.out.println("Sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static class Builder {
        private final String sender;
        private String username;
        private String password;
        private int port = 465;
        public boolean ssl = true;
        private boolean auth = true;
        private String host;

        public Builder(String sender) {
            this.sender = sender;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder credentials(String username, String password) {
            this.password = password;
            this.username = username;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder disableSsl() {
            this.ssl = false;
            return this;
        }

        public Builder disableAuth() {
            this.auth = false;
            return this;
        }

        public Mailer build() {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.ssl.enable", ssl);
            properties.put("mail.smtp.auth", auth);
            return new Mailer(sender, username, password, properties);
        }
    }
}