package it.polimi.ingsw.actions.leaderAction;

import it.polimi.ingsw.actions.Actions;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.playerboard.PlayerBoard;
import it.polimi.ingsw.playerboard.faithTrack.FaithTrack;

import java.util.ArrayList;

public class DiscardLeaderCard extends Actions {
    private final LeaderCard leaderCard;
    private ArrayList<LeaderCard> cardsHand;
    private FaithTrack faithTrack;

    public DiscardLeaderCard(LeaderCard leaderCard, ArrayList<LeaderCard> cards,FaithTrack faithTrack) {
        this.leaderCard = leaderCard;
        this.cardsHand = cards;
        this.faithTrack = faithTrack;
    }

    @Override
    public void doAction(PlayerBoard playerBoard) {
        discard();
    }

    /**
     * discard the selected leader card and add a faith point to the faith track
     */
    public void discard() {
        for (int i=0; i<cardsHand.size(); i++) {
            if (cardsHand.get(i).equals(leaderCard)) {
                cardsHand.remove(i);
            }
        }
        faithTrack.increasePosition();
    }


}