package com.groupten.statemachine.simulation.aging;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.LeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AgingTest {
    @Test
    public void advanceEveryPlayersAgeTest() {
        ILeagueModel leagueModel = new LeagueModel();
        Injector.instance().setLeagueModelObject(leagueModel);

        League league = new League("First League");
        leagueModel.setCurrentLeague(league);
        GameConfig.Aging agingConfig = new GameConfig.Aging(35, 50, 0.01);
        league.setAgingConfig(agingConfig);
        GameConfig.Injuries injuriesConfig = new GameConfig.Injuries(0.05, 1, 260);
        league.setInjuriesConfig(injuriesConfig);

        Player player = new Player("First Free Agent", "goalie", 27, 5, 5, 5, 5);
        league.addFreeAgent(player);
        player = new Player("Second Free Agent", "goalie", 27, 5, 5, 5, 7);
        league.addFreeAgent(player);
        Player bestFreeAgentGoalie = new Player("Third Free Agent", "goalie", 27, 5, 5, 5, 10);
        league.addFreeAgent(bestFreeAgentGoalie);
        player = new Player("Fourth Free Agent", "forward", 27, 5, 5, 5, 10);
        league.addFreeAgent(player);
        Player bestFreeAgentForward = new Player("Fifth Free Agent", "forward", 27, 5, 10, 5, 5);
        league.addFreeAgent(bestFreeAgentForward);

        Conference conference = new Conference(1, "First Conference");
        league.addConference(conference);

        Division division = new Division("First Division");
        conference.addDivision(division);

        Team team = new Team(1, "First Team");
        division.addTeam(team);

        Player teamPlayer = new Player("First Player", "goalie", false, 30, 5, 5, 5, 5);
        team.addActivePlayer(teamPlayer);
        teamPlayer = new Player("Second Player", "goalie", false, 50, 5, 5, 5, 5);
        team.addActivePlayer(teamPlayer);
        teamPlayer = new Player("Third Player", "defense", false, 30, 5, 5, 5, 5);
        team.addActivePlayer(teamPlayer);
        teamPlayer = new Player("Fourth Player", "defense", false, 30, 5, 5, 5, 5);
        team.addActivePlayer(teamPlayer);
        teamPlayer = new Player("Fifth Player", "forward", false, 50, 5, 2, 5, 5);
        team.addActivePlayer(teamPlayer);

        Aging aging = new Aging();
        aging.advanceEveryPlayersAge(league, 365);

        assertTrue(team.getActivePlayers().contains(bestFreeAgentGoalie));
        assertFalse(league.getFreeAgents().contains(bestFreeAgentGoalie));
        assertTrue(team.getActivePlayers().contains(bestFreeAgentForward));
        assertFalse(league.getFreeAgents().contains(bestFreeAgentForward));
    }
}
