package com.groupten.statemachine.simulation.advancetime;

import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class AdvanceTimeTest {
    Season season = new Season(2020);

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/JsonMockNoTeams.json");
        json.instantiateJSONData();
    }

    @Test
    public void advanceTimeTest() {
        AdvanceTime advanceTime = new AdvanceTime();
        advanceTime.advanceTime(season);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("01/10/2020", dateFormat.format(season.getCurrentDate()));
    }

}
