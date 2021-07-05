package com.groupten.leagueobjectmodel.leaguemodel;

import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.generalmanager.GeneralManager;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.ITeamRoster;
import com.groupten.leagueobjectmodel.team.TeamRoster;

public class LeagueModelFactory implements ILeagueModelFactory {

    @Override
    public League createLeague(String leagueName) {
        return new League(leagueName);
    }

    @Override
    public Conference createConference(String conferenceName) {
        return new Conference(conferenceName);
    }

    @Override
    public Division createDivision(String divisionName) {
        return new Division(divisionName);
    }

    @Override
    public GeneralManager createGeneralManager(String managerName, String personality) {
        return new GeneralManager(managerName, personality);
    }

    @Override
    public Season createSeason() {
        return new Season();
    }

    @Override
    public ITeamRoster createTeamRoster() {
        return new TeamRoster();
    }


}
