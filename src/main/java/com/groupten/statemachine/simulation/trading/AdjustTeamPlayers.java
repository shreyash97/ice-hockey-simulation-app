package com.groupten.statemachine.simulation.trading;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.gameconfig.GameConfig;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.ITeamRoster;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.team.TeamRoster;

import java.util.*;
import java.util.stream.Collectors;

public class AdjustTeamPlayers {
    private IConsole console;
    private ILeagueModel leagueModel;
    private League leagueLOM;
    private String playerName;
    private final int teamSize = 20;
    private final int numberOfGoalies = 4;
    private final int numberOfForwards = 16;
    private final int numberOfDefenses = 10;

    public Team UIDropPlayers(Team tradingTeam) {
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        leagueLOM = leagueModel.getCurrentLeague();
        GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
        int goalieCount = 0;
        int forwardCount = 0;
        int defenseCount = 0;
        ArrayList<Player> totalPlayers = new ArrayList<>();

        for(Player player : tradingTeam.getActivePlayers()) {
            totalPlayers.add(player);
        }
        for(Player player : tradingTeam.getInActivePlayers()) {
            totalPlayers.add(player);
        }

        HashMap<Player, Double> teamPlayerStrength = new HashMap<Player, Double>();
        for (Player players : totalPlayers) {
            double playerStrength = players.calculateStrength();
            teamPlayerStrength.put(players, playerStrength);
        }

        Map<Player,Double> sortedTeamPlayerStrength = teamPlayerStrength.entrySet() .stream()
                .sorted(Map.Entry.<Player,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        for (Map.Entry<Player, Double> entry : sortedTeamPlayerStrength.entrySet()) {
            if (entry.getKey().getPosition() == "goalie") {
                goalieCount++;

                if (goalieCount > numberOfGoalies) {
                    totalPlayers.remove(entry.getKey());
                    leagueLOM.addFreeAgent(entry.getKey());
                }
            } else if (entry.getKey().getPosition() == "forward"){
                forwardCount++;

                if (forwardCount > numberOfForwards) {
                    totalPlayers.remove(entry.getKey());
                    leagueLOM.addFreeAgent(entry.getKey());
                }
            } else if (entry.getKey().getPosition() == "defense") {
                defenseCount++;

                if (defenseCount > numberOfDefenses) {
                    totalPlayers.remove(entry.getKey());
                    leagueLOM.addFreeAgent(entry.getKey());
                }
            }
        }
        ITeamRoster teamRoster = new TeamRoster();
        teamRoster.setPlayers(totalPlayers);
        tradingTeam.setActivePlayers(teamRoster.createActivePlayerRoster());
        tradingTeam.setInActivePlayers(teamRoster.createInActivePlayerRoster());
        return tradingTeam;
    }

    public Team userDropPlayers(Team tradingTeam) {
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        leagueLOM = leagueModel.getCurrentLeague();
        GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
        List<Player> players = new ArrayList<>();
        int i = 1;
        int goalieCount = 0;
        int forwardCount = 0;
        int defenseCount = 0;
        ArrayList<Player> totalPlayers = new ArrayList<>();
        List<Player> updatedPlayersList = new ArrayList<Player>();

        for(Player player : tradingTeam.getActivePlayers()) {
            totalPlayers.add(player);
        }
        for(Player player : tradingTeam.getInActivePlayers()) {
            totalPlayers.add(player);
        }

        for (Player player : totalPlayers) {
            if (player.getPosition() == "goalie") {
                goalieCount++;
            } else if(player.getPosition() == "forward"){
                forwardCount++;
            } else if(player.getPosition() == "defense"){
                defenseCount++;
            }
        }
        for (Player player : totalPlayers) {
            if (player.getPosition().equals("goalie")) {
                console.printLine(i + "\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
                        + "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
                i++;
            }
        }
        while (goalieCount > numberOfGoalies) {
            try {
                console.printLine("Please choose a goalie (player name) to drop : \n");
                playerName = console.readLine();
            } catch (Exception e) {
                System.out.println("Invalid value!");
                break;
            }
            for (Player player : totalPlayers) {
                if (playerName.equals(player.getPlayerName())) {
                    goalieCount--;
                    leagueLOM.addFreeAgent(player);
                }
                else {
                    updatedPlayersList.add(player);
                }
            }
            totalPlayers.addAll(updatedPlayersList);
            //tradingTeam.setPlayers(updatedPlayersList);
        }
        for (Player player : totalPlayers) {
            i = 1;
            if (player.getPosition().equals("forward")) {
                console.printLine(i + "\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
                        + "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
                i++;
            }
        }
        while (forwardCount > numberOfForwards) {
            try {
                console.printLine("Please choose a forward (player name) to drop : \n");
                playerName = console.readLine();
            } catch (Exception e) {
                System.out.println("Invalid value!");
                break;
            }
            for (Player player : totalPlayers) {
                if (playerName.equals(player.getPlayerName())) {
                    forwardCount--;
                    leagueLOM.addFreeAgent(player);
                }
                else {
                    updatedPlayersList.add(player);
                }
            }
            totalPlayers.addAll(updatedPlayersList);
            //tradingTeam.setPlayers(updatedPlayersList);
        }
        for (Player player : totalPlayers) {
            i = 1;
            if (player.getPosition().equals("defense")) {
                console.printLine(i + "\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
                        + "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
                i++;
            }
        }
        while (defenseCount > numberOfDefenses) {
            try {
                console.printLine("Please choose a defensemen (player name) to drop : \n");
                playerName = console.readLine();
            } catch (Exception e) {
                System.out.println("Invalid value!");
                break;
            }
            for (Player player : totalPlayers) {
                if (playerName.equals(player.getPlayerName())) {
                    defenseCount--;
                    leagueLOM.addFreeAgent(player);
                }
                else {
                    updatedPlayersList.add(player);
                }
            }
            totalPlayers.addAll(updatedPlayersList);
            //tradingTeam.setPlayers(updatedPlayersList);
        }
        ITeamRoster teamRoster = new TeamRoster();
        teamRoster.setPlayers(totalPlayers);
        tradingTeam.setActivePlayers(teamRoster.createActivePlayerRoster());
        tradingTeam.setInActivePlayers(teamRoster.createInActivePlayerRoster());
        return tradingTeam;
    }

    public Team UIGetFromFreeAgents(Team tradingTeam) {
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        leagueLOM = leagueModel.getCurrentLeague();
        GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
        HashMap<Player, Double> goaliePlayerStrength = new HashMap<Player, Double>();
        HashMap<Player, Double> forwardPlayerStrength = new HashMap<Player, Double>();
        HashMap<Player, Double> defensePlayerStrength = new HashMap<Player, Double>();
        int goalieCount = 0;
        int forwardCount = 0;
        int defenseCount = 0;
        ArrayList<Player> totalPlayers = new ArrayList<>();

        for(Player player : tradingTeam.getActivePlayers()) {
            totalPlayers.add(player);
        }
        for(Player player : tradingTeam.getInActivePlayers()) {
            totalPlayers.add(player);
        }

        for (Player player : totalPlayers) {
            if (player.getPosition() == "goalie") {
                goalieCount++;
            } else if (player.getPosition() == "forward"){
                forwardCount++;
            } else if (player.getPosition() == "defense"){
                defenseCount++;
            }
        }
        for (Player players : leagueLOM.getFreeAgentsGoalies()) {
            double playerStrength = players.calculateStrength();
            goaliePlayerStrength.put(players, playerStrength);
        }
        Map<Player,Double> sortedGoaliePlayerStrength = goaliePlayerStrength.entrySet() .stream()
                .sorted(Map.Entry.<Player,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<Player, Double> entry : sortedGoaliePlayerStrength.entrySet()) {
            if (goalieCount < numberOfGoalies) {
                if (entry.getKey().getPosition().equals("goalie")) {
                    totalPlayers.add(entry.getKey());
                    leagueLOM.getFreeAgentsGoalies().remove(entry.getKey());
                    goalieCount++;
                }
            }
        }
        for (Player players : leagueLOM.getFreeAgentsSkaters()) {
            if(players.getPosition().equals("forward")) {
                forwardPlayerStrength.put(players,players.calculateStrength());
            }
            else if (players.getPosition().equals("defense")) {
                defensePlayerStrength.put(players,players.calculateStrength());
            }
        }
        Map<Player,Double> sortedForwardPlayerStrength = forwardPlayerStrength.entrySet() .stream()
                .sorted(Map.Entry.<Player,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<Player, Double> entry : sortedForwardPlayerStrength.entrySet()) {
            if (forwardCount < numberOfForwards) {
                if (entry.getKey().getPosition().equals("forward")) {
                    totalPlayers.add(entry.getKey());
                    leagueLOM.getFreeAgentsSkaters().remove(entry.getKey());
                    forwardCount++;
                }
            }
        }
        Map<Player,Double> sortedDefensePlayerStrength = defensePlayerStrength.entrySet() .stream()
                .sorted(Map.Entry.<Player,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<Player, Double> entry : sortedDefensePlayerStrength.entrySet()) {
            if (defenseCount < numberOfDefenses) {
                if (entry.getKey().getPosition().equals("defense")) {
                    totalPlayers.add(entry.getKey());
                    leagueLOM.getFreeAgentsSkaters().remove(entry.getKey());
                    defenseCount++;
                }
            }
        }
        ITeamRoster teamRoster = new TeamRoster();
        teamRoster.setPlayers(totalPlayers);
        tradingTeam.setActivePlayers(teamRoster.createActivePlayerRoster());
        tradingTeam.setInActivePlayers(teamRoster.createInActivePlayerRoster());
        return tradingTeam;
    }

    public Team userGetFromFreeAgents(Team tradingTeam) {
        console = Injector.instance().getConsoleObject();
        leagueModel = Injector.instance().getLeagueModelObject();
        leagueLOM = leagueModel.getCurrentLeague();
        GameConfig.Trading tradingConfig = leagueLOM.getTradingConfig();
        HashMap<Player, Double> teamPlayerStrength = new HashMap<Player, Double>();
        List<Player> goalies = new ArrayList<>();
        List<Player> skaters = new ArrayList<>();
        int goalieCount = 0;
        int forwardCount = 0;
        int defenseCount = 0;
        int i = 1;
        ArrayList<Player> totalPlayers = new ArrayList<>();

        for(Player player : tradingTeam.getActivePlayers()) {
            totalPlayers.add(player);
        }
        for(Player player : tradingTeam.getInActivePlayers()) {
            totalPlayers.add(player);
        }

        for (Player player : totalPlayers) {
            if (player.getPosition() == "goalie") {
                goalieCount++;
            } else if(player.getPosition() == "forward"){
                forwardCount++;
            } else if(player.getPosition() == "defense"){
                defenseCount++;
            }
        }
        if (goalieCount < numberOfGoalies) {
            List<Player> updatedPlayersList = new ArrayList<Player>();
            for (Player player : leagueLOM.getFreeAgentsGoalies()) {
                console.printLine(i + "\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
                        + "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
                i++;
            }
            while (goalieCount < numberOfGoalies) {
                try {
                    console.printLine("Please choose a goalie (player name) from the given list.");
                    playerName = console.readLine();
                } catch (Exception e) {
                    System.out.println("Invalid value");
                    break;
                }
                for (Player player : leagueLOM.getFreeAgentsGoalies()) {
                    if (playerName.equals(player.getPlayerName())) {
                        totalPlayers.add(player);
                        goalieCount++;
                    }
                    else {
                        updatedPlayersList.add(player);
                    }
                }
            }
            leagueLOM.getFreeAgents().clear();
            for(Player player : updatedPlayersList) {
                leagueLOM.addFreeAgent(player);
            }
        }
        if (defenseCount < numberOfDefenses) {
            i = 1;
            List<Player> updatedPlayersList = new ArrayList<Player>();
            for (Player player : leagueLOM.getFreeAgentsSkaters()) {
                if(player.getPosition().equals("defense")){
                    console.printLine(i + "\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
                            + "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
                    i++;
                }
            }
            while (defenseCount < numberOfDefenses) {
                try {
                    console.printLine("Please choose a defensemen (player name) from the given list.");
                    playerName = console.readLine();
                } catch (Exception e) {
                    System.out.println("Invalid value");
                    break;
                }
                for (Player player : leagueLOM.getFreeAgentsSkaters()) {
                    if (playerName.equals(player.getPlayerName())) {
                        totalPlayers.add(player);
                        defenseCount++;
                    } else {
                        updatedPlayersList.add(player);
                    }
                }
            }
            leagueLOM.getFreeAgents().clear();
            for(Player player : updatedPlayersList) {
                leagueLOM.addFreeAgent(player);
            }
        }
        if (forwardCount < numberOfForwards) {
            i = 1;
            List<Player> updatedPlayersList = new ArrayList<Player>();
            for (Player player : leagueLOM.getFreeAgentsSkaters()) {
                if(player.getPosition().equals("forward")){
                    console.printLine(i + "\n Player Name : " + player.getPlayerName() + "\n Age : " + player.getAge() + "\n Checking : " + player.getChecking()
                            + "\n Saving : " + player.getSaving() + "\n Shooting : " + player.getShooting() + "\n Saving : " + player.getSaving() + "\n");
                    i++;
                }
            }
            while (forwardCount < numberOfForwards) {
                try {
                    console.printLine("Please choose a forward (player name) from the given list.");
                    playerName = console.readLine();
                } catch (Exception e) {
                    System.out.println("Invalid value");
                    break;
                }
                for (Player player : leagueLOM.getFreeAgentsSkaters()) {
                    if (playerName.equals(player.getPlayerName())) {
                        totalPlayers.add(player);
                        forwardCount++;
                    } else {
                        updatedPlayersList.add(player);
                    }
                }
            }
            leagueLOM.getFreeAgents().clear();
            for(Player player : updatedPlayersList) {
                leagueLOM.addFreeAgent(player);
            }
        }
        return tradingTeam;
    }

}
