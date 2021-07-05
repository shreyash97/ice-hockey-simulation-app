package com.groupten.statemachine.simulation.generateplayoffschedule;

import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class GeneratePlayoffScheduleTest {
    Season season = new Season(2020);

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/JsonMockNoTeams.json");
        json.instantiateJSONData();
    }

    @Test
    public void generatePlayoffScheduleTest() {
        GeneratePlayoffSchedule generatePlayoffSchedule = new GeneratePlayoffSchedule();
        assertFalse(generatePlayoffSchedule.generatePlayoffSchedule(season));
    }
}
