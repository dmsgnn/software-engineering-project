package it.polimi.ingsw.client.representations;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientPlayerBoard {

    private final String playerNickname;
    private boolean connected;
    private ArrayList<String> hand = new ArrayList<>();
    private int handSize = 2;

    private ArrayList<String> playedCards = new ArrayList<>();
    private int playerPosition = 0;
    private final Map<Integer, Boolean> vaticanReports = new HashMap<>();
    private int lorenzoPosition = -1;
    private final Map<Integer, String> devCardSlot = new HashMap<>();

    private Map<Integer, ArrayList<Resource>> warehouse = new HashMap<>();
    private Map<Resource, Integer> strongbox = new HashMap<>();

    private final ArrayList<Resource> exchangeBuff = new ArrayList<>();
    private final ArrayList<Resource> discountBuff = new ArrayList<>();
    private final Map<String, Resource> productionBuff = new HashMap<>();
    private final Map<Integer, Resource> depotBuff = new HashMap<>();
    private final Map<String, Integer> depotBuffCard = new HashMap<>();

    /**
     * constructor
     * @param playerNickname nickname of the player
     */
    public ClientPlayerBoard(String playerNickname) {
        this.playerNickname = playerNickname;
        this.connected=true;
        for(int i=0; i<3; i++){
            warehouse.put(i, new ArrayList<>());
        }
        for(Resource rss: Resource.values()){
            strongbox.put(rss, 0);
        }
    }

    public Map<Integer, Resource> getDepotBuffRes() {
        return depotBuff;
    }

    public Map<String, Integer> getDepotBuffCard(){
        return depotBuffCard;
    }

    /**
     * @param depot selected depot
     * @return true if empty, false otherwise
     */
    public boolean isDepotEmpty(int depot){
        return warehouse.get(depot).isEmpty();
    }

    /**
     * called to know what resource is contained inside a specific depot
     * @param depot selected depot
     * @return resource contained inside the depot, null if empty
     */
    public Resource getWarehouseResource(int depot){
        if(warehouse.containsKey(depot)){
            if(depot<3) return warehouse.get(depot).get(0);
            else return depotBuff.get(depot);
        }
        else return null;
    }

    /**
     * called to know the total resources owned by the player in his depot
     * @return list of resources
     */
    public ArrayList<Resource> storedWarehouseRes(){
        ArrayList<Resource> temp = new ArrayList<>();
        for(Integer i : warehouse.keySet()){
            temp.addAll(warehouse.get(i));
        }
        return temp;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    //Getters

    public String getPlayerNickname() {
        return playerNickname;
    }

    public ArrayList<String> getHand() {
        return hand;
    }

    public ArrayList<String> getPlayedCards() {
        return playedCards;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public int getLorenzoPosition() {
        return lorenzoPosition;
    }

    public int getHandSize() {
        return handSize;
    }

    public Map<Integer, String> getDevCardSlot() {
        return devCardSlot;
    }

    public Map<Integer, ArrayList<Resource>> getWarehouse() {
        return warehouse;
    }

    public Map<Resource, Integer> getStrongbox() {
        return strongbox;
    }

    public ArrayList<Resource> getExchangeBuff() {
        return exchangeBuff;
    }

    public int getExchangeBuffsNum(){
        return exchangeBuff.size();
    }

    public ArrayList<Resource> getDiscountBuff() {
        return discountBuff;
    }

    public Map<Integer, Boolean> getVaticanReports() {
        return vaticanReports;
    }

    public Map<String, Resource> getProductionBuff() {
        return productionBuff;
    }

    public boolean isProductionBuffActive(){
        return productionBuff.size()!=0;
    }

    //Setters or updates


    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    /**
     * adds one played card to playedCards and activates the effect of the leadercardd
     * @param id id of the card
     * @param card leadercard
     */
    public void addPlayedCard(String id, LeaderCard card){
        playedCards.add(id);
        if(card!=null) card.getAbility().clientAbility(this);
    }

    /**
     * removes a card for the player's hand
     * @param id of the card
     */
    public void removeHandCard(String id){
        handSize--;
        hand.remove(id);
    }

    /**
     * sets the hand and changes the handsize accordingly
     * @param hand to set
     */
    public void setHand(ArrayList<String> hand) {
        handSize = hand.size();
        this.hand = hand;
    }

    public void setPlayedCards(ArrayList<String> playedCards) {
        this.playedCards = playedCards;
    }

    public void setVaticanReports(Integer pos, Boolean activated) {
        this.vaticanReports.put(pos, activated);
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setLorenzoPosition(int lorenzoPosition) {
        this.lorenzoPosition = lorenzoPosition;
    }

    public void setWarehouse(Map<Integer, ArrayList<Resource>> warehouse) {
        this.warehouse = warehouse;
    }

    public void setStrongbox(Map<Resource, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setDevCardSlot(ArrayList<String> devCardSlot) {
       for(int i = 0; i< devCardSlot.size(); i++){
           this.devCardSlot.put(i, devCardSlot.get(i));
       }
    }

    public void updateDevCardSlot(int slot, String id) {
        devCardSlot.put(slot, id);
    }

    public void addExchangeBuff(Resource buff){
        exchangeBuff.add(buff);
    }

    public void addDiscountBuff(Resource buff){
        discountBuff.add(buff);
    }

    public void addProductionBuff(Resource buff){
        productionBuff.put(playedCards.get(playedCards.size()-1), buff);
    }

    public void addDepotBuff(Resource res){
        depotBuffCard.put(playedCards.get(playedCards.size()-1),warehouse.size()-1);
        depotBuff.put(warehouse.size()-1, res);
    }
}
