package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.mailer.enums.MailType;
import io.rently.mailerservice.services.MailerService;
import io.rently.mailerservice.utils.Broadcaster;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MailerService service;

    @PostMapping({ "/dispatch", "/dispatch/" })
    public ResponseContent handleDispatch(@RequestBody Map<String, Object> data) throws Exception {
        JSONObject jsonData = new JSONObject(data);
        MailType type = EnumUtils.findEnumInsensitiveCase(MailType.class, jsonData.getString("type"));

        switch (type) {
            case GREETINGS -> service.sendGreetings(jsonData);
            case NEW_LISTING -> service.sendNewListingNotification(jsonData);
            case ACCOUNT_DELETION -> service.sendAccountDeletionNotification(jsonData);
            case GENERIC_NOTIFICATION -> service.sendNotification(jsonData);
            case DEV_ERROR -> service.sendErrorToDev(jsonData);
        }

        return new ResponseContent.Builder().setMessage("Successfully dispatched mail type " + type).build();
    }
}
