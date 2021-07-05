package com.groupten.persistence.m1DB.dao;

import com.groupten.persistence.m1DB.database.IStoredProcedure;
import com.groupten.persistence.m1DB.database.NullStoredProcedure;
import com.groupten.persistence.m1DB.database.StoredProcedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeagueDAO implements ILeagueDAO {

    @Override
    public int createLeague(String leagueName, int averageRetirementAge, int maximumAge, double randomInjuryChance, int injuryDaysHigh,
                            int injuryDaysLow, int lossPoint, double randomTradeOfferChance, int maxPlayerPerTrade, double randomAcceptanceChance) {
        long leagueId = 0;

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("createLeague(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            storedProcedure.setParameter(1, leagueName);
            storedProcedure.setParameter(2, averageRetirementAge);
            storedProcedure.setParameter(3, maximumAge);
            storedProcedure.setParameter(4, randomInjuryChance);
            storedProcedure.setParameter(5, injuryDaysHigh);
            storedProcedure.setParameter(6, injuryDaysLow);
            storedProcedure.setParameter(7, lossPoint);
            storedProcedure.setParameter(8, randomTradeOfferChance);
            storedProcedure.setParameter(9, maxPlayerPerTrade);
            storedProcedure.setParameter(10, randomAcceptanceChance);
            storedProcedure.registerOutputParameterInt(11);
            storedProcedure.execute();
            leagueId = storedProcedure.getOutputParameterInt(11);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return (int) leagueId;
    }

    @Override
    public List<HashMap<String, Object>> getLeagues(String colName, String colValue) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getLeagues(?,?)");
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
    public void updateLeague(int leagueId, String leagueName) {
        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("updateLeague(?,?)");
            storedProcedure.setParameter(1, leagueId);
            storedProcedure.setParameter(2, leagueName);
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
    }

    @Override
    public void deleteLeague(int leagueId) {
        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("deleteLeague(?)");
            storedProcedure.setParameter(1, leagueId);
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
    }

    @Override
    public List<HashMap<String, Object>> getLeagueConferences(int leagueId) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getLeagueConferences(?)");
            storedProcedure.setParameter(1, leagueId);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return list;
    }

    @Override
    public List<HashMap<String, Object>> getLeaguePlayers(int leagueId) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getLeaguePlayers(?)");
            storedProcedure.setParameter(1, leagueId);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return list;
    }

    @Override
    public List<HashMap<String, Object>> getLeagueFreeAgents(int leagueId) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getLeagueFreeAgents(?)");
            storedProcedure.setParameter(1, leagueId);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
        return list;
    }
}
