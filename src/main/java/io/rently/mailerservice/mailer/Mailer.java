package io.rently.mailerservice.mailer;

import io.rently.mailerservice.interfaces.IMessenger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer implements IMessenger {
    private final String sender;
    private final Properties properties;
    private final Authenticator authenticator;

    protected Mailer(String sender, String username, String password, Properties properties) {
        this.sender = sender;
        this.properties = properties;
        this.authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    public void sendEmail(String recipient, String subject, String content) throws MessagingException {
        Session session = Session.getInstance(properties, authenticator);
        // session.setDebug(false);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setContent(content, "text/html");
        Transport.send(message);
    }

    public static class Builder {
        private final String sender;
        private String username;
        private String password;
        private int port = 465;
        private boolean ssl = true;
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