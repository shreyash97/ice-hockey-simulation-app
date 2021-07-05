package com.groupten.statemachine.simulation.simulategame;

import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.statemachine.jsonimport.JSONImport;
import com.groupten.statemachine.simulation.simulategame.strategy.IStrategy;
import com.groupten.statemachine.simulation.simulategame.strategy.RandomStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResolveGameTest {

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/JsonMockNoTeams.json");
        json.instantiateJSONData();
    }

    @Test
    public void getStrategyTest() {
        Season season = new Season();
        ResolveGame resolveGame = new ResolveGame(season);
        IStrategy strategy = new RandomStrategy();
        resolveGame.setStrategy(strategy);
        assertEquals(strategy, resolveGame.getStrategy());
    }

    @Test
    public void setStrategyTest() {
        Season season = new Season();
        ResolveGame resolveGame = new ResolveGame(season);
        IStrategy strategy = new RandomStrategy();
        resolveGame.setStrategy(strategy);
        assertEquals(strategy, resolveGame.getStrategy());
    }


    @Test
    public void getWinnerTest() {
        Season season = new Season();
        ResolveGame resolveGame = new ResolveGame(season);

        Team team1 = new Team(1, "First Team");
        Player player1 = new Player("First Player", "goalie", false, 27, 5, 5, 5, 5);
        team1.addActivePlayer(player1);

        Team team2 = new Team(2, "Second Team");
        Player player2 = new Player("Second Player", "goalie", false, 27, 5, 5, 5, 5);
        team1.addActivePlayer(player2);

        Team team = resolveGame.getWinner(team1, team2);

        assertTrue(team == team1 || team == team2);
    }
}
