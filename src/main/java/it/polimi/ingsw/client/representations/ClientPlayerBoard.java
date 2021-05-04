package it.polimi.ingsw.client.representations;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientPlayerBoard {

    private String playerNickname;
    private ArrayList<String> hand = new ArrayList<>();
    private ArrayList<String> playedCards = new ArrayList<>();
    private int playerPosition = 0;
    private int lorenzoPosition = -1;
    private Map<Integer, String> devCardSlot = new HashMap<>();
    private Map<Integer, ArrayList<Resource>> warehouse = new HashMap<>();
    private Map<Integer, Resource> strongbox = new HashMap<>();

    public ClientPlayerBoard(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getPlayerNickname() {
        return playerNickname;
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
}
