package com.groupten.leagueobjectmodel.coach;

public class CoachBuilder implements ICoachBuilder {
    private Coach coach;

    @Override
    public void reset() {
        this.coach = new Coach();
    }

    @Override
    public void setName(String coachName) {
        coach.setCoachName(coachName);
    }

    @Override
    public void setCoachStats(double coachSkatingStat, double coachShootingStat, double coachCheckingStat, double coachSavingStat) {
        coach.setSkating(coachSkatingStat);
        coach.setShooting(coachShootingStat);
        coach.setChecking(coachCheckingStat);
        coach.setSaving(coachSavingStat);
    }

    @Override
    public Coach getResult() {
        return this.coach;
    }
}
