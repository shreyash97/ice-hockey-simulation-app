package com.groupten.statemachine.simulation.trading;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.IPlayerSubscriber;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.ITeamRoster;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.team.TeamRoster;
import com.groupten.statemachine.simulation.draft.Draft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Trading {

	public Trading() {
	}
	private static final Logger logger = LogManager.getLogger(Trading.class.getName());
	private IConsole console;
	private ILeagueModel leagueModel;
	private League leagueLOM;
	private Team tradeInitializingTeam = new Team();
	private Team tradeFinalizingTeam = new Team();
	private int maxPlayersPerTrade = 0;
	private int lossPoint = 0;
	private String generalManagerPersonality;
	private double generalManagerPersonalityValue = 0.0;
	private double randomTradeOfferChance = 0.0;
	private double randomAcceptanceChance = 0.0;
	private boolean trade = false;
	private double averageGoalieStrength = 0.0;
	private double averageDefenseStrength = 0.0;
	private double averageForwardStrength = 0.0;
	private final int teamSize = 20;
	private final int numberOfGoalies = 4;
	private final int numberOfForwards = 16;
	private final int numberOfDefenses = 10;
	private String weakSection = null;
	private boolean tradeOccured = false;
	private ArrayList<Player> initialTradingPlayers = new ArrayList<>();
	private ArrayList<Player> finalTradingPlayers = new ArrayList<>();
	private List<ITradingSubscriber> subscribers = new ArrayList<>();

	public Team getTradeInitializingTeam() {
		return tradeInitializingTeam;
	}

	public void setTradeInitializingTeam(Team tradeInitializingTeam) {
		this.tradeInitializingTeam = tradeInitializingTeam;
	}

	public Team getTradeFinalizingTeam() {
		return tradeFinalizingTeam;
	}

	public void setTradeFinalizingTeam(Team tradeFinalizingTeam) {
		this.tradeFinalizingTeam = tradeFinalizingTeam;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public int getNumberOfGoalies() {
		return numberOfGoalies;
	}

	public int getNumberOfForwards() {
		return numberOfForwards;
	}

	public int getNumberOfDefenses() {
		return numberOfDefenses;
	}

	public boolean isTrade() {
		return trade;
	}

	public void setTrade(boolean trade) {
		this.trade = trade;
	}

	public int getMaxPlayersPerTrade() {
		return maxPlayersPerTrade;
	}

	public void setMaxPlayersPerTrade(int maxPlayersPerTrade) {
		this.maxPlayersPerTrade = maxPlayersPerTrade;
	}

	public int getLossPoint() {
		return lossPoint;
	}

	public void setLossPoint(int lossPoint) {
		this.lossPoint = lossPoint;
	}

	public String getGeneralManagerPersonality() {
		return generalManagerPersonality;
	}

	public void setGeneralManagerPersonality(String generalManagerPersonality) {
		this.generalManagerPersonality = generalManagerPersonality;
	}

	public double getGeneralManagerPersonalityValue() {
		return generalManagerPersonalityValue;
	}

	public void setGeneralManagerPersonalityValue(double generalManagerPersonalityValue) {
		this.generalManagerPersonalityValue = generalManagerPersonalityValue;
	}

	public double getRandomTradeOfferChance() {
		return randomTradeOfferChance;
	}

	public void setRandomTradeOfferChance(double randomTradeOfferChance) {
		this.randomTradeOfferChance = randomTradeOfferChance;
	}

	public double getRandomAcceptanceChance() {
		return randomAcceptanceChance;
	}

	public void setRandomAcceptanceChance(double randomAcceptanceChance) {
		this.randomAcceptanceChance = randomAcceptanceChance;
	}

	public double getAverageGoalieStrength() {
		return averageGoalieStrength;
	}

	public void setAverageGoalieStrength(double averageGoalieStrength) {
		this.averageGoalieStrength = averageGoalieStrength;
	}

	public double getAverageDefenseStrength() {
		return averageDefenseStrength;
	}

	public void setAverageDefenseStrength(double averageDefenseStrength) {
		this.averageDefenseStrength = averageDefenseStrength;
	}

	public double getAverageForwardStrength() {
		return averageForwardStrength;
	}

	public void setAverageForwardStrength(double averageForwardStrength) {
		this.averageForwardStrength = averageForwardStrength;
	}

	public String getWeakSection() {
		return weakSection;
	}

	public void setWeakSection(String weakSection) {
		this.weakSection = weakSection;
	}

	public ArrayList<Player> getInitialTradingPlayers() {
		return initialTradingPlayers;
	}

	public void setInitialTradingPlayers(ArrayList<Player> initialTradingPlayers) {
		this.initialTradingPlayers = initialTradingPlayers;
	}

	public ArrayList<Player> getFinalTradingPlayers() {
		return finalTradingPlayers;
	}

	public void setFinalTradingPlayers(ArrayList<Player> finalTradingPlayers) {
		this.finalTradingPlayers = finalTradingPlayers;
	}
	public boolean isTradeOccured() {
		return tradeOccured;
	}

	public void setTradeOccured(boolean tradeOccured) {
		this.tradeOccured = tradeOccured;
	}

	public void startTrading() {
		getAveragePlayerStrength();
		getInitialTeam();
		calculateWeakSection();
		getFinalTeam();
		getInitialAndFinalTradingPlayers(weakSection);
		generateTradeOffers();
		adjustingTeamPlayers();
	}
	public void attach(ITradingSubscriber subscriber) {
		subscribers.add(subscriber);
	}

	public void detach(ITradingSubscriber subscriber) {
		subscribers.remove(subscriber);
	}

	private void notifySubscribers() {
		for (ITradingSubscriber subscriber : subscribers) {
			List<Team> teams = new ArrayList<>();
			teams.add(tradeInitializingTeam);
			teams.add(tradeFinalizingTeam);
			subscriber.update(teams);
		}
	}
	public void getInitialTeam() {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();

		for (Conference c : leagueLOM.getConferences().values()) {
			for (Division d : c.getDivisions().values()) {
				for (Team initializingTeam : d.getTeams().values()) {
					if (initializingTeam.isaITeam()) {
						if (initializingTeam.getLossPoint() >= lossPoint) {
							tradeInitializingTeam = initializingTeam;
						}
					}
				}
			}
		}
	}

	public void calculateWeakSection() {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		int numberOfGoalies = 0;
		int numberOfForwards = 0;
		int numberOfDefenses = 0;
		double goalieStrength = 0.0;
		double forwardStrength = 0.0;
		double defenseStrength = 0.0;
		double avgGoalieStrength = 0.0;
		double avgForwardStrength = 0.0;
		double avgDefenseStrength = 0.0;
		double goalieStrengthDifference = 0.0;
		double forwardStrengthDifference = 0.0;
		double defenseStrengthDifference = 0.0;
		ArrayList<Player> totalPlayers = new ArrayList<>();

		for(Player player : tradeInitializingTeam.getActivePlayers()) {
			totalPlayers.add(player);
		}
		for(Player player : tradeInitializingTeam.getInActivePlayers()) {
			totalPlayers.add(player);
		}
		for(Player player : totalPlayers) {
			if(player.getPosition().equals("goalie")) {
				goalieStrength = goalieStrength + player.calculateStrength();
				numberOfGoalies = numberOfGoalies + 1;
			}
			else if(player.getPosition().equals("forward")) {
				forwardStrength = forwardStrength + player.calculateStrength();
				numberOfForwards = numberOfForwards + 1;
			}
			else if(player.getPosition().equals("defense")) {
				defenseStrength = defenseStrength + player.calculateStrength();
				numberOfDefenses = numberOfDefenses + 1;
			}
		}
		avgGoalieStrength = goalieStrength / numberOfGoalies;
		goalieStrengthDifference = avgGoalieStrength - averageGoalieStrength;
		avgForwardStrength = forwardStrength / numberOfForwards;
		forwardStrengthDifference = avgForwardStrength - averageForwardStrength;
		avgDefenseStrength = defenseStrength / numberOfDefenses;
		defenseStrengthDifference = avgDefenseStrength - averageDefenseStrength;
		if(goalieStrengthDifference < forwardStrengthDifference && goalieStrengthDifference < defenseStrengthDifference) {
			weakSection = "goalie";
		}
		else if(forwardStrengthDifference < goalieStrengthDifference && forwardStrengthDifference < defenseStrengthDifference) {
			weakSection = "forward";
		}
		else {
			weakSection = "defense";
		}
	}

	public void getFinalTeam() {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		int numberOfPlayers = 0;
		double playerStrength = 0.0;
		double averageStrength = 0.0;
		double prevAverageStrength = 0.0;

		for (Conference c : leagueLOM.getConferences().values()) {
			for (Division d : c.getDivisions().values()) {
				for (Team finalizingTeam : d.getTeams().values()) {
					if (finalizingTeam == tradeInitializingTeam) {
						return;
					} else {
						for(Player player : finalizingTeam.getActivePlayers()) {
							if(player.getPosition().equals(weakSection)) {
								playerStrength = playerStrength + player.calculateStrength();
								numberOfPlayers = numberOfPlayers + 1;
							}
						}
						for(Player player : finalizingTeam.getInActivePlayers()) {
							if(player.getPosition().equals(weakSection)) {
								playerStrength = playerStrength + player.calculateStrength();
								numberOfPlayers = numberOfPlayers + 1;
							}
						}
						averageStrength = playerStrength / numberOfPlayers;
						if(weakSection.equals("goalie")) {
							if(averageStrength > averageGoalieStrength && averageStrength > prevAverageStrength) {
								prevAverageStrength = averageStrength;
								tradeFinalizingTeam = finalizingTeam;
							}
						}
						else if(weakSection.equals("forward")) {
							if(averageStrength > averageForwardStrength && averageStrength > prevAverageStrength) {
								prevAverageStrength = averageStrength;
								tradeFinalizingTeam = finalizingTeam;
							}
						}
						else if(weakSection.equals("defense")) {
							if(averageStrength > averageDefenseStrength && averageStrength > prevAverageStrength) {
								prevAverageStrength = averageStrength;
								tradeFinalizingTeam = finalizingTeam;
							}
						}
					}
					averageStrength = 0.0;
					playerStrength = 0.0;
					numberOfPlayers = 0;
				}
			}
		}
	}

	public void generateTradeOffers() {
		ITradeFactory tradeFactory = Injector.instance().getTradingObject();
		PlayerTradeOffers playerTradeOffers = tradeFactory.createPlayerTradeOffers();
		PlayersTradeOffers playersTradeOffers = tradeFactory.createPlayersTradeOffers();
		DraftPickTradeOffers draftPickTradeOffers = tradeFactory.createDraftPickTradeOffers();
		HashMap<Integer,Player> draftPickTradeOffer = new HashMap<>();
		HashMap<HashMap<Player,Player>,Double> playerTradeOffer = new HashMap<>();
		HashMap<HashMap<ArrayList<Player>,Player>,Double> playersTradeOffer = new HashMap<>();
		HashMap<Player,Player> playerTradingPlayers = new HashMap<>();
		HashMap<ArrayList<Player>,Player> playersTradingPlayers = new HashMap<>();
		Double averagePlayersStrength = 0.0;
		Double playerTradeOfferStrength = 0.0;
		Double playersTradeOfferStrength = 0.0;

		if(weakSection.equals("goalie")) {
			for(Player player : tradeInitializingTeam.getActivePlayers()) {
				if(player.getPosition().equals("goalie")) {
					averagePlayersStrength = player.calculateStrength();
				}
			}
			if(averagePlayersStrength < averageGoalieStrength - (averageGoalieStrength * 0.7)) {
				draftPickTradeOffer = draftPickTradeOffers.computeDraftPickTradeOffers(weakSection,tradeInitializingTeam,
						tradeFinalizingTeam,finalTradingPlayers,averageGoalieStrength,
						averageForwardStrength,averageDefenseStrength);
			} else {
				playerTradeOffer = playerTradeOffers.computePlayerTradeOffers(weakSection,tradeInitializingTeam,tradeFinalizingTeam,
						initialTradingPlayers,finalTradingPlayers);
				playersTradeOffer = playersTradeOffers.computePlayersTradeOffers(weakSection,tradeInitializingTeam,tradeFinalizingTeam,
						initialTradingPlayers,finalTradingPlayers);
			}
		} else if(weakSection.equals("forward")) {
			for(Player player : tradeInitializingTeam.getActivePlayers()) {
				if(player.getPosition().equals("forward")) {
					averagePlayersStrength = player.calculateStrength();
				}
			}
			if(averagePlayersStrength < averageForwardStrength - (averageForwardStrength * 0.7)) {
				draftPickTradeOffer = draftPickTradeOffers.computeDraftPickTradeOffers(weakSection,tradeInitializingTeam,
						tradeFinalizingTeam,finalTradingPlayers,averageGoalieStrength,
						averageForwardStrength,averageDefenseStrength);
			} else {
				playerTradeOffer = playerTradeOffers.computePlayerTradeOffers(weakSection,tradeInitializingTeam,tradeFinalizingTeam,
						initialTradingPlayers,finalTradingPlayers);
				playersTradeOffer = playersTradeOffers.computePlayersTradeOffers(weakSection,tradeInitializingTeam,tradeFinalizingTeam,
						initialTradingPlayers,finalTradingPlayers);
			}
		} else if(weakSection.equals("defense")) {
			for(Player player : tradeInitializingTeam.getActivePlayers()) {
				if(player.getPosition().equals("defense")) {
					averagePlayersStrength = player.calculateStrength();
				}
			}
			if(averagePlayersStrength < averageDefenseStrength - (averageDefenseStrength * 0.7)) {
				draftPickTradeOffer = draftPickTradeOffers.computeDraftPickTradeOffers(weakSection,tradeInitializingTeam,
						tradeFinalizingTeam,finalTradingPlayers,averageGoalieStrength,
						averageForwardStrength,averageDefenseStrength);
			} else {
				playerTradeOffer = playerTradeOffers.computePlayerTradeOffers(weakSection,tradeInitializingTeam,tradeFinalizingTeam,
						initialTradingPlayers,finalTradingPlayers);
				playersTradeOffer = playersTradeOffers.computePlayersTradeOffers(weakSection,tradeInitializingTeam,tradeFinalizingTeam,
						initialTradingPlayers,finalTradingPlayers);
			}
		}
		if(playerTradeOffer.size() > 0) {
			Map.Entry<HashMap<Player,Player>,Double> playerEntry = playerTradeOffer.entrySet().iterator().next();
			playerTradingPlayers.put(playerEntry.getKey().entrySet().iterator().next().getKey(),
					playerEntry.getKey().entrySet().iterator().next().getValue());
			playerTradeOfferStrength = playerEntry.getValue();
		}
		if(playersTradeOffer.size() > 0) {
			Map.Entry<HashMap<ArrayList<Player>,Player>,Double> playersEntry = playersTradeOffer.entrySet().iterator().next();
			playersTradingPlayers.put(playersEntry.getKey().entrySet().iterator().next().getKey(),
					playersEntry.getKey().entrySet().iterator().next().getValue());
			playersTradeOfferStrength = playersEntry.getValue();
		}
		if(tradeInitializingTeam.isaITeam()) {
			if(draftPickTradeOffer.size() > 0) {
				UIdraftPickTradeAccept(draftPickTradeOffer);
			} else {
				if(playerTradeOfferStrength >= playersTradeOfferStrength) {
					UIPlayerTradeAccept(playerTradingPlayers);
				}
				else {
					UIPlayersTradeAccept(playersTradingPlayers);
				}
			}
		}
		else {
			if(draftPickTradeOffer.size() > 0) {
				userDraftPickTradeAccept(draftPickTradeOffer);
			} else {
				if(playerTradeOfferStrength >= playersTradeOfferStrength) {
					userPlayerTradeAccept(playerTradingPlayers);
				}
				else {
					userPlayersTradeAccept(playersTradingPlayers);
				}
			}
		}
	}

	public boolean UITradeOffer() {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		randomTradeOfferChance = tradingConfig.getRandomTradeOfferChance();
		Random random = new Random();
		int upperbound = 1;

		double randomTradeOfferChanceGenerated = random.nextDouble();
		if (randomTradeOfferChanceGenerated < randomTradeOfferChance) {
			trade = true;
		}
		return trade;
	}

	public void UIPlayerTradeAccept(HashMap<Player, Player> tradingPlayers) {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		Random random = new Random();
		double randomAcceptanceChanceGenerated = random.nextDouble();
		ArrayList<Player> updatedInitialPlayerList = new ArrayList<>();
		ArrayList<Player> updatedFinalPlayerList = new ArrayList<>();
		tradeOccured = false;

			if(tradeFinalizingTeam.getGeneralManager().getManagerPersonality().equals("gambler")) {
				generalManagerPersonalityValue = tradingConfig.getGambler();
			} else if(tradeFinalizingTeam.getGeneralManager().getManagerPersonality().equals("shrewd")) {
				generalManagerPersonalityValue = tradingConfig.getShrewd();
			} else if(tradeFinalizingTeam.getGeneralManager().getManagerPersonality().equals("normal")) {
				generalManagerPersonalityValue = tradingConfig.getNormal();
			}
		if ((randomAcceptanceChanceGenerated + generalManagerPersonalityValue ) > randomAcceptanceChance) {
			for (Map.Entry<Player, Player> Players : tradingPlayers.entrySet()) {
				for(Player player : tradeInitializingTeam.getAllPlayers()) {
					if(player.equals(Players.getKey())) {
						player.detach(tradeInitializingTeam);
						updatedFinalPlayerList.add(player);
					}
					else {
						updatedInitialPlayerList.add(player);
					}
				}
				for(Player player : tradeFinalizingTeam.getAllPlayers()) {
					if(player.equals(Players.getValue())) {
						player.detach(tradeFinalizingTeam);
						updatedInitialPlayerList.add(player);
					}
					else {
						updatedFinalPlayerList.add(player);
					}
				}
			}
			tradeOccured = true;
		}
		ITeamRoster teamRosterInitial = new TeamRoster();
		teamRosterInitial.setPlayers(updatedInitialPlayerList);
		tradeInitializingTeam.setActivePlayers(teamRosterInitial.createActivePlayerRoster());
		tradeInitializingTeam.setInActivePlayers(teamRosterInitial.createInActivePlayerRoster());
		ITeamRoster teamRosterFinal = new TeamRoster();
		teamRosterFinal.setPlayers(updatedFinalPlayerList);
		tradeFinalizingTeam.setActivePlayers(teamRosterFinal.createActivePlayerRoster());
		tradeFinalizingTeam.setInActivePlayers(teamRosterFinal.createInActivePlayerRoster());
		logger.info("Player trade successful!");
		tradeInitializingTeam.setLossPoint(0);
	}

	public void UIPlayersTradeAccept(HashMap<ArrayList<Player>, Player> tradingPlayers) {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		Random random = new Random();
		double randomAcceptanceChanceGenerated = random.nextDouble();
		ArrayList<Player> updatedInitialPlayerList = new ArrayList<>();
		ArrayList<Player> updatedFinalPlayerList = new ArrayList<>();
		tradeOccured = false;

		if(tradeFinalizingTeam.getGeneralManager().getManagerPersonality().equals("gambler")) {
			generalManagerPersonalityValue = tradingConfig.getGambler();
		} else if(tradeFinalizingTeam.getGeneralManager().getManagerPersonality().equals("shrewd")) {
			generalManagerPersonalityValue = tradingConfig.getShrewd();
		} else if(tradeFinalizingTeam.getGeneralManager().getManagerPersonality().equals("normal")) {
			generalManagerPersonalityValue = tradingConfig.getNormal();
		}
		if ((randomAcceptanceChanceGenerated + generalManagerPersonalityValue ) > randomAcceptanceChance) {
			for (Map.Entry<ArrayList<Player>, Player> Players : tradingPlayers.entrySet()) {
				for(Player player : tradeInitializingTeam.getAllPlayers()) {
					for(Player players : Players.getKey()) {
						if(player.equals(players)) {
							player.detach(tradeInitializingTeam);
							updatedFinalPlayerList.add(player);
						}
						else {
							updatedInitialPlayerList.add(player);
						}
					}
				}
				for(Player player : tradeFinalizingTeam.getAllPlayers()) {
					if(player.equals(Players.getValue())) {
						player.detach(tradeFinalizingTeam);
						updatedInitialPlayerList.add(player);
					}
					else {
						updatedFinalPlayerList.add(player);
					}
				}
			}
			tradeOccured = true;
		}
		ITeamRoster teamRosterInitial = new TeamRoster();
		teamRosterInitial.setPlayers(updatedInitialPlayerList);
		tradeInitializingTeam.setActivePlayers(teamRosterInitial.createActivePlayerRoster());
		tradeInitializingTeam.setInActivePlayers(teamRosterInitial.createInActivePlayerRoster());
		ITeamRoster teamRosterFinal = new TeamRoster();
		teamRosterFinal.setPlayers(updatedFinalPlayerList);
		tradeFinalizingTeam.setActivePlayers(teamRosterFinal.createActivePlayerRoster());
		tradeFinalizingTeam.setInActivePlayers(teamRosterFinal.createInActivePlayerRoster());
		logger.info("Players trade successful!");
		tradeInitializingTeam.setLossPoint(0);
	}

	public void userPlayerTradeAccept(HashMap<Player, Player> tradingPlayers) {
		int option = 0;
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		ArrayList<Player> updatedInitialPlayerList = new ArrayList<>();
		ArrayList<Player> updatedFinalPlayerList = new ArrayList<>();
		tradeOccured = false;

		logger.info("User trade initiated!");
		for (Map.Entry<Player, Player> Players : tradingPlayers.entrySet()) {
			console.printLine("\n Player Name : " + Players.getKey().getPlayerName() + "\n Age : " + Players.getKey().getAge() + "\n Checking : " + Players.getKey().getChecking()
					+ "\n Saving : " + Players.getKey().getSaving() + "\n Shooting : " + Players.getKey().getShooting() + "\n Saving : " + Players.getKey().getSaving() + "\n");
			console.printLine("\n Player Name : " + Players.getValue().getPlayerName() + "\n Age : " + Players.getValue().getAge() + "\n Checking : " + Players.getValue().getChecking()
					+ "\n Saving : " + Players.getValue().getSaving() + "\n Shooting : " + Players.getValue().getShooting() + "\n Saving : " + Players.getValue().getSaving() + "\n");
			console.printLine("\n Do you want to trade " + Players.getKey().getPlayerName() + " with " + Players.getValue().getPlayerName());
			console.printLine("1. Yes \n 2. No \n Choose 1 or 2");
			option = console.readInteger();
			switch (option) {
				case 1:
					for(Player player : tradeInitializingTeam.getAllPlayers()) {
						if(player.equals(Players.getKey())) {
							player.detach(tradeInitializingTeam);
							updatedFinalPlayerList.add(player);
						}
						else {
							updatedInitialPlayerList.add(player);
						}
					}
					for(Player player : tradeFinalizingTeam.getAllPlayers()) {
						if(player.equals(Players.getValue())) {
							player.detach(tradeFinalizingTeam);
							updatedInitialPlayerList.add(player);
						}
						else {
							updatedFinalPlayerList.add(player);
						}
					}
					tradeOccured = true;
					logger.info(Players.getKey().getPlayerName() + " traded with " + Players.getValue().getPlayerName());
					break;
				case 2:
					logger.info("Trade offer declined.");
					break;
				default:
					console.printLine("Invalid option!");
			}
			ITeamRoster teamRosterInitial = new TeamRoster();
			teamRosterInitial.setPlayers(updatedInitialPlayerList);
			tradeInitializingTeam.setActivePlayers(teamRosterInitial.createActivePlayerRoster());
			tradeInitializingTeam.setInActivePlayers(teamRosterInitial.createInActivePlayerRoster());
			ITeamRoster teamRosterFinal = new TeamRoster();
			teamRosterFinal.setPlayers(updatedFinalPlayerList);
			tradeFinalizingTeam.setActivePlayers(teamRosterFinal.createActivePlayerRoster());
			tradeFinalizingTeam.setInActivePlayers(teamRosterFinal.createInActivePlayerRoster());
			tradeInitializingTeam.setLossPoint(0);
			break;
		}
	}

	public void userPlayersTradeAccept(HashMap<ArrayList<Player>, Player> tradingPlayers) {
		int option = 0;
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		ArrayList<Player> updatedInitialPlayerList = new ArrayList<>();
		ArrayList<Player> updatedFinalPlayerList = new ArrayList<>();
		tradeOccured = false;

		logger.info("User trade initiated!");
		for (Map.Entry<ArrayList<Player>, Player> Players : tradingPlayers.entrySet()) {
			for(Player player : Players.getKey()) {
				console.printLine("\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
						+ "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
			}
			console.printLine("\n Player Name : " + Players.getValue().getPlayerName() + "\n Age : " + Players.getValue().getAge() + "\n Checking : " + Players.getValue().getChecking()
					+ "\n Saving : " + Players.getValue().getSaving() + "\n Shooting : " + Players.getValue().getShooting() + "\n Saving : " + Players.getValue().getSaving() + "\n");
			console.printLine("\n Do you want to trade the above players?");
			console.printLine("1. Yes \n 2. No \n Choose 1 or 2");
			option = console.readInteger();
			switch (option) {
				case 1:
					for(Player player : tradeInitializingTeam.getAllPlayers()) {
						for(Player players : Players.getKey()) {
							if(player.equals(players)) {
								player.detach(tradeInitializingTeam);
								updatedFinalPlayerList.add(player);
							}
							else {
								updatedInitialPlayerList.add(player);
							}
						}
					}
					for(Player player : tradeFinalizingTeam.getAllPlayers()) {
						if(player.equals(Players.getValue())) {
							player.detach(tradeFinalizingTeam);
							updatedInitialPlayerList.add(player);
						}
						else {
							updatedFinalPlayerList.add(player);
						}
					}
					tradeOccured = true;
					logger.info("Trade of players successful!");
					break;
				case 2:
					logger.info("Trade offer declined.");
					break;
				default:
					console.printLine("Invalid option!");
			}
			ITeamRoster teamRosterInitial = new TeamRoster();
			teamRosterInitial.setPlayers(updatedInitialPlayerList);
			tradeInitializingTeam.setActivePlayers(teamRosterInitial.createActivePlayerRoster());
			tradeInitializingTeam.setInActivePlayers(teamRosterInitial.createInActivePlayerRoster());
			ITeamRoster teamRosterFinal = new TeamRoster();
			teamRosterFinal.setPlayers(updatedFinalPlayerList);
			tradeFinalizingTeam.setActivePlayers(teamRosterFinal.createActivePlayerRoster());
			tradeFinalizingTeam.setInActivePlayers(teamRosterFinal.createInActivePlayerRoster());
			tradeInitializingTeam.setLossPoint(0);
			break;
		}
	}

	public HashMap<HashMap<Team,Team>,Integer> UIdraftPickTradeAccept(HashMap<Integer, Player> draftPickTradeOffer) {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		maxPlayersPerTrade = tradingConfig.getMaxPlayersPerTrade();
		lossPoint = tradingConfig.getLossPoint();
		randomTradeOfferChance = tradingConfig.getRandomTradeOfferChance();
		randomAcceptanceChance = tradingConfig.getRandomAcceptanceChance();
		HashMap<Team,Team> draftPickTradingTeams = new HashMap<>();
		HashMap<HashMap<Team,Team>,Integer> draftPicksTraded = new HashMap<>();
		ArrayList<Player> updatedInitialPlayerList = new ArrayList<>();
		ArrayList<Player> updatedFinalPlayerList = new ArrayList<>();
		tradeOccured = false;

		for (Map.Entry<Integer, Player> Players : draftPickTradeOffer.entrySet()) {
			for(Player player : tradeFinalizingTeam.getAllPlayers()) {
				if(player.equals(Players.getValue())) {
					player.detach(tradeFinalizingTeam);
					updatedInitialPlayerList.add(player);
				}
				else {
					updatedFinalPlayerList.add(player);
				}
			}
			tradeOccured = true;
			logger.info("Draft pick trade successful!");
			draftPickTradingTeams.put(tradeInitializingTeam,tradeFinalizingTeam);
			draftPicksTraded.put(draftPickTradingTeams,Players.getKey());
		}
		tradeInitializingTeam.setLossPoint(0);
		ITeamRoster teamRosterInitial = new TeamRoster();
		teamRosterInitial.setPlayers(updatedInitialPlayerList);
		tradeInitializingTeam.setActivePlayers(teamRosterInitial.createActivePlayerRoster());
		tradeInitializingTeam.setInActivePlayers(teamRosterInitial.createInActivePlayerRoster());
		ITeamRoster teamRosterFinal = new TeamRoster();
		teamRosterFinal.setPlayers(updatedFinalPlayerList);
		tradeFinalizingTeam.setActivePlayers(teamRosterFinal.createActivePlayerRoster());
		tradeFinalizingTeam.setInActivePlayers(teamRosterFinal.createInActivePlayerRoster());
		return draftPicksTraded;
	}

	public HashMap<HashMap<Team,Team>,Integer> userDraftPickTradeAccept(HashMap<Integer, Player> draftPickTradeOffer) {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		HashMap<Team,Team> draftPickTradingTeams = new HashMap<>();
		HashMap<HashMap<Team,Team>,Integer> draftPicksTraded = new HashMap<>();
		ArrayList<Player> updatedInitialPlayerList = new ArrayList<>();
		ArrayList<Player> updatedFinalPlayerList = new ArrayList<>();
		int option = 0;

		for (Map.Entry<Integer, Player> Players : draftPickTradeOffer.entrySet()) {
			console.printLine("\n Player Name : " + Players.getValue().getPlayerName() + "\n Age : " + Players.getValue().getAge() + "\n Checking : " + Players.getValue().getChecking()
					+ "\n Saving : " + Players.getValue().getSaving() + "\n Shooting : " + Players.getValue().getShooting() + "\n Saving : " + Players.getValue().getSaving() + "\n");
			console.printLine("Do you want to trade your draft pick chance in round "+Players.getKey()+" with the above player?");
			console.printLine("1. Yes \n 2. No \n Choose 1 or 2");
			option = console.readInteger();
			switch (option) {
				case 1:
					for(Player player : tradeFinalizingTeam.getAllPlayers()) {
						if(player.equals(Players.getValue())) {
							player.detach(tradeFinalizingTeam);
							updatedInitialPlayerList.add(player);
						}
						else {
							updatedFinalPlayerList.add(player);
						}
					}
					draftPickTradingTeams.put(tradeInitializingTeam,tradeFinalizingTeam);
					draftPicksTraded.put(draftPickTradingTeams,Players.getKey());
					tradeOccured = true;
					logger.info("Trade of player and draft pick successful!");
					break;
				case 2:
					logger.info("Trade offer declined.");
					break;
				default:
					console.printLine("Invalid option!");
			}
		}
		tradeInitializingTeam.setLossPoint(0);
		ITeamRoster teamRosterInitial = new TeamRoster();
		teamRosterInitial.setPlayers(updatedInitialPlayerList);
		tradeInitializingTeam.setActivePlayers(teamRosterInitial.createActivePlayerRoster());
		tradeInitializingTeam.setInActivePlayers(teamRosterInitial.createInActivePlayerRoster());
		ITeamRoster teamRosterFinal = new TeamRoster();
		teamRosterFinal.setPlayers(updatedFinalPlayerList);
		tradeFinalizingTeam.setActivePlayers(teamRosterFinal.createActivePlayerRoster());
		tradeFinalizingTeam.setInActivePlayers(teamRosterFinal.createInActivePlayerRoster());
		return draftPicksTraded;
	}

	public void adjustingTeamPlayers() {
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
		ITradeFactory tradeFactory = Injector.instance().getTradingObject();
		AdjustTeamPlayers adjustTeamPlayers = tradeFactory.createAdjustTeamPlayers();
		int initializingTeamSize = 0;
		int finalizingTeamSize = 0;

		initializingTeamSize = tradeInitializingTeam.getActivePlayers().size() +
				tradeInitializingTeam.getInActivePlayers().size();
		finalizingTeamSize = tradeFinalizingTeam.getActivePlayers().size() +
				tradeFinalizingTeam.getInActivePlayers().size();
		if (tradeInitializingTeam.isaITeam()) {
			if (initializingTeamSize > teamSize) {
				tradeInitializingTeam = adjustTeamPlayers.UIDropPlayers(tradeInitializingTeam);
			} else if (initializingTeamSize < teamSize) {
				tradeInitializingTeam = adjustTeamPlayers.UIGetFromFreeAgents(tradeInitializingTeam);
			}
		}
		if (tradeFinalizingTeam.isaITeam()) {
			if (finalizingTeamSize > teamSize) {
				tradeFinalizingTeam = adjustTeamPlayers.UIDropPlayers(tradeFinalizingTeam);
			}
			if (finalizingTeamSize < teamSize) {
				tradeFinalizingTeam = adjustTeamPlayers.UIGetFromFreeAgents(tradeFinalizingTeam);
			}
		} else {
			if (finalizingTeamSize > teamSize) {
				tradeFinalizingTeam = adjustTeamPlayers.userDropPlayers(tradeFinalizingTeam);
			}

			if (finalizingTeamSize < teamSize) {
				tradeFinalizingTeam = adjustTeamPlayers.userGetFromFreeAgents(tradeFinalizingTeam);
			}
		}
	}

	public void getAveragePlayerStrength(){
		console = Injector.instance().getConsoleObject();
		leagueModel = Injector.instance().getLeagueModelObject();
		leagueLOM = leagueModel.getCurrentLeague();
		int numberOfGoalies = 0;
		int numberOfForwards = 0;
		int numberOfDefenses = 0;
		double goalieStrength = 0.0;
		double defenseStrength = 0.0;
		double forwardStrength = 0.0;

		for(Conference conference : leagueLOM.getConferences().values()){
			for(Division division : conference.getDivisions().values()){
				for(Team team : division.getTeams().values()){
					for(Player player : team.getActivePlayers()){
						if(player.getPosition().equals("goalie"))
						{
							goalieStrength = goalieStrength + player.calculateStrength();
							numberOfGoalies = numberOfGoalies + 1;
						}
						else if(player.getPosition().equals("defense"))
						{
							defenseStrength = defenseStrength + player.calculateStrength();
							numberOfDefenses = numberOfDefenses + 1;
						}
						else if(player.getPosition().equals("forward"))
						{
							forwardStrength = forwardStrength + player.calculateStrength();
							numberOfForwards = numberOfForwards + 1;
						}
					}
					for(Player player : team.getInActivePlayers()){
						if(player.getPosition().equals("goalie"))
						{
							goalieStrength = goalieStrength + player.calculateStrength();
							numberOfGoalies = numberOfGoalies + 1;
						}
						else if(player.getPosition().equals("defense"))
						{
							defenseStrength = defenseStrength + player.calculateStrength();
							numberOfDefenses = numberOfDefenses + 1;
						}
						else if(player.getPosition().equals("forward"))
						{
							forwardStrength = forwardStrength + player.calculateStrength();
							numberOfForwards = numberOfForwards + 1;
						}
					}
				}
			}
		}
		averageGoalieStrength = goalieStrength / numberOfGoalies;
		averageForwardStrength = forwardStrength / numberOfForwards;
		averageDefenseStrength = defenseStrength / numberOfDefenses;
	}

	public void getInitialAndFinalTradingPlayers(String weakSection) {
		for(Player player : tradeInitializingTeam.getActivePlayers()) {
			if(player.getPosition().equals(weakSection)) {
			}
			else {
				initialTradingPlayers.add(player);
			}
		}
		for(Player player : tradeInitializingTeam.getInActivePlayers()) {
			if(player.getPosition().equals(weakSection)) {
			}
			else {
				initialTradingPlayers.add(player);
			}
		}
		for(Player player : tradeFinalizingTeam.getActivePlayers()) {
			if(player.getPosition().equals(weakSection)) {
				finalTradingPlayers.add(player);
			}
		}
		for(Player player : tradeFinalizingTeam.getInActivePlayers()) {
			if(player.getPosition().equals(weakSection)) {
				finalTradingPlayers.add(player);
			}
		}
	}

}



