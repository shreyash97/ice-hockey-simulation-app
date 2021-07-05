package com.groupten.statemachine.simulation.draft.generateplayers.constants;

import com.groupten.statemachine.simulation.draft.generateplayers.IPlayerStatsEnum;

public enum GoaliePlayerStatsRanges implements IPlayerStatsEnum {
    MINIMUM_SKATING_STAT(8.0), MAXIMUM_SKATING_STAT(15.0), MINIMUM_SHOOTING_STAT(1.0), MAXIMUM_SHOOTING_STAT(10.0),
    MINIMUM_CHECKING_STAT(1.0), MAXIMUM_CHECKING_STAT(12.0), MINIMUM_SAVING_STAT(12.0), MAXIMUM_SAVING_STAT(20.0);

    private double value;

    GoaliePlayerStatsRanges(double value) {
        this.value = value;
    }

    @Override
    public double getDoubleValue() {
        return value;
    }
}
