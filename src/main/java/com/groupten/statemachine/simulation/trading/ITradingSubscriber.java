package com.groupten.statemachine.simulation.trading;
import com.groupten.leagueobjectmodel.team.Team;
import java.util.List;

public interface ITradingSubscriber {
    void update(List<Team> Teams);
}
