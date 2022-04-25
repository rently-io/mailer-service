package io.rently.mailerservice.utils;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.mailer.enums.MailType;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Map;

public class Fields {
    private Fields() { }

    public static MailType tryGetMailType(Map<String, Object> data) {
        try {
            return EnumUtils.findEnumInsensitiveCase(MailType.class, Fields.tryGet("type", data));
        } catch (Exception exception) {
            throw Errors.MISSING_OR_INVALID_MAIL_TYPE;
        }
    }

    public static String tryGetOptional(String field, Map<String, Object> data, String fallback) {
        try {
            return data.get(field).toString();
        } catch (Exception ignore) {
            return fallback;
        }
    }

    public static String tryGet(String field, Map<String, Object> data) {
        try {
            return data.get(field).toString();
        } catch (Exception ignore) {
            throw new Errors.HttpBodyFieldMissing(field);
        }
    }

    public static String tryGetElipsed(String field, Map<String, Object> data, int maxLength) {
        String toElipse = tryGet(field, data);
        if (toElipse.length() > maxLength) {
            return toElipse.substring(0, maxLength).trim() + "...";
        }
        return toElipse;
    }
}
