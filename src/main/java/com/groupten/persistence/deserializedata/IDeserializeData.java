package com.groupten.persistence.deserializedata;

import com.groupten.leagueobjectmodel.league.League;

public interface IDeserializeData {

    League importData(String path);

}
