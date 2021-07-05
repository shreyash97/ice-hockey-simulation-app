package com.groupten.leagueobjectmodel.teamstanding;


import com.groupten.leagueobjectmodel.team.Team;

public class TeamStanding implements Comparable<TeamStanding> {
    private Team team;
    private String divisionName;
    private String conferenceName;
    private int points;
    private int leagueRank;
    private int conferenceRank;
    private int divisionRank;

    public TeamStanding(Team team, String divisionName, String conferenceName, int points, int leagueRank, int conferenceRank, int divisionRank) {
        this.team = team;
        this.divisionName = divisionName;
        this.conferenceName = conferenceName;
        this.points = points;
        this.leagueRank = leagueRank;
        this.conferenceRank = conferenceRank;
        this.divisionRank = divisionRank;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLeagueRank() {
        return leagueRank;
    }

    public void setLeagueRank(int leagueRank) {
        this.leagueRank = leagueRank;
    }

    public int getConferenceRank() {
        return conferenceRank;
    }

    public void setConferenceRank(int conferenceRank) {
        this.conferenceRank = conferenceRank;
    }

    public int getDivisionRank() {
        return divisionRank;
    }

    public void setDivisionRank(int divisionRank) {
        this.divisionRank = divisionRank;
    }

    @Override
    public int compareTo(TeamStanding teamStanding) {
        return this.points - teamStanding.getPoints();
    }
}
