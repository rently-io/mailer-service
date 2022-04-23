package io.rently.mailerservice.errors;

import com.bugsnag.Bugsnag;
import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.services.MailerService;
import io.rently.mailerservice.utils.Broadcaster;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorController {
    private final List<String> emails;

    @Autowired
    private Bugsnag bugsnag;

    @Autowired
    private MailerService service;

    public ErrorController(@Value("#{'${first.responders}'.split(',')}") List<String> emails) {
        this.emails = emails;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseContent unhandledErrors(HttpServletResponse response, Exception exception) {
        Broadcaster.error(exception.getMessage());
        Map<String, Object> error = new HashMap<>();
        error.put("datetime", new Date());
        error.put("message", exception.getMessage());
        error.put("service", "Mailer service");
        error.put("cause", exception.getCause());
        error.put("trace", exception.getStackTrace());
        error.put("exceptionType", exception.getClass());
        error.put("emails", emails);
        service.sendErrorToDev(new JSONObject(error));
        bugsnag.notify(exception);
        ResponseStatusException resEx = Errors.INTERNAL_SERVER_ERROR;
        response.setStatus(resEx.getStatus().value());
        return new ResponseContent.Builder(resEx.getStatus()).setMessage(resEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(ResponseStatusException.class)
    public static ResponseContent handleResponseException(HttpServletResponse response, ResponseStatusException ex) {
        Broadcaster.httpError(ex);
        response.setStatus(ex.getStatus().value());
        return new ResponseContent.Builder(ex.getStatus()).setMessage(ex.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler({
            MethodNotAllowedException.class,
            HttpRequestMethodNotSupportedException.class,
            NoHandlerFoundException.class,
            HttpMessageNotReadableException.class,
    })
    public static ResponseContent handleInvalidURI(HttpServletResponse response) {
        ResponseStatusException resEx = Errors.INVALID_URI_PATH;
        Broadcaster.httpError(resEx);
        response.setStatus(resEx.getStatus().value());
        return new ResponseContent.Builder(resEx.getStatus()).setMessage(resEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public static ResponseContent handleMissingParam(HttpServletResponse response) {
        ResponseStatusException resEx = Errors.MISSING_PARAM;
        Broadcaster.httpError(resEx);
        response.setStatus(resEx.getStatus().value());
        return new ResponseContent.Builder(resEx.getStatus()).setMessage(resEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler({ SendFailedException.class, JSONException.class })
    public static ResponseContent handleBadRequests(HttpServletResponse response, Exception ex) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        Broadcaster.httpError(status, ex.getMessage());
        response.setStatus(status.value());
        return new ResponseContent.Builder(status).setMessage(ex.getMessage()).build();
    }
}
