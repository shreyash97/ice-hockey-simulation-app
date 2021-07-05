package com.groupten.leagueobjectmodel.team;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.LeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SwapPlayersTest {
    @Test
    public void swapPlayersAndUpdateRostersTest() {
        ILeagueModel leagueModel = new LeagueModel();
        Injector.instance().setLeagueModelObject(leagueModel);

        League league = new League("First League");
        leagueModel.setCurrentLeague(league);
        GameConfig.Aging agingConfig = new GameConfig.Aging(35, 50, 0.01);
        league.setAgingConfig(agingConfig);
        GameConfig.Injuries injuriesConfig = new GameConfig.Injuries(0.05, 1, 260);
        league.setInjuriesConfig(injuriesConfig);

        ITeamBuilder teamBuilder = new TeamBuilder();
        teamBuilder.reset();
        teamBuilder.setTeamName("First Team");

        Team team = teamBuilder.getResult();
        List<Player> players = initializePlayers(team);
        teamBuilder.setPlayerRosters(players);

        List<Player> activePlayers = team.getActivePlayers();
        List<Player> inActivePlayers = team.getInActivePlayers();

        Player firstActivePlayer = activePlayers.get(0);
        System.out.println(firstActivePlayer.getPlayerName() + " " + firstActivePlayer.getPosition());

        while (firstActivePlayer.isInjured() == false) {
            firstActivePlayer.checkInjury();
        }

        Player firstActivePlayerAfterSwap = activePlayers.get(0);
        System.out.println(firstActivePlayerAfterSwap.getPlayerName() + " " + firstActivePlayerAfterSwap.getPosition());
        assertTrue(inActivePlayers.contains(firstActivePlayer));

        Player firstActiveGoalie = activePlayers.get(18);
        System.out.println(firstActiveGoalie.getPlayerName() + " " + firstActiveGoalie.getPosition());

        Player secondActiveGoalie = activePlayers.get(19);
        System.out.println(secondActiveGoalie.getPlayerName() + " " + secondActiveGoalie.getPosition());

        Player firstInActiveGoalie = inActivePlayers.get(8);
        System.out.println(firstInActiveGoalie.getPlayerName() + " " + firstInActiveGoalie.getPosition());

        while (firstActiveGoalie.isInjured() == false && secondActiveGoalie.isInjured() == false) {
            firstActiveGoalie.checkInjury();
            secondActiveGoalie.checkInjury();
        }

        Player firstActiveGoalieAfterSwap = activePlayers.get(18);
        Player secondActiveGoalieAfterSwap = activePlayers.get(19);

        System.out.println(firstActiveGoalieAfterSwap.getPlayerName() + " " + firstActiveGoalieAfterSwap.getPosition());
        System.out.println(secondActiveGoalieAfterSwap.getPlayerName() + " " + secondActiveGoalieAfterSwap.getPosition());
    }

    private List<Player> initializePlayers(Team team) {
        List<Player> players = new ArrayList<>();

        double skating = 2.0;
        double shooting = 2.0;
        double checking = 5.0;
        double saving = 2.0;

        for (int i = 0; i < 30; i++) {
            String playerName = "Forward Player " + i;
            Player player = new Player(playerName, "forward", false, 23, 11, 2000,
                    skating, shooting, checking, saving);
            player.attach(team);
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
            player.attach(team);
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
            player.attach(team);
            players.add(player);

            skating++;
            shooting++;
            checking++;
            saving++;
        }

        return players;
    }
}
