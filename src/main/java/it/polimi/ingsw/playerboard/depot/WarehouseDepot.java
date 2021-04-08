package it.polimi.ingsw.playerboard.depot;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.FullWarehouseException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.playerboard.depot.BaseDepot;

public class WarehouseDepot extends BaseDepot {

    public WarehouseDepot(int capacity, int occupied, Resource resource) throws ZeroCapacityException {
        super(capacity, occupied, resource);
    }


    @Override
    public void addResources(Resource newResource) throws ZeroCapacityException, FullWarehouseException {
        int occupied;
        //case capacity=0
        if (getCapacity() ==0) {throw new ZeroCapacityException();}
        //increase amount
        if (getResource() == newResource) {
            //isFull or not
            if (!isFull()){
                occupied= getOccupied() +1;
                setOccupied(occupied);
            }
            else throw new FullWarehouseException();

        }
        //new resource
        else{

            setResource(newResource);
            setOccupied(1);
        }

    }

}
