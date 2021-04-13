package it.polimi.ingsw.actions;


import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.EmptyWarehouseException;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.playerboard.PlayerBoard;
import it.polimi.ingsw.playerboard.depot.BaseDepot;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Map;

public abstract class Actions {


    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
    }

    public boolean validAction(PlayerBoard playerBoard){
        return true;
    }

    public void payResources(PlayerBoard playerBoard, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources) throws InsufficientResourcesException, EmptyWarehouseException {
        ArrayList<BaseDepot> playerDepots =  playerBoard.getWarehouse().getDepots();
        for(Map.Entry<Resource, Integer> entry : warehouseResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 0; i < 3; i++) { //cambia il 3 quando avrò metodo
                if(key == playerDepots.get(i).getResource()){
                    playerDepots.get(i).removeResource(value);
                }
            }
        }
        for(Map.Entry<Resource, Integer> entry : leaderDepotResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 3; i < playerDepots.size(); i++) { //cambia il 3 quando avrò metodo
                if(key == playerDepots.get(i).getResource()){
                    playerDepots.get(i).removeResource(value);
                }
            }
        }
        for(Map.Entry<Resource, Integer> entry : strongboxResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            playerBoard.getStrongbox().removeResource(key, value);
        }
    }
}
