package io.rently.mailerservice.utils;

import io.rently.mailerservice.errors.Errors;

import java.util.Map;

public class Properties {
    private Properties() { }

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
}
