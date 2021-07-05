package com.groupten.statemachine.simulation.aging;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Aging implements IAging {
    private ILeagueModel leagueModel;

    @Override
    public void advanceEveryPlayersAge(League league, int days) {
        leagueModel = Injector.instance().getLeagueModelObject();
        advanceTeamPlayerAges(league, days);
        advanceFreeAgentAges(league, days);
    }

    private void advanceTeamPlayerAges(League league, int days) {
        Map<String, Conference> conferences = league.getConferences();
        List<Player> freeAgents = league.getFreeAgents();

        for (Conference conference : conferences.values()) {
            Map<String, Division> divisions = conference.getDivisions();

            for (Division division : divisions.values()) {
                Map<String, Team> teams = division.getTeams();
                traverseTeamsMapAndAdvancePlayerAges(teams, freeAgents, days);
            }
        }
    }

    private void traverseTeamsMapAndAdvancePlayerAges(Map<String, Team> teams, List<Player> freeAgents, int days) {
        for (Team team : teams.values()) {
            List<Player> activePlayers = team.getActivePlayers();
            List<Player> inActivePlayers = team.getInActivePlayers();

            team.setActivePlayers(updatePlayerRosterBasedOnRetirements(activePlayers, freeAgents, days));
            team.setInActivePlayers(updatePlayerRosterBasedOnRetirements(inActivePlayers, freeAgents, days));

        }
    }

    private List<Player> updatePlayerRosterBasedOnRetirements(List<Player> players, List<Player> freeAgents, int days) {
        List<Player> updatedPlayersList = new ArrayList<Player>();

        for (Player player : players) {
            boolean playerShouldRetire = player.increaseAgeAndCheckIfPlayerShouldBeRetired(days);
            if (playerShouldRetire) {
                Player bestFreeAgent = findBestFreeAgent(freeAgents, player);
                updatedPlayersList.add(bestFreeAgent);
                freeAgents.remove(bestFreeAgent);
            } else {
                updatedPlayersList.add(player);
            }
        }

        return updatedPlayersList;
    }

    private void advanceFreeAgentAges(League league, int days) {
        List<Player> freeAgents = league.getFreeAgents();
        freeAgents.removeIf(player -> player.increaseAgeAndCheckIfPlayerShouldBeRetired(days));
    }

    private Player findBestFreeAgent(List<Player> freeAgents, Player player) {
        String playerPosition = player.getPosition();
        boolean isPlayerCaptain = player.isCaptain();

        Predicate<Player> byPosition = freeAgent -> freeAgent.getPosition().equals(playerPosition);
        List<Player> freeAgentsWithSamePosition = freeAgents.stream().filter(byPosition).collect(Collectors.toList());

        TreeMap<Double, Player> freeAgentsRanked = new TreeMap<Double, Player>();

        for (Player freeAgentPlayer : freeAgentsWithSamePosition) {
            double strength = freeAgentPlayer.calculateStrength();
            freeAgentsRanked.put(strength, freeAgentPlayer);
        }

        Map.Entry<Double, Player> bestFreeAgentEntry = freeAgentsRanked.lastEntry();
        Player bestFreeAgent = bestFreeAgentEntry.getValue();
        bestFreeAgent.setCaptain(isPlayerCaptain);

        return bestFreeAgent;
    }

}
