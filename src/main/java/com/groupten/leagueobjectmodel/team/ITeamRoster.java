package com.groupten.leagueobjectmodel.team;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;

public interface ITeamRoster {
    void setPlayers(List<Player> players);
    List<Player> createActivePlayerRoster();
    List<Player> createInActivePlayerRoster();
    List<Player> returnExcessPlayers();
}
