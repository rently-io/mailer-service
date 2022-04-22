package io.rently.mailerservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Errors {
    public static final ResponseStatusException UNAUTHORIZED_REQUEST = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request is either no longer valid or has been tampered with");
    public static final ResponseStatusException INVALID_REQUEST = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request made");
    public static final ResponseStatusException INVALID_URI_PATH = new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Invalid or incomplete URI");
    public static final ResponseStatusException INTERNAL_SERVER_ERROR = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Request could not be processed due to an internal server error");
    public static final ResponseStatusException NO_DATA = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No content found in request body");
    public static final ResponseStatusException MISSING_PARAM = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Some parameters are missing in the request URL");
    public static final ResponseStatusException INVALID_EMAIL_ADDRESS = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid email address present in request body.");

    public static class HttpFieldMissing extends ResponseStatusException {
        public HttpFieldMissing(String fieldName) {
            super(HttpStatus.NOT_ACCEPTABLE, "A non-optional field has missing value. Value of field '" + fieldName + "' was expected but got null");
        }
    }

    public static class HttpBodyFieldMissing extends ResponseStatusException {
        public HttpBodyFieldMissing(String property) {
            super(HttpStatus.NOT_ACCEPTABLE, "Property `" + property + "` is missing in the request body");
        }
    }

    public static class HttpValidationFailure extends ResponseStatusException {
        public HttpValidationFailure(String fieldName, Class<?> fieldType, String value) {
            super(HttpStatus.NOT_ACCEPTABLE, "Validation failure occurred. Value of field '" + fieldName + "' could not be recognized as type " + fieldType.getName() + " (value: '" + value + "')");
        }
    }
}
