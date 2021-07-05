package com.groupten.persistence.comparator;

import com.groupten.leagueobjectmodel.league.League;

public interface IComparator {

    boolean compareLeagues(League exportedLeague, League importedLeague);

}
