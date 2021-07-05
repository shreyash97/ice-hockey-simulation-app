package com.groupten.leagueobjectmodel.generalmanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralManagerTest {
    @Test
    public void isManagerNameValidTest() {

        GeneralManager generalManager = new GeneralManager("First Manager", "normal");
        assertTrue(GeneralManager.isManagerValid(generalManager));
        GeneralManager generalManager1 = new GeneralManager("", "normal");
        assertFalse(GeneralManager.isManagerValid(generalManager1));
        GeneralManager generalManager2 = new GeneralManager("", "normal");
        assertFalse(GeneralManager.isManagerValid(generalManager2));
        GeneralManager generalManager3 = new GeneralManager("Null", "normal");
        assertFalse(GeneralManager.isManagerValid(generalManager3));
    }

    @Test
    public void getManagerIDTest() {
        GeneralManager generalManager = new GeneralManager(1, "First General Manager");
        assertEquals(1, generalManager.getManagerID());
    }

    @Test
    public void setManagerIDTest() {
        GeneralManager generalManager = new GeneralManager("First General Manager");
        generalManager.setManagerID(1);
        assertEquals(1, generalManager.getManagerID());
    }

    @Test
    public void getManagerNameTest() {
        GeneralManager generalManager = new GeneralManager(1, "First General Manager");
        assertEquals("First General Manager", generalManager.getManagerName());
    }

    @Test
    public void setManagerNameTest() {
        GeneralManager generalManager = new GeneralManager("First General Manager");
        generalManager.setManagerName("Updated First General Manager");
        assertEquals("Updated First General Manager", generalManager.getManagerName());
    }

    @Test
    public void setLeagueIDTest() {
        GeneralManager generalManager = new GeneralManager("First General Manager");
        generalManager.setLeagueID(1);
        assertEquals(1, generalManager.getLeagueID());
    }

    @Test
    public void setTeamIDTest() {
        GeneralManager generalManager = new GeneralManager("First General Manager");
        generalManager.setTeamID(1);
        assertEquals(1, generalManager.getTeamID());
    }
}
