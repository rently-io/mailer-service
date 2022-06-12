package io.rently.mailerservice.errors;

import com.bugsnag.Bugsnag;
import io.rently.mailerservice.configs.ExceptionControllerTestConfigs;
import io.rently.mailerservice.dtos.ResponseContent;
import io.rently.mailerservice.services.ReporterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(ExceptionController.class)
@ContextConfiguration(classes = ExceptionControllerTestConfigs.class)
class ExceptionControllerTest {

    public MockHttpServletResponse response;
    @Autowired
    public ExceptionController controller;
    @Autowired
    public ReporterService reporter;
    @Autowired
    public Bugsnag bugsnag;

    @BeforeEach
    void setup() {
        response = new MockHttpServletResponse();
    }

    @Test
    void unhandledErrors_reporterInvoked_bugsnagInvoked_void() {
        Exception exception = new Exception("This is an unhandled exception");

        ResponseContent content = controller.unhandledErrors(response, exception);

        verify(reporter, times(1)).sendReportToDevs(
                argThat(report -> {
                    assert Objects.equals(report.get("message"), exception.getMessage());
                    assert Objects.equals(report.get("service"), "Mailer service");
                    assert Objects.equals(report.get("cause"), exception.getCause());
                    assert Objects.equals(report.get("trace"), Arrays.toString(exception.getStackTrace()));
                    assert Objects.equals(report.get("exceptionType"), exception.getClass());
                    return true;
                })
        );

        verify(bugsnag, times(1)).notify(
                (Throwable) argThat(thrw -> {
                    assert thrw.getClass() == exception.getClass();
                    return true;
                })
        );

        ResponseStatusException expectedException = Errors.INTERNAL_SERVER_ERROR;
        assert response.getStatus() == expectedException.getStatus().value();
        assert content.getStatus() == expectedException.getStatus().value();
        assert Objects.requireNonNull(expectedException.getMessage()).contains(content.getMessage());
    }

    @Test
    void handleResponseException() {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a bad request exception");

        ResponseContent content = ExceptionController.handleResponseException(response, exception);

        assert response.getStatus() == exception.getStatus().value();
        assert content.getStatus() == exception.getStatus().value();
        assert Objects.requireNonNull(exception.getMessage()).contains(content.getMessage());
    }

    @Test
    void handleInvalidURI() {
        ResponseStatusException expectedException = Errors.INVALID_URI_PATH;

        ResponseContent content = ExceptionController.handleInvalidURI(response);

        assert response.getStatus() == expectedException.getStatus().value();
        assert content.getStatus() == expectedException.getStatus().value();
        assert Objects.requireNonNull(expectedException.getMessage()).contains(content.getMessage());
    }

    @Test
    void handleMissingParam() {
        ResponseStatusException expectedException = Errors.MISSING_PARAM;

        ResponseContent content = ExceptionController.handleMissingParam(response);

        assert response.getStatus() == expectedException.getStatus().value();
        assert content.getStatus() == expectedException.getStatus().value();
        assert Objects.requireNonNull(expectedException.getMessage()).contains(content.getMessage());
    }

    @Test
    void handleBadRequests() {
        Exception exception = new Exception("This is a bad request.");

        ResponseContent content = ExceptionController.handleBadRequests(response, exception);

        assert response.getStatus() == HttpStatus.NOT_ACCEPTABLE.value();
        assert content.getStatus() == HttpStatus.NOT_ACCEPTABLE.value();
        assert Objects.requireNonNull(exception.getMessage()).contains(content.getMessage());
    }

    @Test
    void httpBodyFieldMissing() {
        Errors.HttpBodyFieldMissing exception = new Errors.HttpBodyFieldMissing("message");

        ResponseContent content = ExceptionController.handleBadRequests(response, exception);

        assert response.getStatus() == HttpStatus.NOT_ACCEPTABLE.value();
        assert content.getStatus() == HttpStatus.NOT_ACCEPTABLE.value();
        assert Objects.requireNonNull(exception.getMessage()).contains(content.getMessage());
    }
}