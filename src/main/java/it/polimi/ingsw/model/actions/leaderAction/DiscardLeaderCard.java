package it.polimi.ingsw.model.actions.leaderAction;

import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.faithTrack.FaithTrack;

import java.util.ArrayList;

public class DiscardLeaderCard extends Actions {
    private final LeaderCard leaderCard;
    private final ArrayList<LeaderCard> cardsHand;
    private final FaithTrack faithTrack;

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
