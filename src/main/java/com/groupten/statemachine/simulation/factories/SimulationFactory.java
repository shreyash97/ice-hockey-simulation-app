package com.groupten.statemachine.simulation.factories;

import com.groupten.statemachine.simulation.draft.Draft;
import com.groupten.statemachine.simulation.draft.IDraft;

public class SimulationFactory implements ISimulationFactory {

    @Override
    public IDraft createDraft() {
        return new Draft();
    }
}
