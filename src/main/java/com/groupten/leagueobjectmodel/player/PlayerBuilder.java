package com.groupten.leagueobjectmodel.player;

import java.time.LocalDateTime;

public class PlayerBuilder implements IPlayerBuilder {
    Player player;

    @Override
    public void reset() {
        this.player = new Player();
    }

    @Override
    public void setProfile(String playerName, String playerPosition) {
        player.setPlayerName(playerName);
        player.setPosition(playerPosition);
    }

    @Override
    public void setAsCaptain(boolean captain) {
        player.setCaptain(captain);
    }

    @Override
    public void setAge(double age) {
        player.setAge(age);
    }

    @Override
    public void setAgeFromBirthDay(int playerBirthDay, int playerBirthMonth, int playerBirthYear) {
        player.setBirthDay(playerBirthDay);
        player.setBirthMonth(playerBirthMonth);
        player.setBirthYear(playerBirthYear);
        player.setAge(initializePlayerAge(playerBirthDay, playerBirthMonth, playerBirthYear));
    }

    @Override
    public void setDraftYear() {
        LocalDateTime today = LocalDateTime.now();
        player.setDraftYear(today.getYear());
    }

    @Override
    public void setPlayerStats(double playerSkatingStat, double playerShootingStat, double playerCheckingStat, double playerSavingStat) {
        player.setSkating(playerSkatingStat);
        player.setShooting(playerShootingStat);
        player.setChecking(playerCheckingStat);
        player.setSaving(playerSavingStat);
    }

    @Override
    public Player getResult() {
        return this.player;
    }

    private double initializePlayerAge(int birthDay, int birthMonth, int birthYear) {
        LocalDateTime today = LocalDateTime.now();
        return today.getYear() - birthYear + ((today.getMonthValue() - birthMonth) / 12.0) + ((today.getDayOfMonth() - birthDay) / 365.0);
    }
}
