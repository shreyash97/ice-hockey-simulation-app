package com.groupten.leagueobjectmodel.team;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.generalmanager.GeneralManager;
import com.groupten.leagueobjectmodel.leaguemodel.IPersistModel;
import com.groupten.leagueobjectmodel.player.IPlayerSubscriber;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.persistence.m1DB.dao.ITeamDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Team implements IPlayerSubscriber {
    private final int REQUIRED_NUMBER_OF_ACTIVE_PLAYERS = 20;

    private int teamID;
    private int divisionID;
    private String teamName;
    private boolean aITeam;
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> inActivePlayers = new ArrayList<>();
    private List<Player> allPlayers = new ArrayList<>();
    private GeneralManager generalManager;
    private Coach headCoach;
    private double teamStrength;
    private int winPoint;
    private int lossPoint;
    private transient ISwapPlayers swapPlayers;

    public Team() {
        this.aITeam = true;
    }

    public Team(String teamName) {
        this.aITeam = true;
        this.teamName = teamName;
    }

    public Team(int teamID, String teamName) {
        this(teamName);
        this.aITeam = true;
        this.teamID = teamID;
    }

    public static boolean isTeamNameValid(String teamName) {
        if (teamName.isEmpty() || teamName.isBlank() || teamName.toLowerCase().equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addActivePlayer(Player player) {
        if (Player.arePlayerFieldsValid(player.getPlayerName(), player.getPosition(),
                player.getSkating(), player.getShooting(), player.getChecking(), player.getSaving())) {
            int initialSize = activePlayers.size();
            player.attach(this);
            activePlayers.add(player);
            return activePlayers.size() > initialSize;
        } else {
            return false;
        }
    }

    public void persistPlayerWithTeam(Player player) {
        ITeamDAO teamDAO = Injector.instance().getTeamDatabaseObject();
        teamDAO.attachTeamPlayer(teamID, player.getPlayerID());
    }

    public boolean isPlayersCountValid() {
        return activePlayers.size() == REQUIRED_NUMBER_OF_ACTIVE_PLAYERS;
    }

    public boolean doesTeamHaveOneCaptain() {
        List<Boolean> captains = new ArrayList<>();
        for (Player player : activePlayers) {
            captains.add(player.isCaptain());
        }
        int count = Collections.frequency(captains, true);

        return count == 1;
    }

    public double calculateTeamStrength() {
        for (Player player : activePlayers) {
            String pos = player.getPosition();
            double playerStrength = player.calculateStrength();

            if (player.isInjured()) {
                teamStrength += (playerStrength / 2);
            } else {
                teamStrength += playerStrength;
            }
        }

        return teamStrength;
    }

    public double calculateTotalTeamStrength() {
        for (Player player : activePlayers) {
            String pos = player.getPosition();
            double playerStrength = player.calculateStrength();

            if (player.isInjured()) {
                teamStrength += (playerStrength / 2);
            } else {
                teamStrength += playerStrength;
            }
        }
        for (Player player : inActivePlayers) {
            String pos = player.getPosition();
            double playerStrength = player.calculateStrength();

            if (player.isInjured()) {
                teamStrength += (playerStrength / 2);
            } else {
                teamStrength += playerStrength;
            }
        }

        return teamStrength;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int tID) {
        teamID = tID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String tN) {
        teamName = tN;
    }

    public GeneralManager getGeneralManager() {
        return generalManager;
    }

    public boolean setGeneralManager(GeneralManager generalManager) {
        if (GeneralManager.isManagerValid(generalManager)) {
            this.generalManager = generalManager;
            return true;
        } else {
            return false;
        }
    }

    public Coach getHeadCoach() {
        return headCoach;
    }

    public boolean setHeadCoach(Coach headCoach) {
        if (Coach.areCoachFieldsValid(headCoach.getCoachName(), headCoach.getSkating(), headCoach.getShooting(), headCoach.getChecking(), headCoach.getSaving())) {
            this.headCoach = headCoach;
            return true;
        } else {
            return false;
        }
    }

    public List<Player> getAllPlayers() {
        ArrayList<Player> totalPlayers = new ArrayList<>();

        for (Player player : getActivePlayers()) {
            totalPlayers.add(player);
        }
        for (Player player : getInActivePlayers()) {
            totalPlayers.add(player);
        }

        return totalPlayers;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.activePlayers = null;
        this.inActivePlayers = null;
        this.allPlayers = allPlayers;
    }

    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(List<Player> activePlayers) {
        attachTeamObserverToPlayer(activePlayers);
        this.activePlayers = activePlayers;
    }

    public double getTeamStrength() {
        return teamStrength;
    }

    public void setTeamStrength(double teamStrength) {
        this.teamStrength = teamStrength;
    }

    public boolean isaITeam() {
        return aITeam;
    }

    public void setaITeam(boolean aITeam) {
        this.aITeam = aITeam;
    }

    public int getWinPoint() {
        return winPoint;
    }

    public void setWinPoint(int winPoint) {
        this.winPoint = winPoint;
    }

    public int getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(int lossPoint) {
        this.lossPoint = lossPoint;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public List<Player> getInActivePlayers() {
        return inActivePlayers;
    }

    public void setInActivePlayers(List<Player> inActivePlayers) {
        attachTeamObserverToPlayer(inActivePlayers);
        this.inActivePlayers = inActivePlayers;
    }

    @Override
    public void update(Player player) {
        swapInjuredActivePlayerWithInActivePlayer(player);
    }

    public ISwapPlayers getSwapPlayers() {
        return swapPlayers;
    }

    public void setSwapPlayers(ISwapPlayers swapPlayers) {
        this.swapPlayers = swapPlayers;
    }

    private void swapInjuredActivePlayerWithInActivePlayer(Player player) {
        Map<String, List<Player>> updatedRosters = swapPlayers.swapPlayersAndUpdateRosters(activePlayers, inActivePlayers, player);
        activePlayers = updatedRosters.get(PlayerRosterNames.ACTIVE_PLAYERS_ROSTER.name());
        inActivePlayers = updatedRosters.get(PlayerRosterNames.INACTIVE_PLAYERS_ROSTER.name());
    }

    private void attachTeamObserverToPlayer(List<Player> players) {
        for (Player player : players) {
            player.attach(this);
        }
    }

}
