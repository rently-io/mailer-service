package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.mailer.enums.MailType;
import io.rently.mailerservice.services.MailerService;
import io.rently.mailerservice.utils.Broadcaster;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class MailerController {

    @PostMapping({ "/dispatch", "/dispatch/" })
    public ResponseContent handleDispatch(@RequestBody Map<String, Object> data) {
        JSONObject jsonData = new JSONObject(data);
        MailType type = EnumUtils.findEnumInsensitiveCase(MailType.class, jsonData.getString("type"));

        switch (type) {
            case GREETINGS -> MailerService.sendGreetings(jsonData);
            case NEW_LISTING -> MailerService.sendNewListingNotification(jsonData);
            case ACCOUNT_DELETION -> MailerService.sendAccountDeletionNotification(jsonData);
            case GENERIC_NOTIFICATION -> MailerService.sendNotification(jsonData);
            case DEV_ERROR -> MailerService.sendErrorToDev(jsonData);
        }

        return new ResponseContent.Builder().setMessage("Successfully dispatched mail type " + type).build();
    }
}
