package com.groupten.leagueobjectmodel.team;

import com.groupten.leagueobjectmodel.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeamBuilderTest {
    @Test
    public void resetTest() {
        ITeamBuilder teamBuilder = new TeamBuilder();
        teamBuilder.reset();
        assert (teamBuilder.getResult() != null);
    }

    @Test
    public void setTeamNameTest() {
        ITeamBuilder teamBuilder = new TeamBuilder();
        teamBuilder.reset();
        teamBuilder.setTeamName("First Team");
        assertEquals("First Team", teamBuilder.getResult().getTeamName());
    }

    @Test
    public void setPlayerRostersTest() {
        ITeamBuilder teamBuilder = new TeamBuilder();
        teamBuilder.reset();
        teamBuilder.setTeamName("First Team");

        List<Player> players = initializePlayers();
        teamBuilder.setPlayerRosters(players);
        Team team = teamBuilder.getResult();

        List<Player> activePlayers = team.getActivePlayers();
        List<Player> inActivePlayers = team.getInActivePlayers();

        assertEquals(20, activePlayers.size());
        assertEquals(10, inActivePlayers.size());
        assertEquals("Forward Player 15", activePlayers.get(0).getPlayerName());
        assertEquals("Forward Player 4", activePlayers.get(11).getPlayerName());
        assertEquals("Defense Player 9", activePlayers.get(12).getPlayerName());
        assertEquals("Goalie Player 3", activePlayers.get(18).getPlayerName());
    }

    private List<Player> initializePlayers() {
        List<Player> players = new ArrayList<>();

        double skating = 2.0;
        double shooting = 2.0;
        double checking = 5.0;
        double saving = 2.0;

        for (int i = 0; i < 16; i++) {
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

        for (int i = 0; i < 10; i++) {
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

        for (int i = 0; i < 4; i++) {
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
