package io.rently.mailerservice.services;

import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.mailer.templates.DevError;
import io.rently.mailerservice.mailer.templates.interfaces.ITemplate;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReporterService {

    @Autowired
    private IMailer mailer;
    @Value("#{'${first.responders}'.split(',')}")
    public List<String> firstResponders;

    public void sendReportToDevs(Map<String, Object> data) {
        String service = Fields.tryGetOptional("service", data, "Unknown source");
        String message = Fields.tryGetOptional("message", data, "No message");
        String cause = Fields.tryGetOptional("cause", data, "Not specified");
        String trace = Fields.tryGetOptional("trace", data, "No trace");
        String exceptionType = Fields.tryGetOptional("exceptionType", data, "Unknown");
        DevError devError = new DevError(service, message, cause, trace, exceptionType, firstResponders, new Date().toString());
        Broadcaster.info("Dispatching error report from " + service + " to " + firstResponders.size() + " dev(s)");
        for (String email : firstResponders) {
            trySendingEmail(email, "[ERROR] " + service, devError);
        }
    }

    public void trySendingEmail(String email, String subject, ITemplate template) {
        try {
            mailer.sendEmail(email, subject, template.getTemplate());
        } catch(Exception ex) {
            Broadcaster.warn("Could not notify email: " + email);
        }
    }
}
