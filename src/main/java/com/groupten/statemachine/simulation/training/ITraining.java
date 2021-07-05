package com.groupten.statemachine.simulation.training;

public interface ITraining {
    void trainPlayers();

    void subscribe(ITrainingObserver observer);

    void unsubscribe(ITrainingObserver observer);

    void notifyObserver();
}
