package com.groupten.leagueobjectmodel.season;

import com.groupten.injector.Injector;
import com.groupten.leagueobjectmodel.conference.Conference;
import com.groupten.leagueobjectmodel.division.Division;
import com.groupten.leagueobjectmodel.league.League;
import com.groupten.leagueobjectmodel.leaguemodel.ILeagueModel;
import com.groupten.leagueobjectmodel.schedule.Schedule;
import com.groupten.leagueobjectmodel.seasonstat.SeasonStat;
import com.groupten.leagueobjectmodel.team.Team;
import com.groupten.leagueobjectmodel.teamstanding.TeamStanding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Season implements ISeason {

    private Date currentDate;
    private Date regularSeasonStartsAt;
    private Date regularSeasonEndsAt;
    private Date tradeEndsAt;
    private Date playoffStartsAt;
    private Date playoffEndsBy;
    private List<TeamStanding> teamStandings = new ArrayList<>();
    private List<TeamStanding> playoffTeamStandings = new ArrayList<>();
    private List<Schedule> regularSchedules = new ArrayList<>();
    private List<Schedule> playoffSchedules = new ArrayList<>();
    private Team winner = null;
    private SeasonStat seasonStat;
    private List<ISeasonObserver> observers = new ArrayList<>();

    public Season() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);

        this.initDates(year);
        this.generateTeamStandings();
        this.seasonStat = new SeasonStat();
    }

    public Season(int year) {
        this.initDates(year);
        this.generateTeamStandings();
        this.seasonStat = new SeasonStat();
    }

    public void attach(ISeasonObserver observer) {
        this.observers.add(observer);
    }

    public void detach(ISeasonObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (ISeasonObserver observer : this.observers) {
            observer.updateRegularSeasonEnd(this);
        }
    }

    @Override
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date date) {
        this.currentDate = date;
    }

    @Override
    public Date getRegularSeasonStartsAt() {
        return regularSeasonStartsAt;
    }

    @Override
    public Date getRegularSeasonEndsAt() {
        return regularSeasonEndsAt;
    }

    @Override
    public Date getTradeEndsAt() {
        return tradeEndsAt;
    }

    @Override
    public Date getPlayoffStartsAt() {
        return playoffStartsAt;
    }

    @Override
    public Date getPlayoffEndsBy() {
        return playoffEndsBy;
    }

    @Override
    public void advanceTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.currentDate);
        cal.add(Calendar.DATE, 1);
        this.currentDate = cal.getTime();

        if (isTodayRegularSeasonEnd()) {
            notifyObservers();
        }

    }

    public List<TeamStanding> getTeamStandings() {
        return teamStandings;
    }

    public List<TeamStanding> getPlayoffTeamStandings() {
        return playoffTeamStandings;
    }

    @Override
    public boolean generateRegularSchedule() {
        if (teamStandings.size() <= 0) {
            return false;
        }
        HashSet<String> conferenceNames = new HashSet<>();
        HashSet<String> divisionNames = new HashSet<>();
        teamStandings.forEach(teamStanding -> {
            conferenceNames.add(teamStanding.getConferenceName());
            divisionNames.add(teamStanding.getDivisionName());
        });

        divisionNames.forEach(divisionName -> {
            ArrayList<TeamStanding> sameDivisionTeamStanding = new ArrayList<>();
            teamStandings.forEach(teamStanding -> {
                if (teamStanding.getDivisionName().equals(divisionName)) {
                    sameDivisionTeamStanding.add(teamStanding);
                }
            });
            if (sameDivisionTeamStanding.size() < 8) {
                TeamStanding teamStanding1 = sameDivisionTeamStanding.get(0);
                TeamStanding teamStanding2 = sameDivisionTeamStanding.get(1);
                for (int i = 0; i < 5; i++) {
                    Schedule schedule = new Schedule();
                    schedule.addTeam(teamStanding1.getTeam());
                    schedule.addTeam(teamStanding2.getTeam());
                    regularSchedules.add(schedule);
                }
                sameDivisionTeamStanding.remove(teamStanding1);
                sameDivisionTeamStanding.remove(teamStanding2);
            }
            sameDivisionTeamStanding.forEach(teamStanding1 -> {
                sameDivisionTeamStanding.stream().filter(teamStanding2 -> teamStanding2 != teamStanding1).forEach(teamStanding2 -> {
                    for (int i = 0; i < 4; i++) {
                        Schedule schedule = new Schedule();
                        schedule.addTeam(teamStanding1.getTeam());
                        schedule.addTeam(teamStanding2.getTeam());
                        regularSchedules.add(schedule);
                    }
                });
            });
        });

        conferenceNames.forEach(conferenceName -> {
            ArrayList<TeamStanding> sameConferenceTeamStanding = new ArrayList<>();
            teamStandings.forEach(teamStanding -> {
                if (teamStanding.getConferenceName().equals(conferenceName)) {
                    sameConferenceTeamStanding.add(teamStanding);
                }
            });
            sameConferenceTeamStanding.forEach(teamStanding1 -> {
                sameConferenceTeamStanding.stream().filter(teamStanding2 -> teamStanding2 != teamStanding1).forEach(teamStanding2 -> {
                    for (int i = 0; i < 3; i++) {
                        Schedule schedule = new Schedule();
                        schedule.addTeam(teamStanding1.getTeam());
                        schedule.addTeam(teamStanding2.getTeam());
                        regularSchedules.add(schedule);
                    }
                });
            });
        });

        teamStandings.forEach(teamStanding1 -> {
            teamStandings.stream().filter(teamStanding2 -> teamStanding2 != teamStanding1).forEach(teamStanding2 -> {
                for (int i = 0; i < 2; i++) {
                    Schedule schedule = new Schedule();
                    schedule.addTeam(teamStanding1.getTeam());
                    schedule.addTeam(teamStanding2.getTeam());
                    regularSchedules.add(schedule);
                }
            });
        });

        Collections.shuffle(regularSchedules, new Random());
        regularSchedules.forEach(regularSchedule -> {
            regularSchedule.setGameDate(randomDateBetween(this.regularSeasonStartsAt, this.regularSeasonEndsAt));
        });
        return true;
    }

    @Override
    public boolean generatePlayoffSchedule() {
        if (teamStandings.size() <= 0) {
            return false;
        }
        HashSet<String> conferenceNames = new HashSet<>();
        teamStandings.forEach(teamStanding -> {
            conferenceNames.add(teamStanding.getConferenceName());
        });

        conferenceNames.forEach(conferenceName -> {
            HashSet<String> divisionNames = new HashSet<>();
            teamStandings.forEach(teamStanding -> {
                if (teamStanding.getConferenceName().equals(conferenceName)) {
                    divisionNames.add(teamStanding.getDivisionName());
                }
            });
            List<TeamStanding> conferenceTop10 = new ArrayList<>();
            divisionNames.forEach(divisionName -> {
                teamStandings.forEach(teamStanding -> {
                    if (teamStanding.getDivisionName().equals(divisionName)) {
                        if (teamStanding.getDivisionRank() <= 5) {
                            conferenceTop10.add(teamStanding);
                        }
                    }
                });
            });

            TeamStanding team1 = conferenceTop10.get(0);
            playoffTeamStandings.add(team1);
            TeamStanding team2 = conferenceTop10.get(5);
            playoffTeamStandings.add(team2);
            TeamStanding team3 = conferenceTop10.get(1);
            playoffTeamStandings.add(team3);
            TeamStanding team4 = conferenceTop10.get(6);
            playoffTeamStandings.add(team4);
            TeamStanding team5 = conferenceTop10.get(2);
            playoffTeamStandings.add(team5);
            TeamStanding team6 = conferenceTop10.get(7);
            playoffTeamStandings.add(team6);
            TeamStanding team7;
            TeamStanding team8;
            TeamStanding div1Top4 = conferenceTop10.get(3);
            TeamStanding div2Top4 = conferenceTop10.get(8);
            TeamStanding div1Top5 = conferenceTop10.get(4);
            TeamStanding div2Top5 = conferenceTop10.get(9);
            if (div1Top4.getPoints() > div2Top4.getPoints()) {
                team7 = div1Top4;
                if (div1Top5.getPoints() > div2Top4.getPoints()) {
                    team8 = div1Top5;
                } else {
                    team8 = div2Top4;
                }
            } else {
                team7 = div2Top4;
                if (div1Top4.getPoints() > div2Top5.getPoints()) {
                    team8 = div1Top4;
                } else {
                    team8 = div2Top5;
                }
            }
            playoffTeamStandings.add(team7);
            playoffTeamStandings.add(team8);

            Calendar cal = Calendar.getInstance();
            cal.setTime(this.playoffStartsAt);

            Schedule schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            schedule.addTeam(team1.getTeam());
            schedule.addTeam(team8.getTeam());
            playoffSchedules.add(schedule);

            cal.add(Calendar.DATE, 1);
            schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            schedule.addTeam(team2.getTeam());
            schedule.addTeam(team7.getTeam());
            playoffSchedules.add(schedule);

            cal.add(Calendar.DATE, 1);
            schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            schedule.addTeam(team3.getTeam());
            schedule.addTeam(team4.getTeam());
            playoffSchedules.add(schedule);

            cal.add(Calendar.DATE, 1);
            schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            schedule.addTeam(team5.getTeam());
            schedule.addTeam(team6.getTeam());
            playoffSchedules.add(schedule);
        });

        conferenceNames.forEach(conferenceName -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.playoffStartsAt);
            cal.add(Calendar.DATE, 4);
            Schedule schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            playoffSchedules.add(schedule);

            cal.add(Calendar.DATE, 1);
            schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            playoffSchedules.add(schedule);
        });

        conferenceNames.forEach(conferenceName -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.playoffStartsAt);
            cal.add(Calendar.DATE, 6);
            Schedule schedule = new Schedule();
            schedule.setGameDate(cal.getTime());
            playoffSchedules.add(schedule);
        });


        Calendar cal = Calendar.getInstance();
        cal.setTime(this.playoffStartsAt);
        cal.add(Calendar.DATE, 7);
        Schedule schedule = new Schedule();
        schedule.setGameDate(cal.getTime());
        playoffSchedules.add(schedule);

        return true;
    }

    @Override
    public List<Schedule> schedulesToday() {
        List<Schedule> scheduleList = new ArrayList<>();
        regularSchedules.forEach(regularSchedule -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (dateFormat.format(regularSchedule.getGameDate()).equals(dateFormat.format(currentDate))) {
                scheduleList.add(regularSchedule);
            }
        });
        playoffSchedules.forEach(playoffSchedule -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (dateFormat.format(playoffSchedule.getGameDate()).equals(dateFormat.format(currentDate))) {
                scheduleList.add(playoffSchedule);
            }
        });
        return scheduleList;
    }

    @Override
    public void recordWin(Team team) {
        teamStandings.forEach(teamStanding -> {
            if (teamStanding.getTeam().getTeamName().equals(team.getTeamName())) {
                teamStanding.setPoints(teamStanding.getPoints() + 2);
            }
        });
        if (currentDate.getTime() < playoffStartsAt.getTime()) {
            updateRanks();
        } else {
            updatePlayoffSchedule(team);
        }
    }

    @Override
    public Team getSeasonWinner() {
        return winner;
    }

    @Override
    public SeasonStat getSeasonStat() {
        return seasonStat;
    }

    @Override
    public boolean isTodayRegularSeasonEnd() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(regularSeasonEndsAt).equals(dateFormat.format(currentDate));
    }

    @Override
    public boolean isTradeEnded() {
        long diffInMillies = Math.abs(tradeEndsAt.getTime() - currentDate.getTime());
        long diff = Math.abs(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));
        return diff <= 0;
    }

    @Override
    public boolean isWinnerDetermined() {
        if (winner == null) {
            return false;
        } else {
            return true;
        }
    }

    private void updateRanks() {
        HashSet<String> conferenceNames = new HashSet<>();
        HashSet<String> divisionNames = new HashSet<>();
        teamStandings.forEach(teamStanding -> {
            conferenceNames.add(teamStanding.getConferenceName());
            divisionNames.add(teamStanding.getDivisionName());
        });

        divisionNames.forEach(divisionName -> {
            List<TeamStanding> divisionTeamStandings = new ArrayList<>();

            teamStandings.forEach(teamStanding -> {
                if (teamStanding.getDivisionName().equals(divisionName)) {
                    divisionTeamStandings.add(teamStanding);
                }
            });

            Collections.sort(divisionTeamStandings);
            Collections.reverse(divisionTeamStandings);
            for (int i = 0; i < divisionTeamStandings.size(); i++) {
                divisionTeamStandings.get(i).setDivisionRank(i + 1);
            }
        });

        conferenceNames.forEach(conferenceName -> {
            List<TeamStanding> conferenceTeamStandings = new ArrayList<>();

            teamStandings.forEach(teamStanding -> {
                if (teamStanding.getConferenceName().equals(conferenceName)) {
                    conferenceTeamStandings.add(teamStanding);
                }
            });

            Collections.sort(conferenceTeamStandings);
            Collections.reverse(conferenceTeamStandings);
            for (int i = 0; i < conferenceTeamStandings.size(); i++) {
                conferenceTeamStandings.get(i).setConferenceRank(i + 1);
            }
        });

        List<TeamStanding> leagueTeamStandings = teamStandings;
        Collections.sort(leagueTeamStandings);
        Collections.reverse(leagueTeamStandings);
        for (int i = 0; i < leagueTeamStandings.size(); i++) {
            leagueTeamStandings.get(i).setLeagueRank(i + 1);
        }
    }

    private void updatePlayoffSchedule(Team team) {

        Optional<Schedule> playoffSchedule =
                playoffSchedules.stream().filter(schedule -> schedule.getTeams().size() < 2).findFirst();

        if (playoffSchedule.isPresent()) {
            playoffSchedule.get().addTeam(team);
        } else {
            winner = team;
        }
    }

    private void initDates(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DATE, 30);
        this.currentDate = cal.getTime();

        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DATE, 1);
        this.regularSeasonStartsAt = cal.getTime();

        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 4);
        this.tradeEndsAt = lastDayOfMonth(cal.getTime(), Calendar.MONDAY);

        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 2);
        this.regularSeasonEndsAt = nthDayOfMonth(cal.getTime(), Calendar.SATURDAY, 1);

        this.playoffStartsAt = nthDayOfMonth(cal.getTime(), Calendar.WEDNESDAY, 2);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        this.playoffEndsBy = cal.getTime();
    }

    private Date lastDayOfMonth(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);

        int lobosMagicNumber = (13 - day) % 7;
        cal.add(Calendar.DAY_OF_MONTH, -(((lobosMagicNumber + cal.get(Calendar.DAY_OF_WEEK)) % 7) + 1));

        return cal.getTime();
    }

    private Date nthDayOfMonth(Date date, int day, int nth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        int occCount = 0;
        while (occCount < nth) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (cal.get(Calendar.DAY_OF_WEEK) == day) {
                occCount++;
            }
        }
        return cal.getTime();
    }

    private void generateTeamStandings() {
        ILeagueModel leagueModel = Injector.instance().getLeagueModelObject();
        League league = leagueModel.getCurrentLeague();
        Map<String, Conference> conferences = league.getConferences();
        conferences.forEach((conferenceName, conference) -> {
            Map<String, Division> divisions = conference.getDivisions();
            divisions.forEach((divisionName, division) -> {
                Map<String, Team> teams = division.getTeams();
                teams.forEach((teamName, team) -> {
                    TeamStanding teamStanding = new TeamStanding(team, division.getDivisionName(), conference.getConferenceName(), 0, 0, 0, 0);
                    this.teamStandings.add(teamStanding);
                });
            });
        });
    }

    private Date randomDateBetween(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

}
