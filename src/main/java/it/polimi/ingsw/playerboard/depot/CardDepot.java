package it.polimi.ingsw.playerboard.depot;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.FullWarehouseException;
import it.polimi.ingsw.exceptions.WrongResourceException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.playerboard.depot.BaseDepot;

public class CardDepot extends BaseDepot {
    public CardDepot(int capacity, int occupied, Resource resource) throws ZeroCapacityException {
        super(capacity, occupied, resource);
    }


    /**
     * takes in a resource and if it is of the right type,
     * increases the amount of the depot by one
     * @param newResource
     */
    public void addResources(Resource newResource) throws ZeroCapacityException, FullWarehouseException, WrongResourceException {
        //case capacity=0
        if (getCapacity()==0) {throw new ZeroCapacityException();}
        if (getResource() == newResource) {
            //isFull or not
            if (!isFull()){
                setOccupied(getOccupied()+1);
            }
            else throw new FullWarehouseException();

        }

        else throw new WrongResourceException();
    }
}
