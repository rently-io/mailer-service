package io.rently.mailerservice.interfaces;

public interface IMailer {

    void sendEmail(String recipient, String subject, String content) throws Exception;

}
