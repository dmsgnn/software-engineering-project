package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.playerboard.faithTrack.PlayerFaithTrack;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.leadercard.LeaderCard;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerBoard {
    private Warehouse warehouse;
    private ArrayList<LeaderCard> leaderCards;
    private Strongbox strongbox;
    private ArrayList<DevelopmentCardSlot> slots;
    private PlayerFaithTrack faithTrack;
    private CardBuffs leaderCardBuffs;
    private HashMap<Color,Integer> level1;
    private HashMap<Color,Integer> level2;
    private HashMap<Color,Integer> level3;
    private HashMap<Integer,HashMap<Color,Integer>> colorRequirements;

    /**
     * creation of the playerboard
     * @param player who owns the playerboard
     * @throws ZeroCapacityException if there is a warehouse configuration error
     */
    public PlayerBoard(Player player, Game game) throws ZeroCapacityException {
        colorRequirements = new HashMap<>();
        level1 = level2= level3= new HashMap<Color,Integer>(){{
            put(Color.YELLOW,0);
            put(Color.BLUE,0);
            put(Color.GREEN,0);
            put(Color.PURPLE,0);
        }};
        colorRequirements.put(1, level1);
        colorRequirements.put(2, level2);
        colorRequirements.put(3, level3);
        warehouse = new Warehouse();
        leaderCards = new ArrayList<LeaderCard>();
        strongbox = new Strongbox();
        slots = new ArrayList<DevelopmentCardSlot>();
        slots.add(new DevelopmentCardSlot());
        slots.add(new DevelopmentCardSlot());
        slots.add(new DevelopmentCardSlot());
        faithTrack = new PlayerFaithTrack(game);
        leaderCardBuffs = new CardBuffs();
    }


    //public void manageResources(){}

    /**
     * activates a card and adds it to the playerboard
     * @param card that must be activated
     */
    public void addLeaderCard(LeaderCard card){
        leaderCards.add(card);
    }

    /**
     * basic production operation
     * @param cost1 to activate production
     * @param cost2 to activate production
     * @param gain obtained from production
     * @throws EmptyWarehouseException
     * @throws InsufficientResourcesException
     * @throws ZeroCapacityException
     * @throws WrongResourceException
     * @throws FullWarehouseException
     */
    public void boardProduction(Resource cost1, Resource cost2, Resource gain) throws EmptyWarehouseException, InsufficientResourcesException, ZeroCapacityException, WrongResourceException, FullWarehouseException {
        Map<Resource, Integer> resources = warehouse.storedResources();
        if ((resources.get(cost1)!=0)&&(resources.get(cost2)!=0)){
            for (int i=0; i<warehouse.getDepots().size(); i++){
                if (warehouse.getDepots().get(i).getResource()==cost1){
                warehouse.getDepots().get(i).removeResource(1);
                }
                if (warehouse.getDepots().get(i).getResource()==cost2){
                    warehouse.getDepots().get(i).removeResource(1);
                }
            }
        }
        strongbox.addResource(gain,1);
    }

    /**
     * check if seven development cards have been activated
     * @return true/false
     */
    public boolean sevenCards(){
        int a = slots.get(0).numOfCards();
        int b = slots.get(1).numOfCards();
        int c = slots.get(2).numOfCards();
        return (a + b + c) == 7;

    }

    public HashMap<Integer, HashMap<Color, Integer>> getColorRequirements() {
        return colorRequirements;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
    public Strongbox getStrongbox() {
        return strongbox;
    }
    public ArrayList<DevelopmentCardSlot> getSlots() {
        return slots;
    }
    public PlayerFaithTrack getFaithTrack() {
        return faithTrack;
    }
    public CardBuffs getLeaderCardBuffs() {
        return leaderCardBuffs;
    }
}
