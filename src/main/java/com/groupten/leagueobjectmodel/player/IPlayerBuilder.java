package com.groupten.leagueobjectmodel.player;

public interface IPlayerBuilder {
    void reset();

    void setProfile(String playerName, String playerPosition);

    void setAsCaptain(boolean captain);

    void setAge(double age);

    void setAgeFromBirthDay(int playerBirthDay, int playerBirthMonth, int playerBirthYear);

    void setDraftYear();

    void setPlayerStats(double playerSkatingStat, double playerShootingStat, double playerCheckingStat, double playerSavingStat);

    Player getResult();
}
