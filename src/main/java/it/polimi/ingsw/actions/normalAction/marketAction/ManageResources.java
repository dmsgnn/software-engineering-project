package it.polimi.ingsw.actions.normalAction.marketAction;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.actions.Actions;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.playerboard.PlayerBoard;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;

public class ManageResources extends Actions {

    private final ArrayList<ArrayList<Resource>> resources;

    public ManageResources(ArrayList<ArrayList<Resource>> resources) {
        this.resources = resources;
    }

    /**
     * set the new warehouse configuration
     * @param playerBoard of the current player
     */
    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if(!validAction(playerBoard)) throw new InvalidActionException();
        else{
            try {
                manageRes(playerBoard);
            }catch (InsufficientResourcesException | EmptyWarehouseException | FullWarehouseException | ZeroCapacityException | WrongResourceException e) {
                throw new InvalidActionException();
            }
        }
    }

    /**
     * check the validity of the action
     * @param playerBoard of the current player
     * @return true if the action is valid
     */
    @Override
    public boolean validAction(PlayerBoard playerBoard) {

        //if the array.size is wrong
        if (playerBoard.getWarehouse().getDepots().size()< resources.size()) return false;
        for (int i=0; i< resources.size();i++) {

            //if the depots have the same resources
            if (i!=0){
                if (resources.get(0).get(0)== resources.get(i).get(0)) return false;
            }

            //if the capacity is wrong
            if (playerBoard.getWarehouse().getDepots().get(i).getCapacity() < resources.get(i).size())return false;

            //if the resources of the depot are wrong
            for (int j=0;j< resources.size();j++){
                if (resources.get(i).get(0)!=resources.get(i).get(j)) return false;
            }

        }

        return true;
    }


    /**
     * clears the depots and fills them with the new organization
     * @param playerBoard of the current player
     */
    public void manageRes(PlayerBoard playerBoard) throws InsufficientResourcesException, EmptyWarehouseException, FullWarehouseException, ZeroCapacityException, WrongResourceException {
        //empty depots
        for (int i=0;i<playerBoard.getWarehouse().getDepots().size();i++) {
            playerBoard.getWarehouse().getDepots().get(i).removeResource(playerBoard.getWarehouse().getDepots().get(i).getOccupied());
        }
        //add resources
        for (int i=0;i< resources.size();i++){
            for (int j=0;i<resources.get(i).size();j++) {
                playerBoard.getWarehouse().getDepots().get(i).addResources(resources.get(i).get(j));
            }
        }
    }
}
