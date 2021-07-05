package com.groupten.statemachine.simulation.draft;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModelFactory;
import com.groupten.leagueobjectmodel.season.ISeason;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.Test;

public class DraftTest {
    @Test
    public void executeTest() {
        JSONImport jsonTestSuccess = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/league.json";
        jsonTestSuccess.importJSONData(path);
        jsonTestSuccess.instantiateJSONData();

        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League league = leagueModel.getCurrentLeague();

        ILeagueModelFactory leagueModelFactory = Injector.instance().getLeagueModelFactory();
        IDraft draft = Injector.instance().getDraftInterface();
        Season season = leagueModelFactory.createSeason();
        draft.execute(season);
    }
}
