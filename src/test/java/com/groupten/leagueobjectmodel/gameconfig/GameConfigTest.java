package com.groupten.leagueobjectmodel.gameconfig;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameConfigTest {
    @Test
    public void agingConfigTest() {
        GameConfig.Aging agingConfig = new GameConfig.Aging(35, 50, 0.01);
        assertEquals(35, agingConfig.getAverageRetirementAge());
        assertEquals(50, agingConfig.getMaximumAge());
    }

    @Test
    public void gameResolverConfigTest() {
        GameConfig.GameResolver gameResolverConfig = new GameConfig.GameResolver(0.1);
        assertEquals(0.1, gameResolverConfig.getRandomWinChance(), 0.0);
    }

    @Test
    public void injuriesConfigTest() {
        GameConfig.Injuries injuriesConfig = new GameConfig.Injuries(0.05, 1, 260);
        assertEquals(0.05, injuriesConfig.getRandomInjuryChance(), 0.0);
        assertEquals(1, injuriesConfig.getInjuryDaysLows());
        assertEquals(260, injuriesConfig.getInjuryDaysHigh());
    }

    @Test
    public void trainingConfigTest() {
        GameConfig.Training trainingConfig = new GameConfig.Training(100);
        assertEquals(100, trainingConfig.getDaysUntilStatIncreaseCheck());
    }

    @Test
    public void tradingConfigTest() {
        GameConfig.Trading tradingConfig = new GameConfig.Trading(8, 0.05, 2, 0.05, -0.1, 0.0, 0.1);
        assertEquals(8, tradingConfig.getLossPoint());
        assertEquals(0.05, tradingConfig.getRandomTradeOfferChance(), 0.0);
        assertEquals(2, tradingConfig.getMaxPlayersPerTrade());
        assertEquals(0.05, tradingConfig.getRandomAcceptanceChance(), 0.0);
    }
}
