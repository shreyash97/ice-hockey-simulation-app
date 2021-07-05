package com.groupten.statemachine.loadteam;

public interface ILoadTeam {

    void userPromptForLoadingTeam();

    boolean doesTeamExist();

    boolean loadExistingLeague();

    boolean validateUserInput();

}
