package com.groupten.statemachine.simulation.statsdecay;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;

public interface IStatsDecay {
    void checkLikelihoodAndDecayStatus(List<Player> players);
}
