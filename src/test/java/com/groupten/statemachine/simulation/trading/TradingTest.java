package com.groupten.statemachine.simulation.trading;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TradingTest {
    private IConsole console;
    private ILeagueModel leagueModel;
    private League leagueLOM;
    GameConfig.Trading tradingConfig;
    ITradeFactory tradeFactory = Injector.instance().getTradingObject();
    PlayerTradeOffers playerTradeOffers = tradeFactory.createPlayerTradeOffers();
    PlayersTradeOffers playersTradeOffers = tradeFactory.createPlayersTradeOffers();
    DraftPickTradeOffers draftPickTradeOffers = tradeFactory.createDraftPickTradeOffers();
    Trading trading = tradeFactory.createTrading();
    AdjustTeamPlayers adjustTeamPlayers = tradeFactory.createAdjustTeamPlayers();
    @Before
    public void instantiate(){
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        JSONImport jsonImport = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/TradingJsonMock.json";
        jsonImport.importJSONData(path);
        jsonImport.instantiateJSONData();
        leagueLOM = leagueModel.getCurrentLeague();
        tradingConfig = leagueLOM.getTradingConfig();
    }

    @Test
    public void getAveragePlayerStrengthTest(){
        assertEquals(trading.getAverageDefenseStrength(),0.0,0);
        assertEquals(trading.getAverageForwardStrength(),0.0,0);
        assertEquals(trading.getAverageGoalieStrength(),0.0,0);
        trading.getAveragePlayerStrength();
        assertNotEquals(trading.getAverageDefenseStrength(),0.0,0);
        assertNotEquals(trading.getAverageForwardStrength(),0.0,0);
        assertNotEquals(trading.getAverageGoalieStrength(),0.0,0);

    }

    @Test
    public void getInitialTeamTest(){
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    if(team.getTeamName().equals("Cairo Blazers")) {
                        team.setLossPoint(20);
                    }
                }
            }
        }
        trading.getInitialTeam();
        assertEquals(trading.getTradeInitializingTeam().getTeamName(), "Cairo Blazers");

    }

    @Test
    public void getFinalTeamTest(){
        trading.setAverageDefenseStrength(26.33096590909091);
        trading.setAverageForwardStrength(26.741666666666667);
        trading.setAverageGoalieStrength(22.15625);
        trading.setWeakSection("defense");
        assertEquals(trading.getTradeFinalizingTeam().getTeamName(),null);
        trading.getFinalTeam();
        assertEquals(trading.getTradeFinalizingTeam().getTeamName(),"Winnipeg Hound Dogs");
    }

    @Test
    public void getInitialAndFinalTradingPlayersTest(){
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                   if(team.getTeamName().equals("Ottawa Blues")) {
                       trading.setTradeInitializingTeam(team);
                   } else if(team.getTeamName().equals("Winnipeg Hound Dogs")) {
                       trading.setTradeFinalizingTeam(team);
                   }
                }
            }
        }
        trading.setWeakSection("defense");
        assertEquals(trading.getInitialTradingPlayers().size(),0);
        assertEquals(trading.getFinalTradingPlayers().size(),0);
        trading.getInitialAndFinalTradingPlayers(trading.getWeakSection());
        assertNotEquals(trading.getInitialTradingPlayers().size(),0);
        assertNotEquals(trading.getFinalTradingPlayers().size(),0);
    }

    @Test
    public void computePlayerTradeOffersTest() {

        trading.setWeakSection("defense");
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    if(team.getTeamName().equals("Ottawa Blues")) {
                        trading.setTradeInitializingTeam(team);
                        for(Player player : trading.getTradeInitializingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {

                            }
                            else {
                                trading.getInitialTradingPlayers().add(player);
                            }
                        }
                    } else if(team.getTeamName().equals("Winnipeg Hound Dogs")) {
                        trading.setTradeFinalizingTeam(team);
                        for(Player player : trading.getTradeFinalizingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {
                                trading.getFinalTradingPlayers().add(player);
                            }
                        }
                    }
                }
            }
        }
        HashMap<HashMap<Player,Player>,Double> playerTradeOffer = playerTradeOffers.computePlayerTradeOffers(trading.getWeakSection(),
                trading.getTradeInitializingTeam(),trading.getTradeFinalizingTeam(),trading.getInitialTradingPlayers(),trading.getFinalTradingPlayers());
        assertEquals(playerTradeOffer.size(),1);
    }

    @Test
    public void computePlayersTradeOffersTest() {
        trading.setWeakSection("defense");
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    if(team.getTeamName().equals("Ottawa Blues")) {
                        trading.setTradeInitializingTeam(team);
                        for(Player player : trading.getTradeInitializingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {

                            }
                            else {
                                trading.getInitialTradingPlayers().add(player);
                            }
                        }
                    } else if(team.getTeamName().equals("Winnipeg Hound Dogs")) {
                        trading.setTradeFinalizingTeam(team);
                        for(Player player : trading.getTradeFinalizingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {
                                trading.getFinalTradingPlayers().add(player);
                            }
                        }
                    }
                }
            }
        }
        HashMap<HashMap<ArrayList<Player>, Player>, Double> playersTradeOffer = playersTradeOffers.computePlayersTradeOffers(trading.getWeakSection(),
                trading.getTradeInitializingTeam(),trading.getTradeFinalizingTeam(),trading.getInitialTradingPlayers(),trading.getFinalTradingPlayers());
        assertEquals(playersTradeOffer.size(),1);
    }

    @Test
    public void computeDraftPickTradeOffersTest() {
        trading.setWeakSection("defense");
        trading.setAverageGoalieStrength(26.33096590909091);
        trading.setAverageForwardStrength(26.741666666666667);
        trading.setAverageDefenseStrength(22.15625);
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    if(team.getTeamName().equals("Ottawa Blues")) {
                        trading.setTradeInitializingTeam(team);
                    } else if(team.getTeamName().equals("Winnipeg Hound Dogs")) {
                        trading.setTradeFinalizingTeam(team);
                        for(Player player : trading.getTradeFinalizingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {
                                trading.getFinalTradingPlayers().add(player);
                            }
                        }
                    }
                }
            }
        }
        HashMap<Integer,Player> draftPickTradeOffer = draftPickTradeOffers.computeDraftPickTradeOffers(trading.getWeakSection(),
                trading.getTradeInitializingTeam(),trading.getTradeFinalizingTeam(),trading.getFinalTradingPlayers(),trading.getAverageGoalieStrength(),
                trading.getAverageForwardStrength(),trading.getAverageDefenseStrength());
        assertEquals(draftPickTradeOffer.size(),1);
    }

    @Test
    public void UITradeOfferTest(){

        if(trading.UITradeOffer())
        {
            assertTrue(trading.isTrade());
        }
        else
        {
            assertFalse(trading.isTrade());
        }

    }

    @Test
    public void UIPlayerTradeAcceptTest(){
        HashMap<Player, Player> tradingPlayers = new HashMap<>();
        trading.setWeakSection("defense");
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    if(team.getTeamName().equals("Ottawa Blues")) {
                        trading.setTradeInitializingTeam(team);
                        trading.getTradeInitializingTeam().setLossPoint(11);
                        for(Player player : trading.getTradeInitializingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {

                            }
                            else {
                                trading.getInitialTradingPlayers().add(player);
                            }
                        }
                    } else if(team.getTeamName().equals("Winnipeg Hound Dogs")) {
                        trading.setTradeFinalizingTeam(team);
                        for(Player player : trading.getTradeFinalizingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {
                                trading.getFinalTradingPlayers().add(player);
                            }
                        }
                    }
                }
            }
        }
        HashMap<HashMap<Player,Player>,Double> playerTradeOffer = playerTradeOffers.computePlayerTradeOffers(trading.getWeakSection(),
                trading.getTradeInitializingTeam(),trading.getTradeFinalizingTeam(),trading.getInitialTradingPlayers(),trading.getFinalTradingPlayers());
        Map.Entry<HashMap<Player,Player>,Double> entry = playerTradeOffer.entrySet().iterator().next();
        tradingPlayers.put(entry.getKey().entrySet().iterator().next().getKey(),
                entry.getKey().entrySet().iterator().next().getValue());
        assertEquals(trading.getTradeInitializingTeam().getAllPlayers().size(),30);
        assertNotEquals(trading.getTradeInitializingTeam().getLossPoint(),0);
        trading.UIPlayerTradeAccept(tradingPlayers);
        assertEquals(trading.getTradeInitializingTeam().getLossPoint(),0);
    }

    @Test
    public void UIPlayersTradeAcceptTest(){
        HashMap<ArrayList<Player>, Player> tradingPlayers = new HashMap<>();
        trading.setWeakSection("defense");
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    if(team.getTeamName().equals("Ottawa Blues")) {
                        trading.setTradeInitializingTeam(team);
                        trading.getTradeInitializingTeam().setLossPoint(11);
                        for(Player player : trading.getTradeInitializingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {

                            }
                            else {
                                trading.getInitialTradingPlayers().add(player);
                            }
                        }
                    } else if(team.getTeamName().equals("Winnipeg Hound Dogs")) {
                        trading.setTradeFinalizingTeam(team);
                        for(Player player : trading.getTradeFinalizingTeam().getAllPlayers()) {
                            if(player.getPosition().equals(trading.getWeakSection())) {
                                trading.getFinalTradingPlayers().add(player);
                            }
                        }
                    }
                }
            }
        }
        HashMap<HashMap<ArrayList<Player>, Player>, Double> playersTradeOffer = playersTradeOffers.computePlayersTradeOffers(trading.getWeakSection(),
                trading.getTradeInitializingTeam(),trading.getTradeFinalizingTeam(),trading.getInitialTradingPlayers(),trading.getFinalTradingPlayers());
        Map.Entry<HashMap<ArrayList<Player>,Player>,Double> entry = playersTradeOffer.entrySet().iterator().next();
        tradingPlayers.put(entry.getKey().entrySet().iterator().next().getKey(),
                entry.getKey().entrySet().iterator().next().getValue());
        assertEquals(trading.getTradeInitializingTeam().getAllPlayers().size(),30);
        assertNotEquals(trading.getTradeInitializingTeam().getLossPoint(),0);
        trading.UIPlayersTradeAccept(tradingPlayers);
        assertEquals(trading.getTradeInitializingTeam().getLossPoint(),0);
    }

    @Test
    public void UIDropPlayersTest(){
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    // System.out.println(team.getTeamName()+"  "+team.getAllPlayers().size());
                    while(team.getAllPlayers().size() > 30) {
                        adjustTeamPlayers.UIDropPlayers(team);
                        assertNotEquals(team.getAllPlayers().size(),30);
                    }
                    assertEquals(team.getAllPlayers().size(),30);
                }
            }
        }
    }

    @Test
    public void UIGetFromFreeAgentsTest(){
        for (Conference c : leagueLOM.getConferences().values()) {
            for (Division d : c.getDivisions().values()) {
                for (Team team : d.getTeams().values()) {
                    //System.out.println(team.getTeamName()+"  "+team.getAllPlayers().size());
                    while(team.getAllPlayers().size() < 30) {
                        adjustTeamPlayers.UIDropPlayers(team);
                        assertNotEquals(team.getAllPlayers().size(),30);
                    }
                    assertEquals(team.getAllPlayers().size(),30);
                }
            }
        }
    }

}
