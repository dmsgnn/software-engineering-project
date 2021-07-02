package it.polimi.ingsw.model.actions;


import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.exceptions.CantPayException;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.depot.BaseDepot;
import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Map;

/**
 * abstract class extended by all the actions
 */
public abstract class Actions {

    /**
     * performs the action
     * @param playerBoard playerBoard of the current player
     * @throws InvalidActionException called if the player can't perform the action
     */
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException, InsufficientResourcesException, WrongLevelException, NoCardsLeftException {
    }

    /**
     * controls if the action can be performed
     * @param playerBoard of the current player
     */
    public boolean validAction(PlayerBoard playerBoard) throws NoCardsLeftException, WrongLevelException {
        return true;
    }

    /**
     * called to pay the specified resources
     * @param playerBoard of the current player
     * @param warehouseResources resources to pay from the warehouse
     * @param leaderDepotResources resources to pay from the leadercards depots
     * @param strongboxResources resources to pay from the strongbox
     * @throws InsufficientResourcesException if the player didn't select enough resources
     * @throws CantPayException if the player can't pay what he declared
     */
    public void payResources(PlayerBoard playerBoard, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources) throws InsufficientResourcesException, CantPayException {
        if(!enoughResPossessed(playerBoard, warehouseResources, leaderDepotResources, strongboxResources)) {
            throw new CantPayException();
        }
        //removes the resources that the player wants to pay from the warehouse
        ArrayList<BaseDepot> playerDepots =  playerBoard.getWarehouse().getDepots();
        for(Map.Entry<Resource, Integer> entry : warehouseResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 0; i < playerBoard.getWarehouse().getBaseDepotsNum(); i++) {
                if(key == playerDepots.get(i).getResource()){
                    playerDepots.get(i).removeResource(value);
                }
            }
        }
        //removes the resources that the player wants to pay from the leadercards
        for(Map.Entry<Resource, Integer> entry : leaderDepotResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = playerBoard.getWarehouse().getBaseDepotsNum(); i < playerBoard.getWarehouse().getDepotsNum(); i++) {
                if(key == playerDepots.get(i).getResource()){
                    playerDepots.get(i).removeResource(value);
                }
            }
        }
        //removes the resources that the player wants to pay from the strongbox
        for(Map.Entry<Resource, Integer> entry : strongboxResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            playerBoard.getStrongbox().removeResource(key, value);
        }
    }

    /**
     * called to check if the player has enough resources
     * @param playerBoard of the current player
     * @param warehouseResources resources to pay from the warehouse
     * @param leaderDepotResources resources to pay from the leadercards depots
     * @param strongboxResources resources to pay from the strongbox
     * @return true if he can pay, false otherwise
     */
    private boolean enoughResPossessed(PlayerBoard playerBoard, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources){
        ArrayList<BaseDepot> playerDepots =  playerBoard.getWarehouse().getDepots();
        //controls if the player can pay the resources he selected
        for(Map.Entry<Resource, Integer> entry : warehouseResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            boolean flag = false; //is false if the one resource isn't contained inside the depots
            if (value == 0) flag = true;
            else if (value < 0) return false;
            else {
                for (int i = 0; i < playerBoard.getWarehouse().getBaseDepotsNum(); i++) {
                    if (key == playerDepots.get(i).getResource()) {
                        flag = true;
                        if (playerDepots.get(i).getOccupied() < value) return false;
                    }
                }
            }
            if(!flag) return false;
        }
        for(Map.Entry<Resource, Integer> entry : leaderDepotResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            boolean flag = false; //is false if the one resource isn't contained inside the leaderDepot
            if (value == 0) flag = true;
            else if (value < 0) return false;
            else {
                for (int i = playerBoard.getWarehouse().getBaseDepotsNum(); i < playerBoard.getWarehouse().getDepotsNum(); i++) {
                    if (key == playerDepots.get(i).getResource()) {
                        flag = true;
                        if (playerDepots.get(i).getOccupied() < value) return false;
                    }
                }
            }
            if(!flag) return false;
        }
        for(Map.Entry<Resource, Integer> entry : strongboxResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            if (value < 0) return false;
            if(playerBoard.getStrongbox().getResources().get(key)<value) return false;
        }

        return true;
    }
}
