package it.polimi.ingsw.model.playerboard.depot;

import it.polimi.ingsw.model.Resource;


public class BaseDepot {
    private int capacity;
    private int occupied;
    private Resource resource;

    public BaseDepot(int capacity, int occupied, Resource resource) {
        this.capacity = capacity;
        this.occupied = occupied;
        this.resource = resource;
    }

    /**   Add/change resource in the depot
     * @return
     */
    public boolean addResources(Resource newResource){return false;}

    /**
     * remove an amount from the depot
     * @param amount to remove
     */
    public void removeResource(int amount) {
        this.occupied -= amount;
        if (this.occupied==0) this.resource=null;
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
    public boolean correctResource(Resource resource1){
        return  true;
    }
}
