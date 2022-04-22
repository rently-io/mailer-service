package io.rently.mailerservice.errors;

import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.utils.Broadcaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorController {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseContent handleGenericException(HttpServletResponse response, Exception exception) {
        Broadcaster.error(exception.getMessage());
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
    @ExceptionHandler(MethodNotAllowedException.class)
    public static ResponseContent handleInvalidURI(HttpServletResponse response) {
        ResponseStatusException resEx = Errors.INVALID_URI_PATH;
        response.setStatus(resEx.getStatus().value());
        return new ResponseContent.Builder(resEx.getStatus()).setMessage(resEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public static ResponseContent handleInvalidMethod(HttpServletResponse response) {
        ResponseStatusException resEx = Errors.INVALID_URI_PATH;
        response.setStatus(resEx.getStatus().value());
        return new ResponseContent.Builder(resEx.getStatus()).setMessage(resEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public static ResponseContent handleInvalidFormatException(HttpServletResponse response) {
        ResponseStatusException respEx = Errors.NO_DATA;
        response.setStatus(respEx.getStatus().value());
        return new ResponseContent.Builder(respEx.getStatus()).setMessage(respEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public static ResponseContent handleMissingParam(HttpServletResponse response) {
        ResponseStatusException respEx = Errors.MISSING_PARAM;
        response.setStatus(respEx.getStatus().value());
        return new ResponseContent.Builder(respEx.getStatus()).setMessage(respEx.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(Errors.HttpValidationFailure.class)
    public static ResponseContent handleValidationFailure(HttpServletResponse response, Errors.HttpValidationFailure ex) {
        response.setStatus(ex.getStatus().value());
        return new ResponseContent.Builder(ex.getStatus()).setMessage(ex.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(Errors.HttpFieldMissing.class)
    public static ResponseContent handleValidationFailure(HttpServletResponse response, Errors.HttpFieldMissing ex) {
        response.setStatus(ex.getStatus().value());
        return new ResponseContent.Builder(ex.getStatus()).setMessage(ex.getReason()).build();
    }

    @ResponseBody
    @ExceptionHandler(SendFailedException.class)
    public static ResponseContent handleInvalidEmail(HttpServletResponse response, SendFailedException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.setStatus(status.value());
        return new ResponseContent.Builder(status).setMessage(ex.getMessage()).build();
    }
}
