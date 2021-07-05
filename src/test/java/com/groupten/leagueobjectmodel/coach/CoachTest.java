package com.groupten.leagueobjectmodel.coach;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoachTest {
    @Test
    public void areCoachFieldsValidTest() {
        String coachName = "First Coach";
        double skating = 0.5;
        double shooting = 0.5;
        double checking = 0.5;
        double saving = 0.5;
        assertTrue(Coach.areCoachFieldsValid(coachName, skating, shooting, checking, saving));
        saving = 1.5;
        assertFalse(Coach.areCoachFieldsValid(coachName, skating, shooting, checking, saving));
    }

    @Test
    public void getCoachIDTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertEquals(1, coach.getCoachID());
    }

    @Test
    public void setCoachIDTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setCoachID(1);
        assertEquals(1, coach.getCoachID());
    }

    @Test
    public void getCoachNameTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertEquals("First Coach", coach.getCoachName());
    }

    @Test
    public void setCoachNameTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setCoachName("Updated First Coach");
        assertEquals("Updated First Coach", coach.getCoachName());
    }

    @Test
    public void setSkatingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertEquals(0.5, coach.getSkating(), 0.0);
    }

    @Test
    public void getSkatingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setSkating(1.0);
        assertEquals(1.0, coach.getSkating(), 0.0);
    }

    @Test
    public void setShootingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertEquals(0.5, coach.getShooting(), 0.0);
    }

    @Test
    public void getShootingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setShooting(1.0);
        assertEquals(1.0, coach.getShooting(), 0.0);
    }

    @Test
    public void setCheckingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertEquals(0.5, coach.getChecking(), 0.0);
    }

    @Test
    public void getCheckingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setChecking(1.0);
        assertEquals(1.0, coach.getChecking(), 0.0);
    }

    @Test
    public void setSavingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertEquals(0.5, coach.getSaving(), 0.0);
    }

    @Test
    public void getSavingTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setSaving(1.0);
        assertEquals(1.0, coach.getSaving(), 0.0);
    }

    @Test
    public void setLeagueIDTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setLeagueID(1);
        assertEquals(1, coach.getLeagueID());
    }

    @Test
    public void setTeamIDTest() {
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        coach.setTeamID(1);
        assertEquals(1, coach.getTeamID());
    }
}
