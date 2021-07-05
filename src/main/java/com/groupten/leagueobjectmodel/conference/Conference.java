package com.groupten.leagueobjectmodel.conference;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.leaguemodel.IPersistModel;
import com.groupten.persistence.m1DB.dao.IConferenceDAO;

import java.util.HashMap;
import java.util.Map;

public class Conference {
    private final Map<String, Division> divisions = new HashMap<>();
    public int leagueID;
    private int conferenceID;
    private String conferenceName;

    public Conference(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public Conference(int conferenceID, String conferenceName) {
        this(conferenceName);
        this.conferenceID = conferenceID;
    }

    public static boolean isConferenceNameValid(String conferenceName) {
        if (conferenceName.isEmpty() || conferenceName.isBlank() || conferenceName.toLowerCase().equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDivision(Division division) {
        if (Division.isDivisionNameValid(division.getDivisionName())) {
            String divisionName = division.getDivisionName();
            int initialSize = divisions.size();
            divisions.put(divisionName, division);
            return divisions.size() > initialSize;
        } else {
            return false;
        }
    }

    public boolean isNumberOfDivisionsEven() {
        return divisions.size() % 2 == 0;
    }

    public boolean containsDivision(String divisionName) {
        return divisions.containsKey(divisionName);
    }

    public Division getDivision(String divisionName) {
        return divisions.get(divisionName);
    }

    public Map<String, Division> getDivisions() {
        return divisions;
    }

    public int getConferenceID() {
        return conferenceID;
    }

    public void setConferenceID(int conferenceID) {
        this.conferenceID = conferenceID;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }
}
