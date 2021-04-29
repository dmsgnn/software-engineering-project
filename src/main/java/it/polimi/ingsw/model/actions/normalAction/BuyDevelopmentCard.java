package it.polimi.ingsw.model.actions.normalAction;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.Gameboard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

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
    private int slotNumber;

    public BuyDevelopmentCard(Gameboard board, HashMap<Resource, Integer> depotResources, HashMap<Resource, Integer> strongboxResources, HashMap<Resource, Integer> cardDepotResources, Color color, int level, int slotNumber) {
        this.warehouseDepotResources = depotResources;
        this.strongboxResources = strongboxResources;
        this.cardDepotResources = cardDepotResources;
        this.board = board;
        this.color = color;
        this.level = level;
        this.slotNumber = slotNumber;
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
        return (board.viewCard(color, level).checkCardRequirements(totalPayment) && playerBoard.getSlots().get(slotNumber-1).validAction(board.viewCard(color, level)));
    }

    /**
     * checks if the action can be done and then make the payment and buy the card and place it in the development card slot
     * @throws InvalidActionException if the action can't be done because the player hasn't make a correct selection or if he doesn't have
     * enough resources to buy the card
     */
    @Override
    public void doAction(PlayerBoard playerBoard) throws WrongLevelException, NoCardsLeftException, InvalidActionException {
        if (!validAction(playerBoard)) throw new InvalidActionException();
        else {
            try {
                payResources(playerBoard, warehouseDepotResources, cardDepotResources, strongboxResources);
            } catch (InsufficientResourcesException | CantPayException e) {
                throw new InvalidActionException();
            }
            playerBoard.getSlots().get(slotNumber-1).addCardOnTop(board.buyCard(color, level), playerBoard);
        }
    }
}