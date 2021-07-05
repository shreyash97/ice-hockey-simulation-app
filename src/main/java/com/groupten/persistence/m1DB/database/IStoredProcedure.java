package com.groupten.persistence.m1DB.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IStoredProcedure {

    void cleanup();

    void setParameter(int paramIndex, boolean value) throws SQLException;

    void registerOutputParameterBoolean(int paramIndex) throws SQLException;

    String getOutputParameterBoolean(int paramIndex) throws SQLException;

    void setParameter(int paramIndex, String value) throws SQLException;

    void setParameter(int paramIndex, double value) throws SQLException;

    void registerOutputParameterString(int paramIndex) throws SQLException;

    String getOutputParameterString(int paramIndex) throws SQLException;

    void setParameter(int paramIndex, int value) throws SQLException;

    void registerOutputParameterInt(int paramIndex) throws SQLException;

    int getOutputParameterInt(int paramIndex) throws SQLException;

    void setParameter(int paramIndex, long value) throws SQLException;

    void registerOutputParameterLong(int paramIndex) throws SQLException;

    long getOutputParameterLong(int paramIndex) throws SQLException;

    ArrayList<HashMap<String, Object>> executeWithResults() throws SQLException;

    void execute() throws SQLException;
}
