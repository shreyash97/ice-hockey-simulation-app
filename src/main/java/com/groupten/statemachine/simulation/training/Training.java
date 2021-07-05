package com.groupten.statemachine.simulation.training;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Training implements ITraining {

    private final int MAX_PLAYER_STAT_VALUE = 20;
    private List<ITrainingObserver> observers = new ArrayList<>();
    private LinkedHashMap<Coach, Integer> coachRanking = new LinkedHashMap<>();

    @Override
    public void subscribe(ITrainingObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(ITrainingObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (ITrainingObserver observer : observers) {
            observer.updateCoachRanking(coachRanking);
        }
    }

    @Override
    public void trainPlayers() {
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League leagueLOM = leagueModel.getCurrentLeague();

        Map<String, Conference> conferences = leagueLOM.getConferences();
        for (Conference conference : conferences.values()) {

            Map<String, Division> divisions = conference.getDivisions();
            for (Division division : divisions.values()) {

                Map<String, Team> teams = division.getTeams();
                for (Team team : teams.values()) {

                    double skating = team.getHeadCoach().getSkating();
                    double shooting = team.getHeadCoach().getShooting();
                    double checking = team.getHeadCoach().getChecking();
                    double saving = team.getHeadCoach().getSaving();

                    int count = 0;

                    List<Player> players = team.getActivePlayers();

                    for (Player player : players) {
                        if (compareStatisticWithRandomValue(skating)) {
                            if (player.getSkating() < MAX_PLAYER_STAT_VALUE) {
                                player.setSkating(player.getSkating() + 1);
                                count++;
                            }
                        } else {
                            player.checkInjury();
                        }

                        if (compareStatisticWithRandomValue(shooting)) {
                            if (player.getShooting() < MAX_PLAYER_STAT_VALUE) {
                                player.setShooting(player.getShooting() + 1);
                                count++;
                            }
                        } else {
                            player.checkInjury();
                        }

                        if (compareStatisticWithRandomValue(checking)) {
                            if (player.getChecking() < MAX_PLAYER_STAT_VALUE) {
                                player.setChecking(player.getChecking() + 1);
                                count++;
                            }
                        } else {
                            player.checkInjury();
                        }

                        if (compareStatisticWithRandomValue(saving)) {
                            if (player.getSaving() < MAX_PLAYER_STAT_VALUE) {
                                player.setSaving(player.getSaving() + 1);
                                count++;
                            }
                        } else {
                            player.checkInjury();
                        }
                    }
                    coachRanking.put(team.getHeadCoach(), count);
                }
            }
        }
        notifyObserver();
    }

    private boolean compareStatisticWithRandomValue(double statistic) {
        double random = Math.round(Math.random() * 100.0) / 100.0;
        return (random < statistic);
    }
}
