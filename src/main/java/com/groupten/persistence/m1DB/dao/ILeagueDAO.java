package com.groupten.persistence.m1DB.dao;

import java.util.HashMap;
import java.util.List;

public interface ILeagueDAO {
    int createLeague(String leagueName, int averageRetirementAge, int maximumAge, double randomInjuryChance, int injuryDaysHigh,
                     int injuryDaysLow, int lossPoint, double randomTradeOfferChance, int maxPlayerPerTrade, double randomAcceptanceChance);

    List<HashMap<String, Object>> getLeagues(String colName, String colValue);

    void updateLeague(int leagueId, String leagueName);

    void deleteLeague(int leagueId);

    List<HashMap<String, Object>> getLeagueConferences(int leagueId);

    List<HashMap<String, Object>> getLeaguePlayers(int leagueId);

    List<HashMap<String, Object>> getLeagueFreeAgents(int leagueId);
}
