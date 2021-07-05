package com.groupten.leagueobjectmodel.team;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.player.PlayerPosition;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamRoster implements ITeamRoster {
    private final int MAX_TOTAL_PLAYERS = 30;
    private final int MAX_INACTIVE_PLAYERS = 10;
    private final int MAX_ACTIVE_FORWARD_PLAYERS = 12;
    private final int MAX_ACTIVE_DEFENSE_PLAYERS = 6;
    private final int MAX_ACTIVE_GOALIES = 2;

    private List<Player> players;
    private List<Player> activePlayerRoster;
    private List<Player> inActivePlayerRooster;

    public TeamRoster() { }

    @Override
    public void setPlayers(List<Player> players) {
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();

        if (players.size() < MAX_TOTAL_PLAYERS) {
            players.addAll(leagueModel.getCurrentLeague().getFreeAgents());
        }

        this.players = players;
        rankPlayersAccordingToStrength();
    }

    @Override
    public List<Player> createActivePlayerRoster() {
        List<Player> activeForwardPlayers = filterPlayersByPosition(PlayerPosition.FORWARD.name().toLowerCase()).stream().limit(MAX_ACTIVE_FORWARD_PLAYERS).collect(Collectors.toList());
        List<Player> activeDefensePlayers = filterPlayersByPosition(PlayerPosition.DEFENSE.name().toLowerCase()).stream().limit(MAX_ACTIVE_DEFENSE_PLAYERS).collect(Collectors.toList());
        List<Player> activeGoaliePlayers = filterPlayersByPosition(PlayerPosition.GOALIE.name().toLowerCase()).stream().limit(MAX_ACTIVE_GOALIES).collect(Collectors.toList());

        this.activePlayerRoster = Stream.of(activeForwardPlayers, activeDefensePlayers, activeGoaliePlayers).flatMap(Collection::stream).collect(Collectors.toList());
        return this.activePlayerRoster;
    }

    @Override
    public List<Player> createInActivePlayerRoster() {
        for (Player player : activePlayerRoster) {
            this.players.remove(player);
        }

        this.inActivePlayerRooster = players.subList(0, MAX_INACTIVE_PLAYERS);
        return this.inActivePlayerRooster;
    }

    @Override
    public List<Player> returnExcessPlayers() {
        for (Player player : activePlayerRoster) {
            this.players.remove(player);
        }

        return players.subList(MAX_INACTIVE_PLAYERS, players.size());
    }

    private void rankPlayersAccordingToStrength() {
        this.players.sort(Comparator.comparingDouble(Player::calculateStrength).reversed());
    }

    private List<Player> filterPlayersByPosition(String position) {
        return this.players.stream().filter(player -> player.getPosition().equals(position)).collect(Collectors.toList());
    }
}
