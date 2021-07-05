package com.groupten.statemachine.simulation.draft.strategy;

import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.teamstanding.TeamStanding;

import java.util.List;
import java.util.Map;

public interface IDraftContext {
    void setStrategy(IDraftStrategy draftStrategy);

    void executeStrategy(List<TeamStanding> teamStandings, List<Player> players, List<List<Team>> tradePickTeams);
}
