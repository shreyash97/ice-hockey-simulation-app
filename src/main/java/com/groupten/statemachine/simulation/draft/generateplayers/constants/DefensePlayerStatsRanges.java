package com.groupten.statemachine.simulation.draft.generateplayers.constants;

import com.groupten.statemachine.simulation.draft.generateplayers.IPlayerStatsEnum;

public enum DefensePlayerStatsRanges implements IPlayerStatsEnum {
    MINIMUM_SKATING_STAT(10.0), MAXIMUM_SKATING_STAT(19.0), MINIMUM_SHOOTING_STAT(9.0), MAXIMUM_SHOOTING_STAT(18.0),
    MINIMUM_CHECKING_STAT(12.0), MAXIMUM_CHECKING_STAT(20.0), MINIMUM_SAVING_STAT(1.0), MAXIMUM_SAVING_STAT(12.0);

    private double value;

    DefensePlayerStatsRanges(double value) {
        this.value = value;
    }

    @Override
    public double getDoubleValue() {
        return value;
    }
}
