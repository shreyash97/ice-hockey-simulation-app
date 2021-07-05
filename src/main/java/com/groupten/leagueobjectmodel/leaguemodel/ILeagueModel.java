package com.groupten.leagueobjectmodel.leaguemodel;

import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;

public interface ILeagueModel {
    League getCurrentLeague();
    boolean setCurrentLeague(League currentLeague);
    int getTotalNumberOfTeams();
    void addExcessPlayersToFreeAgentsList(List<Player> excessPlayers);
}
