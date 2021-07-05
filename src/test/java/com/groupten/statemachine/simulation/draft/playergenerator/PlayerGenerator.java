package com.groupten.statemachine.simulation.draft.playergenerator;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.statemachine.jsonimport.JSONImport;
import com.groupten.statemachine.simulation.draft.generateplayers.IPlayersGenerator;
import org.junit.Test;

public class PlayerGenerator {
    @Test
    public void generatePlayersTest() {
        JSONImport jsonTestSuccess = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/JsonMock.json";
        jsonTestSuccess.importJSONData(path);
        jsonTestSuccess.instantiateJSONData();
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        IPlayersGenerator playersGenerator = Injector.instance().getPlayersGeneratorInterface();
        playersGenerator.generatePlayers();
    }
}
