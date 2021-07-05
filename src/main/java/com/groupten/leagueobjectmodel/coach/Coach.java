package com.groupten.leagueobjectmodel.coach;

import com.groupten.leagueobjectmodel.leaguemodel.IPersistModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Coach implements IPersistModel {
    private int coachID;
    private int leagueID;
    private int teamID;
    private String coachName;
    private double skating;
    private double shooting;
    private double checking;
    private double saving;

    public Coach() { }

    public Coach(String n) {
        coachName = n;
    }

    public Coach(String n, double sk, double sh, double ch, double sa) {
        coachName = n;
        skating = sk;
        shooting = sh;
        checking = ch;
        saving = sa;
    }

    public Coach(int cID, String cN, double sk, double sh, double ch, double sa) {
        this(cN, sk, sh, ch, sa);
        coachID = cID;
    }

    public static boolean areCoachFieldsValid(String cN, double sk, double sh, double ch, double sa) {
        return isCoachNameValid(cN) && areStatsValid(sk, sh, ch, sa);
    }

    private static boolean isCoachNameValid(String pN) {
        if (pN.isEmpty() || pN.isBlank() || pN.toLowerCase().equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean areStatsValid(double... args) {
        List<Boolean> validChecks = new ArrayList<>();

        for (double stat : args) {
            validChecks.add(stat >= 0.0 && stat <= 1.0);
        }

        return Collections.frequency(validChecks, false) == 0;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getCoachID() {
        return coachID;
    }

    public void setCoachID(int coachID) {
        this.coachID = coachID;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public double getSkating() {
        return skating;
    }

    public void setSkating(double skating) {
        this.skating = skating;
    }

    public double getShooting() {
        return shooting;
    }

    public void setShooting(double shooting) {
        this.shooting = shooting;
    }

    public double getChecking() {
        return checking;
    }

    public void setChecking(double checking) {
        this.checking = checking;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
    }

    @Override
    public boolean save() {
        return false;
    }
}
