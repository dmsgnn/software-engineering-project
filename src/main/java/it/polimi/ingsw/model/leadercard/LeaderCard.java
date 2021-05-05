package it.polimi.ingsw.model.leadercard;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.leadercard.Requirements.Requirements;
import it.polimi.ingsw.model.leadercard.ability.Ability;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

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
    public String getId() {
        return id;
    }

    /**
     * activate the leader card
     */
    public void activateCard(PlayerBoard playerBoard) {
        ability.useAbility(playerBoard);
    }




}
