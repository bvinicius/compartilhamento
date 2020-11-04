package br.pucrs.ages.townsq.model;

public enum ReputationEventType {
    CREATED_QUESTION("CREATED_QUESTION"),
    COMPLETED_PROFILE("COMPLETED_PROFILE"),
    DELETED_QUESTION("DELETED_QUESTION");

    private final String value;

    ReputationEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
