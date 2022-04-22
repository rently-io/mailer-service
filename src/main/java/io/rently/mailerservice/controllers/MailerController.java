package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.services.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class MailerController {

    @Autowired
    private MailerService service;

    @PostMapping("/dispatch/greetings")
    public ResponseContent handlePostGreetings(@RequestBody Map<String, Object> data) {
        service.handleSendGreetings(data);
        return new ResponseContent.Builder().setMessage("Greetings sent!").build();
    }
}
