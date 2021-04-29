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

public abstract class Actions {


    public void doAction(PlayerBoard playerBoard) throws InvalidActionException, InsufficientResourcesException, WrongLevelException, NoCardsLeftException {
    }

    public boolean validAction(PlayerBoard playerBoard) throws NoCardsLeftException, WrongLevelException {
        return true;
    }

    public void payResources(PlayerBoard playerBoard, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources, Map<Resource, Integer> strongboxResources) throws InsufficientResourcesException, CantPayException {
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
