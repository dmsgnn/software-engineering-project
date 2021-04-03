package it.polimi.ingsw.leaderCard;

import it.polimi.ingsw.leaderCard.Requirements.Requirements;
import it.polimi.ingsw.leaderCard.ability.Ability;

public class LeaderCard {
    private int victoryPoints;
    private Ability ability;
    private Requirements requirements;

    public LeaderCard(int victoryPoints, Ability ability, Requirements requirements) {
        this.victoryPoints = victoryPoints;
        this.ability = ability;
        this.requirements = requirements;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
