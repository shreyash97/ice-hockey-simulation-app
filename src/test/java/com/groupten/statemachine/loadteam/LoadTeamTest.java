package com.groupten.statemachine.loadteam;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoadTeamTest {

    @Test
    public void validateUserInputTest() {
        LoadTeam loadTeam = new LoadTeam();
        loadTeam.setTeamName("Iron Man Team");
        assertTrue(loadTeam.validateUserInput());
    }

    @Test
    public void isLeagueNameUniqueTest() {
        LoadTeam loadTeam = new LoadTeam("src/test/java/com/groupten/mocks/");
        loadTeam.setTeamName("Team Deep");
        assertTrue(loadTeam.doesTeamExist());
    }

    @Test
    public void loadExistingLeagueTest() {
        LoadTeam loadTeam = new LoadTeam("src/test/java/com/groupten/mocks/");
        loadTeam.setTeamName("Team Deep");
        loadTeam.setFileToBeLoaded("Team_Deep");
        assertTrue(loadTeam.loadExistingLeague());
    }

    @Test
    public void loadExistingLeagueFailTest() {
        LoadTeam loadTeam = new LoadTeam("xyz/");
        loadTeam.setTeamName("Team Deep");
        loadTeam.setFileToBeLoaded("Team_Deep");
        assertFalse(loadTeam.loadExistingLeague());
    }
}
