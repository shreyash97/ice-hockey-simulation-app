package com.groupten.statemachine.simulation.simulategame.strategy;

import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.shift.Shift;
import com.groupten.leagueobjectmodel.team.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class AlgoStrategy implements IStrategy {
    private static final Logger logger = LogManager.getLogger(AlgoStrategy.class.getName());
    private Random rand = new Random();
    private Season season;
    private List<Shift> team1Shifts = new ArrayList<>();
    private List<Shift> team2Shifts = new ArrayList<>();
    private Map<Player, Integer> shots = new HashMap<>();
    private Map<Player, Integer> penalties = new HashMap<>();
    private Map<Player, Integer> goals = new HashMap<>();
    private Map<Player, Integer> saves = new HashMap<>();
    private final int PERIODS = 3;
    private final int PERIOD_TIME_SEC = 1200;
    private final int SHIFTS = 40;
    private final int SHIFT_FORWARD = 3;
    private final int SHIFT_DEFENSE = 2;
    private final double PENALTY_CHANCE = 0.2;
    private final double SAVING_BENCHMARK = 14;

    private List<IAlgoStrategyObserver> observers = new ArrayList<>();

    public void attach(IAlgoStrategyObserver observer) {
        this.observers.add(observer);
    }

    public void detach(IAlgoStrategyObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers() {
        for (IAlgoStrategyObserver observer : this.observers) {
            observer.updateShots(shots);
            observer.updatePenalties(penalties);
            observer.updateGoals(goals);
            observer.updateSaves(saves);
        }
    }

    @Override
    public Team getWinner(Season season, Team team1, Team team2) {
        this.season = season;
        List<Player> team1Players = team1.getActivePlayers();
        List<Player> team2Players = team2.getActivePlayers();
        team1Shifts = prepareShifts(team1Players);
        team2Shifts = prepareShifts(team2Players);

        int team1Goals = 0;
        int team2Goals = 0;

        for (int s = 0; s < SHIFTS; s++) {
            Shift team1Shift = team1Shifts.get(s);
            Shift team2Shift = team2Shifts.get(s);
            int team1Shots = simulateShots(team1Shift,team2Shift);
            int team2Shots = simulateShots(team2Shift,team1Shift);
            int team1GoalAttempt = simulateGoalAttempt(team1Shift,team2Shift,team1Shots);
            int team2GoalAttempt = simulateGoalAttempt(team2Shift,team1Shift,team2Shots);
            team1Goals = team1Goals + simulateGoals(team1Shift,team2Shift,team1GoalAttempt);
            team2Goals = team2Goals + simulateGoals(team2Shift,team1Shift,team2GoalAttempt);
        }


        notifyObservers();

        resetAvailTOI(team1Players);
        resetAvailTOI(team2Players);

        if (team1Goals > team2Goals) {
            logger.info(team1.getTeamName()+" won against "+team2.getTeamName());
            return team1;
        } else {
            logger.info(team1.getTeamName()+" won against "+team2.getTeamName());
            return team2;
        }
    }

    private List<Shift> prepareShifts(List<Player> players) {
        int total_time = PERIODS * PERIOD_TIME_SEC;
        int shiftInterval = total_time / SHIFTS;
        List<Shift> teamShifts = new ArrayList<>();
        List<Player> forwards = players.stream()
                .filter(player -> player.getPosition().equals("forward"))
                .collect(Collectors.toList());
        List<Player> defensemen = players.stream()
                .filter(player -> player.getPosition().equals("defense"))
                .collect(Collectors.toList());
        List<Player> goalies = players.stream()
                .filter(player -> player.getPosition().equals("goalie"))
                .collect(Collectors.toList());

        for (int i = 0; i < SHIFTS; i++) {
            Shift shift = new Shift(shiftInterval);
            int forwardsAdded = 0;
            for(Player forward:forwards){
                if(forward.getAvailTOI() >= shiftInterval){
                    shift.addForward(forward);
                    forward.setAvailTOI(forward.getAvailTOI() - shiftInterval);
                    forwardsAdded++;
                }
                if(forwardsAdded == SHIFT_FORWARD){
                    break;
                }
            }
            int defensemenAdded = 0;
            for(Player defense:defensemen){
                if(defense.getAvailTOI() >= shiftInterval){
                    shift.addDefense(defense);
                    defense.setAvailTOI(defense.getAvailTOI() - shiftInterval);
                    defensemenAdded++;
                }
                if(defensemenAdded == SHIFT_DEFENSE){
                    break;
                }
            }
            if (season.getCurrentDate().getTime() >= season.getRegularSeasonEndsAt().getTime()) {
                Player goalie = goalies.get(0);
                for (Player player : goalies) {
                    if (player.getSaving() > goalie.getSaving()) {
                        goalie = player;
                    }
                }
                shift.setGoalie(goalie);
            } else {
                Player goalie = goalies.get(rand.nextInt(goalies.size()));
                shift.setGoalie(goalie);
            }
            teamShifts.add(shift);
        }
        return teamShifts;
    }

    private int simulateShots(Shift team, Shift opposingTeam){
        int shots = 0;
        if (team.getShootingStat() > opposingTeam.getShootingStat()) {
            shots++;
            recordShot(team);
            if (team.getSkatingStat() > opposingTeam.getSkatingStat()) {
                shots++;
                recordShot(team);
            }
        }
        return shots;
    }

    private int simulateGoalAttempt(Shift team, Shift opposingTeam, int shots){
        int goalAttempts =0;
        for (int i = 0; i < shots; i++) {
            goalAttempts++;
            if (opposingTeam.getCheckingStat() > team.getCheckingStat()) {
                goalAttempts--;
                if (rand.nextFloat() < PENALTY_CHANCE) {
                    goalAttempts++;
                    recordPenalty(opposingTeam);
                }
            }
        }
        return goalAttempts;
    }

    private int simulateGoals(Shift team, Shift opposingTeam, int goalAttempts){
        int goals =0;
        for (int i = 0; i < goalAttempts; i++) {
            if ((opposingTeam.getGoalie().getSaving() > SAVING_BENCHMARK) == rand.nextBoolean()) {
                goals++;
                recordGoal(team);
            } else {
                recordSave(opposingTeam);
            }
        }
        return goals;
    }

    private void recordShot(Shift shift){
        Player shotBy = shift.getForwards().get(rand.nextInt(shift.getForwards().size()));
        shots.merge(shotBy, 1, Integer::sum);
        logger.info(shotBy.getPlayerName()+" made a shot");
    }

    private void recordPenalty(Shift shift){
        Player penaltyBy = shift.getDefensemen().get(rand.nextInt(shift.getDefensemen().size()));
        penalties.merge(penaltyBy, 1, Integer::sum);
        logger.info(penaltyBy.getPlayerName()+" made a penalty");
    }

    private void recordGoal(Shift shift){
        Player goalBy = shift.getForwards().get(rand.nextInt(shift.getForwards().size()));
        goals.merge(goalBy, 1, Integer::sum);
        logger.info(goalBy.getPlayerName()+" made a goal");
    }

    private void recordSave(Shift shift){
        Player goalie = shift.getGoalie();
        saves.merge(goalie, 1, Integer::sum);
        logger.info(goalie.getPlayerName()+" saved a goal");
    }

    private void resetAvailTOI(List<Player> players) {
        players.forEach(Player::resetAvailTOI);
    }

}


