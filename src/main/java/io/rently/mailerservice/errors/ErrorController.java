package io.rently.mailerservice.errors;

import com.bugsnag.Bugsnag;
import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.services.ReporterService;
import io.rently.mailerservice.utils.Broadcaster;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorController {

    @Autowired
    private Bugsnag bugsnag;

    @Autowired
    private ReporterService reporter;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseContent unhandledErrors(HttpServletResponse response, Exception exception) {
        Broadcaster.error(exception.getMessage());
        Map<String, Object> report = new HashMap<>();
        report.put("datetime", new Date());
        report.put("message", exception.getMessage());
        report.put("service", "Mailer service");
        report.put("cause", exception.getCause());
        report.put("trace", Arrays.toString(exception.getStackTrace()));
        report.put("exceptionType", exception.getClass());
        reporter.sendReportToDevs(report);
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
