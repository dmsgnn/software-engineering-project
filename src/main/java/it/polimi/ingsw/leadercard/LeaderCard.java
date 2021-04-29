package it.polimi.ingsw.leadercard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.leadercard.Requirements.Requirements;
import it.polimi.ingsw.leadercard.ability.Ability;
import it.polimi.ingsw.playerboard.PlayerBoard;

public class LeaderCard {
    private final int victoryPoints;
    private final Ability ability;
    private final Requirements requirements;
    private final String id;

    public LeaderCard(String id, int victoryPoints, Ability ability, Requirements requirements, Resource resource) {
        this.id = id;
        this.victoryPoints = victoryPoints;
        this.ability = ability;
        this.requirements = requirements;
        this.ability.setResource(resource);
    }

    public Ability getAbility() {
        return ability;
    }
    public Requirements getRequirements() {
        return requirements;
    }
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * activate the leader card
     */
    public void activateCard(PlayerBoard playerBoard) {
        ability.useAbility(playerBoard);
    }




}
