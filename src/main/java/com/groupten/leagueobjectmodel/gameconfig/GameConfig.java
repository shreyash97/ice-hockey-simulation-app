package com.groupten.leagueobjectmodel.gameconfig;

public class GameConfig {
    public static class Aging {
        private int averageRetirementAge;
        private int maximumAge;
        private double statDecayChance;

        public Aging(int averageRetirementAge, int maximumAge, double statDecayChance) {
            this.averageRetirementAge = averageRetirementAge;
            this.maximumAge = maximumAge;
            this.statDecayChance = statDecayChance;
        }

        public int getAverageRetirementAge() {
            return averageRetirementAge;
        }

        public int getMaximumAge() {
            return maximumAge;
        }

        public double getStatDecayChance() {
            return statDecayChance;
        }
    }

    public static class GameResolver {
        private double randomWinChance;

        public GameResolver(double randomWinChance) {
            this.randomWinChance = randomWinChance;
        }

        public double getRandomWinChance() {
            return randomWinChance;
        }
    }

    public static class Injuries {
        private double randomInjuryChance;
        private int injuryDaysLows;
        private int injuryDaysHigh;

        public Injuries(double randomInjuryChance, int injuryDaysLows, int injuryDaysHigh) {
            this.randomInjuryChance = randomInjuryChance;
            this.injuryDaysLows = injuryDaysLows;
            this.injuryDaysHigh = injuryDaysHigh;
        }

        public double getRandomInjuryChance() {
            return randomInjuryChance;
        }

        public int getInjuryDaysLows() {
            return injuryDaysLows;
        }

        public int getInjuryDaysHigh() {
            return injuryDaysHigh;
        }
    }

    public static class Training {
        private int daysUntilStatIncreaseCheck;

        public Training(int daysUntilStatIncreaseCheck) {
            this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
        }

        public int getDaysUntilStatIncreaseCheck() {
            return daysUntilStatIncreaseCheck;
        }
    }

    public static class Trading {
        private int lossPoint;
        private double randomTradeOfferChance;
        private int maxPlayersPerTrade;
        private double randomAcceptanceChance;
        private double shrewd;
        private double normal;
        private double gambler;


        public Trading(int lossPoint, double randomTradeOfferChance, int maxPlayersPerTrade,
                       double randomAcceptanceChance, double shrewd, double normal, double gambler) {
            this.lossPoint = lossPoint;
            this.randomTradeOfferChance = randomTradeOfferChance;
            this.maxPlayersPerTrade = maxPlayersPerTrade;
            this.randomAcceptanceChance = randomAcceptanceChance;
            this.shrewd = shrewd;
            this.normal = normal;
            this.gambler = gambler;

        }

        public int getLossPoint() {
            return lossPoint;
        }

        public double getRandomTradeOfferChance() {
            return randomTradeOfferChance;
        }

        public int getMaxPlayersPerTrade() {
            return maxPlayersPerTrade;
        }

        public double getRandomAcceptanceChance() {
            return randomAcceptanceChance;
        }

        public double getShrewd() {
            return shrewd;
        }

        public double getNormal() {
            return normal;
        }

        public double getGambler() {
            return gambler;
        }
    }
}
