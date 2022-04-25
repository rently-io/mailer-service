package io.rently.mailerservice.controllers;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.mailer.enums.MailType;
import io.rently.mailerservice.services.MailerService;
import io.rently.mailerservice.services.ReporterService;
import io.rently.mailerservice.utils.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.yaml.snakeyaml.util.EnumUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class MailerController {

    @Autowired
    private MailerService mailer;

    @Autowired
    private ReporterService reporter;

    @PostMapping({ "/emails/dispatch", "/emails/dispatch/" })
    public ResponseContent handleDispatch(@RequestBody Map<String, Object> data) {
        MailType type = Fields.tryGetMailType(data);

        switch (type) {
            case GREETINGS -> mailer.sendGreetings(data);
            case NEW_LISTING -> mailer.sendNewListingNotification(data);
            case ACCOUNT_DELETION -> mailer.sendAccountDeletionNotification(data);
            case GENERIC_NOTIFICATION -> mailer.sendNotification(data);
            case LISTING_DELETION -> mailer.sendListingDeletionNotification(data);
            case DEV_ERROR -> reporter.sendReportToDevs(data);
        }

        return new ResponseContent.Builder().setMessage("Successfully dispatched mail: " + type).build();
    }

    @PostMapping({ "/report/dispatch", "/report/dispatch/" })
    public ResponseContent handleErrorDispatch(@RequestBody Map<String, Object> data) {
        reporter.sendReportToDevs(data);
        return new ResponseContent.Builder().setMessage("Error report dispatched").build();
    }
}
