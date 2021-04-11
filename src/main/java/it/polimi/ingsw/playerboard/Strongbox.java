package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Resource;

import javax.naming.InsufficientResourcesException;
import java.util.HashMap;
import java.util.Map;

public class Strongbox {

    private Map<Resource, Integer> resources;

    /**
     * initialize the strongbox
     *
     */
    public Strongbox() {
        resources = new HashMap<>();
        resources.put(Resource.STONES, 0);
        resources.put(Resource.COINS, 0);
        resources.put(Resource.SERVANTS, 0);
        resources.put(Resource.SHIELDS, 0);

    }

    /**
     * enters a certain amount of a resource
     * @param resource
     * @param amount
     */
    public void addResource(Resource resource, int amount) {
        this.resources.replace(resource, this.resources.get(resource), amount + this.resources.get(resource));
    }

    /**
     * removes a certain amount of a resource
     * @param resource
     * @param amount
     */
    public void removeResource(Resource resource, int amount) throws InsufficientResourcesException {
        if (this.resources.get(resource)< amount){
            throw new InsufficientResourcesException();
        }
        else {
            this.resources.replace(resource, this.resources.get(resource), this.resources.get(resource) - amount);
        }
    }

    /**
     * returns the map
     * @return
     */
    public Map<Resource, Integer> getResources() {
        return this.resources;
    }

    /**
     *
     * @param resource
     * @return
     */
    public int getValue(Resource resource){
        return resources.get(resource);

    }
}