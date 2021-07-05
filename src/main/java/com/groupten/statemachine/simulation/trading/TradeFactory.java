package com.groupten.statemachine.simulation.trading;

public class TradeFactory implements ITradeFactory {

    private static TradeFactory tradeFactorySingleton = null;

    @Override
    public PlayerTradeOffers createPlayerTradeOffers() {
        return new PlayerTradeOffers();
    }

    @Override
    public PlayersTradeOffers createPlayersTradeOffers() {
        return new PlayersTradeOffers();
    }

    @Override
    public DraftPickTradeOffers createDraftPickTradeOffers() {
        return new DraftPickTradeOffers();
    }

    @Override
    public AdjustTeamPlayers createAdjustTeamPlayers() { return new AdjustTeamPlayers(); }

    @Override
    public Trading createTrading() {
        return new Trading();
    }
}
