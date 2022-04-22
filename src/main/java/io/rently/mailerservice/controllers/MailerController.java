package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.mailer.enums.MailType;
import io.rently.mailerservice.services.MailerService;
import io.rently.mailerservice.utils.Properties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class MailerController {

    @PostMapping("/dispatch")
    public ResponseContent handlePostGreetings(@RequestBody Map<String, Object> data) {
        MailType type = EnumUtils.findEnumInsensitiveCase(MailType.class, Properties.tryGetProperty("type", data));

        switch (type) {
            case GREETINGS -> MailerService.sendGreetings(data);
            case NEW_LISTING -> MailerService.sendNewListingNotification(data);
        }

        return new ResponseContent.Builder().setMessage("Successfully dispatched: " + type).build();
    }
}
