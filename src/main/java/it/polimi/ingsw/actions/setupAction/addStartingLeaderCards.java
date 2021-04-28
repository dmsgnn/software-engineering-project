package it.polimi.ingsw.actions.setupAction;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.ArrayList;

public class addStartingLeaderCards {
    private ArrayList<LeaderCard> cards;
    private Player player;

    public addStartingLeaderCards(ArrayList<LeaderCard> cards, Player player) {
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
        if(cards.size()!=2){
            return false;
        }
        else return true;
    }
}
