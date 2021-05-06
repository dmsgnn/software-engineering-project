package it.polimi.ingsw.model.actions.setupAction;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.ArrayList;

public class AddStartingLeaderCards extends Actions {
    private ArrayList<LeaderCard> cards;
    private Player player;

    public AddStartingLeaderCards(ArrayList<LeaderCard> cards, Player player) {
        this.cards = cards;
        this.player = player;
    }

    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if(!validAction(playerBoard)) throw new InvalidActionException();
        else{
            player.pickStartingLeaderCards(cards);
        }
    }

    public boolean validAction(PlayerBoard playerBoard){
        return cards.size() == 2;
    }
}
