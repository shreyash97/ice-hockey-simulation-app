package com.groupten.persistence.m1DB.dao;

import java.util.HashMap;
import java.util.List;

public interface IPlayerDAO {
    int createPlayer(String playerName, String position, double age, double skating, double shooting, double checking, double saving);

    List<HashMap<String, Object>> getPlayers(String colName, String colValue);

    void updatePlayer(int playerId, String playerName, String position, boolean isCaptain);

    void deletePlayer(int playerId);

    List<HashMap<String, Object>> getCaptains(String colName, String colValue);

    List<HashMap<String, Object>> getFreeAgents(String colName, String colValue);
}
