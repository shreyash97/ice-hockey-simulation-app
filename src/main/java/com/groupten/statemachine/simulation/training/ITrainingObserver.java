package com.groupten.statemachine.simulation.training;

import com.groupten.leagueobjectmodel.coach.Coach;

import java.util.LinkedHashMap;

public interface ITrainingObserver {
    void updateCoachRanking(LinkedHashMap<Coach, Integer> coachRanking);
}
