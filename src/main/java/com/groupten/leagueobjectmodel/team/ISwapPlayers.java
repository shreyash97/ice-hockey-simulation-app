package com.groupten.leagueobjectmodel.team;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;
import java.util.Map;

public interface ISwapPlayers {
    Map<String, List<Player>> swapPlayersAndUpdateRosters(List<Player> activePlayerRoster, List<Player> inActivePlayerRoster,
                                                          Player player);
}
