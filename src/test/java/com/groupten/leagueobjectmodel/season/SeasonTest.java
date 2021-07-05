package com.groupten.leagueobjectmodel.season;

import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class SeasonTest {

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/JsonMockNoTeams.json");
        json.instantiateJSONData();
    }

    @Test
    public void getCurrentDateTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("30/09/2020", dateFormat.format(season.getCurrentDate()));
    }

    @Test
    public void getRegularSeasonStartsAtTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("01/10/2020", dateFormat.format(season.getRegularSeasonStartsAt()));
    }

    @Test
    public void getRegularSeasonEndsAtTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("03/04/2021", dateFormat.format(season.getRegularSeasonEndsAt()));
    }

    @Test
    public void getTradeEndsAtTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("22/02/2021", dateFormat.format(season.getTradeEndsAt()));
    }

    @Test
    public void getPlayoffStartsAtTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("14/04/2021", dateFormat.format(season.getPlayoffStartsAt()));
    }

    @Test
    public void getPlayoffEndsByTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("01/06/2021", dateFormat.format(season.getPlayoffEndsBy()));
    }

    @Test
    public void advanceTimeTest() {
        Season season = new Season(2020);
        season.advanceTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("01/10/2020", dateFormat.format(season.getCurrentDate()));
    }

    @Test
    public void setCurrentDateTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            season.setCurrentDate(dateFormat.parse("10/09/2020"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals("10/09/2020", dateFormat.format(season.getCurrentDate().getTime()));
    }

    @Test
    public void isTodayRegularSeasonEndTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = dateFormat.parse("03/04/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        season.setCurrentDate(d);
        assertTrue(season.isTodayRegularSeasonEnd());
    }

    @Test
    public void schedulesTodayTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = dateFormat.parse("30/09/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        season.setCurrentDate(d);

        assertEquals(0, season.schedulesToday().size());
    }

    @Test
    public void isTradeEndedTest() {
        Season season = new Season(2020);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = dateFormat.parse("20/02/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        season.setCurrentDate(d);
        assertFalse(season.isTradeEnded());

        try {
            d = dateFormat.parse("23/02/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        season.setCurrentDate(d);
        assertTrue(season.isTradeEnded());
    }

    @Test
    public void isWinnerDetermined() {
        Season season = new Season(2020);
        assertFalse(season.isWinnerDetermined());
    }

    @Test
    public void generateRegularScheduleTest() {
        Season season = new Season(2020);
        assertFalse(season.generateRegularSchedule());
    }

    @Test
    public void generatePlayoffScheduleTest() {
        Season season = new Season(2020);
        assertFalse(season.generatePlayoffSchedule());
    }
}
