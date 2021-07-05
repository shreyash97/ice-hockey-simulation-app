package com.groupten.leagueobjectmodel.team;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.List;

public class TeamBuilder implements ITeamBuilder {
    private Team team;

    @Override
    public void reset() {
        this.team = new Team();
        ISwapPlayers swapPlayers = new SwapPlayers();
        team.setSwapPlayers(swapPlayers);
    }

    @Override
    public void setTeamName(String teamName) {
        team.setTeamName(teamName);
    }


    @Override
    public void setPlayerRosters(List<Player> players) {
        ITeamRoster teamRoster = new TeamRoster();
        teamRoster.setPlayers(players);
        team.setActivePlayers(teamRoster.createActivePlayerRoster());
        team.setInActivePlayers(teamRoster.createInActivePlayerRoster());
    }

    @Override
    public Team getResult() {
        return this.team;
    }
}
