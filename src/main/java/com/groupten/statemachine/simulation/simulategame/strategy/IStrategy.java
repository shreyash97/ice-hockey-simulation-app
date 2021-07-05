package com.groupten.statemachine.simulation.simulategame.strategy;

import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.Team;

public interface IStrategy {
    Team getWinner(Season season, Team team1, Team team2);
}
