package com.groupten.leagueobjectmodel.team;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;

public interface ITeamBuilder {
    void reset();

    void setTeamName(String teamName);

    void setPlayerRosters(List<Player> players);

    Team getResult();
}
