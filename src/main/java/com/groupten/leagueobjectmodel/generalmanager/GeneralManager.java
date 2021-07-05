package com.groupten.leagueobjectmodel.generalmanager;

import com.groupten.leagueobjectmodel.leaguemodel.IPersistModel;

public class GeneralManager {
    private int managerID;
    private String managerName;
    private String managerPersonality;
    private int leagueID;
    private int teamID;

    public GeneralManager(String mN) {
        managerName = mN;
    }

    public GeneralManager(String mN, String mP) {
        managerName = mN;
        managerPersonality = mP;
    }

    public GeneralManager(int mID, String mN) {
        this(mN);
        managerID = mID;
    }

    public static boolean isManagerValid(GeneralManager generalManager) {
        if (generalManager.getManagerName().isEmpty() || generalManager.getManagerName().isBlank() || generalManager.getManagerName().toLowerCase().equals("null") ||
                generalManager.getManagerPersonality().isEmpty() || generalManager.getManagerPersonality().isBlank() || generalManager.getManagerPersonality().toLowerCase().equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int mID) {
        managerID = mID;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String mN) {
        managerName = mN;
    }

    public String getManagerPersonality() {
        return managerPersonality;
    }

    public void setManagerPersonality(String mp) {
        managerPersonality = mp;
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
}
