package com.groupten.statemachine.simulation.trophy;

import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public interface ITrophy {

    void awardTrophy();

    void trophyWinners();

    LinkedList<TrophyPerSeason> getHistoricData();

    void setCoachRanking(LinkedHashMap<Coach, Integer> coachRanking);

    void setTeamRanking(LinkedHashMap<Team, Integer> teamRanking);

    void setBestPlayerRanking(Map<Player, Integer> bestPlayerRanking);

    void setBestDefenseMenRanking(Map<Player, Integer> bestDefenseMenRanking);

    void setTopGoalRanking(Map<Player, Integer> topGoalRanking);

    void setBestGoalieRanking(Map<Player, Integer> bestGoalieRanking);

}
