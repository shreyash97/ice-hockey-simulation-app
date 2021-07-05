package com.groupten.leagueobjectmodel.conference;

import com.groupten.leagueobjectmodel.division.Division;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConferenceTest {
    @Test
    public void addDivisionTest() {
        Conference conference = new Conference("First Conference");
        Division division = new Division("First Division");
        assertTrue(conference.addDivision(division));
    }

    @Test
    public void isNumberOfDivisionsEvenTest() {
        Conference conference = new Conference("First Conference");
        Division divisionOne = new Division("First Division");
        conference.addDivision(divisionOne);
        assertFalse(conference.isNumberOfDivisionsEven());

        Division divisionTwo = new Division("Second Division");
        conference.addDivision(divisionTwo);
        assertTrue(conference.isNumberOfDivisionsEven());
    }

    @Test
    public void containsDivisionTest() {
        Conference conference = new Conference("First Conference");
        Division division = new Division("First Division");
        conference.addDivision(division);
        assertTrue(conference.containsDivision("First Division"));
    }

    @Test
    public void getDivisionTest() {
        Conference conference = new Conference("First Conference");
        Division division = new Division("First Division");
        conference.addDivision(division);
        assertEquals("First Division", conference.getDivision("First Division").getDivisionName());
    }

    @Test
    public void getDivisionsTest() {
        Conference conference = new Conference("First Conference");
        Division division = new Division("First Division");
        conference.addDivision(division);
        division = new Division("Second Division");
        conference.addDivision(division);
        assertEquals(2, conference.getDivisions().size());
    }

    @Test
    public void isConferenceNameValidTest() {
        String conferenceName = "First Conference";
        assertTrue(Conference.isConferenceNameValid(conferenceName));
        conferenceName = "";
        assertFalse(Conference.isConferenceNameValid(conferenceName));
        conferenceName = " ";
        assertFalse(Conference.isConferenceNameValid(conferenceName));
        conferenceName = "Null";
        assertFalse(Conference.isConferenceNameValid(conferenceName));
    }

    @Test
    public void getConferenceIDTest() {
        Conference conference = new Conference(1, "First Conference");
        assertEquals(1, conference.getConferenceID());
    }

    @Test
    public void setConferenceIDTest() {
        Conference conference = new Conference("First Conference");
        conference.setConferenceID(1);
        assertEquals(1, conference.getConferenceID());
    }

    @Test
    public void getConferenceNameTest() {
        Conference conference = new Conference("First Conference");
        assertEquals("First Conference", conference.getConferenceName());
    }

    @Test
    public void setConferenceNameTest() {
        Conference conference = new Conference("First Conference");
        conference.setConferenceName("Updated First Conference");
        assertEquals("Updated First Conference", conference.getConferenceName());
    }
}
