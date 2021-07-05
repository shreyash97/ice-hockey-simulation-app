package com.groupten.statemachine.loadteam;

import com.groupten.IO.console.IConsole;
import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.persistence.deserializedata.IDeserializeData;

import java.io.File;

public class LoadTeam implements ILoadTeam {

    private IConsole console;
    private String teamName;
    private String fileToBeLoaded;
    private String path;

    public LoadTeam() {
        this.path = System.getProperty("user.dir") + "/";
    }

    public LoadTeam(String path) {
        this.path = path;
    }

    @Override
    public void userPromptForLoadingTeam() {
        console = Injector.instance().getConsoleObject();
        console.printLine("Enter the Team name:");
        teamName = console.readLine();
    }

    @Override
    public boolean validateUserInput() {
        return teamName.length() > 0;
    }

    @Override
    public boolean doesTeamExist() {
        File[] files = new File(path).listFiles();

        if (files == null) {
            return false;
        } else {
            for (File file : files) {
                if (file.isFile()) {
                    String userInput = teamName.replace(" ", "_");
                    String fileName = file.getName().replace(" ", "_").split("\\.")[0];
                    if (userInput.equals(fileName)) {
                        fileToBeLoaded = fileName;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean loadExistingLeague() {
        IDeserializeData deserializeData = Injector.instance().getDeserializeDataObject();
        League league = deserializeData.importData(path + fileToBeLoaded + ".json");
        if (league == null) {
            return false;
        } else {
            ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
            return leagueModel.setCurrentLeague(league);
        }
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setFileToBeLoaded(String fileToBeLoaded) {
        this.fileToBeLoaded = fileToBeLoaded;
    }
}
