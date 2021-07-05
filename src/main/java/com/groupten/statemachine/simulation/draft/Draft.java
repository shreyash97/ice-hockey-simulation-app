package com.groupten.statemachine.simulation.draft;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.player.PlayerPosition;
import com.groupten.leagueobjectmodel.season.ISeason;
import com.groupten.leagueobjectmodel.season.Season;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.teamstanding.TeamStanding;
import com.groupten.statemachine.simulation.draft.generateplayers.IPlayersGenerator;
import com.groupten.statemachine.simulation.draft.strategy.IDraftContext;
import com.groupten.statemachine.simulation.draft.strategy.IDraftStrategy;
import com.groupten.statemachine.simulation.trading.ITradingSubscriber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Draft implements IDraft, ITradingSubscriber {
    private static final Logger logger = LogManager.getLogger(Draft.class.getName());
    private List<TeamStanding> combinedSortedTeamStandings = new ArrayList<>();
    private List<TeamStanding> regularSeasonStandings = new ArrayList<>();
    private List<TeamStanding> playoffStandings = new ArrayList<>();
    private List<List<Team>> tradePickTeams = new ArrayList<>();

    private List<Player> forwardPlayersForDraft = new ArrayList<>();
    private List<Player> defensePlayersForDraft = new ArrayList<>();
    private List<Player> goaliePlayersForDraft = new ArrayList<>();
    private IDraftContext draftContext;
    private IDraftStrategy draftStrategy;

    @Override
    public void execute(Season season) {
        initialize(season);

        draftContext = Injector.instance().getDraftContextInterface();
        draftPlayers(draftContext, PlayerPosition.FORWARD.name());
        draftPlayers(draftContext, PlayerPosition.DEFENSE.name());
        draftPlayers(draftContext, PlayerPosition.GOALIE.name());
    }

    private void initialize(ISeason season) {
        preprocessTeamStandings(season);
        generateDraftPlayers();
    }

    private void preprocessTeamStandings(ISeason season) {
        regularSeasonStandings = season.getTeamStandings();
        playoffStandings = season.getPlayoffTeamStandings();

        sortTeamStandingsInAscendingOrder();
        removePlayoffTeamsFromRegularTeamStandings();
        getCombinedTeamStandings();
    }

    private void generateDraftPlayers() {
        IPlayersGenerator playersGenerator = Injector.instance().getPlayersGeneratorInterface();
        Map<String, List<Player>> generatedPlayers = playersGenerator.generatePlayers();

        forwardPlayersForDraft = generatedPlayers.get(PlayerPosition.FORWARD.name());
        defensePlayersForDraft = generatedPlayers.get(PlayerPosition.DEFENSE.name());
        goaliePlayersForDraft = generatedPlayers.get(PlayerPosition.GOALIE.name());
    }

    private void sortTeamStandingsInAscendingOrder() {
        Collections.sort(regularSeasonStandings);
        Collections.sort(playoffStandings);
    }

    private void removePlayoffTeamsFromRegularTeamStandings() {
        for (TeamStanding teamStanding : playoffStandings) {
            regularSeasonStandings.remove(teamStanding);
        }
    }

    private void getCombinedTeamStandings() {
        regularSeasonStandings.addAll(playoffStandings);
        combinedSortedTeamStandings.addAll(regularSeasonStandings);
    }

    private void draftPlayers(IDraftContext draftContext, String position) {
        int currentDraftRound = 0;
        int draftCount = 0;
        List<Player> draftPlayersList = new ArrayList<>();

        if (position.equals(PlayerPosition.FORWARD.name())) {
            draftPlayersList = forwardPlayersForDraft;
            draftCount = DraftConstants.NUMBER_OF_FORWARD_DRAFT_ROUNDS.getIntValue();
        } else if (position.equals(PlayerPosition.DEFENSE.name())) {
            draftPlayersList = defensePlayersForDraft;
            draftCount = DraftConstants.NUMBER_OF_DEFENSE_DRAFT_ROUNDS.getIntValue();
        } else if (position.equals(PlayerPosition.GOALIE.name())) {
            draftPlayersList = goaliePlayersForDraft;
            draftCount = DraftConstants.NUMBER_OF_GOALIE_DRAFT_ROUNDS.getIntValue();
        }

        while (currentDraftRound < draftCount) {
            logger.info("Draft Round " + currentDraftRound + " for drafting players at position: " + position);

            if (tradePickTeams.size() == 0) {
                draftStrategy = Injector.instance().getWeakTeamPicksFirstStrategy();
                draftContext.setStrategy(draftStrategy);
            } else {
                draftStrategy = Injector.instance().getTradedTeamsStrategy();
                draftContext.setStrategy(draftStrategy);
            }

            draftContext.executeStrategy(combinedSortedTeamStandings, draftPlayersList, tradePickTeams);
            currentDraftRound += 1;
        }
    }

    @Override
    public void update(List<Team> teams) {
        tradePickTeams.add(teams);
    }
}
