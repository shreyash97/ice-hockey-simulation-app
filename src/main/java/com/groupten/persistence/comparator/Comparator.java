package com.groupten.persistence.comparator;

import com.groupten.leagueobjectmodel.league.League;

public class Comparator implements IComparator {

    public boolean compareLeagues(League exportedLeague, League importedLeague) {
        return exportedLeague.getLeagueName().equals(importedLeague.getLeagueName());
    }

}
