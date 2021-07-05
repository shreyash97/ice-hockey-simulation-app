package com.groupten.statemachine.simulation.simulategame;

import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.statemachine.jsonimport.JSONImport;
import com.groupten.statemachine.simulation.simulategame.strategy.AlgoStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AlgoStrategyTest {

    @BeforeClass
    public static void setup() {
        JSONImport json = new JSONImport();
        json.importJSONData("src/test/java/com/groupten/mocks/JsonMockNoTeams.json");
        json.instantiateJSONData();
    }

    @Test
    public void getWinnerTest() {
        Season season = new Season();
        ResolveGame resolveGame = new ResolveGame(season);
        resolveGame.setStrategy(new AlgoStrategy());

        Team team1 = new Team(1, "First Team");
        for (int i = 0; i < 3; i++) {
            Player forward = new Player("Froward " + i, "forward", false, 27, 5, 5, 5, 5);
            forward.setAvailTOI(3600);
            team1.addActivePlayer(forward);
        }

        for (int i = 0; i < 2; i++) {
            Player defense = new Player("Defense " + i, "defense", false, 27, 5, 5, 5, 5);
            defense.setAvailTOI(3600 * 3);
            team1.addActivePlayer(defense);
        }

        Player goalie1 = new Player("Goalie1", "goalie", false, 27, 5, 5, 5, 5);
        team1.addActivePlayer(goalie1);

        Team team2 = new Team(2, "Second Team");
        for (int i = 0; i < 3; i++) {
            Player forward = new Player("Froward " + i, "forward", false, 27, 5, 5, 5, 5);
            forward.setAvailTOI(3600);
            team2.addActivePlayer(forward);
        }

        for (int i = 0; i < 2; i++) {
            Player defense = new Player("Defense " + i, "defense", false, 27, 5, 5, 5, 5);
            defense.setAvailTOI(3600 * 3);
            team2.addActivePlayer(defense);
        }

        Player goalie2 = new Player("Goalie2", "goalie", false, 27, 5, 5, 5, 5);
        team2.addActivePlayer(goalie2);

        Team team = resolveGame.getWinner(team1, team2);

        assertTrue(team == team1 || team == team2);
    }
}
