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

public class DraftPickTradeOffers {

    private IConsole console;
    private ILeagueModel leagueModel;
    private League leagueLOM;

    public HashMap<Integer,Player> computeDraftPickTradeOffers(String weakSection, Team tradeInitializingTeam,
                                            Team tradeFinalizingTeam,ArrayList<Player> finalTradingPlayers,
                                            Double averageGoalieStrength, Double averageForwardStrength,
                                            Double averageDefenseStrength) {
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        leagueLOM = leagueModel.getCurrentLeague();
        GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
        HashMap<Player,Double> finalTradingPlayersStrength = new HashMap<>();
        HashMap<HashMap<Integer,Player>,Double> afterTradingTeamStrength = new HashMap<>();
        HashMap<Integer,Player> draftPickTradeOffer = new HashMap<>();
        Player playerTraded;
        final int numberOfDraftPicks = 7;

        for(Player player : finalTradingPlayers) {
            if(player.getPosition().equals("goalie")) {
                if(player.calculateStrength() > averageGoalieStrength) {
                    finalTradingPlayersStrength.put(player, player.calculateStrength());
                }
            } else  if(player.getPosition().equals("forward")) {
                if(player.calculateStrength() > averageForwardStrength) {
                    finalTradingPlayersStrength.put(player, player.calculateStrength());
                }
            } else  if(player.getPosition().equals("defense")) {
                if(player.calculateStrength() > averageDefenseStrength) {
                    finalTradingPlayersStrength.put(player, player.calculateStrength());
                }
            }

        }

        Map<Player,Double> finalSortedTradingPlayersStrength = finalTradingPlayersStrength.entrySet() .stream()
                .sorted(Map.Entry.<Player,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Set<Player> playerSet = finalSortedTradingPlayersStrength.keySet();
        ArrayList<Player> finalSortedTradingPlayers = new ArrayList<Player>(playerSet);
        Double initialTeamStrength = tradeInitializingTeam.calculateTotalTeamStrength();
        Double tempInitialTeamStrength = 0.0;
        for(int i=1;i<=numberOfDraftPicks;i++) {
            HashMap<Integer,Player> playerDraftPickTrading = new HashMap<>();
            tempInitialTeamStrength = initialTeamStrength;
            playerTraded = finalSortedTradingPlayers.get(i);
            tempInitialTeamStrength = tempInitialTeamStrength + playerTraded.calculateStrength();
            playerDraftPickTrading.put(i,playerTraded);
            afterTradingTeamStrength.put(playerDraftPickTrading,tempInitialTeamStrength);
        }

        Map<HashMap<Integer,Player>,Double> sortedAfterTradingTeamStrength = afterTradingTeamStrength.entrySet() .stream()
                .sorted(Map.Entry.<HashMap<Integer,Player>,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Map.Entry<HashMap<Integer,Player>,Double> entry = sortedAfterTradingTeamStrength.entrySet().iterator().next();
        Map.Entry<Integer,Player> draftEntry = entry.getKey().entrySet().iterator().next();
        draftPickTradeOffer.put(draftEntry.getKey(),draftEntry.getValue());
        return draftPickTradeOffer;
    }

}
