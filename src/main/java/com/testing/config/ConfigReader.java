package com.testing.config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {

    private static final Dotenv dotenv = Dotenv.load();

    public static String getEmail() {
        return dotenv.get("PERIPLUS_EMAIL");
    }
    public static String getPassword() {
        return dotenv.get("PERIPLUS_PASSWORD");
    }
    public static String getBaseUrl() {
        return dotenv.get("BASE_URL");
    }
}