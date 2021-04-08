package it.polimi.ingsw.leaderAction;

import it.polimi.ingsw.Player;
import it.polimi.ingsw.leadercard.LeaderCard;

public class DiscardLeaderCard implements LeaderAction{
    private final LeaderCard leaderCard;

    public DiscardLeaderCard(LeaderCard leaderCard) {
        this.leaderCard = leaderCard;
    }

    /**
     * discard the selected leader card and add a faith point to the faith track
     */
    @Override
    public void doLeaderAction(Player player) {
        player.discardLeaderCard(leaderCard);
        player.getPlayerBoard().getFaithTrack().increaseVictoryPoints(1);
    }
}
