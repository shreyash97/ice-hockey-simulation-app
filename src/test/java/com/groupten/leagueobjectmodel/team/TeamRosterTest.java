package com.groupten.leagueobjectmodel.team;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModelFactory;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeamRosterTest {
    @Test
    public void createActiveInActiveRostersTest() {
        JSONImport jsonTestSuccess = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/league.json";
        jsonTestSuccess.importJSONData(path);
        jsonTestSuccess.instantiateJSONData();

        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        ILeagueModelFactory leagueModelFactory = Injector.instance().getLeagueModelFactory();
        ITeamRoster teamRoster = leagueModelFactory.createTeamRoster();

        List<Player> players = initializePlayers();
        teamRoster.setPlayers(players);

        List<Player> activePlayers = teamRoster.createActivePlayerRoster();
        List<Player> inActivePlayers = teamRoster.createInActivePlayerRoster();
        List<Player> excessPlayers = teamRoster.returnExcessPlayers();

        assertEquals(20, activePlayers.size());
        assertEquals(10, inActivePlayers.size());
        assertEquals(23, excessPlayers.size());

        leagueModel.addExcessPlayersToFreeAgentsList(excessPlayers);
        League currentLeague = leagueModel.getCurrentLeague();

        assertEquals(80, currentLeague.getFreeAgents().size());
    }

    private List<Player> initializePlayers() {
        List<Player> players = new ArrayList<>();

        double skating = 2.0;
        double shooting = 2.0;
        double checking = 5.0;
        double saving = 2.0;

        for (int i = 0; i < 30; i++) {
            String playerName = "Forward Player " + i;
            Player player = new Player(playerName, "forward", false, 23, 11, 2000,
                    skating, shooting, checking, saving);
            players.add(player);

            skating++;
            shooting++;
            checking++;
            saving++;
        }

        skating = 2.0;
        shooting = 2.0;
        checking = 2.0;
        saving = 2.0;

        for (int i = 0; i < 15; i++) {
            String playerName = "Defense Player " + i;
            Player player = new Player(playerName, "defense", false, 23, 11, 2000,
                    skating, shooting, checking, saving);
            players.add(player);

            skating++;
            shooting++;
            checking++;
            saving++;
        }

        skating = 2.0;
        shooting = 2.0;
        checking = 2.0;
        saving = 2.0;

        for (int i = 0; i < 8; i++) {
            String playerName = "Goalie Player " + i;
            Player player = new Player(playerName, "goalie", false, 23, 11, 2000,
                    skating, shooting, checking, saving);
            players.add(player);

            skating++;
            shooting++;
            checking++;
            saving++;
        }

        return players;
    }

}
