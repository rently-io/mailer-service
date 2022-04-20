package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.services.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class MailerController {

    @Autowired
    private MailerService service;

    @PostMapping("/dispatch")
    public ResponseContent handlePost(@RequestParam(required = false) String userId, @RequestBody(required = false) String something) {
        service.sendMail();
        return null;
    }
}
