package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.MarbleColors;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.serverToClient.*;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.marble.Marbles;

import java.util.Map;

public class ServerView extends Observable<ClientMessage> implements Observer<ServerMessage> {


    private class ClientMessageReceiver implements Observer<ClientMessage>{

        @Override
        public void update(ClientMessage message) {
            sendToController(message);
        }
    }

    private final String playerName;
    private ServerSocketHandler proxy;

    public ServerView(){playerName=null;}
    public ServerView(String name, ServerSocketHandler proxy){
        this.playerName= name;
        this.proxy= proxy;
        proxy.addObserver(new ClientMessageReceiver());
    }


    /**
     * Notifies the controller of changes on the client
     * @param message {@link ClientMessage}
     */
    private void sendToController(ClientMessage message) {
        notify(message);
    }

    /**
     * Sends the specified message to the client
     * @param message {@link ServerMessage}
     */
    private void sendMessage(ServerMessage message){
        if (proxy!=null) proxy.sendMessage(message);
    }

    public void startTurn(){
        sendMessage(new StartTurn());
    }

    public void numberOfPlayerSelection(){
        sendMessage(new PlayerNumberRequest());
    }

    /**
     * send the market and the free marble to the client
     * @param marbles of the market
     * @param freeMarble
     */
    public void sendMarket(MarbleColors[][] marbles, MarbleColors freeMarble) {
        sendMessage(new MarketMarbles(marbles,freeMarble));
    }

    public void sendDevCardGrid(String[][] devCardGrid) {
        sendMessage(new DevCardGrid(devCardGrid));
    }

    public void sendAllCards(Map<String, Color> colorMap, Map<String, Integer> levelMap) {
    sendMessage(new AllCardsMessage(colorMap,levelMap));
    }









    public String getUsername() {
        return playerName;
    }


    /**
     * Method invoked when there is a notify on the controller
     * @param message Specified {@link ServerMessage}
     */
    @Override
    public void update(ServerMessage message) {
        sendMessage(message);
    }
}
