package com.groupten.statemachine.simulation.simulategame;

import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.statemachine.simulation.simulategame.strategy.IStrategy;
import com.groupten.statemachine.simulation.simulategame.strategy.RandomStrategy;

public class ResolveGame implements IResolveGame {
    private final Season season;
    private IStrategy strategy;

    public ResolveGame(Season season) {
        this.strategy = new RandomStrategy();
        this.season = season;
    }

    @Override
    public IStrategy getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Team getWinner(Team team1, Team team2) {
        return strategy.getWinner(this.season, team1, team2);
    }
}
