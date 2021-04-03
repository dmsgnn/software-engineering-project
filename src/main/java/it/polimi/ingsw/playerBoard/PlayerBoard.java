package it.polimi.ingsw.playerBoard;

import it.polimi.ingsw.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.leaderCard.LeaderCard;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Map;

public class PlayerBoard {
    private Game game;
    private Player player;
    private Warehouse warehouse;
    private ArrayList<LeaderCard> leaderCards;
    private Strongbox strongbox;
    private ArrayList<DevelopmentCardSlot> slots;
    private PlayerFaithTrack faithTrack;
    private boolean activeDiscountCard;
    private CardBuffs leaderCardBuffs;

    /**
     * creation of the playerboard
     * @param player who owns the playerboard
     * @throws ZeroCapacityException if there is a warehouse configuration error
     */
    public PlayerBoard(Player player, Game game) throws ZeroCapacityException {
        this.player = player ;
        this.game = game;
        warehouse = new Warehouse();
        leaderCards = new ArrayList<LeaderCard>();
        strongbox = new Strongbox();
        slots = new ArrayList<DevelopmentCardSlot>();
        slots.add(new DevelopmentCardSlot());
        slots.add(new DevelopmentCardSlot());
        slots.add(new DevelopmentCardSlot());
        faithTrack = new PlayerFaithTrack(this.game);
        leaderCardBuffs = new CardBuffs();
    }


    //public void manageResources(){}

    /**
     * activates a card and adds it to the playerboard
     * @param card that must be activated
     */
    public void addLeaderCard(LeaderCard card) throws ErrorActivationLeaderCardException {

        if (!leaderCards.add(card)) throw new ErrorActivationLeaderCardException();
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
        if ((a+b+c) ==7){
            return true;
        }
        else return false;

    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
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

    public boolean isActiveDiscountCard() {
        return activeDiscountCard;
    }

    public CardBuffs getLeaderCardBuffs() {
        return leaderCardBuffs;
    }
}
