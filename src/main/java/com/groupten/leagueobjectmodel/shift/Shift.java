package com.groupten.leagueobjectmodel.shift;

import com.groupten.leagueobjectmodel.player.Player;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Shift {
    private int interval = 0;
    private List<Player> forwards = new ArrayList<>();
    private List<Player> defensemen = new ArrayList<>();
    private Player goalie;

    public Shift(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public List<Player> getForwards() {
        return forwards;
    }

    public void addForward(Player player) throws InputMismatchException, IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException();
        }
        if (player.getPosition().equals("forward")) {
            forwards.add(player);
        } else {
            throw new InputMismatchException();
        }
    }

    public List<Player> getDefensemen() {
        return defensemen;
    }

    public void addDefense(Player player) throws InputMismatchException, IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException();
        }
        if (player.getPosition().equals("defense")) {
            defensemen.add(player);
        } else {
            throw new InputMismatchException();
        }
    }

    public Player getGoalie() {
        return goalie;
    }

    public void setGoalie(Player player) throws InputMismatchException, IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException();
        }
        if (player.getPosition().equals("goalie")) {
            this.goalie = player;
        } else {
            throw new InputMismatchException();
        }
    }

    public double getShootingStat() {
        double shootingStat = 0;
        for (Player forward : forwards) {
            shootingStat = shootingStat + forward.getShooting();
        }
        return shootingStat;
    }

    public double getSkatingStat() {
        double skatingStat = 0;
        for (Player forward : forwards) {
            skatingStat = skatingStat + forward.getSkating();
        }
        for (Player defense : defensemen) {
            skatingStat = skatingStat + defense.getSkating();
        }
        return skatingStat;
    }

    public double getCheckingStat() {
        double checkingStat = 0;
        for (Player defense : defensemen) {
            checkingStat = checkingStat + defense.getChecking();
        }
        return checkingStat;
    }
}
