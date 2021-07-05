package com.groupten.statemachine.createteam;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.generalmanager.GeneralManager;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CreateTeamTest {

    @Test
    public void validateUserInputTest() {
        CreateTeam createTeam = new CreateTeam();
        createTeam.setConferenceName("XYZ");
        createTeam.setDivisionName("ABC");
        createTeam.setTeamName("LMO");
        assertTrue(createTeam.validateUserInput());
    }

    @Test
    public void ifConferenceAndDivisionExistTest() {

        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League league = new League("Deep Test League");
        Conference conference = new Conference("Deep Test Conference");
        Division division = new Division("Deep Division Division");
        conference.addDivision(division);
        league.addConference(conference);
        leagueModel.setCurrentLeague(league);

        CreateTeam createTeam = new CreateTeam();
        createTeam.setConferenceName("Deep Test Conference");
        createTeam.setDivisionName("Deep Division Division");

        assertTrue(createTeam.ifConferenceAndDivisionExist());

    }

    @Test
    public void instantiateNewTeamTest() {
        List<Player> listOfPlayer = new ArrayList<>();
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League league = new League("Deep Test League");
        Conference conference = new Conference("Deep Test Conference");
        Division division = new Division("Deep Division Division");
        conference.addDivision(division);
        league.addConference(conference);
        leagueModel.setCurrentLeague(league);

        CreateTeam createTeam = new CreateTeam();
        createTeam.setConferenceName("Deep Test Conference");
        createTeam.setDivisionName("Deep Division Division");
        createTeam.setTeamName("Deep Test 3");
        createTeam.setGeneralManager(new GeneralManager("First General Manager", "Normal"));
        createTeam.setHeadCoach(new Coach("First Coach", 0.5, 0.5, 0.5, 0.5));

        double skating = 2.0;
        double shooting = 2.0;
        double checking = 5.0;
        double saving = 2.0;

        for (int i = 0; i < 30; i++) {
            String playerName = "Forward Player " + i;
            Player player = new Player(playerName, "forward", false, 23, 11, 2000,
                    skating, shooting, checking, saving);
            listOfPlayer.add(player);

            skating++;
            shooting++;
            checking++;
            saving++;
        }

        createTeam.setFreeAgents(listOfPlayer);

        createTeam.ifConferenceAndDivisionExist();

        assertTrue(createTeam.instantiateNewTeam());
    }
}
