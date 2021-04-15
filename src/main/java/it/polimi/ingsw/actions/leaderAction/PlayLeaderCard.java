package it.polimi.ingsw.actions.leaderAction;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.actions.Actions;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.playerboard.PlayerBoard;
import javax.naming.InsufficientResourcesException;
import java.util.HashMap;
import java.util.Map;

public class PlayLeaderCard extends Actions {
    private final LeaderCard leaderCard;
    private Map<Resource, Integer> warehouseDepotResources;
    private Map<Resource, Integer> strongboxResources;
    private Map<Resource, Integer> cardDepotResources;

    public PlayLeaderCard(LeaderCard leaderCard, Map<Resource, Integer> warehouseDepotResources, Map<Resource, Integer> strongboxResources, Map<Resource, Integer> cardDepotResources) {
        this.leaderCard = leaderCard;
        this.warehouseDepotResources = warehouseDepotResources;
        this.strongboxResources = strongboxResources;
        this.cardDepotResources = cardDepotResources;
    }

    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
       if (!validAction(playerBoard)) throw new InvalidActionException();
       else{
           try {
               payResources(playerBoard,warehouseDepotResources,cardDepotResources,strongboxResources);
               playLeaderCard(playerBoard);
           } catch (CantPayException | InsufficientResourcesException | EmptyWarehouseException | ZeroCapacityException e) {
               throw new InvalidActionException();
           }
       }

    }

    @Override
    public boolean validAction(PlayerBoard playerBoard) {
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
    public void playLeaderCard(PlayerBoard playerBoard) throws ZeroCapacityException {
        playerBoard.addLeaderCard(leaderCard);
        leaderCard.activateCard(playerBoard);    }
}
