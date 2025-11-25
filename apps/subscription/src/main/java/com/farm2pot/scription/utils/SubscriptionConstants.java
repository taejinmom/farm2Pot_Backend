package com.farm2pot.scription.utils;

public class SubscriptionConstants {

    public enum SUBSCRIPTION_STATUS {
        RESERVED("RESERVED"),
        ACTIVE("ACTIVE"),
        CANCELED("CANCELED"),
        EXPIRED("EXPIRED");

        private final String code;

        SUBSCRIPTION_STATUS(String code) { this.code = code; }

        public String code() { return code; }
    }

    public enum SUBSCRIPTION_HIST_STATUS {
        ACTIVE("ACTIVE"),
        RESERVED("RESERVED"),
        CANCELED("CANCELED"),
        COMPLETED("COMPLETED");

        private final String code;

        SUBSCRIPTION_HIST_STATUS(String code) { this.code = code; }

        public String code() { return code; }
    }
}
