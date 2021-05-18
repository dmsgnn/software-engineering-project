package it.polimi.ingsw.model.actions.normalAction;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.CantPayException;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UseProduction extends Actions {
    private final ArrayList<Integer> developmentCardSlotIndex;
    private final ArrayList<Integer> leaderCardProdIndex;
    private final ArrayList<Resource> leaderCardProdGain;
    private final ArrayList<Resource> boardResources;
    private final Map<Resource, Integer> warehouseResources;
    private final Map<Resource, Integer> leaderDepotResources;
    private final Map<Resource, Integer> strongboxResources;

    public UseProduction(ArrayList<Integer> developmentCardSlotIndex, ArrayList<Integer> leaderCardProdIndex, ArrayList<Resource> leaderCardProdGain,
                         ArrayList<Resource> boardResources, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources,
                         Map<Resource, Integer> strongboxResources){
        this.developmentCardSlotIndex = developmentCardSlotIndex;
        this.leaderCardProdIndex = leaderCardProdIndex;
        this.leaderCardProdGain = leaderCardProdGain;
        this.boardResources = boardResources;
        this.warehouseResources = warehouseResources;
        this.leaderDepotResources = leaderDepotResources;
        this.strongboxResources = strongboxResources;
    }

    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if(!validAction(playerBoard)) throw new InvalidActionException();
        else{
            try {
                payResources(playerBoard, warehouseResources, leaderDepotResources, strongboxResources);
            } catch (InsufficientResourcesException | CantPayException e) {
                throw new InvalidActionException();
            }
            gainProduction(playerBoard);
        }
    }

    private void gainProduction(PlayerBoard playerBoard){
        Map<Resource, Integer> total = new HashMap<>();

        for(Resource resource : Resource.values()){
            total.put(resource, 0);
        }
        int faithPoints;

        if(!boardResources.isEmpty()) total.put(boardResources.get(2), total.get(boardResources.get(2))+1);

        for(Resource leaderGain: leaderCardProdGain){
            total.put(leaderGain, total.get(leaderGain)+1);
        }
        faithPoints = leaderCardProdIndex.size();

        for(Integer i: developmentCardSlotIndex){
            DevelopmentCard card = playerBoard.getSlots().get(i).lookTop();
            Map<Resource, Integer> tempResource = card.getProduction().getResourceGain();

            faithPoints = faithPoints + card.getProduction().getFaithGain();
            tempResource.forEach((key, value) -> total.put(key, total.get(key) + value));
        }

        //adds the produced resources to the strongbox
        total.forEach((key, value) -> playerBoard.getStrongbox().addResource(key, value));
        //increases faith points
        while (faithPoints>0){
            playerBoard.getFaithTrack().increasePosition();
            faithPoints--;
        }

    }

    @Override
    public boolean validAction(PlayerBoard playerBoard){
        if(leaderCardProdIndex.isEmpty() && developmentCardSlotIndex.isEmpty() && boardResources.isEmpty()) return false;
        if(!boardResources.isEmpty() && boardResources.size()<3) return false;
        //checks if the the player gave the correct resources
        Map<Resource, Integer> totalPayment = new HashMap<>();
        for(Resource resource: Resource.values()){
            totalPayment.put(resource, 0);
        }
        warehouseResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
        leaderDepotResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));
        strongboxResources.forEach((key, value) -> totalPayment.merge(key, value, Integer::sum));

        Map<Resource, Integer> totalCost = new HashMap<>();
        for(Resource resource: Resource.values()){
            totalCost.put(resource, 0);
        }

        for(Integer i: developmentCardSlotIndex){
            if(playerBoard.getSlots().get(i).isEmpty()) return false;
            DevelopmentCard card = playerBoard.getSlots().get(i).lookTop();
            Map<Resource, Integer> tempResource = card.getProduction().getProdCost();
            tempResource.forEach((key, value) -> totalCost.merge(key, value, Integer::sum));
        }
        for(Integer i: leaderCardProdIndex){
            if(playerBoard.getLeaderCardBuffs().getProductionBuff().size() < leaderCardProdIndex.size()) return false;
            Resource key = playerBoard.getLeaderCardBuffs().getProductionBuff().get(i);
            totalCost.put(key, totalCost.get(key)+1);
        }
        if(!boardResources.isEmpty()){
            totalCost.put(boardResources.get(0), totalCost.get(boardResources.get(0))+1);
            totalCost.put(boardResources.get(1), totalCost.get(boardResources.get(1))+1);
        }

        return totalPayment.equals(totalCost);
    }

}
