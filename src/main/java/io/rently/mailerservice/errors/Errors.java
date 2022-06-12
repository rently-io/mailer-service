package io.rently.mailerservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Errors {
    public static final ResponseStatusException UNAUTHORIZED_REQUEST = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request token is either no longer valid or has been tampered with");
    public static final ResponseStatusException INVALID_URI_PATH = new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Invalid or incomplete URI");
    public static final ResponseStatusException INTERNAL_SERVER_ERROR = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Request could not be processed due to an internal server error");
    public static final ResponseStatusException MISSING_PARAM = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Some parameters are missing in the request URL");
    public static final ResponseStatusException INVALID_EMAIL_ADDRESS = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid email address present in request body.");
    public static final ResponseStatusException MISSING_OR_INVALID_MAIL_TYPE = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Property `type` is not present or invalid in request body.");
    public static final ResponseStatusException EXPIRED_TOKEN = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is no longer valid");
    public static final ResponseStatusException MALFORMED_TOKEN = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is malformed or has been tampered with");

    public static class HttpBodyFieldMissing extends ResponseStatusException {
        public HttpBodyFieldMissing(String property) {
            super(HttpStatus.NOT_ACCEPTABLE, "Property `" + property + "` is missing in the request body");
        }
    }
}
