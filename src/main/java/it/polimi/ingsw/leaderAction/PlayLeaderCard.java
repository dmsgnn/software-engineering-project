package it.polimi.ingsw.leaderAction;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.leadercard.LeaderCard;

public class PlayLeaderCard implements LeaderAction{
    private final LeaderCard leaderCard;

    public PlayLeaderCard(LeaderCard leaderCard) {
        this.leaderCard = leaderCard;
    }

    /**
     * activates the selected leader card
     */
    @Override
    public void doLeaderAction(Player player) throws ErrorActivationLeaderCardException, ExchangeBuffErrorException, DiscountBuffErrorException, ZeroCapacityException, ProductionBuffErrorException {
        player.getPlayerBoard().addLeaderCard(leaderCard);
        leaderCard.activateCard(player.getPlayerBoard());    }
}
