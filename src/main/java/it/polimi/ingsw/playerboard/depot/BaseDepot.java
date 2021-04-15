package it.polimi.ingsw.playerboard.depot;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.EmptyWarehouseException;
import it.polimi.ingsw.exceptions.FullWarehouseException;
import it.polimi.ingsw.exceptions.WrongResourceException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;

import javax.naming.InsufficientResourcesException;

public class BaseDepot {
    private int capacity;
    private int occupied;
    private Resource resource;

    public BaseDepot(int capacity, int occupied, Resource resource) throws ZeroCapacityException {
        this.capacity = capacity;
        if (this.capacity==0) {
            throw new ZeroCapacityException();
        }
        this.occupied = occupied;
        this.resource = resource;
    }

    /**   Add/change resource in the depot
     */
    public void addResources(Resource newResource) throws ZeroCapacityException, FullWarehouseException, WrongResourceException {}

    /**
     * remove an amount from the depot
     * @param amount to remove
     */
    public void removeResource(int amount) throws EmptyWarehouseException, InsufficientResourcesException {
        if (isEmpty()) throw new EmptyWarehouseException();
        if ((this.occupied - amount) >= 0) {
            this.occupied -= amount;
            if (this.occupied==0) this.resource=null;
        }
        else throw new InsufficientResourcesException();
    }

    /**
     *  depot full or not
     * @return true if the depot is full
     */
    public boolean isFull(){
        if(this.occupied == this.capacity){
            return true;
        }
        else return false;
    }


    /**
     *  depot empty or not
     * @return true if the depot is empty
     */
    public boolean isEmpty() {
        if (this.occupied == 0) {
            return true;
        } else return false;
    }

    /**
     * available space  in depot
     * @return the space left in the depot
     */
    public int spaceLeft(){
        return  (capacity - occupied);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public Resource getResource() {
        return resource;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
