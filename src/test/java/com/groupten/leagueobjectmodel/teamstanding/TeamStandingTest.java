package com.groupten.leagueobjectmodel.teamstanding;

import com.groupten.leagueobjectmodel.team.Team;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamStandingTest {
    @Test
    public void getTeamTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals("Team1", teamStanding.getTeam().getTeamName());
    }

    @Test
    public void setTeamTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setTeam(new Team("Team11"));
        assertEquals("Team11", teamStanding.getTeam().getTeamName());
    }

    @Test
    public void getDivisionNameTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals("Division1", teamStanding.getDivisionName());
    }

    @Test
    public void setDivisionNameTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setDivisionName("Division11");
        assertEquals("Division11", teamStanding.getDivisionName());
    }

    @Test
    public void getConferenceNameTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals("Conference1", teamStanding.getConferenceName());
    }

    @Test
    public void setConferenceNameTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setConferenceName("Conference11");
        assertEquals("Conference11", teamStanding.getConferenceName());
    }

    @Test
    public void getPointsTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals(1, teamStanding.getPoints());
    }

    @Test
    public void setPointsTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setPoints(11);
        assertEquals(11, teamStanding.getPoints());
    }

    @Test
    public void getLeagueRankTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals(2, teamStanding.getLeagueRank());
    }

    @Test
    public void setLeagueRankTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setLeagueRank(22);
        assertEquals(22, teamStanding.getLeagueRank());
    }

    @Test
    public void getConferenceRankTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals(3, teamStanding.getConferenceRank());
    }

    @Test
    public void setConferenceRankTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setConferenceRank(33);
        assertEquals(33, teamStanding.getConferenceRank());
    }

    @Test
    public void getDivisionRankTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        assertEquals(4, teamStanding.getDivisionRank());
    }

    @Test
    public void setDivisionRankTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        teamStanding.setDivisionRank(44);
        assertEquals(44, teamStanding.getDivisionRank());
    }

    @Test
    public void compareToTest() {
        TeamStanding teamStanding = new TeamStanding(new Team("Team1"), "Division1", "Conference1", 1, 2, 3, 4);
        TeamStanding teamStanding2 = new TeamStanding(new Team("Team2"), "Division1", "Conference1", 5, 6, 7, 8);
        assertEquals(-4, teamStanding.compareTo(teamStanding2));
    }
}
