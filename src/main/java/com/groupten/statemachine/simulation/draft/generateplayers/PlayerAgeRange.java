package com.groupten.statemachine.simulation.draft.generateplayers;

public enum PlayerAgeRange implements IPlayerStatsEnum {
    MINIMUM_AGE(18.0), MAXIMUM_AGE(21.0);

    private double value;

    PlayerAgeRange(double value) {
        this.value = value;
    }

    @Override
    public double getDoubleValue() {
        return value;
    }
}
