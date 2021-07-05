package com.groupten.statemachine.simulation.injury;

import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;

import java.util.Map;

public class Injury {
    public static void checkPlayerInjuriesAcrossLeague(League league) {
        traverseLeagueModelToCheckForInjuries(league);
    }

    private static void traverseLeagueModelToCheckForInjuries(League league) {
        Map<String, Conference> conferences = league.getConferences();
        for (Conference conference : conferences.values()) {
            traverseDivisions(conference);
        }
    }

    private static void traverseDivisions(Conference conference) {
        Map<String, Division> divisions = conference.getDivisions();
        for (Division division : divisions.values()) {
            traverseTeams(division);
        }
    }

    private static void traverseTeams(Division division) {
        Map<String, Team> teams = division.getTeams();
        for (Team team : teams.values()) {
            traversePlayersAndCheckInjuries(team);
        }
    }

    private static void traversePlayersAndCheckInjuries(Team team) {
        for (Player player : team.getActivePlayers()) {
            player.checkInjury();
        }
    }
}

