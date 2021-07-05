package com.groupten.statemachine.simulation.generateplayoffschedule;

import com.groupten.leagueobjectmodel.season.Season;

public class GeneratePlayoffSchedule implements IGeneratePlayoffSchedule {
    @Override
    public boolean generatePlayoffSchedule(Season season) {
        return season.generatePlayoffSchedule();
    }
}
