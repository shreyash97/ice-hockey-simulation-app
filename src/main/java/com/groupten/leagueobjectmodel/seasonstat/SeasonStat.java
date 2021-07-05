package com.groupten.leagueobjectmodel.seasonstat;

import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.statemachine.simulation.simulategame.strategy.IAlgoStrategyObserver;

import java.util.Map;

public class SeasonStat implements IAlgoStrategyObserver {
    private float avgShots = 0;
    private float avgPenalties = 0;
    private float avgGoals = 0;
    private float avgSaves = 0;

    public float getAvgShots() {
        return avgShots;
    }

    public float getAvgPenalties() {
        return avgPenalties;
    }

    public float getAvgGoals() {
        return avgGoals;
    }

    public float getAvgSaves() {
        return avgSaves;
    }

    @Override
    public void updateShots(Map<Player, Integer> shots) {
        int gameShots = 0;
        for (Map.Entry<Player, Integer> entry : shots.entrySet()) {
            Player player = entry.getKey();
            Integer count = entry.getValue();
            gameShots = gameShots + count;
        }
        avgShots = (avgShots + gameShots) / 2;
    }

    @Override
    public void updatePenalties(Map<Player, Integer> penalties) {
        int gamePenalties = 0;
        for (Map.Entry<Player, Integer> entry : penalties.entrySet()) {
            Player player = entry.getKey();
            Integer count = entry.getValue();
            gamePenalties = gamePenalties + count;
        }
        avgPenalties = (avgPenalties + gamePenalties) / 2;
    }

    @Override
    public void updateGoals(Map<Player, Integer> goals) {
        int gameGoals = 0;
        for (Map.Entry<Player, Integer> entry : goals.entrySet()) {
            Player player = entry.getKey();
            Integer count = entry.getValue();
            gameGoals = gameGoals + count;
        }
        avgGoals = (avgGoals + gameGoals) / 2;
    }

    @Override
    public void updateSaves(Map<Player, Integer> saves) {
        int gameSaves = 0;
        for (Map.Entry<Player, Integer> entry : saves.entrySet()) {
            Player player = entry.getKey();
            Integer count = entry.getValue();
            gameSaves = gameSaves + count;
        }
        avgSaves = (avgSaves + gameSaves) / 2;
    }
}
