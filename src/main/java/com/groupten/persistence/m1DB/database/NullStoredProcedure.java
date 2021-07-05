package com.groupten.persistence.m1DB.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class NullStoredProcedure implements IStoredProcedure {

    public void cleanup() {
    }

    public void setParameter(int paramIndex, boolean value) throws SQLException {
    }

    public void registerOutputParameterBoolean(int paramIndex) throws SQLException {
    }

    public String getOutputParameterBoolean(int paramIndex) throws SQLException {
        return null;
    }

    public void setParameter(int paramIndex, String value) throws SQLException {
    }

    public void setParameter(int paramIndex, double value) throws SQLException {
    }

    public void registerOutputParameterString(int paramIndex) throws SQLException {
    }

    public String getOutputParameterString(int paramIndex) throws SQLException {
        return null;
    }

    public void setParameter(int paramIndex, int value) throws SQLException {
    }

    public void registerOutputParameterInt(int paramIndex) throws SQLException {
    }

    public int getOutputParameterInt(int paramIndex) throws SQLException {
        return 0;
    }

    public void setParameter(int paramIndex, long value) throws SQLException {
    }

    public void registerOutputParameterLong(int paramIndex) throws SQLException {
    }

    public long getOutputParameterLong(int paramIndex) throws SQLException {
        return 0;
    }

    public ArrayList<HashMap<String, Object>> executeWithResults() throws SQLException {
        return null;
    }

    public void execute() throws SQLException {
    }

    private ArrayList<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        return null;
    }
}
