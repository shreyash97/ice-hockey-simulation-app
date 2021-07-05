package com.groupten.statemachine.simulation.statsdecay;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;

public class StatsDecay implements IStatsDecay {

    @Override
    public void checkLikelihoodAndDecayStatus(List<Player> players) {
        for (Player player : players) {
            if (shouldStatsDecay()) {
                player.decayStats();
            }
        }
    }

    private boolean shouldStatsDecay() {
        return calculateLikeliHoodOfDecay() >= getAgingConfig().getStatDecayChance();
    }

    private double calculateLikeliHoodOfDecay() {
        return Math.random();
    }

    private GameConfig.Aging getAgingConfig() {
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League league = leagueModel.getCurrentLeague();
        return league.getAgingConfig();
    }
}
