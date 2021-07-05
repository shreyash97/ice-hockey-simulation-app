package com.groupten.leagueobjectmodel.shift;

import com.groupten.leagueobjectmodel.player.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShiftTest {

    @Test
    public void getIntervalTest() {
        Shift shift = new Shift(10);
        assertEquals(10, shift.getInterval());
    }

    @Test
    public void getForwardsTest() {
        Shift shift = new Shift(10);
        Player player = new Player(1, "First Player", "forward", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addForward(player);
        assertEquals(1, shift.getForwards().size());
    }

    @Test
    public void addForwardTest() {
        Shift shift = new Shift(10);
        Player player1 = new Player(1, "First Player", "forward", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addForward(player1);
        Player player2 = new Player(2, "Second Player", "forward", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addForward(player2);
        assertEquals(2, shift.getForwards().size());
    }

    @Test
    public void getDefensemenTest() {
        Shift shift = new Shift(10);
        Player player = new Player(1, "First Player", "defense", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addDefense(player);
        assertEquals(1, shift.getDefensemen().size());
    }

    @Test
    public void addDefenseTest() {
        Shift shift = new Shift(10);
        Player player1 = new Player(1, "First Player", "defense", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addDefense(player1);
        Player player2 = new Player(2, "Second Player", "defense", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addDefense(player2);
        assertEquals(2, shift.getDefensemen().size());
    }

    @Test
    public void getGoalieTest() {
        Shift shift = new Shift(10);
        Player player = new Player(1, "First Player", "goalie", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.setGoalie(player);
        assertEquals("First Player", shift.getGoalie().getPlayerName());
    }

    @Test
    public void addGoalieTest() {
        Shift shift = new Shift(10);
        Player player1 = new Player(1, "First Player", "goalie", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.setGoalie(player1);
        assertEquals("First Player", shift.getGoalie().getPlayerName());
    }

    @Test
    public void getShootingStatTest() {
        Shift shift = new Shift(10);
        Player player1 = new Player(1, "First Player", "forward", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addForward(player1);
        Player player2 = new Player(2, "Second Player", "forward", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addForward(player2);
        assertEquals(10, (int) shift.getShootingStat());
    }

    @Test
    public void getSkatingStatTest() {
        Shift shift = new Shift(10);
        Player player1 = new Player(1, "First Player", "forward", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addForward(player1);
        Player player2 = new Player(2, "Second Player", "defense", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addDefense(player2);
        assertEquals(10, (int) shift.getSkatingStat());
    }

    @Test
    public void getCheckingStatTest() {
        Shift shift = new Shift(10);
        Player player1 = new Player(1, "First Player", "defense", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addDefense(player1);
        Player player2 = new Player(2, "Second Player", "defense", false, 20.0, 5.0, 5.0, 5.0, 5.0);
        shift.addDefense(player2);
        assertEquals(10, (int) shift.getCheckingStat());
    }
}
