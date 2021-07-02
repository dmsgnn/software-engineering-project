package it.polimi.ingsw.controller;

/**
 * this enum contains the actions that a player can perform
 */
public enum Actions {
    PLAYLEADERCARD("Play a leader card"),
    DISCARDLEADERCARD("Discard a leader card"),
    BUYDEVELOPMENTCARD("Buy one Development Card"),
    USEPRODUCTION("Activate the Production"),
    MARKETACTION("Take Resources from the Market"),
    ENDTURN("End turn"),
    MANAGE("Manage resources");

    private final String label;

    Actions(String s) {
        this.label = s;
    }

    @Override
    public String toString() {
        return label;
    }
}
