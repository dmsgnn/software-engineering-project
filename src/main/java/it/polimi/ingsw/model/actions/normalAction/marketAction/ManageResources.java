package it.polimi.ingsw.model.actions.normalAction.marketAction;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import it.polimi.ingsw.model.playerboard.faithTrack.FaithTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class  ManageResources extends Actions {

    private final ArrayList<ArrayList<Resource>> resources;
    private final Map<Resource,Integer> newResources;
    private final Map<Resource,Integer> discResources;
    private final boolean initial;
    private final FaithTrack faithTrack;

    public ManageResources(Map<Integer, ArrayList<Resource>> resources, Map<Resource, Integer> res, Map<Resource, Integer> discResources, boolean initial, FaithTrack faithTrack) {
        this.resources = new ArrayList<ArrayList<Resource>>(){{
           for (int i=0;i<resources.size();i++){
               add(i,resources.get(i));
           }
        }};
        this.newResources = res;
        this.discResources = discResources;
        this.initial = initial;
        this.faithTrack = faithTrack;
    }

    /**
     * set the new warehouse configuration
     * @param playerBoard of the current player
     */
    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if(!validAction(playerBoard)) throw new InvalidActionException();
        else{

            manageResources(playerBoard);

        }
    }

    /**
     * check the validity of the action
     * @param playerBoard of the current player
     * @return true if the action is valid
     */
    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        if (!initial) {
            Map<Resource, Integer> totalRes = new HashMap<>();
            Map<Resource,Integer> stored ;
            stored = new HashMap<>(playerBoard.getWarehouse().storedResources());
            for (Resource resource : Resource.values()) {
                totalRes.put(resource, stored.get(resource) + newResources.get(resource));
            }

            //if the array.size is wrong
            if (playerBoard.getWarehouse().getDepots().size() < resources.size()) return false;
            for (int i = 0; i < resources.size(); i++) {

                //no cheat
                if (resources.get(i).size()!=0){
                    if (resources.size()==3) {
                        if ((resources.get(i).size() + discResources.get(resources.get(i).get(0))) != totalRes.get(resources.get(i).get(0)))
                            return false;
                    }
                }

                //if the depots have the same resources
                if (i != 0) {
                    if (resources.get(0).size() != 0 && resources.get(i).size() != 0) {
                        if (i<3) {
                            if (resources.get(0).get(0).equals(resources.get(i).get(0))) return false;
                        }
                    }
                }

                //if the capacity is wrong
                if (playerBoard.getWarehouse().getDepots().get(i).getCapacity() < resources.get(i).size()) return false;

                //if the resources of the depot are wrong
                for (int j = 0; j < resources.get(i).size(); j++) {
                    if (!(resources.get(i).get(0).equals(resources.get(i).get(j)))) return false;
                }

            }
            if (resources.size()==4){
                if (!resources.get(3).isEmpty()){
                    if (!playerBoard.getWarehouse().getDepots().get(3).correctResource(resources.get(3).get(0))) return false;
                }
            }
            if (resources.size()==5){
                if (!resources.get(3).isEmpty()){
                    if (!playerBoard.getWarehouse().getDepots().get(3).correctResource(resources.get(3).get(0))) return false;
                }
                if (!resources.get(4).isEmpty()){
                    return playerBoard.getWarehouse().getDepots().get(4).correctResource(resources.get(4).get(0));
                }
            }

        }
        return true;
    }


    /**
     * clears the depots and fills them with the new organization
     * @param playerBoard of the current player
     */
    public void manageResources(PlayerBoard playerBoard) {
        //empty depots
        if (!initial){
            for (int i=0;i<playerBoard.getWarehouse().getDepots().size();i++) {
                playerBoard.getWarehouse().getDepots().get(i).removeResource(playerBoard.getWarehouse().getDepots().get(i).getOccupied());
            }
        }
        //add resources
        for (int i=0;i< resources.size();i++){
            for (int j=0;j<resources.get(i).size();j++) {
                playerBoard.getWarehouse().getDepots().get(i).addResources(resources.get(i).get(j));
            }
        }
        if (discResources!=null) {
            int size = 0;
            for (Resource resource : Resource.values()) {
                size += discResources.get(resource);
            }
            for (int i = 0; i < size; i++) {
                faithTrack.increaseAllPositions();
            }
        }

    }
}
