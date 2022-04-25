package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.mailer.enums.MailType;
import io.rently.mailerservice.services.MailerService;
import io.rently.mailerservice.services.ReporterService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class MailerController {

    @Autowired
    private ReporterService reporter;

    @Autowired
    private MailerService mailer;

    @PostMapping({ "/emails/dispatch", "/emails/dispatch/" })
    public ResponseContent handleDispatch(@RequestBody Map<String, Object> data) {
        JSONObject jsonData = new JSONObject(data);
        MailType type = EnumUtils.findEnumInsensitiveCase(MailType.class, jsonData.getString("type"));

        switch (type) {
            case GREETINGS -> mailer.sendGreetings(jsonData);
            case NEW_LISTING -> mailer.sendNewListingNotification(jsonData);
            case ACCOUNT_DELETION -> mailer.sendAccountDeletionNotification(jsonData);
            case GENERIC_NOTIFICATION -> mailer.sendNotification(jsonData);
            case LISTING_DELETION -> mailer.sendListingDeletionNotification(jsonData);
            case DEV_ERROR -> new RedirectView("/report/dispatch");
        }

        return new ResponseContent.Builder().setMessage("Successfully dispatched mail: " + type).build();
    }

    @PostMapping({ "/report/dispatch", "/report/dispatch/" })
    public ResponseContent handleErrorDispatch(@RequestBody Map<String, Object> data) {
        reporter.sendReportToDevs(data);
        return new ResponseContent.Builder().setMessage("Error report dispatched").build();
    }
}
