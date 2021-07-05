package com.groupten.leagueobjectmodel.leaguemodel;

import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.generalmanager.GeneralManager;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.ITeamRoster;


public interface ILeagueModelFactory {
    League createLeague(String leagueName);

    Conference createConference(String conferenceName);

    Division createDivision(String divisionName);

    GeneralManager createGeneralManager(String managerName, String personality);

    Season createSeason();

    ITeamRoster createTeamRoster();
}
