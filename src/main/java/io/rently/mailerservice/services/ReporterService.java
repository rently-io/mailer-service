package io.rently.mailerservice.services;

import io.rently.mailerservice.interfaces.IMessenger;
import io.rently.mailerservice.mailer.Mailer;
import io.rently.mailerservice.mailer.templates.DevError;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Properties;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReporterService {
    private final List<String> firstResponders;
    private final IMessenger mailer;

    public ReporterService(
            @Value("#{'${first.responders}'.split(',')}") List<String> firstResponders,
            @Value("${mailer.password}") String password,
            @Value("${mailer.email}") String email,
            @Value("${mailer.host}") String host
    ) {
        this.firstResponders = firstResponders;
        this.mailer = new Mailer.Builder(email).credentials(email, password).host(host).build();
    }

    public void sendReportToDevs(Map<String, Object> data) {
        String service = Properties.tryGetOptional("service", data, "Unknown source");
        String message = Properties.tryGetOptional("message", data, "No message");
        String cause = Properties.tryGetOptional("cause", data, "Not specified");
        String trace = Properties.tryGetOptional("trace", data, "No trace");
        String exceptionType = Properties.tryGetOptional("exceptionType", data, "Unknown");

        Broadcaster.info("Dispatching error report from " + service + " to " + firstResponders.size() + " dev(s)");

        for (String email : firstResponders) {
            try {
                mailer.sendEmail(email, "[ERROR] " + service, DevError.getTemplate(service, message, cause, trace, exceptionType, firstResponders, new Date().toString()));
            } catch(Exception ex) {
                Broadcaster.warn("Could not notify email: " + email);
            }
        }
    }
}
