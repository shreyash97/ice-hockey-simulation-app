package com.groupten.statemachine.simulation.initializeseason;

import com.groupten.leagueobjectmodel.season.Season;

public class InitializeSeason implements IInitializeSeason {
    @Override
    public boolean generateRegularSchedule(Season season) {
        return season.generateRegularSchedule();
    }
}
