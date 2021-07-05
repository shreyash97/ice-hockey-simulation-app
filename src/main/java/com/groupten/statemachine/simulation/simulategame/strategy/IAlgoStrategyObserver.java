package com.groupten.statemachine.simulation.simulategame.strategy;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.Map;

public interface IAlgoStrategyObserver {
    void updateShots(Map<Player, Integer> shots);

    void updatePenalties(Map<Player, Integer> penalties);

    void updateGoals(Map<Player, Integer> goals);

    void updateSaves(Map<Player, Integer> goals);
}
