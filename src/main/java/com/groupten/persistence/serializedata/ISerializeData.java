package com.groupten.persistence.serializedata;

import com.groupten.leagueobjectmodel.league.League;

public interface ISerializeData {

    boolean exportData(League leagueLOM, String path);

}
