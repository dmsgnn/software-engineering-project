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
    private int lorenzoPosition = -1;
    private Map<Integer, String> devCardSlot = new HashMap<>();

    private Map<Integer, ArrayList<Resource>> warehouse = new HashMap<>();
    private Map<Integer, Resource> strongbox = new HashMap<>();

    private ArrayList<Resource> exchangeBuff = new ArrayList<>();
    private ArrayList<Resource> discountBuff = new ArrayList<>();
    private ArrayList<Resource> productionBuff = new ArrayList<>();


    public ClientPlayerBoard(String playerNickname) {
        this.playerNickname = playerNickname;
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

    public Map<Integer, Resource> getStrongbox() {
        return strongbox;
    }

    public ArrayList<Resource> getExchangeBuff() {
        return exchangeBuff;
    }

    public ArrayList<Resource> getDiscountBuff() {
        return discountBuff;
    }

    public ArrayList<Resource> getProductionBuff() {
        return productionBuff;
    }

    public void setHand(ArrayList<String> hand) {
        this.hand = hand;
    }

    public void setPlayedCards(ArrayList<String> playedCards) {
        this.playedCards = playedCards;
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

    public void setStrongbox(Map<Integer, Resource> strongbox) {
        this.strongbox = strongbox;
    }

    public void setDevCardSlot(Map<Integer, String> devCardSlot) {
        this.devCardSlot = devCardSlot;
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
