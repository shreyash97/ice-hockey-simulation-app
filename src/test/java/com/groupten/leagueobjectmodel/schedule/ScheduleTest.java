package com.groupten.leagueobjectmodel.schedule;

import com.groupten.leagueobjectmodel.team.Team;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ScheduleTest {

    @Test
    public void getGameDateTest() {
        Date date = new Date();
        Schedule schedule = new Schedule(date);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        assertEquals(dateFormat.format(date), dateFormat.format(schedule.getGameDate()));
    }

    @Test
    public void setGameDateTest() {
        Schedule schedule = new Schedule();
        Date date = new Date();
        schedule.setGameDate(date);
        assertEquals(date, schedule.getGameDate());
    }

    @Test
    public void addTeamTest() {
        Schedule schedule = new Schedule();
        schedule.addTeam(new Team("Team1"));
        assertEquals(1, schedule.getTeams().size());
    }

    @Test
    public void getTeamTest() {
        Schedule schedule = new Schedule();
        schedule.addTeam(new Team("Team1"));
        schedule.addTeam(new Team("Team2"));
        schedule.addTeam(new Team("Team3"));
        assertEquals(3, schedule.getTeams().size());
    }
}
