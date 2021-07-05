package com.groupten.leagueobjectmodel.leaguemodel;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.persistence.m1DB.dao.ILeagueDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueModel implements ILeagueModel {
    private League currentLeague;

    @Override
    public League getCurrentLeague() {
        return currentLeague;
    }

    @Override
    public boolean setCurrentLeague(League currentLeague) {
        if (League.isLeagueNameValid(currentLeague.getLeagueName())) {
            this.currentLeague = currentLeague;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getTotalNumberOfTeams() {
        int teamCount = 0;
        for (Conference conference : currentLeague.getConferences().values()) {
            for (Division division : conference.getDivisions().values()) {
                teamCount += division.getTeams().size();
            }
        }

        return teamCount;
    }

    @Override
    public void addExcessPlayersToFreeAgentsList(List<Player> excessPlayers) {
        for (Player player : excessPlayers) {
            this.currentLeague.addFreeAgent(player);
        }
    }

}
