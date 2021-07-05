package com.groupten.statemachine.simulation.initializeseason;

import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class InitializeSeasonTest {
    Season season = new Season(2020);

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/JsonMockNoTeams.json");
        json.instantiateJSONData();
    }

    @Test
    public void generatePlayoffScheduleTest() {
        InitializeSeason initializeSeason = new InitializeSeason();
        assertFalse(initializeSeason.generateRegularSchedule(season));
    }
}
