package io.rently.mailerservice.utils;

import io.rently.mailerservice.errors.Errors;

import java.util.Map;

public class Properties {

    private Properties() { }

    public static String tryGetProperty(String property, Map<String, Object> data) {
        try {
            return data.get(property).toString();
        } catch (Exception ignore) {
            throw new Errors.HttpBodyFieldMissing(property);
        }
    }

}
