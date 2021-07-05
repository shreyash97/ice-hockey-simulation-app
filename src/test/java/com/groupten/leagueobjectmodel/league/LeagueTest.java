package com.groupten.leagueobjectmodel.league;

import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.generalmanager.GeneralManager;
import com.groupten.leagueobjectmodel.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeagueTest {
    @Test
    public void addFreeAgentTest() {
        League league = new League(1, "First League");
        Player player = new Player("First Player", "goalie", 27, 5, 5, 5, 5);
        player.setPlayerID(1);
        assertTrue(league.addFreeAgent(player));
        player = new Player(2, "Second Player", "goalie", 27, 5, 5, 5, 5);
        assertTrue(league.addFreeAgent(player));
    }

    @Test
    public void addCoachTest() {
        League league = new League(1, "First League");
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        assertTrue(league.addCoach(coach));
    }

    @Test
    public void addGeneralManagerTest() {
        League league = new League(1, "First League");
        GeneralManager generalManager = new GeneralManager(1, "First General Manager");
        assertTrue(league.addGeneralManager(generalManager));
    }

    @Test
    public void isNumberOFConferencesEvenTest() {
        League league = new League(1, "First League");
        Conference conferenceOne = new Conference(1, "First Conference");
        league.addConference(conferenceOne);
        assertFalse(league.isNumberOfConferencesEven());

        Conference conferenceTwo = new Conference(2, "Second Conference");
        league.addConference(conferenceTwo);
        assertTrue(league.isNumberOfConferencesEven());
    }

    @Test
    public void addConferenceTest() {
        League league = new League(1, "First League");
        Conference conference = new Conference(1, "First Conference");
        assertTrue(league.addConference(conference));
    }

    @Test
    public void containsConference() {
        League league = new League(1, "First League");
        Conference conference = new Conference(1, "First Conference");
        league.addConference(conference);
        assertTrue(league.containsConference("First Conference"));
    }

    @Test
    public void getConferenceTest() {
        League league = new League(1, "First League");
        Conference conference = new Conference(1, "First Conference");
        league.addConference(conference);
        assertEquals("First Conference", league.getConference("First Conference").getConferenceName());
    }

    @Test
    public void getConferencesTest() {
        League league = new League(1, "First League");
        Conference conference = new Conference(1, "First Conference");
        league.addConference(conference);
        conference = new Conference(2, "Second Conference");
        league.addConference(conference);
        assertEquals(2, league.getConferences().size());
    }

    @Test
    public void getCoachesTest() {
        League league = new League(1, "First League");
        Coach coach = new Coach(1, "First Coach", 0.5, 0.5, 0.5, 0.5);
        league.addCoach(coach);
        coach = new Coach(2, "Second Coach", 0.5, 0.5, 0.5, 0.5);
        league.addCoach(coach);
        assertEquals(2, league.getCoaches().size());
    }

    @Test
    public void getFreeAgentsTest() {
        League league = new League(1, "First League");
        Player player = new Player(1, "First Player", "goalie", 27, 5, 5, 5, 5);
        league.addFreeAgent(player);
        player = new Player(2, "Second Player", "goalie", 27, 5, 5, 5, 5);
        league.addFreeAgent(player);
        assertEquals(2, league.getFreeAgents().size());
    }

    @Test
    public void getGeneralManagersTest() {
        League league = new League(1, "First League");
        GeneralManager generalManager = new GeneralManager(1, "First General Manager");
        league.addGeneralManager(generalManager);
        generalManager = new GeneralManager(2, "Second General Manager");
        league.addGeneralManager(generalManager);
        assertEquals(2, league.getGeneralManagers().size());
    }

    @Test
    public void isLeagueNameValidTest() {
        String leagueName = "DHL";
        assertTrue(League.isLeagueNameValid(leagueName));
        leagueName = "";
        assertFalse(League.isLeagueNameValid(leagueName));
        leagueName = " ";
        assertFalse(League.isLeagueNameValid(leagueName));
        leagueName = "Null";
        assertFalse(League.isLeagueNameValid(leagueName));
    }

    @Test
    public void getLeagueIDTest() {
        League league = new League(1, "First League");
        assertEquals(1, league.getLeagueID());
    }

    @Test
    public void setLeagueIDTest() {
        League league = new League(1, "First League");
        league.setLeagueID(2);
        assertEquals(2, league.getLeagueID());
    }

    @Test
    public void setLeagueNameTest() {
        League league = new League(1, "First League");
        league.setLeagueName("Updated First League");
        assertEquals("Updated First League", league.getLeagueName());
    }

    @Test
    public void getLeagueNameTest() {
        League league = new League(1, "First League");
        assertEquals("First League", league.getLeagueName());
    }

    @Test
    public void setAgingConfigTest() {
        League league = new League(1, "First League");
        GameConfig.Aging agingConfig = new GameConfig.Aging(35, 50, 0.01);
        league.setAgingConfig(agingConfig);
        assertEquals(35, agingConfig.getAverageRetirementAge());
        assertEquals(50, agingConfig.getMaximumAge());
    }

    @Test
    public void setGameResolverConfigTest() {
        League league = new League(1, "First League");
        GameConfig.GameResolver gameResolverConfig = new GameConfig.GameResolver(0.1);
        league.setGameResolverConfig(gameResolverConfig);
        assertEquals(0.1, gameResolverConfig.getRandomWinChance(), 0.0);
    }

    @Test
    public void setInjuriesConfigTest() {
        League league = new League(1, "First League");
        GameConfig.Injuries injuriesConfig = new GameConfig.Injuries(0.05, 1, 260);
        league.setInjuriesConfig(injuriesConfig);
        assertEquals(0.05, injuriesConfig.getRandomInjuryChance(), 0.0);
        assertEquals(1, injuriesConfig.getInjuryDaysLows());
        assertEquals(260, injuriesConfig.getInjuryDaysHigh());
    }

    @Test
    public void setTrainingConfigTest() {
        League league = new League(1, "First League");
        GameConfig.Training trainingConfig = new GameConfig.Training(100);
        league.setTrainingConfig(trainingConfig);
        assertEquals(100, trainingConfig.getDaysUntilStatIncreaseCheck());
    }

    @Test
    public void setTradingConfig() {
        League league = new League(1, "First League");
        GameConfig.Trading tradingConfig = new GameConfig.Trading(8, 0.05, 2, 0.05, -0.1, 0.0, 0.1);
        league.setTradingConfig(tradingConfig);
        assertEquals(8, tradingConfig.getLossPoint());
        assertEquals(0.05, tradingConfig.getRandomTradeOfferChance(), 0.0);
        assertEquals(2, tradingConfig.getMaxPlayersPerTrade());
        assertEquals(0.05, tradingConfig.getRandomAcceptanceChance(), 0.0);
    }

}
