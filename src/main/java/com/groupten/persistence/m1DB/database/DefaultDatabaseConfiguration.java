package com.groupten.persistence.m1DB.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class DefaultDatabaseConfiguration implements IDatabaseConfiguration {
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;

    public DefaultDatabaseConfiguration() {
        JsonObject configData = getConfigData();
        try {
            String DB_HOST = configData.get("DB_HOST").getAsString();
            String DB_PORT = configData.get("DB_PORT").getAsString();
            String DB_NAME = configData.get("DB_NAME").getAsString();
            DB_URL = "jdbc:" + "mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
            DB_USER = configData.get("DB_USER").getAsString();
            DB_PASS = configData.get("DB_PASS").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JsonObject getConfigData() {
        String CONFIG_PATH = "configuration.json";
        JsonParser parser = new JsonParser();
        JsonObject configData = null;
        try {
            configData = (JsonObject) parser.parse(new FileReader(CONFIG_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configData;
    }

    public String getDatabaseUserName() {
        return DB_USER;
    }

    public String getDatabasePassword() {
        return DB_PASS;
    }

    public String getDatabaseURL() {
        return DB_URL;
    }
}
