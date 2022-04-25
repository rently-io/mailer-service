package io.rently.mailerservice.interfaces;

public interface IMessenger {

    void sendEmail(String recipient, String subject, String content) throws Exception;

}
