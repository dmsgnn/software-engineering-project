package it.polimi.ingsw.actions.normalAction;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.actions.Actions;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.gameboard.Gameboard;
import it.polimi.ingsw.playerboard.PlayerBoard;

import javax.naming.InsufficientResourcesException;
import java.util.HashMap;
import java.util.Map;

public class BuyDevelopmentCard extends Actions {
    private Map<Resource, Integer> warehouseDepotResources;
    private Map<Resource, Integer> strongboxResources;
    private Map<Resource, Integer> cardDepotResources;
    private Gameboard board;
    private Color color;
    private int level;

    public BuyDevelopmentCard(Gameboard board, HashMap<Resource, Integer> depotResources, HashMap<Resource, Integer> strongboxResources, HashMap<Resource, Integer> cardDepotResources, Color color, int level) {
        this.warehouseDepotResources = depotResources;
        this.strongboxResources = strongboxResources;
        this.cardDepotResources = cardDepotResources;
        this.board = board;
        this.color = color;
        this.level = level;
    }


    /**
     * makes new map with a sum of all player's selected resources and check that they are equals to the cost of the card he wants to buy
     * @return true if the selection of resources is right
     */
    @Override
    public boolean validAction(PlayerBoard playerBoard) throws NoCardsLeftException, WrongLevelException {
        // checks that the received resources are the same of the cost of the card that the player wants to buy
        Map<Resource, Integer> totalPayment = new HashMap<>();
        for(Resource resource: Resource.values()){
            totalPayment.put(resource, 0);
        }
        warehouseDepotResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
        cardDepotResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
        strongboxResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
        return board.viewCard(color, level).checkCardRequirements(totalPayment);
    }

    /**
     * checks if the action can be done and then make the payment and buy the card
     * @throws InvalidActionException if the action can't be done because the player hasn't make a correct selection or if he doesn't have
     * enough resources to buy the card
     */
    @Override
    public void doAction(PlayerBoard playerBoard) throws WrongLevelException, NoCardsLeftException, InvalidActionException {
        if (!validAction(playerBoard)) throw new InvalidActionException();
        else {
            try {
                payResources(playerBoard, warehouseDepotResources, cardDepotResources, strongboxResources);
            } catch (InsufficientResourcesException | EmptyWarehouseException | CantPayException e) {
                throw new InvalidActionException();
            }
            board.buyCard(color, level);
        }
    }
}