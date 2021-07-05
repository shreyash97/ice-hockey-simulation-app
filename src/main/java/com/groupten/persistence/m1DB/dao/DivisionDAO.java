package com.groupten.persistence.m1DB.dao;

import com.groupten.persistence.m1DB.database.IStoredProcedure;
import com.groupten.persistence.m1DB.database.NullStoredProcedure;
import com.groupten.persistence.m1DB.database.StoredProcedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DivisionDAO implements IDivisionDAO {

    @Override
    public int createDivision(int conferenceId, String divisionName) {
        int divisionId = 0;

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("createDivision(?,?,?)");
            storedProcedure.setParameter(1, conferenceId);
            storedProcedure.setParameter(2, divisionName);
            storedProcedure.registerOutputParameterInt(3);
            storedProcedure.execute();
            divisionId = storedProcedure.getOutputParameterInt(3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return divisionId;
    }

    @Override
    public List<HashMap<String, Object>> getDivisions(String colName, String colValue) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getDivisions(?,?)");
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
    public void updateDivision(int divisionId, String divisionName) {
        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("updateDivision(?,?)");
            storedProcedure.setParameter(1, divisionId);
            storedProcedure.setParameter(2, divisionName);
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
    }

    @Override
    public void deleteDivision(int divisionId) {
        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("deleteDivision(?)");
            storedProcedure.setParameter(1, divisionId);
            storedProcedure.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }
    }

    @Override
    public List<HashMap<String, Object>> getDivisionTeams(int divisionId) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        IStoredProcedure storedProcedure = new NullStoredProcedure();
        try {
            storedProcedure = new StoredProcedure("getDivisionTeams(?)");
            storedProcedure.setParameter(1, divisionId);
            list = storedProcedure.executeWithResults();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure.cleanup();
        }

        return list;
    }
}
