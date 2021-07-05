package com.groupten.statemachine.simulation.draft.generateplayers;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;
import java.util.Map;

public interface IPlayersGenerator {
    Map<String, List<Player>> generatePlayers();
}
