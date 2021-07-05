package com.groupten.statemachine;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.statemachine.createteam.ICreateTeam;
import com.groupten.statemachine.jsonimport.IJSONImport;
import com.groupten.statemachine.loadteam.ILoadTeam;
import com.groupten.statemachine.simulation.ISimulation;
import com.groupten.statemachine.simulation.training.ITraining;
import com.groupten.statemachine.simulation.training.ITrainingObserver;
import com.groupten.statemachine.simulation.trophy.ITrophy;

import java.io.IOException;

public class StateMachine {
    public void init() {
        IConsole console = Injector.instance().getConsoleObject();

        console.printLine("\t\t\t\t\t\t\t\t##############################\n\t\t\t\t\t\t\t\t### Hockey Game Simulation ###\n\t\t\t\t\t\t\t\t##############################");
        console.printLine("\nDo you want to import JSON file? (y/n)");
        String choice = console.readLine().toLowerCase();
        if (choice.equals("y")) {
            importJson();
        } else if (choice.equals("n")) {
            loadTeam();
        } else {
            console.printLine("ERROR: Invalid Input.");
            continueOrExit();
            init();
        }
    }

    private void importJson() {
        IConsole console = Injector.instance().getConsoleObject();
        IJSONImport json = Injector.instance().getJSONObject();

        console.printLine("Please enter the file path:");

        String path = console.readLine();
        try {
            json.importJSONData(path);
            console.printLine("SUCCESS: Reading JSON file.");
            if (json.instantiateJSONData()) {
                console.printLine("SUCCESS: JSON Loaded.");
                createTeam();
            } else {
                console.printLine("ERROR: Reading JSON file.");
            }
        } catch (IOException e) {
            console.printLine("ERROR: Invalid File Path.");
        }
        continueOrExit();
        importJson();
    }

    private void createTeam() {
        IConsole console = Injector.instance().getConsoleObject();
        ICreateTeam createTeam = Injector.instance().getCreateTeamObject();

        console.printLine("Proceeding to team creation. Please answer the below questions.");
        createTeam.userPromptForNewTeam();

        if (createTeam.validateUserInput()) {
            if (createTeam.ifConferenceAndDivisionExist()) {
                if (createTeam.selectTeamGeneralManager()) {
                    if (createTeam.selectTeamHeadCoach()) {
                        if (createTeam.selectTeamGoalies()) {
                            if (createTeam.selectTeamForwards()) {
                                if (createTeam.selectTeamDefense()) {
                                    if (createTeam.instantiateNewTeam()) {
                                        console.printLine("SUCCESS: Team Created.");
                                        playerChoice();
                                    } else {
                                        console.printLine("FAILURE: Some error occurred.");
                                    }
                                } else {
                                    console.printLine("ERROR: Invalid Input.");
                                }
                            } else {
                                console.printLine("ERROR: Invalid Input.");
                            }
                        } else {
                            console.printLine("ERROR: Invalid Input.");
                        }
                    } else {
                        console.printLine("ERROR: Invalid Input.");
                    }
                } else {
                    console.printLine("ERROR: Invalid Input.");
                }

            } else {
                console.printLine("ERROR: Conference or Division does not exist.");
            }
        } else {
            console.printLine("ERROR: Invalid Input.");
        }
        continueOrExit();
        createTeam();

    }

    private void loadTeam() {
        IConsole console = Injector.instance().getConsoleObject();
        ILoadTeam loadTeam = Injector.instance().getLoadTeamObject();

        console.printLine("Proceeding to team load. Please answer the below questions.");
        loadTeam.userPromptForLoadingTeam();
        if (loadTeam.doesTeamExist()) {
            if (loadTeam.loadExistingLeague()) {
                console.printLine("SUCCESS: Team Selected.");
                playerChoice();
            } else {
                console.printLine("FAILURE: Some error occurred.");
            }
        } else {
            console.printLine("ERROR: Team does not exist.");
        }
        continueOrExit();
        loadTeam();
    }

    private void playerChoice() {
        IConsole console = Injector.instance().getConsoleObject();

        console.printLine("\nHow many seasons do you want to simulate?");
        int numberOfSeasons = console.readInteger();
        simulate(numberOfSeasons);
    }

    private void simulate(int numberOfSeasons) {
        ITraining training = Injector.instance().getTrainingObject();
        ITrophy trophy = Injector.instance().getTrophyObject();
        training.subscribe((ITrainingObserver) trophy);

        IConsole console = Injector.instance().getConsoleObject();
        console.printLine("Preparing for simulation.");

        ISimulation simulation = Injector.instance().getSimulationObject();

        console.printLine("Simulating " + numberOfSeasons + " Seasons.");
        simulation.init(numberOfSeasons);
        end();
    }

    private void continueOrExit() {
        IConsole console = Injector.instance().getConsoleObject();

        console.printLine("\nDo you want to Retry? (y/n)");
        String choice = console.readLine().toLowerCase();
        if (choice.equals("y")) {
            console.printLine("\nOk..starting again.");
        } else if (choice.equals("n")) {
            end();
        } else {
            console.printLine("ERROR: Invalid Input.");
        }
    }

    private void end() {
        IConsole console = Injector.instance().getConsoleObject();
        console.printLine("Good Bye!");
        System.exit(0);
    }
}
