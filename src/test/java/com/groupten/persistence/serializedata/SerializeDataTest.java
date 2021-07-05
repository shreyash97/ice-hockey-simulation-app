package com.groupten.persistence.serializedata;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SerializeDataTest {

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/league.json");
        json.instantiateJSONData();
    }

    @Test
    public void exportDataTestSuccess() {
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        leagueModel.getCurrentLeague().setUserTeam("Team Deep");
        League exportedLeague = leagueModel.getCurrentLeague();
        String path = "src/main/resources/";
        SerializeData serializeData = new SerializeData();
        assertTrue(serializeData.exportData(exportedLeague, path));
    }

    @Test
    public void exportDataTestUnSuccess() {
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        leagueModel.getCurrentLeague().setUserTeam("Team Deep");
        League exportedLeague = leagueModel.getCurrentLeague();
        String path = "xyz/";
        SerializeData serializeData = new SerializeData();
        assertFalse(serializeData.exportData(exportedLeague, path));
    }

}
