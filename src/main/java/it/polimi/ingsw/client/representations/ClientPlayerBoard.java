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
    private Map<Integer, Boolean> vaticanReports = new HashMap<>();
    private int lorenzoPosition = -1;
    private Map<Integer, String> devCardSlot = new HashMap<>();

    private Map<Integer, ArrayList<Resource>> warehouse = new HashMap<>();
    private Map<Resource, Integer> strongbox = new HashMap<>();

    private ArrayList<Resource> exchangeBuff = new ArrayList<>();
    private ArrayList<Resource> discountBuff = new ArrayList<>();
    private Map<String, Resource> productionBuff = new HashMap<>();

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

    public boolean isDepotEmpty(int depot){
        return warehouse.get(depot).isEmpty();
    }

    public Resource getWarehouseResource(int depot){
        if(warehouse.containsKey(depot)){
            return warehouse.get(depot).get(0);
        }
        else return null;
    }

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

    public void addPlayedCard(String id, LeaderCard card){
        playedCards.add(id);
        if(card!=null) card.getAbility().clientAbility(this);
    }

    public void setPlayedCards(ArrayList<String> playedCards) {
        this.playedCards = playedCards;
    }

    public void setVaticanReports(Integer pos, Boolean activated) {
        this.vaticanReports.put(pos, activated);
    }

    public void removeHandCard(String id){
        handSize--;
        hand.remove(id);
    }

    public void setHand(ArrayList<String> hand) {
        this.hand = hand;
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

    public void updateWarehouse(Map<Integer, ArrayList<Resource>> warehouse) {
        for(Map.Entry<Integer, ArrayList<Resource>> entry : warehouse.entrySet()){
            Integer key = entry.getKey();
            ArrayList<Resource> value = entry.getValue();
            this.warehouse.put(key, value);
        }
    }

    public void updateStrongbox(Map<Resource, Integer> strongbox) {
        for(Map.Entry<Resource, Integer> entry : strongbox.entrySet()){
            Resource key = entry.getKey();
            Integer value = entry.getValue();
            this.strongbox.put(key, value);
        }
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
}
