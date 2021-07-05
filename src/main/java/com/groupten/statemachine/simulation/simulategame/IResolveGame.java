package com.groupten.statemachine.simulation.simulategame;

import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.statemachine.simulation.simulategame.strategy.IStrategy;

public interface IResolveGame {
    IStrategy getStrategy();

    void setStrategy(IStrategy strategy);

    Team getWinner(Team team1, Team team2);
}
