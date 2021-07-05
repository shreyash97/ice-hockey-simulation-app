package com.groupten.statemachine.simulation.advancetime;

import com.groupten.leagueobjectmodel.season.Season;

public class AdvanceTime implements IAdvanceTime {
    @Override
    public void advanceTime(Season season) {
        season.advanceTime();
    }
}
