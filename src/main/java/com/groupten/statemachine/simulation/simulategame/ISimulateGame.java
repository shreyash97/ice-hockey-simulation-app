package com.groupten.statemachine.simulation.simulategame;

import com.groupten.leagueobjectmodel.schedule.Schedule;
import com.groupten.leagueobjectmodel.season.Season;

public interface ISimulateGame {
    void simulateGame(Season season, Schedule schedule);
}
