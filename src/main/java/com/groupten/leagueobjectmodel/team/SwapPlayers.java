package com.groupten.leagueobjectmodel.team;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwapPlayers implements ISwapPlayers {
    private Map<String, List<Player>> updatedRosters = new HashMap<>();

    @Override
    public Map<String, List<Player>> swapPlayersAndUpdateRosters(List<Player> activePlayerRoster, List<Player> inActivePlayerRoster, Player player) {
        System.out.println(player.getPlayerName() + " at " + player.getPosition() + " position should be swapped.");

        boolean isPlayerAlreadyInActive = inActivePlayerRoster.contains(player);

        if (isPlayerAlreadyInActive) {
            updatedRosters.put(PlayerRosterNames.ACTIVE_PLAYERS_ROSTER.name(), activePlayerRoster);
            updatedRosters.put(PlayerRosterNames.INACTIVE_PLAYERS_ROSTER.name(), inActivePlayerRoster);

            return updatedRosters;
        } else {
            for (Player inActivePlayer : inActivePlayerRoster) {
                if (inActivePlayer.getPosition().equals(player.getPosition()) && inActivePlayer.isInjured() == false) {
                    int inActivePlayerIndex = inActivePlayerRoster.indexOf(inActivePlayer);
                    int activePlayerIndex = activePlayerRoster.indexOf(player);
                    activePlayerRoster.set(activePlayerIndex, inActivePlayer);
                    inActivePlayerRoster.set(inActivePlayerIndex, player);

                    updatedRosters.put(PlayerRosterNames.ACTIVE_PLAYERS_ROSTER.name(), activePlayerRoster);
                    updatedRosters.put(PlayerRosterNames.INACTIVE_PLAYERS_ROSTER.name(), inActivePlayerRoster);
                    break;
                } else {
                    updatedRosters.put(PlayerRosterNames.ACTIVE_PLAYERS_ROSTER.name(), activePlayerRoster);
                    updatedRosters.put(PlayerRosterNames.INACTIVE_PLAYERS_ROSTER.name(), inActivePlayerRoster);
                    break;
                }
            }
        }

        return updatedRosters;
    }
}
