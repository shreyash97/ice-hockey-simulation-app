package com.groupten.leagueobjectmodel.schedule;

import com.groupten.leagueobjectmodel.team.Team;

import java.util.Date;
import java.util.HashSet;

public class Schedule {
    private Date gameDate;
    private HashSet<Team> teams = new HashSet<>();

    public Schedule() {
        gameDate = null;
    }

    public Schedule(Date date) {
        gameDate = new Date();
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public boolean addTeam(Team team) {
        if (teams.size() <= 2) {
            teams.add(team);
            return true;
        } else {
            return false;
        }
    }

    public HashSet<Team> getTeams() {
        return teams;
    }
}
