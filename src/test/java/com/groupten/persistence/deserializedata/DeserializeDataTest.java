package com.groupten.persistence.deserializedata;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.persistence.serializedata.SerializeData;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DeserializeDataTest {

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/league.json");
        json.instantiateJSONData();
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League exportedLeague = leagueModel.getCurrentLeague();
        leagueModel.getCurrentLeague().setUserTeam("Team Deep");
        String path = "src/main/resources/";
        SerializeData serializeData = new SerializeData();
        serializeData.exportData(exportedLeague, path);
    }

    @Test
    public void importDataTestSuccess() {
        String path = "src/test/java/com/groupten/mocks/Team_Deep.json";
        DeserializeData deserializeData = new DeserializeData();
        assertNotNull(deserializeData.importData(path));
    }

    @Test
    public void importDataTestUnSuccess() {
        String path = "";
        DeserializeData deserializeData = new DeserializeData();
        assertNull(deserializeData.importData(path));
    }

}
