package com.groupten.persistence.m1DB.dao;

import java.util.HashMap;
import java.util.List;

public interface IDivisionDAO {
    int createDivision(int conferenceId, String divisionName);

    List<HashMap<String, Object>> getDivisions(String colName, String colValue);

    void updateDivision(int divisionId, String divisionName);

    void deleteDivision(int divisionId);

    List<HashMap<String, Object>> getDivisionTeams(int divisionId);
}
