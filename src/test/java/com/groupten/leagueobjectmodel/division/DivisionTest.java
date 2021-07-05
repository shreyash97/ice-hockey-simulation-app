package com.groupten.leagueobjectmodel.division;

import com.groupten.leagueobjectmodel.team.Team;
import org.junit.Test;

import static org.junit.Assert.*;

public class DivisionTest {
    @Test
    public void addTeamTest() {
        Division division = new Division(1, "First Division");
        Team team = new Team(1, "First Team");
        assertTrue(division.addTeam(team));
    }

    @Test
    public void containsTeamTest() {
        Division division = new Division(1, "First Division");
        Team team = new Team(1, "First Team");
        division.addTeam(team);
        assertTrue(division.containsTeam("First Team"));
    }

    @Test
    public void getTeamTest() {
        Division division = new Division(1, "First Division");
        Team team = new Team(1, "First Team");
        division.addTeam(team);
        assertEquals("First Team", division.getTeam("First Team").getTeamName());
    }

    @Test
    public void getTeamsTest() {
        Division division = new Division(1, "First Division");
        Team team = new Team(1, "First Team");
        division.addTeam(team);
        team = new Team(2, "Second Team");
        division.addTeam(team);
        assertEquals(2, division.getTeams().size());
    }

    @Test
    public void isDivisionNameValidTest() {
        String divisionName = "First Division";
        assertTrue(Division.isDivisionNameValid(divisionName));
        divisionName = "";
        assertFalse(Division.isDivisionNameValid(divisionName));
        divisionName = " ";
        assertFalse(Division.isDivisionNameValid(divisionName));
        divisionName = "Null";
        assertFalse(Division.isDivisionNameValid(divisionName));
    }

    @Test
    public void getDivisionIDTest() {
        Division division = new Division(1, "First Division");
        assertEquals(1, division.getDivisionID());
    }

    @Test
    public void setDivisionIDTest() {
        Division division = new Division("First Division");
        division.setDivisionID(1);
        assertEquals(1, division.getDivisionID());
    }

    @Test
    public void getDivisionNameTest() {
        Division division = new Division(1, "First Division");
        assertEquals("First Division", division.getDivisionName());
    }

    @Test
    public void setDivisionNameTest() {
        Division division = new Division("First Division");
        division.setDivisionName("Updated First Division");
        assertEquals("Updated First Division", division.getDivisionName());
    }
}
