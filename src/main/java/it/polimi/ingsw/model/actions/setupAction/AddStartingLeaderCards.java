package it.polimi.ingsw.model.actions.setupAction;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.ArrayList;

/**
 * action that initializes the leadercards of one player
 */
public class AddStartingLeaderCards extends Actions {
    private ArrayList<LeaderCard> cards;
    private Player player;

    public AddStartingLeaderCards(ArrayList<LeaderCard> cards, Player player) {
        this.cards = cards;
        this.player = player;
    }
    /**
     * performs the action
     * @param playerBoard playerBoard of the current player
     * @throws InvalidActionException called if the player can't perform the action
     */
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if(!validAction(playerBoard)) throw new InvalidActionException();
        else{
            player.pickStartingLeaderCards(cards);
        }
    }
    /**
     * controls if the action can be performed
     * @param playerBoard of the current player
     */
    public boolean validAction(PlayerBoard playerBoard){
        return cards.size() == 2;
    }
}
