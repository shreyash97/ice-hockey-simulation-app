package com.groupten.persistence.serializedata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groupten.leagueobjectmodel.league.League;

import java.io.FileWriter;
import java.io.IOException;

public class SerializeData implements ISerializeData {

    public SerializeData() {
    }

    public boolean exportData(League leagueLOM, String path) {

        path += leagueLOM.getUserTeam().replace(" ", "_") + ".json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter fileWriter = new FileWriter(path);
            gson.toJson(leagueLOM, fileWriter);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
