package com.groupten.statemachine.simulation.trophy;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TrophyTest {

    private static LinkedHashMap<Coach, Integer> coachRanking = new LinkedHashMap<>();
    private static Map<Player, Integer> bestPlayerRanking = new HashMap<>();
    private static Map<Player, Integer> bestDefenseMenRanking = new HashMap<>();
    private static Map<Player, Integer> topGoalRanking = new HashMap<>();
    private static Map<Player, Integer> bestGoalieRanking = new HashMap<>();
    private static LinkedHashMap<Team, Integer> teamRanking = new LinkedHashMap<>();

    @BeforeClass
    public static void setup() {
        ITrophy trophy = new Trophy();
        Injector.instance().setTrophyObject(trophy);

        teamRanking.put(new Team("Deep Team"), 100);
        teamRanking.put(new Team("Sneha Team"), 9999);
        teamRanking.put(new Team("Iron Man Team"), 2);
        teamRanking.put(new Team("Captain Team"), 4);
        teamRanking.put(new Team("Avengers Team"), 50);
        teamRanking.put(new Team("Sherlock Team"), 48);
        teamRanking.put(new Team("Blah Team"), 1000);
        teamRanking.put(new Team("Zoop Team"), 7);
        teamRanking.put(new Team("a Team"), 1);
        teamRanking.put(new Team("b Team"), 109);

        bestPlayerRanking.put(new Player("First Player"), 100);
        bestPlayerRanking.put(new Player("Second Player"), 1);
        bestPlayerRanking.put(new Player("Third Player"), 110);
        bestPlayerRanking.put(new Player("Fourth Player"), 1000);
        bestPlayerRanking.put(new Player("Fifth Player"), 102);
        bestPlayerRanking.put(new Player("Sixth Player"), 103);

        bestGoalieRanking.put(new Player("First Player"), 100);
        bestGoalieRanking.put(new Player("Second Player"), 1);
        bestGoalieRanking.put(new Player("Third Player"), 110);
        bestGoalieRanking.put(new Player("Fourth Player"), 1000);
        bestGoalieRanking.put(new Player("Fifth Player"), 102);
        bestGoalieRanking.put(new Player("Sixth Player"), 103);

        coachRanking.put(new Coach("A"), 100);
        coachRanking.put(new Coach("B"), 999);
        coachRanking.put(new Coach("C"), 2);
        coachRanking.put(new Coach("D"), 4);
        coachRanking.put(new Coach("E"), 50);
        coachRanking.put(new Coach("F"), 48);
        coachRanking.put(new Coach("G"), 1000);
        coachRanking.put(new Coach("H"), 7);
        coachRanking.put(new Coach("I"), 109);

        topGoalRanking.put(new Player("First Player"), 100);
        topGoalRanking.put(new Player("Second Player"), 1);
        topGoalRanking.put(new Player("Third Player"), 110);
        topGoalRanking.put(new Player("Fourth Player"), 1000);
        topGoalRanking.put(new Player("Fifth Player"), 102);
        topGoalRanking.put(new Player("Sixth Player"), 103);

        bestDefenseMenRanking.put(new Player("First Player"), 100);
        bestDefenseMenRanking.put(new Player("Second Player"), 1);
        bestDefenseMenRanking.put(new Player("Third Player"), 110);
        bestDefenseMenRanking.put(new Player("Fourth Player"), 1000);
        bestDefenseMenRanking.put(new Player("Fifth Player"), 102);
        bestDefenseMenRanking.put(new Player("Sixth Player"), 103);

        trophy.setTeamRanking(teamRanking);
        trophy.setBestPlayerRanking(bestPlayerRanking);
        trophy.setBestGoalieRanking(bestGoalieRanking);
        trophy.setCoachRanking(coachRanking);
        trophy.setTopGoalRanking(topGoalRanking);
        trophy.setBestDefenseMenRanking(bestDefenseMenRanking);
        trophy.setTeamRanking(teamRanking);

        trophy.awardTrophy();
    }

    @Test
    public void presidentTrophyTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getPresidentTrophy().getTeamName(), "Sneha Team");
        assertNotEquals(trophy.getHistoricData().get(0).getPresidentTrophy().getTeamName(), "");
    }

    @Test
    public void calderMemorialTrophyTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getCalderMemorialTrophy().getPlayerName(), "Fourth Player");
        assertNotEquals(trophy.getHistoricData().get(0).getCalderMemorialTrophy().getPlayerName(), "");
    }

    @Test
    public void vezinaTrophyTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getVezinaTrophy().getPlayerName(), "Fourth Player");
        assertNotEquals(trophy.getHistoricData().get(0).getVezinaTrophy().getPlayerName(), "");
    }

    @Test
    public void jackAdamAwardTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getJackAdamAward().getCoachName(), "G");
        assertNotEquals(trophy.getHistoricData().get(0).getJackAdamAward().getCoachName(), "");
    }

    @Test
    public void mauriceRichardTrophyTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getMauriceRichardTrophy().getPlayerName(), "Fourth Player");
        assertNotEquals(trophy.getHistoricData().get(0).getMauriceRichardTrophy().getPlayerName(), "");
    }

    @Test
    public void robHawkeyMemorialTrophyTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getRobHawkeyMemorialTrophy().getPlayerName(), "Fourth Player");
        assertNotEquals(trophy.getHistoricData().get(0).getRobHawkeyMemorialTrophy().getPlayerName(), "");
    }

    @Test
    public void participationAwardTest() {
        ITrophy trophy = Injector.instance().getTrophyObject();
        assertEquals(trophy.getHistoricData().get(0).getParticipationAward().getTeamName(), "a Team");
        assertNotEquals(trophy.getHistoricData().get(0).getParticipationAward().getTeamName(), "");
    }

}
