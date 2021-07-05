package com.groupten.leagueobjectmodel.season;

import com.groupten.leagueobjectmodel.schedule.Schedule;
import com.groupten.leagueobjectmodel.seasonstat.SeasonStat;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.teamstanding.TeamStanding;

import java.util.Date;
import java.util.List;

public interface ISeason {
    Date getCurrentDate();

    Date getRegularSeasonStartsAt();

    Date getRegularSeasonEndsAt();

    Date getTradeEndsAt();

    Date getPlayoffStartsAt();

    Date getPlayoffEndsBy();

    void advanceTime();

    boolean generateRegularSchedule();

    boolean generatePlayoffSchedule();

    List<Schedule> schedulesToday();

    void recordWin(Team team);

    Team getSeasonWinner();

    SeasonStat getSeasonStat();

    boolean isTodayRegularSeasonEnd();

    boolean isTradeEnded();

    boolean isWinnerDetermined();

    List<TeamStanding> getTeamStandings();

    List<TeamStanding> getPlayoffTeamStandings();
}
