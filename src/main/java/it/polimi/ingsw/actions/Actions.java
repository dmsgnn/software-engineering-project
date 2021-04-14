package it.polimi.ingsw.actions;


import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.CantPayException;
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

    public void payResources(PlayerBoard playerBoard, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources) throws InsufficientResourcesException, EmptyWarehouseException, CantPayException {
        if(!enoughResPossessed(playerBoard, warehouseResources, leaderDepotResources, strongboxResources)) {
            throw new CantPayException();
        }
        ArrayList<BaseDepot> playerDepots =  playerBoard.getWarehouse().getDepots();
        for(Map.Entry<Resource, Integer> entry : warehouseResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = 0; i < playerBoard.getWarehouse().getBaseDepotsNum(); i++) { //cambia il 3 quando avrò metodo
                if(key == playerDepots.get(i).getResource()){
                    playerDepots.get(i).removeResource(value);
                }
            }
        }
        for(Map.Entry<Resource, Integer> entry : leaderDepotResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            for (int i = playerBoard.getWarehouse().getBaseDepotsNum(); i < playerBoard.getWarehouse().getDepotsNum(); i++) { //cambia il 3 quando avrò metodo

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

    private boolean enoughResPossessed(PlayerBoard playerBoard, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources){
        ArrayList<BaseDepot> playerDepots =  playerBoard.getWarehouse().getDepots();
        //controls if the player can pay the resources he said
        for(Map.Entry<Resource, Integer> entry : warehouseResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            boolean flag = false; //is false if the one resource isn't contained inside the depots
            for (int i = 0; i < playerBoard.getWarehouse().getBaseDepotsNum(); i++) {
                if(key == playerDepots.get(i).getResource()){
                    flag = true;
                    if(playerDepots.get(i).getOccupied()<value) return false;
                }
            }
            if(!flag) return false;
        }
        for(Map.Entry<Resource, Integer> entry : leaderDepotResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            boolean flag = false; //is false if the one resource isn't containted inside the leaderDepot
            for (int i = playerBoard.getWarehouse().getBaseDepotsNum(); i < playerBoard.getWarehouse().getDepotsNum(); i++) {
                if(key == playerDepots.get(i).getResource()){
                    flag = true;
                    if(playerDepots.get(i).getOccupied()<value) return false;
                }
            }
            if(!flag) return false;
        }
        for(Map.Entry<Resource, Integer> entry : strongboxResources.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            if(playerBoard.getStrongbox().getResources().get(key)<value) return false;
        }

        return true;
    }
}
