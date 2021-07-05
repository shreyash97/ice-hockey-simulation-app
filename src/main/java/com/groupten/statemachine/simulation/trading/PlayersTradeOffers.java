package com.groupten.statemachine.simulation.trading;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;

import java.util.*;
import java.util.stream.Collectors;

public class PlayersTradeOffers {
    private IConsole console;
    private ILeagueModel leagueModel;
    private League leagueLOM;
    private int maxPlayersPerTrade = 0;

    public HashMap<HashMap<ArrayList<Player>,Player>,Double> computePlayersTradeOffers(String weakSection, Team tradeInitializingTeam,
                                                                                       Team tradeFinalizingTeam, ArrayList<Player> initialTradingPlayers, ArrayList<Player> finalTradingPlayers) {
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        leagueLOM = leagueModel.getCurrentLeague();
        GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
        maxPlayersPerTrade = tradingConfig.getMaxPlayersPerTrade();
        int maximumPlayerPerTrade = 1;
        int playersChosen = 0;
        Double initialTeamStrengthSum = 0.0;
        Double finalTeamStrengthSum = 0.0;
        double initialTeamStrengthMinRange = 0.0;
        double initialTeamStrengthMaxRange = 0.0;
        double finalTeamStrengthMinRange = 0.0;
        double finalTeamStrengthMaxRange = 0.0;
        Double initialPlayersToTradeStrengthSum = 0.0;
        ArrayList<Double> initialTeamStrength = new ArrayList<>();
        ArrayList<Double> finalTeamStrength = new ArrayList<>();
        ArrayList<ArrayList<Player>> initialCombinationsOfPlayers = new ArrayList<>();
        HashMap<HashMap<ArrayList<Player>,Player>,Double> tempInitialTeamStrength = new HashMap<>();
        HashMap<HashMap<ArrayList<Player>,Player>,Double> tempFinalTeamStrength = new HashMap<>();
        HashMap<HashMap<ArrayList<Player>,Player>,Double> playersTradeOffer = new HashMap<>();

        if(maximumPlayerPerTrade < maxPlayersPerTrade) {
            for(int j = 1; j <= initialTradingPlayers.size() ; j++) {
                ArrayList<Player> playerCombination = new ArrayList<>();
                for(int k = j+1 ; k < initialTradingPlayers.size() && playersChosen < maximumPlayerPerTrade ; k++) {
                    if(playerCombination.contains(initialTradingPlayers.get(j))) {

                    }
                    else {
                        playerCombination.add(initialTradingPlayers.get(j));
                        playersChosen++;
                    }
                    playerCombination.add(initialTradingPlayers.get(k));
                    playersChosen++;
                }
                if(playerCombination.isEmpty()) {

                }
                else {
                    initialCombinationsOfPlayers.add(playerCombination);
                }
            }
            maximumPlayerPerTrade++;
        }
        initialTeamStrengthMinRange = tradeInitializingTeam.calculateTeamStrength() -
                (tradeInitializingTeam.calculateTeamStrength()*0.1);
        initialTeamStrengthMaxRange = tradeInitializingTeam.calculateTeamStrength() +
                (tradeInitializingTeam.calculateTeamStrength()*0.1);
        finalTeamStrengthMinRange = tradeFinalizingTeam.calculateTeamStrength() -
                (tradeFinalizingTeam.calculateTeamStrength()*0.1);
        finalTeamStrengthMaxRange = tradeFinalizingTeam.calculateTeamStrength() +
                (tradeFinalizingTeam.calculateTeamStrength()*0.1);

        ArrayList<Double> freeAgentGoalieStrength = new ArrayList<>();
        ArrayList<Double> freeAgentForwardStrength = new ArrayList<>();
        ArrayList<Double> freeAgentDefenseStrength = new ArrayList<>();

        String finalPositionToTrade = null;
        for(Player player : tradeInitializingTeam.getActivePlayers()) {
            initialTeamStrength.add(player.calculateStrength());
        }
        for(Player player : tradeInitializingTeam.getInActivePlayers()) {
            initialTeamStrength.add(player.calculateStrength());
        }
        for(Player player : tradeFinalizingTeam.getActivePlayers()) {
            finalTeamStrength.add(player.calculateStrength());
        }

        for(Player player : tradeFinalizingTeam.getInActivePlayers()) {
            finalTeamStrength.add(player.calculateStrength());
        }

        for(ArrayList<Player> initialPlayersToTrade : initialCombinationsOfPlayers ) {
            ArrayList<Double> initialPlayerToTradeStrength = new ArrayList<>();
            ArrayList<String> initialPositionsToTrade = new ArrayList<>();
            for(Double initialStrength : initialTeamStrength)
            {
                initialTeamStrengthSum = initialTeamStrengthSum + initialStrength;
            }

            for(Player player : leagueLOM.getFreeAgentsGoalies()) {
                freeAgentGoalieStrength.add(player.calculateStrength());
            }
            for(Player player : leagueLOM.getFreeAgentsSkaters()) {
                if(player.getPosition().equals("forward")) {
                    freeAgentForwardStrength.add(player.calculateStrength());
                }
                else if (player.getPosition().equals("defense")) {
                    freeAgentDefenseStrength.add(player.calculateStrength());
                }
            }
            Collections.sort(freeAgentGoalieStrength);
            Collections.reverse(freeAgentGoalieStrength);
            Collections.sort(freeAgentForwardStrength);
            Collections.reverse(freeAgentForwardStrength);
            Collections.sort(freeAgentDefenseStrength);
            Collections.reverse(freeAgentDefenseStrength);

            for(Player finalPlayerToTrade : finalTradingPlayers) {
                for(Player player : initialPlayersToTrade) {
                    initialPlayerToTradeStrength.add(player.calculateStrength());
                    initialPositionsToTrade.add(player.getPosition());
                }
                for(Double initialStrength : initialPlayerToTradeStrength) {
                    initialPlayersToTradeStrengthSum = initialPlayersToTradeStrengthSum + initialStrength;
                    finalTeamStrength.add(initialStrength);
                }
                finalPositionToTrade = finalPlayerToTrade.getPosition();
                initialTeamStrengthSum = initialTeamStrengthSum - initialPlayersToTradeStrengthSum;
                initialTeamStrengthSum = initialTeamStrengthSum + finalPlayerToTrade.calculateStrength();
                Collections.sort(finalTeamStrength);
                Collections.reverse(finalTeamStrength);

                for(String position : initialPositionsToTrade) {
                    if(position.equals(finalPositionToTrade)) {
                        initialPositionsToTrade.remove(position);
                        break;
                    }
                }
                for(int i=0;i<initialPositionsToTrade.size()-1;i++) {
                    finalTeamStrength.remove(finalTeamStrength.size() - 1);
                }
                for(Double finalStrength : finalTeamStrength)
                {
                    finalTeamStrengthSum = finalTeamStrengthSum + finalStrength;
                }

                for(String position : initialPositionsToTrade) {
                    int goalie = 0;
                    int forward = 0;
                    int defense = 0;
                    if(position.equals("goalie")) {
                        initialTeamStrengthSum = initialTeamStrengthSum + freeAgentGoalieStrength.get(goalie);
                        goalie++;
                    } else if(position.equals("forward")) {
                        initialTeamStrengthSum = initialTeamStrengthSum + freeAgentForwardStrength.get(forward);
                        forward++;
                    } else if(position.equals("defense")) {
                        initialTeamStrengthSum = initialTeamStrengthSum + freeAgentDefenseStrength.get(defense);
                        defense++;
                    }
                }
                if(initialTeamStrengthSum >= initialTeamStrengthMinRange && finalTeamStrengthSum >= finalTeamStrengthMinRange) {
                    HashMap<ArrayList<Player>,Player> tradingPlayers = new HashMap<>();
                    tradingPlayers.put(initialPlayersToTrade,finalPlayerToTrade);
                    tempInitialTeamStrength.put(tradingPlayers,initialTeamStrengthSum);
                    tempFinalTeamStrength.put(tradingPlayers,finalTeamStrengthSum);
                }
            }
            initialTeamStrengthSum = 0.0;
            finalTeamStrengthSum = 0.0;
            initialPlayersToTradeStrengthSum = 0.0;
            initialTeamStrengthSum = 0.0;
            finalTeamStrengthSum = 0.0;
            freeAgentGoalieStrength.clear();
            freeAgentDefenseStrength.clear();
            freeAgentForwardStrength.clear();
        }
        if(tempInitialTeamStrength.size() > 0) {
            Map<HashMap<ArrayList<Player>,Player>,Double> initialSortedTeamStrength = tempInitialTeamStrength.entrySet() .stream()
                    .sorted(Map.Entry.<HashMap<ArrayList<Player>,Player>,Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            Map.Entry<HashMap<ArrayList<Player>,Player>,Double> entry = initialSortedTeamStrength.entrySet().iterator().next();
            playersTradeOffer.put(entry.getKey(),entry.getValue());
        }
        return playersTradeOffer;
    }

}
