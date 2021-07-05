package com.groupten.statemachine.jsonimport;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JSONImportTest {

    @Test
    public void importJSONDataTest() {
        JSONImport jsonTestSuccess = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/JsonMock.json";
        assertTrue(jsonTestSuccess.importJSONData(path));
    }

    @Test
    public void importJSONDataTestUnsuccessful() {
        JSONImport jsonTestUnSuccess = new JSONImport();
        String path = "";
        assertFalse(jsonTestUnSuccess.importJSONData(path));
    }

    @Test
    public void instantiateJSONDataTest() {
        JSONImport jsonTestSuccess = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/JsonMock.json";
        jsonTestSuccess.importJSONData(path);
        assertTrue(jsonTestSuccess.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_Player() {
        JSONImport jsonTestPlayerBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/PlayerBlankMock.json";
        jsonTestPlayerBlank.importJSONData(path);
        assertFalse(jsonTestPlayerBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_Division() {
        JSONImport jsonTestDivisionBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/DivisionBlankMock.json";
        jsonTestDivisionBlank.importJSONData(path);
        assertFalse(jsonTestDivisionBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_Conference() {
        JSONImport jsonTestConferenceBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/ConferenceBlankMock.json";
        jsonTestConferenceBlank.importJSONData(path);
        assertFalse(jsonTestConferenceBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_League() {
        JSONImport jsonTestLeagueBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/LeagueBlankMock.json";
        jsonTestLeagueBlank.importJSONData(path);
        assertFalse(jsonTestLeagueBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_FreeAgents() {
        JSONImport jsonTestFreeAgentBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/FreeAgentBlankMock.json";
        jsonTestFreeAgentBlank.importJSONData(path);
        assertFalse(jsonTestFreeAgentBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_Team() {
        JSONImport jsonTestTeamBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/TeamBlankMock.json";
        jsonTestTeamBlank.importJSONData(path);
        assertFalse(jsonTestTeamBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_Coach() {
        JSONImport jsonTestCoachBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/CoachBlankMock.json";
        jsonTestCoachBlank.importJSONData(path);
        assertFalse(jsonTestCoachBlank.instantiateJSONData());
    }

    @Test
    public void instantiateJSONDataTest_Manager() {
        JSONImport jsonTestManagerBlank = new JSONImport();
        String path = "src/test/java/com/groupten/mocks/ManagerBlankMock.json";
        jsonTestManagerBlank.importJSONData(path);
        assertFalse(jsonTestManagerBlank.instantiateJSONData());
    }

}
