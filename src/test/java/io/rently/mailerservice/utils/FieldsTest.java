package io.rently.mailerservice.utils;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.mailer.enums.MailType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldsTest {

    @Test
    void tryGetMailType_invalidEmailType_throwMissingEmail() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", null);

        assertThrows(Errors.MISSING_OR_INVALID_MAIL_TYPE.getClass(), () -> Fields.tryGetMailType(data));
    }

    @Test
    void tryGetMailType_validEmailType_returnMailType() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "DEV_ERROR");

        MailType mailType = Fields.tryGetMailType(data);

        assert mailType != null;
    }

    @Test
    void tryGetOptional_emptyMap_returnFallback() {
        String fallback = "my fallback";
        Map<String, Object> data = new HashMap<>();

        String field = Fields.tryGetOptional("myfield", data, fallback);

        assert Objects.equals(field, fallback);
    }

    @Test
    void tryGetOptional_emptyField_returnFallback() {
        String fallback = "my fallback";
        String toSearch = "field";
        Map<String, Object> data = new HashMap<>();
        data.put(toSearch, null);

        String field = Fields.tryGetOptional("myfield", data, fallback);

        assert Objects.equals(field, fallback);
    }

    @Test
    void tryGet_missingField_throwHttpBodyFieldMissing() {
        String toSearch = "field";
        Map<String, Object> data = new HashMap<>();

        assertThrows(Errors.HttpBodyFieldMissing.class, () -> Fields.tryGet(toSearch, data));
    }

    @Test
    void tryGet_fieldPresent_returnField() {
        String toSearch = "field";
        String expectedValue = "my data";
        Map<String, Object> data = new HashMap<>();
        data.put(toSearch, expectedValue);

        String value = Fields.tryGet(toSearch, data);

        assert Objects.equals(value, expectedValue);
    }

    @Test
    void tryGetElipsed_longString_returnShortString() {
        String fieldName = "field";
        String longString = "this is a string that exceeds 20 characters that needs to be shortened";
        int thresholdChar = 20;
        Map<String, Object> data = new HashMap<>();
        data.put(fieldName, longString);

        String shortString = Fields.tryGetElipsed(fieldName, data, thresholdChar);

        assert shortString.length() == thresholdChar + 3;
    }

    @Test
    void tryGetElipsed_shortString_returnShortString() {
        String fieldName = "field";
        String originalString = "this is a short string";
        int thresholdChar = 50;
        Map<String, Object> data = new HashMap<>();
        data.put(fieldName, originalString);

        String shortString = Fields.tryGetElipsed(fieldName, data, thresholdChar);

        assert shortString.equals(originalString);
    }
}