package com.groupten.statemachine.simulation.statsdecay;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.player.IPlayerBuilder;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.statemachine.jsonimport.JSONImport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatsDecayTest {
    @Test
    public void checkLikelihoodAndDecayStatusTest() {
        JSONImport jsonTestSuccess = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/JsonMock.json";
        jsonTestSuccess.importJSONData(path);
        assertTrue(jsonTestSuccess.instantiateJSONData());

        IPlayerBuilder playerBuilder = Injector.instance().getPlayerBuilder();
        playerBuilder.reset();
        playerBuilder.setProfile("First Player", "forward");
        playerBuilder.setAsCaptain(true);
        playerBuilder.setDraftYear();
        playerBuilder.setAge(18.0);
        playerBuilder.setPlayerStats(5.0, 5.0, 5.0, 5.0);

        Player player = playerBuilder.getResult();
        List<Player> players = new ArrayList<>();
        players.add(player);

        IStatsDecay statsDecay = Injector.instance().getStatsDecay();

        while (player.getSkating() == 5.0) {
            statsDecay.checkLikelihoodAndDecayStatus(players);
        }

        assertEquals(4.0, player.getSkating(), 0.0);

    }
}
