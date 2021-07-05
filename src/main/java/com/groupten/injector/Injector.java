package com.groupten.injector;

import com.groupten.IO.console.Console;
import com.groupten.IO.console.IConsole;
import com.groupten.leagueobjectmodel.coach.CoachBuilder;
import com.groupten.leagueobjectmodel.coach.ICoachBuilder;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModelFactory;
import com.groupten.leagueobjectmodel.leaguemodel.LeagueModel;
import com.groupten.leagueobjectmodel.leaguemodel.LeagueModelFactory;
import com.groupten.leagueobjectmodel.player.IPlayerBuilder;
import com.groupten.leagueobjectmodel.player.PlayerBuilder;
import com.groupten.leagueobjectmodel.team.ITeamBuilder;
import com.groupten.leagueobjectmodel.team.TeamBuilder;
import com.groupten.persistence.comparator.Comparator;
import com.groupten.persistence.comparator.IComparator;
import com.groupten.persistence.deserializedata.DeserializeData;
import com.groupten.persistence.deserializedata.IDeserializeData;
import com.groupten.persistence.m1DB.dao.*;
import com.groupten.persistence.serializedata.ISerializeData;
import com.groupten.persistence.serializedata.SerializeData;
import com.groupten.statemachine.createteam.CreateTeam;
import com.groupten.statemachine.createteam.ICreateTeam;
import com.groupten.statemachine.jsonimport.IJSONImport;
import com.groupten.statemachine.jsonimport.JSONImport;
import com.groupten.statemachine.loadteam.ILoadTeam;
import com.groupten.statemachine.loadteam.LoadTeam;
import com.groupten.statemachine.simulation.ISimulation;
import com.groupten.statemachine.simulation.Simulation;
import com.groupten.statemachine.simulation.advancetime.AdvanceTime;
import com.groupten.statemachine.simulation.advancetime.IAdvanceTime;
import com.groupten.statemachine.simulation.aging.Aging;
import com.groupten.statemachine.simulation.aging.IAging;
import com.groupten.statemachine.simulation.draft.Draft;
import com.groupten.statemachine.simulation.draft.IDraft;
import com.groupten.statemachine.simulation.draft.generateplayers.IPlayersGenerator;
import com.groupten.statemachine.simulation.draft.generateplayers.PlayersGenerator;
import com.groupten.statemachine.simulation.draft.strategy.*;
import com.groupten.statemachine.simulation.factories.ISimulationFactory;
import com.groupten.statemachine.simulation.factories.SimulationFactory;
import com.groupten.statemachine.simulation.generateplayoffschedule.GeneratePlayoffSchedule;
import com.groupten.statemachine.simulation.generateplayoffschedule.IGeneratePlayoffSchedule;
import com.groupten.statemachine.simulation.initializeseason.IInitializeSeason;
import com.groupten.statemachine.simulation.initializeseason.InitializeSeason;
import com.groupten.statemachine.simulation.simulategame.ISimulateGame;
import com.groupten.statemachine.simulation.simulategame.SimulateGame;
import com.groupten.statemachine.simulation.statsdecay.IStatsDecay;
import com.groupten.statemachine.simulation.statsdecay.StatsDecay;
import com.groupten.statemachine.simulation.trading.ITradeFactory;
import com.groupten.statemachine.simulation.trading.TradeFactory;
import com.groupten.statemachine.simulation.training.ITraining;
import com.groupten.statemachine.simulation.training.Training;
import com.groupten.statemachine.simulation.trophy.ITrophy;
import com.groupten.statemachine.simulation.trophy.Trophy;

public class Injector {

    private static Injector instance = null;
    private IConsole consoleInterface;

    private ILeagueModelFactory leagueModelFactory;
    private IJSONImport jsonInterface;
    private ICreateTeam createTeamInterface;
    private ILoadTeam loadTeamInterface;
    private ISimulation simulationInterface;
    private IInitializeSeason initializeSeasonInterface;
    private IAdvanceTime advanceTimeInterface;
    private IGeneratePlayoffSchedule generatePlayoffScheduleInterface;
    private ITraining trainingInterface;
    private IAging agingInterface;
    private ISimulateGame simulateGameInterface;
    private ISerializeData serializeDataInterface;
    private IDeserializeData deserializeDataInterface;
    private ITradeFactory tradingInterface;
    private IComparator comparatorInterface;
    private ILeagueDAO leagueDatabaseInterface;
    private IConferenceDAO conferenceDatabaseInterface;
    private IDivisionDAO divisionDatabaseInterface;
    private ITeamDAO teamDatabaseInterface;
    private IPlayerDAO playerDatabaseInterface;
    private ITrophy trophyInterface;
    private IDraft draftInterface;
    private IPlayersGenerator playersGeneratorInterface;
    private IDraftContext draftContextInterface;
    private IDraftStrategy weakTeamPicksFirstStrategy;
    private IDraftStrategy tradedTeamsStrategy;
    private ISimulationFactory simulationFactory;
    private IStatsDecay statsDecay;

    private ILeagueModel leagueModel;
    private ITeamBuilder teamBuilder;
    private ICoachBuilder coachBuilder;
    private IPlayerBuilder playerBuilder;

    private Injector() {
        consoleInterface = new Console();
        leagueModelFactory = new LeagueModelFactory();
        jsonInterface = new JSONImport();
        createTeamInterface = new CreateTeam();
        loadTeamInterface = new LoadTeam();
        simulationInterface = new Simulation();
        initializeSeasonInterface = new InitializeSeason();
        advanceTimeInterface = new AdvanceTime();
        generatePlayoffScheduleInterface = new GeneratePlayoffSchedule();
        trainingInterface = new Training();
        agingInterface = new Aging();
        simulateGameInterface = new SimulateGame();
        comparatorInterface = new Comparator();
        serializeDataInterface = new SerializeData();
        deserializeDataInterface = new DeserializeData();
        tradingInterface = new TradeFactory();
        trophyInterface = new Trophy();
        draftInterface = new Draft();
        playersGeneratorInterface = new PlayersGenerator();
        draftContextInterface = new DraftContext();
        weakTeamPicksFirstStrategy = new WeakTeamPicksFirstStrategy();
        tradedTeamsStrategy = new TradedTeamsStrategy();
        simulationFactory = new SimulationFactory();
        leagueDatabaseInterface = new LeagueDAO();
        conferenceDatabaseInterface = new ConferenceDAO();
        divisionDatabaseInterface = new DivisionDAO();
        teamDatabaseInterface = new TeamDAO();
        playerDatabaseInterface = new PlayerDAO();
        leagueModel = new LeagueModel();
        teamBuilder = new TeamBuilder();
        coachBuilder = new CoachBuilder();
        playerBuilder = new PlayerBuilder();
        statsDecay = new StatsDecay();
    }

    public static Injector instance() {
        if (instance == null) {
            instance = new Injector();
        }

        return instance;
    }

    public static void setInstance(Injector instance) {
        Injector.instance = instance;
    }

    public IConsole getConsoleObject() {
        return consoleInterface;
    }

    public ITrophy getTrophyObject() {
        return trophyInterface;
    }

    public void setTrophyObject(ITrophy trophyInterface) {
        this.trophyInterface = trophyInterface;
    }

    public IComparator getComparatorObject() {
        return comparatorInterface;
    }

    public ISerializeData getSerializeDataObject() {
        return serializeDataInterface;
    }

    public IDeserializeData getDeserializeDataObject() {
        return deserializeDataInterface;
    }

    public IJSONImport getJSONObject() {
        return jsonInterface;
    }

    public ICreateTeam getCreateTeamObject() {
        return createTeamInterface;
    }

    public ILoadTeam getLoadTeamObject() {
        return loadTeamInterface;
    }

    public ISimulation getSimulationObject() {
        return simulationInterface;
    }

    public IInitializeSeason getInitializeSeasonsObject() {
        return initializeSeasonInterface;
    }

    public IAdvanceTime getAdvanceTimeObject() {
        return advanceTimeInterface;
    }

    public IGeneratePlayoffSchedule getGeneratePlayoffScheduleeObject() {
        return generatePlayoffScheduleInterface;
    }

    public ITraining getTrainingObject() {
        return trainingInterface;
    }

    public IAging getAgingObject() {
        return agingInterface;
    }

    public ISimulateGame getSimulateGameObject() {
        return simulateGameInterface;
    }

    public ITradeFactory getTradingObject() {
        return tradingInterface;
    }

    public ILeagueDAO getLeagueDatabaseObject() {
        return leagueDatabaseInterface;
    }

    public IConferenceDAO getConferenceDatabaseObject() {
        return conferenceDatabaseInterface;
    }

    public IDivisionDAO getDivisionDatabaseObject() {
        return divisionDatabaseInterface;
    }

    public ITeamDAO getTeamDatabaseObject() {
        return teamDatabaseInterface;
    }

    public IPlayerDAO getPlayerDatabaseObject() {
        return playerDatabaseInterface;
    }

    public ILeagueModel getLeagueModelObject() {
        return leagueModel;
    }

    public void setLeagueModelObject(ILeagueModel leagueModel) {
        this.leagueModel = leagueModel;
    }

    public ILeagueModelFactory getLeagueModelFactory() {
        return leagueModelFactory;
    }

    public ITeamBuilder getTeamBuilder() {
        return teamBuilder;
    }

    public ICoachBuilder getCoachBuilder() {
        return coachBuilder;
    }

    public IPlayerBuilder getPlayerBuilder() {
        return playerBuilder;
    }

    public IDraft getDraftInterface() {
        return draftInterface;
    }

    public IPlayersGenerator getPlayersGeneratorInterface() {
        return playersGeneratorInterface;
    }

    public IDraftContext getDraftContextInterface() {
        return draftContextInterface;
    }

    public IDraftStrategy getWeakTeamPicksFirstStrategy() {
        return weakTeamPicksFirstStrategy;
    }

    public ISimulationFactory getSimulationFactory() {
        return simulationFactory;
    }

    public IDraftStrategy getTradedTeamsStrategy() {
        return tradedTeamsStrategy;
    }

    public IStatsDecay getStatsDecay() {
        return statsDecay;
    }
}
