package com.groupten.statemachine.simulation.trophy;

import com.groupten.leagueobjectmodel.coach.Coach;
import com.groupten.leagueobjectmodel.player.Player;
import com.groupten.leagueobjectmodel.team.Team;

public class TrophyPerSeason {

    private Team presidentTrophy, participationAward;
    private Player calderMemorialTrophy, VezinaTrophy, mauriceRichardTrophy, robHawkeyMemorialTrophy;
    private Coach JackAdamAward;

    public Team getPresidentTrophy() {
        return presidentTrophy;
    }

    public void setPresidentTrophy(Team presidentTrophy) {
        this.presidentTrophy = presidentTrophy;
    }

    public Team getParticipationAward() {
        return participationAward;
    }

    public void setParticipationAward(Team participationAward) {
        this.participationAward = participationAward;
    }

    public Player getCalderMemorialTrophy() {
        return calderMemorialTrophy;
    }

    public void setCalderMemorialTrophy(Player calderMemorialTrophy) {
        this.calderMemorialTrophy = calderMemorialTrophy;
    }

    public Player getVezinaTrophy() {
        return VezinaTrophy;
    }

    public void setVezinaTrophy(Player vezinaTrophy) {
        VezinaTrophy = vezinaTrophy;
    }

    public Player getMauriceRichardTrophy() {
        return mauriceRichardTrophy;
    }

    public void setMauriceRichardTrophy(Player mauriceRichardTrophy) {
        this.mauriceRichardTrophy = mauriceRichardTrophy;
    }

    public Player getRobHawkeyMemorialTrophy() {
        return robHawkeyMemorialTrophy;
    }

    public void setRobHawkeyMemorialTrophy(Player robHawkeyMemorialTrophy) {
        this.robHawkeyMemorialTrophy = robHawkeyMemorialTrophy;
    }

    public Coach getJackAdamAward() {
        return JackAdamAward;
    }

    public void setJackAdamAward(Coach jackAdamAward) {
        JackAdamAward = jackAdamAward;
    }
}
