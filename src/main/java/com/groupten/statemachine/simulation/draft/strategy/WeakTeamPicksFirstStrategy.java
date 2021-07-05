package com.groupten.statemachine.simulation.draft.strategy;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModelFactory;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.ITeamRoster;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.teamstanding.TeamStanding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WeakTeamPicksFirstStrategy implements IDraftStrategy {
    private static final Logger logger = LogManager.getLogger(WeakTeamPicksFirstStrategy.class.getName());

    @Override
    public void execute(List<TeamStanding> teamStandings, List<Player> players, List<List<Team>> tradePickTeams) {
        ILeagueModelFactory leagueModelFactory = Injector.instance().getLeagueModelFactory();
        ITeamRoster teamRoster = leagueModelFactory.createTeamRoster();
        players.sort(Comparator.comparingDouble(Player::calculateStrength).reversed());

        for (TeamStanding teamStanding : teamStandings) {
            Team team = teamStanding.getTeam();

            List<Player> activePlayers = team.getActivePlayers();
            List<Player> inActivePlayers = team.getInActivePlayers();
            activePlayers.addAll(inActivePlayers);

            Player bestPlayer = players.get(0);

            logger.info(bestPlayer.getPlayerName() + " drafted by " + team.getTeamName());

            activePlayers.add(bestPlayer);
            teamRoster.setPlayers(activePlayers);
            team.setActivePlayers(teamRoster.createActivePlayerRoster());
            team.setInActivePlayers(teamRoster.createInActivePlayerRoster());

            players.remove(bestPlayer);
        }
    }
}
