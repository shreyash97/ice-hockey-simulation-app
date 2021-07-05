package com.groupten.statemachine.jsonimport;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.coach.ICoachBuilder;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.generalmanager.GeneralManager;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModelFactory;
import com.groupten.leagueobjectmodel.player.IPlayerBuilder;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.ITeamBuilder;
import com.groupten.leagueobjectmodel.team.Team;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JSONImport implements IJSONImport {

    private JsonObject jsonData;

    public JSONImport() {
    }

    @Override
    public boolean importJSONData(String path) {
        JsonParser parser = new JsonParser();

        try {
            jsonData = (JsonObject) parser.parse(new FileReader(path));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean instantiateJSONData() {

        ILeagueModelFactory leagueModelFactory = Injector.instance().getLeagueModelFactory();
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        ICoachBuilder coachBuilder = Injector.instance().getCoachBuilder();
        ITeamBuilder teamBuilder = Injector.instance().getTeamBuilder();
        IPlayerBuilder playerBuilder = Injector.instance().getPlayerBuilder();
        League leagueLOM;
        Conference conferenceLOM;
        Division divisionLOM;
        GeneralManager managerLOM;
        Coach coachLOM;
        Player playerLOM;
        Team teamLOM;
        GameConfig.Aging agingLOM;
        GameConfig.GameResolver gameResolverLOM;
        GameConfig.Injuries injuriesLOM;
        GameConfig.Training trainingLOM;
        GameConfig.Trading tradingLOM;

        JsonObject conference, division, team, headCoach, coach, teamPlayer, freeAgent, generalManager;
        JsonObject gamePlayConfig, aging, gameResolver, injuries, training, trading, gmTable;
        JsonArray conferences, divisions, teams, players;

        boolean success = false;

        gamePlayConfig = (JsonObject) jsonData.get("gameplayConfig");
        aging = (JsonObject) gamePlayConfig.get("aging");
        gameResolver = (JsonObject) gamePlayConfig.get("gameResolver");
        injuries = (JsonObject) gamePlayConfig.get("injuries");
        training = (JsonObject) gamePlayConfig.get("training");
        trading = (JsonObject) gamePlayConfig.get("trading");
        gmTable = (JsonObject) trading.get("gmTable");

        int averageRetirementAge = aging.get("averageRetirementAge").getAsInt();
        int maximumAge = aging.get("maximumAge").getAsInt();
        double statDecayChance = aging.get("statDecayChance").getAsDouble();

        double randomWinChance = gameResolver.get("randomWinChance").getAsDouble();

        double randomInjuryChance = injuries.get("randomInjuryChance").getAsDouble();
        int injuryDaysLow = injuries.get("injuryDaysLow").getAsInt();
        int injuryDaysHigh = injuries.get("injuryDaysHigh").getAsInt();

        int daysUntilStatIncreaseCheck = training.get("daysUntilStatIncreaseCheck").getAsInt();

        int lossPoint = trading.get("lossPoint").getAsInt();
        double randomTradeOfferChance = trading.get("randomTradeOfferChance").getAsDouble();
        int maxPlayersPerTrade = trading.get("maxPlayersPerTrade").getAsInt();
        double randomAcceptanceChance = trading.get("randomAcceptanceChance").getAsDouble();
        double shrewd = gmTable.get("shrewd").getAsDouble();
        double normal = gmTable.get("normal").getAsDouble();
        double gambler = gmTable.get("gambler").getAsDouble();

        String leagueName = jsonData.get("leagueName").getAsString();
        conferences = (JsonArray) jsonData.get("conferences");

        leagueLOM = leagueModelFactory.createLeague(leagueName);

        agingLOM = new GameConfig.Aging(averageRetirementAge, maximumAge, statDecayChance);
        gameResolverLOM = new GameConfig.GameResolver(randomWinChance);
        injuriesLOM = new GameConfig.Injuries(randomInjuryChance, injuryDaysLow, injuryDaysHigh);
        trainingLOM = new GameConfig.Training(daysUntilStatIncreaseCheck);
        tradingLOM = new GameConfig.Trading(lossPoint, randomTradeOfferChance, maxPlayersPerTrade,
                randomAcceptanceChance, shrewd, normal, gambler);

        leagueLOM.setAgingConfig(agingLOM);
        leagueLOM.setGameResolverConfig(gameResolverLOM);
        leagueLOM.setInjuriesConfig(injuriesLOM);
        leagueLOM.setTrainingConfig(trainingLOM);
        leagueLOM.setTradingConfig(tradingLOM);

        try {
            for (int i = 0; i < conferences.size(); i++) {
                conference = (JsonObject) conferences.get(i);
                divisions = (JsonArray) conference.get("divisions");
                String conferenceName = conference.get("conferenceName").getAsString();

                conferenceLOM = leagueModelFactory.createConference(conferenceName);

                for (int j = 0; j < divisions.size(); j++) {
                    division = (JsonObject) divisions.get(j);
                    teams = (JsonArray) division.get("teams");
                    String divisionName = division.get("divisionName").getAsString();

                    divisionLOM = leagueModelFactory.createDivision(divisionName);

                    for (int k = 0; k < teams.size(); k++) {
                        List<Player> allPlayers = new ArrayList<>();

                        team = (JsonObject) teams.get(k);
                        String teamName = team.get("teamName").getAsString();

                        generalManager = (JsonObject) team.get("generalManager");
                        String gmName = generalManager.get("name").getAsString();
                        String gmPersonality = generalManager.get("personality").getAsString();

                        headCoach = (JsonObject) team.get("headCoach");
                        String coachName = headCoach.get("name").getAsString();
                        double coachSkating = headCoach.get("skating").getAsDouble();
                        double coachShooting = headCoach.get("shooting").getAsDouble();
                        double coachChecking = headCoach.get("checking").getAsDouble();
                        double coachSaving = headCoach.get("saving").getAsDouble();
                        players = (JsonArray) team.get("players");

                        for (int l = 0; l < players.size(); l++) {
                            teamPlayer = (JsonObject) players.get(l);
                            String playerName = teamPlayer.get("playerName").getAsString();
                            String position = teamPlayer.get("position").getAsString();
                            boolean captain = teamPlayer.get("captain").getAsBoolean();
                            int playerBirthDay = teamPlayer.get("birthDay").getAsInt();
                            int playerBirthMonth = teamPlayer.get("birthMonth").getAsInt();
                            int playerBirthYear = teamPlayer.get("birthYear").getAsInt();
                            double playerSkating = teamPlayer.get("skating").getAsDouble();
                            double playerShooting = teamPlayer.get("shooting").getAsDouble();
                            double playerChecking = teamPlayer.get("checking").getAsDouble();
                            double playerSaving = teamPlayer.get("saving").getAsDouble();

                            if (Player.arePlayerFieldsValid(playerName, position, playerSkating, playerShooting, playerChecking, playerSaving)) {
                                success = true;
                            } else {
                                throw new Exception("Issue with JSON Data");
                            }

                            playerBuilder.reset();
                            playerBuilder.setProfile(playerName, position);
                            playerBuilder.setAsCaptain(captain);
                            playerBuilder.setAgeFromBirthDay(playerBirthDay, playerBirthMonth, playerBirthYear);
                            playerBuilder.setDraftYear();
                            playerBuilder.setPlayerStats(playerSkating, playerShooting, playerChecking, playerSaving);
                            playerLOM = playerBuilder.getResult();

                            allPlayers.add(playerLOM);
                        }

                        teamBuilder.reset();
                        teamBuilder.setTeamName(teamName);
                        teamBuilder.setPlayerRosters(allPlayers);
                        teamLOM = teamBuilder.getResult();

                        managerLOM = leagueModelFactory.createGeneralManager(gmName, gmPersonality);
                        coachBuilder.reset();
                        coachBuilder.setName(coachName);
                        coachBuilder.setCoachStats(coachSkating, coachShooting, coachChecking, coachSaving);
                        coachLOM = coachBuilder.getResult();

                        if (teamLOM.setGeneralManager(managerLOM)) {
                            success = true;
                        } else {
                            throw new Exception("Issue with JSON Data");
                        }

                        if (teamLOM.setHeadCoach(coachLOM)) {
                            success = true;
                        } else {
                            throw new Exception("Issue with JSON Data");
                        }

                        if (divisionLOM.addTeam(teamLOM)) {
                            success = true;
                        } else {
                            throw new Exception("Issue with JSON Data");
                        }
                    }
                    if (conferenceLOM.addDivision(divisionLOM)) {
                        success = true;
                    } else {
                        throw new Exception("Issue with JSON Data");
                    }
                }
                if (leagueLOM.addConference(conferenceLOM)) {
                    success = true;
                } else {
                    throw new Exception("Issue with JSON Data");
                }

            }

            JsonArray freeAgents = (JsonArray) jsonData.get("freeAgents");
            for (int i = 0; i < freeAgents.size(); i++) {
                freeAgent = (JsonObject) freeAgents.get(i);
                String playerName = freeAgent.get("playerName").getAsString();
                String position = freeAgent.get("position").getAsString();
                int playerBirthDay = freeAgent.get("birthDay").getAsInt();
                int playerBirthMonth = freeAgent.get("birthMonth").getAsInt();
                int playerBirthYear = freeAgent.get("birthYear").getAsInt();
                double playerSkating = freeAgent.get("skating").getAsDouble();
                double playerShooting = freeAgent.get("shooting").getAsDouble();
                double playerChecking = freeAgent.get("checking").getAsDouble();
                double playerSaving = freeAgent.get("saving").getAsDouble();

                playerBuilder.reset();
                playerBuilder.setProfile(playerName, position);
                playerBuilder.setAgeFromBirthDay(playerBirthDay, playerBirthMonth, playerBirthYear);
                playerBuilder.setPlayerStats(playerSkating, playerShooting, playerChecking, playerSaving);
                playerLOM = playerBuilder.getResult();

                if (leagueLOM.addFreeAgent(playerLOM)) {
                    success = true;
                } else {
                    throw new Exception("Issue with JSON Data");
                }
            }

            JsonArray coaches = (JsonArray) jsonData.get("coaches");

            for (int i = 0; i < coaches.size(); i++) {
                coach = (JsonObject) coaches.get(i);
                String coachName = coach.get("name").getAsString();
                double coachSkating = coach.get("skating").getAsDouble();
                double coachShooting = coach.get("shooting").getAsDouble();
                double coachChecking = coach.get("checking").getAsDouble();
                double coachSaving = coach.get("saving").getAsDouble();

                coachLOM = new Coach(coachName, coachSkating, coachShooting, coachChecking, coachSaving);

                if (leagueLOM.addCoach(coachLOM)) {
                    success = true;
                } else {
                    throw new Exception("Issue with JSON Data");
                }
            }

            JsonArray generalManagers = (JsonArray) jsonData.get("generalManagers");

            for (int i = 0; i < generalManagers.size(); i++) {
                generalManager = (JsonObject) generalManagers.get(i);
                String gmName = generalManager.get("name").getAsString();
                String gmPersonality = generalManager.get("personality").getAsString();

                managerLOM = new GeneralManager(gmName, gmPersonality);

                if (leagueLOM.addGeneralManager(managerLOM)) {
                    success = true;
                } else {
                    throw new Exception("Issue with JSON Data");
                }
            }

            if (leagueModel.setCurrentLeague(leagueLOM)) {
                success = true;
            } else {
                throw new Exception("Issue with JSON Data");
            }
        } catch (Exception e) {
            success = false;
        }

        return success;
    }
}
