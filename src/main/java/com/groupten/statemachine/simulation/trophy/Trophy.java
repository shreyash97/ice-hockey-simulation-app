package com.groupten.statemachine.simulation.trophy;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.season.ISeasonObserver;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.teamstanding.TeamStanding;
import com.groupten.statemachine.simulation.simulategame.strategy.IAlgoStrategyObserver;
import com.groupten.statemachine.simulation.training.ITrainingObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Trophy implements ITrophy, ITrainingObserver, ISeasonObserver, IAlgoStrategyObserver {

    private LinkedList<TrophyPerSeason> historicData = new LinkedList<>();
    private LinkedHashMap<Coach, Integer> coachRanking = new LinkedHashMap<>();
    private Map<Player, Integer> bestPlayerRanking = new HashMap<>();
    private Map<Player, Integer> bestDefenseMenRanking = new HashMap<>();
    private Map<Player, Integer> topGoalRanking = new HashMap<>();
    private Map<Player, Integer> bestGoalieRanking = new HashMap<>();
    private LinkedHashMap<Team, Integer> teamRanking = new LinkedHashMap<>();
    private static final Logger logger = LogManager.getLogger(Trophy.class.getName());

    @Override
    public void updateCoachRanking(LinkedHashMap<Coach, Integer> coachRanking) {
        this.coachRanking = coachRanking;
    }

    @Override
    public void updateShots(Map<Player, Integer> bestPlayerRanking) {
        for (Map.Entry<Player, Integer> entry : bestPlayerRanking.entrySet()) {
            this.bestPlayerRanking.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    @Override
    public void updatePenalties(Map<Player, Integer> bestDefenseMenRanking) {
        for (Map.Entry<Player, Integer> entry : bestDefenseMenRanking.entrySet()) {
            this.bestDefenseMenRanking.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    @Override
    public void updateGoals(Map<Player, Integer> topGoalRanking) {
        for (Map.Entry<Player, Integer> entry : topGoalRanking.entrySet()) {
            this.topGoalRanking.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    @Override
    public void updateSaves(Map<Player, Integer> bestGoalieRanking) {
        for (Map.Entry<Player, Integer> entry : bestGoalieRanking.entrySet()) {
            this.bestGoalieRanking.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    @Override
    public void updateRegularSeasonEnd(Season season) {
        List<TeamStanding> teamStandings = season.getTeamStandings();
        for (TeamStanding teamStanding : teamStandings) {
            teamRanking.put(teamStanding.getTeam(), teamStanding.getPoints());
        }
    }

    @Override
    public void awardTrophy() {

        Team bestTeam = null, leastTeam = null;
        Coach bestCoach = null;
        Player bestPlayer = null, bestGoalie = null, bestDefenseMen = null, topGoalScorer = null;
        int min = -1, max = 999999;

        TrophyPerSeason trophyPerSeason = new TrophyPerSeason();
        historicData.offerFirst(trophyPerSeason);

        for (Map.Entry<Team, Integer> entry : teamRanking.entrySet()) {
            if (entry.getValue() > min) {
                min = entry.getValue();
                bestTeam = entry.getKey();
            }
        }
        trophyPerSeason.setPresidentTrophy(bestTeam);

        min = -1;
        for (Map.Entry<Player, Integer> entry : bestPlayerRanking.entrySet()) {
            if (entry.getValue() > min) {
                min = entry.getValue();
                bestPlayer = entry.getKey();
            }
        }
        trophyPerSeason.setCalderMemorialTrophy(bestPlayer);

        min = -1;
        for (Map.Entry<Player, Integer> entry : bestGoalieRanking.entrySet()) {
            if (entry.getValue() > min) {
                min = entry.getValue();
                bestGoalie = entry.getKey();
            }
        }
        trophyPerSeason.setVezinaTrophy(bestGoalie);

        min = -1;
        for (Map.Entry<Coach, Integer> entry : coachRanking.entrySet()) {
            if (entry.getValue() > min) {
                min = entry.getValue();
                bestCoach = entry.getKey();
            }
        }
        trophyPerSeason.setJackAdamAward(bestCoach);

        min = -1;
        for (Map.Entry<Player, Integer> entry : topGoalRanking.entrySet()) {
            if (entry.getValue() > min) {
                min = entry.getValue();
                topGoalScorer = entry.getKey();
            }
        }
        trophyPerSeason.setMauriceRichardTrophy(topGoalScorer);

        min = -1;
        for (Map.Entry<Player, Integer> entry : bestDefenseMenRanking.entrySet()) {
            if (entry.getValue() > min) {
                min = entry.getValue();
                bestDefenseMen = entry.getKey();
            }
        }
        trophyPerSeason.setRobHawkeyMemorialTrophy(bestDefenseMen);

        for (Map.Entry<Team, Integer> entry : teamRanking.entrySet()) {
            if (entry.getValue() < max) {
                max = entry.getValue();
                leastTeam = entry.getKey();
            }
        }
        trophyPerSeason.setParticipationAward(leastTeam);

        teamRanking.clear();
        coachRanking.clear();
        bestPlayerRanking.clear();
        bestDefenseMenRanking.clear();
        topGoalRanking.clear();
        bestGoalieRanking.clear();
    }

    @Override
    public void trophyWinners() {
        IConsole console = Injector.instance().getConsoleObject();

        int seasonNumber = historicData.size();

        console.printLine("*************************************");
        console.printLine("--------- Trophy Winners ----------");
        for (TrophyPerSeason trophyPerSeason : historicData) {
            console.printLine("\nWinner of Season " + seasonNumber);
            console.printLine("\nTrophy \t\t\t Winner");
            console.printLine("President's Trophy\t\t\t" + trophyPerSeason.getPresidentTrophy().getTeamName());
            console.printLine("Calder Memorial Trophy\t\t\t" + trophyPerSeason.getCalderMemorialTrophy().getPlayerName());
            console.printLine("Vezina Trophy\t\t\t" + trophyPerSeason.getVezinaTrophy().getPlayerName());
            console.printLine("Jack Adam's Award\t\t\t" + trophyPerSeason.getJackAdamAward().getCoachName());
            console.printLine("Maurice Richard Trophy\t\t\t" + trophyPerSeason.getMauriceRichardTrophy().getPlayerName());
            console.printLine("Rob Hawkey Memorial Cup\t\t\t" + trophyPerSeason.getRobHawkeyMemorialTrophy().getPlayerName());
            console.printLine("Participation Award\t\t\t" + trophyPerSeason.getParticipationAward().getTeamName());

            logger.info("President's Trophy for season " + seasonNumber + " is " + trophyPerSeason.getPresidentTrophy().getTeamName());
            logger.info("Calder Memorial Trophy for season " + seasonNumber + " is " + trophyPerSeason.getCalderMemorialTrophy().getPlayerName());
            logger.info("Vezina Trophy for season " + seasonNumber + " is " + trophyPerSeason.getVezinaTrophy().getPlayerName());
            logger.info("Jack Adam's Award for season " + seasonNumber + " is " + trophyPerSeason.getJackAdamAward().getCoachName());
            logger.info("Maurice Richard Trophy for season " + seasonNumber + " is " + trophyPerSeason.getMauriceRichardTrophy().getPlayerName());
            logger.info("Rob Hawkey Memorial Cup for season " + seasonNumber + " is " + trophyPerSeason.getRobHawkeyMemorialTrophy().getPlayerName());
            logger.info("Participation Award for season " + seasonNumber + " is " + trophyPerSeason.getParticipationAward().getTeamName());

            seasonNumber--;

        }
        console.printLine("*************************************");
    }

    @Override
    public LinkedList<TrophyPerSeason> getHistoricData() {
        return historicData;
    }

    @Override
    public void setCoachRanking(LinkedHashMap<Coach, Integer> coachRanking) {
        this.coachRanking = coachRanking;
    }

    @Override
    public void setTeamRanking(LinkedHashMap<Team, Integer> teamRanking) {
        this.teamRanking = teamRanking;
    }

    @Override
    public void setBestPlayerRanking(Map<Player, Integer> bestPlayerRanking) {
        this.bestPlayerRanking = bestPlayerRanking;
    }

    @Override
    public void setBestDefenseMenRanking(Map<Player, Integer> bestDefenseMenRanking) {
        this.bestDefenseMenRanking = bestDefenseMenRanking;
    }

    @Override
    public void setTopGoalRanking(Map<Player, Integer> topGoalRanking) {
        this.topGoalRanking = topGoalRanking;
    }

    @Override
    public void setBestGoalieRanking(Map<Player, Integer> bestGoalieRanking) {
        this.bestGoalieRanking = bestGoalieRanking;
    }
}
