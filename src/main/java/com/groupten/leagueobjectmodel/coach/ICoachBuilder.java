package com.groupten.leagueobjectmodel.coach;

public interface ICoachBuilder {
    void reset();

    void setName(String coachName);

    void setCoachStats(double coachSkatingStat, double coachShootingStat, double coachCheckingStat, double coachSavingStat);

    Coach getResult();
}
