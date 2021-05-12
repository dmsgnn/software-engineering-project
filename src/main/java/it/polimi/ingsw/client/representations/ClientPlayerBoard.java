package it.polimi.ingsw.client.representations;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientPlayerBoard {

    private final String playerNickname;
    private ArrayList<String> hand = new ArrayList<>();

    private ArrayList<String> playedCards = new ArrayList<>();
    private int playerPosition = 0;
    private Map<Integer, Boolean> vaticanReports = new HashMap<>();
    private int lorenzoPosition = -1;
    private Map<Integer, String> devCardSlot = new HashMap<>();

    private Map<Integer, ArrayList<Resource>> warehouse = new HashMap<>();
    private Map<Resource, Integer> strongbox = new HashMap<>();

    private ArrayList<Resource> exchangeBuff = new ArrayList<>();
    private ArrayList<Resource> discountBuff = new ArrayList<>();
    private ArrayList<Resource> productionBuff = new ArrayList<>();

    public ClientPlayerBoard(String playerNickname) {
        this.playerNickname = playerNickname;
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

    public ArrayList<Resource> getProductionBuff() {
        return productionBuff;
    }

    public boolean isProductionBuffActive(){
        return productionBuff.size()!=0;
    }

    public void addPlayedCard(String id){
        playedCards.add(id);
        //metodo per aggiungere buff nel posto giusto?
    }

    public void setPlayedCards(ArrayList<String> playedCards) {
        this.playedCards = playedCards;
    }

    public void setVaticanReports(Integer pos) {
        this.vaticanReports.put(pos, true);
    }

    public void removeHandCard(String id){
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



    public void setDevCardSlot(Map<Integer, String> devCardSlot) {
        this.devCardSlot = devCardSlot;
    }

    public void updateDevCardSlot(int slot, String id) {
        devCardSlot.put(slot, id);
    }

    public void setExchangeBuff(ArrayList<Resource> exchangeBuff) {
        this.exchangeBuff = exchangeBuff;
    }

    public void setDiscountBuff(ArrayList<Resource> discountBuff) {
        this.discountBuff = discountBuff;
    }

    public void setProductionBuff(ArrayList<Resource> productionBuff) {
        this.productionBuff = productionBuff;
    }
}
