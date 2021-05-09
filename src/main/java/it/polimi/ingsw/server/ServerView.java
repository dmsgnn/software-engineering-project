package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.MarbleColors;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.serverToClient.*;
import it.polimi.ingsw.messages.serverToClient.layoutUpdate.ReConnectionUpdate;
import it.polimi.ingsw.messages.serverToClient.layoutUpdate.SetupGameUpdate;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.leadercard.LeaderCard;

import java.util.ArrayList;
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
     */
    public void sendMarket(MarbleColors[][] marbles, MarbleColors freeMarble) {
        sendMessage(new MarketMarbles(marbles,freeMarble));
    }

    public void sendDevCardGrid(String[][] devCardGrid) {
        sendMessage(new DevCardGrid(devCardGrid));
    }

    public void sendReconnectionMessage(Map<String, ArrayList<String>> devCardSlots, Map<String, Integer> faithPositions,
                                        Map<String, ArrayList<String>> leaderCardsPlayed, ArrayList<String> leaderCards,
                                        Map<String, Map<Resource, Integer>> strongbox, Map<String, Map<Integer, ArrayList<Resource>>> warehouse) {


        sendMessage(new ReConnectionUpdate(devCardSlots,faithPositions,leaderCardsPlayed,leaderCards,strongbox,warehouse));


    }


    public void sendResourceManageRequest(ArrayList<Resource>resources) {sendMessage(new ResourcesManageRequest(resources));
    }


    public void sendLeaderCards(ArrayList<String> leaderCards){
        sendMessage(new LeaderCardsRequest(leaderCards));
    }

    public void sendStartingResource(int i) {
        sendMessage(new ResourcesRequest(i));
    }

    public void sendSetupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> warehouse, Map<String,Integer> position) {
        sendMessage(new SetupGameUpdate(leaderCards,warehouse,position));

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
