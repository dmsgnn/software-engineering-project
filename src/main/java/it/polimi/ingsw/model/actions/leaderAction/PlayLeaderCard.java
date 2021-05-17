package it.polimi.ingsw.model.actions.leaderAction;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayLeaderCard extends Actions {
    private final LeaderCard leaderCard;
    private final HashMap<Resource, Integer> warehouseDepotResources;
    private final HashMap<Resource, Integer> strongboxResources;
    private final HashMap<Resource, Integer> cardDepotResources;
    private final ArrayList<LeaderCard> cardsHand;

    public PlayLeaderCard(ArrayList<LeaderCard> cardsHand, LeaderCard leaderCard, HashMap<Resource, Integer> warehouseDepotResources, HashMap<Resource, Integer> strongboxResources, HashMap<Resource, Integer> cardDepotResources) {
        this.cardsHand = cardsHand;
        this.leaderCard = leaderCard;
        this.warehouseDepotResources = warehouseDepotResources;
        this.strongboxResources = strongboxResources;
        this.cardDepotResources = cardDepotResources;

    }

    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
       if (!validAction(playerBoard)) throw new InvalidActionException();
       else{
           //payResources(playerBoard,warehouseDepotResources,cardDepotResources,strongboxResources);
           playLeaderCard(playerBoard);

       }

    }

    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        boolean check = false;
        for (LeaderCard card : cardsHand) {
            if (card.equals(leaderCard)) {
                check = true;
                break;
            }
        }
        if (!check) return false;
        // checks that the received resources are the same of the cost of the card that the player wants to buy
        if (!leaderCard.getRequirements().IsColor()) {
            Map<Resource, Integer> totalPayment = new HashMap<>();
            for (Resource resource : Resource.values()) {
                totalPayment.put(resource, 0);
            }
            warehouseDepotResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
            cardDepotResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
            strongboxResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
            for (Resource resource : Resource.values()) {
                if (!totalPayment.get(resource).equals(leaderCard.getRequirements().getRequirements(playerBoard).get(resource)))
                    return false;
            }
            return true;
        }
        else {
            return leaderCard.getRequirements().checkRequirements(playerBoard);
        }
    }

    /**
     * activates the selected leader card
     */
    public void playLeaderCard(PlayerBoard playerBoard) {
        playerBoard.addLeaderCard(leaderCard);
        leaderCard.activateCard(playerBoard);
        for (int i = 0; i < cardsHand.size(); i++) {
            if (cardsHand.get(i).equals(leaderCard)) {
                cardsHand.remove(i);
                break;
            }
        }
    }
}
