package com.groupten.statemachine.simulation.trading;

public interface ITradeFactory {

    public PlayerTradeOffers createPlayerTradeOffers();

    public PlayersTradeOffers createPlayersTradeOffers();

    public DraftPickTradeOffers createDraftPickTradeOffers();

    public AdjustTeamPlayers createAdjustTeamPlayers();

    public Trading createTrading();
}
