package com.groupten.persistence.m1DB.dao;

import com.groupten.persistence.m1DB.database.IStoredProcedure;
import com.groupten.persistence.m1DB.database.NullStoredProcedure;
import com.groupten.persistence.m1DB.database.StoredProcedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDAO implements IPlayerDAO {

    @Override
    public int createPlayer(String playerName, String position, double age, double skating, double shooting, double checking, double saving) {
        int playerId = 0;

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("createPlayer(?, ?, ?, ?, ?, ?, ?, ?)");
            storedProcedure.setParameter(1, playerName);
            storedProcedure.setParameter(2, position);
            storedProcedure.setParameter(3, age);
            storedProcedure.setParameter(4, skating);
            storedProcedure.setParameter(5, shooting);
            storedProcedure.setParameter(6, checking);
            storedProcedure.setParameter(7, saving);
            storedProcedure.registerOutputParameterInt(8);
            storedProcedure.execute();
            playerId = storedProcedure.getOutputParameterInt(8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return playerId;
    }

    @Override
    public List<HashMap<String, Object>> getPlayers(String colName, String colValue) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getActivePlayers(?,?)");
            storedProcedure.setParameter(1, colName);
            storedProcedure.setParameter(2, colValue);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return list;
    }

    @Override
    public void updatePlayer(int playerId, String playerName, String position, boolean isCaptain) {
        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("updatePlayer(?,?,?,?)");
            storedProcedure.setParameter(1, playerId);
            storedProcedure.setParameter(2, playerName);
            storedProcedure.setParameter(2, position);
            storedProcedure.setParameter(2, isCaptain);
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
    }

    @Override
    public void deletePlayer(int playerId) {
        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("deletePlayer(?)");
            storedProcedure.setParameter(1, playerId);
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
    }

    @Override
    public List<HashMap<String, Object>> getCaptains(String colName, String colValue) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getCaptains(?,?)");
            storedProcedure.setParameter(1, colName);
            storedProcedure.setParameter(2, colValue);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return list;
    }

    @Override
    public List<HashMap<String, Object>> getFreeAgents(String colName, String colValue) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getFreeAgents(?,?)");
            storedProcedure.setParameter(1, colName);
            storedProcedure.setParameter(2, colValue);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return list;
    }
}
