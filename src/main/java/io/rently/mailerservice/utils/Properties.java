package io.rently.mailerservice.utils;

import org.json.JSONObject;

public class Properties {
    private Properties() { }

    public static String tryGetOptional(String field, JSONObject json, String fallback) {
        try {
            return json.getString(field);
        } catch (Exception ignore) {
            return fallback;
        }
    }
}
