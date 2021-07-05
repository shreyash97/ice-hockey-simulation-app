package com.groupten.statemachine.simulation.draft.generateplayers;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.IPlayerBuilder;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.player.PlayerPosition;
import com.groupten.statemachine.simulation.draft.DraftConstants;
import com.groupten.statemachine.simulation.draft.generateplayers.constants.DefensePlayerStatsRanges;
import com.groupten.statemachine.simulation.draft.generateplayers.constants.ForwardPlayerStatsRanges;
import com.groupten.statemachine.simulation.draft.generateplayers.constants.GoaliePlayerStatsRanges;

import java.util.*;

public class PlayersGenerator implements IPlayersGenerator {
    private final String[] firstNamesToPickFrom = {"Raghav", "Wen", "Prashant", "Fred", "Tina", "Walter", "Mozhgan", "Karan", "Sammy", "Don", "Nancy", "Shakuntala", "Mary", "Abraham", "Pat"};
    private final String[] lastNamesToPickFrom = {"Patel", "Rogers", "Plant", "Williamson", "Nelson", "Wu", "Morrison", "Hawkey", "Marley", "Chang", "Singh", "Hendrix", "Burbchuk", "Fei", "Greenbaum"};
    private double minimumAge = PlayerAgeRange.MINIMUM_AGE.getDoubleValue();
    private double maximumAge = PlayerAgeRange.MAXIMUM_AGE.getDoubleValue();
    private double minimumSkatingStat;
    private double maximumSkatingStat;
    private double minimumShootingStat;
    private double maximumShootingStat;
    private double minimumCheckingStat;
    private double maximumCheckingStat;
    private double minimumSavingStat;
    private double maximumSavingStat;
    private Map<String, List<Player>> playersGenerated = new HashMap<>();

    @Override
    public Map<String, List<Player>> generatePlayers() {
        List<Player> forwardPlayers = buildPlayersForPosition(PlayerPosition.FORWARD.name());
        List<Player> defensePlayers = buildPlayersForPosition(PlayerPosition.DEFENSE.name());
        List<Player> goaliePlayers = buildPlayersForPosition(PlayerPosition.GOALIE.name());

        playersGenerated.put(PlayerPosition.FORWARD.name(), forwardPlayers);
        playersGenerated.put(PlayerPosition.DEFENSE.name(), defensePlayers);
        playersGenerated.put(PlayerPosition.GOALIE.name(), goaliePlayers);

        return playersGenerated;
    }

    private List<Player> buildPlayersForPosition(String position) {
        List<Player> players = new ArrayList<>();
        IPlayerBuilder playerBuilder = Injector.instance().getPlayerBuilder();

        int numberOfPlayers = calculateNumberOfPlayersToGenerate(position);

        if (position.equals(PlayerPosition.FORWARD.name())) {
            initializePlayerStatsRangesForForwardPosition();
        } else if (position.equals(PlayerPosition.DEFENSE.name())) {
            initializePlayerStatsRangesForDefensePosition();
        } else if (position.equals(PlayerPosition.GOALIE.name())) {
            initializePlayerStatsRangesForGoaliePosition();
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            playerBuilder.reset();
            String playerName = generatePlayerNameRandomly();
            playerBuilder.setProfile(playerName, position);

            double playerAge = generateRandomValueBetweenInterval(minimumAge, maximumAge);
            playerBuilder.setAge(playerAge);
            playerBuilder.setDraftYear();

            double skatingStat = generateRandomValueBetweenInterval(minimumSkatingStat, maximumSkatingStat);
            double shootingStat = generateRandomValueBetweenInterval(minimumShootingStat, maximumShootingStat);
            double checkingStat = generateRandomValueBetweenInterval(minimumCheckingStat, maximumCheckingStat);
            double savingStat = generateRandomValueBetweenInterval(minimumSavingStat, maximumSavingStat);
            playerBuilder.setPlayerStats(skatingStat, shootingStat, checkingStat, savingStat);

            players.add(playerBuilder.getResult());
        }

        return players;
    }

    private int calculateNumberOfPlayersToGenerate(String position) {
        int draftRounds = 0;
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();

        if (position.equals(PlayerPosition.FORWARD.name())) {
            draftRounds = DraftConstants.NUMBER_OF_FORWARD_DRAFT_ROUNDS.getIntValue();
        } else if (position.equals(PlayerPosition.DEFENSE.name())) {
            draftRounds = DraftConstants.NUMBER_OF_DEFENSE_DRAFT_ROUNDS.getIntValue();
        } else if (position.equals(PlayerPosition.GOALIE.name())) {
            draftRounds = DraftConstants.NUMBER_OF_GOALIE_DRAFT_ROUNDS.getIntValue();
        }

        return draftRounds * leagueModel.getTotalNumberOfTeams();
    }

    private double generateRandomValueBetweenInterval(double intervalStart, double intervalEnd) {
        double random = new Random().nextDouble();
        return intervalStart + (random * (intervalEnd - intervalStart));
    }

    private String generatePlayerNameRandomly() {
        int firstNameIndex = new Random().nextInt(firstNamesToPickFrom.length);
        int lastNameIndex = new Random().nextInt(lastNamesToPickFrom.length);
        return firstNamesToPickFrom[firstNameIndex] + " " + lastNamesToPickFrom[lastNameIndex];
    }

    private void initializePlayerStatsRangesForForwardPosition() {
        minimumSkatingStat = ForwardPlayerStatsRanges.MINIMUM_SKATING_STAT.getDoubleValue();
        maximumSkatingStat = ForwardPlayerStatsRanges.MAXIMUM_SKATING_STAT.getDoubleValue();
        minimumShootingStat = ForwardPlayerStatsRanges.MINIMUM_SHOOTING_STAT.getDoubleValue();
        maximumShootingStat = ForwardPlayerStatsRanges.MAXIMUM_SHOOTING_STAT.getDoubleValue();
        minimumCheckingStat = ForwardPlayerStatsRanges.MINIMUM_CHECKING_STAT.getDoubleValue();
        maximumCheckingStat = ForwardPlayerStatsRanges.MAXIMUM_CHECKING_STAT.getDoubleValue();
        minimumSavingStat = ForwardPlayerStatsRanges.MINIMUM_SAVING_STAT.getDoubleValue();
        maximumSavingStat = ForwardPlayerStatsRanges.MAXIMUM_SAVING_STAT.getDoubleValue();
    }

    private void initializePlayerStatsRangesForDefensePosition() {
        minimumSkatingStat = DefensePlayerStatsRanges.MINIMUM_SKATING_STAT.getDoubleValue();
        maximumSkatingStat = DefensePlayerStatsRanges.MAXIMUM_SKATING_STAT.getDoubleValue();
        minimumShootingStat = DefensePlayerStatsRanges.MINIMUM_SHOOTING_STAT.getDoubleValue();
        maximumShootingStat = DefensePlayerStatsRanges.MAXIMUM_SHOOTING_STAT.getDoubleValue();
        minimumCheckingStat = DefensePlayerStatsRanges.MINIMUM_CHECKING_STAT.getDoubleValue();
        maximumCheckingStat = DefensePlayerStatsRanges.MAXIMUM_CHECKING_STAT.getDoubleValue();
        minimumSavingStat = DefensePlayerStatsRanges.MINIMUM_SAVING_STAT.getDoubleValue();
        maximumSavingStat = DefensePlayerStatsRanges.MAXIMUM_SAVING_STAT.getDoubleValue();
    }

    private void initializePlayerStatsRangesForGoaliePosition() {
        minimumSkatingStat = GoaliePlayerStatsRanges.MINIMUM_SKATING_STAT.getDoubleValue();
        maximumSkatingStat = GoaliePlayerStatsRanges.MAXIMUM_SKATING_STAT.getDoubleValue();
        minimumShootingStat = GoaliePlayerStatsRanges.MINIMUM_SHOOTING_STAT.getDoubleValue();
        maximumShootingStat = GoaliePlayerStatsRanges.MAXIMUM_SHOOTING_STAT.getDoubleValue();
        minimumCheckingStat = GoaliePlayerStatsRanges.MINIMUM_CHECKING_STAT.getDoubleValue();
        maximumCheckingStat = GoaliePlayerStatsRanges.MAXIMUM_CHECKING_STAT.getDoubleValue();
        minimumSavingStat = GoaliePlayerStatsRanges.MINIMUM_SAVING_STAT.getDoubleValue();
        maximumSavingStat = GoaliePlayerStatsRanges.MAXIMUM_SAVING_STAT.getDoubleValue();
    }

}
