package it.polimi.ingsw.server;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.messages.serverToClient.*;
import it.polimi.ingsw.messages.serverToClient.layoutUpdate.*;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.ArrayList;
import java.util.Map;

public class ServerView extends Observable<ClientMessage> implements Observer<ServerMessage> {

    public void lorenzoUpdate(String message, int lorenzoPos, String[][] newGrid) {
        sendMessage(new LorenzoMessage(message, lorenzoPos, newGrid));
    }

    public void disconnectionMessage(String username) {
        sendMessage(new Disconnection(username));
    }

    public void reconnectionMessage(String username) {
        sendMessage(new Reconnection(username));
    }

    public void endGameMessage() { sendMessage(new EndGameMessage());
    }

    public void finalScoreMessage(Map<String, Integer> score,boolean lorenzo) {
        sendMessage(new FinalScoreMessage(score,lorenzo));
    }

    private class ClientMessageReceiver implements Observer<ClientMessage>{
        @Override
        public void update(ClientMessage message) {
            sendToController(message);
        }
    }

    private final String playerName;
    private final ServerSocketHandler proxy;

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

    public void startTurn(String name){
        sendMessage(new StartTurn(name));
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

    public void sendReconnectionMessage(String username, Map<String, Map<Integer, ArrayList<String>>> devCardSlots, Map<String, Integer> faithPositions,
                                        Map<String, ArrayList<String>> leaderCardsPlayed, ArrayList<String> leaderCards,
                                        Map<String, Map<Resource, Integer>> strongbox, Map<String, Map<Integer, ArrayList<Resource>>> warehouse, Map<String, Integer> cardsInHand, Map<String, Boolean> playersConnected, Map<String, Map<Integer, Boolean>> vaticanReportActivated, boolean gameStarted) {


        sendMessage(new ReConnectionUpdate(username, devCardSlots,faithPositions,leaderCardsPlayed,leaderCards,strongbox,warehouse, cardsInHand, playersConnected,vaticanReportActivated,gameStarted));


    }


    public void sendResourceManageRequest(ArrayList<Resource>resources) {sendMessage(new ResourcesManageRequest(resources));
    }


    public void sendLeaderCards(ArrayList<String> leaderCards){
        sendMessage(new LeaderCardsRequest(leaderCards));
    }

    public void startingResourceMessage(int i) {
        sendMessage(new ResourcesRequest(i));
    }

    public void sendSetupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> warehouse, Map<String,Integer> position) {
        sendMessage(new SetupGameUpdate(leaderCards,warehouse,position));

    }

    public void sendPossibleActions(ArrayList<Actions> possibleActions) {
        sendMessage(new ActionRequest(possibleActions));
    }

    public void sendActionResponse(Actions action) {
        sendMessage( new ActionResponse(action));
    }

    public void sendPlayers(ArrayList<String> players) {
        sendMessage(new Players(players));
    }

    public void sendError(Error invalid_starting_resources) {
        sendMessage(new ErrorMessage(invalid_starting_resources));
    }

    public void sendPlayLeaderCardUpdate(String nickname, String id, Map<Integer, ArrayList<Resource>> warehouse) {
        sendMessage(new PlayLeaderCardUpdate(nickname,id,warehouse));
    }

    public void sendDiscardLeaderCardUpdate(String nickname, String id) {
        sendMessage(new DiscardLeaderCardUpdate(nickname,id));
    }

    public void sendMarketActionUpdate(String nickname, Map<Integer, ArrayList<Resource>> integerArrayListMap, ArrayList<MarbleColors> marbleColorsArrayList, MarbleColors freeMarble, int marketIndex, boolean isRowOrColumn) {
        sendMessage(new MarketActionUpdate(nickname, integerArrayListMap, marbleColorsArrayList,freeMarble,marketIndex,isRowOrColumn));
    }

    public void sendBuyDevelopmentCardUpdate(String username, String id, int slotNumber, String newId, Color color, int level, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        sendMessage(new BuyDevelopmentCardUpdate(username,id,slotNumber,newId,color,level,warehouse,strongbox));
    }

    public void sendUseProductionUpdate(String username, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        sendMessage(new UseProductionUpdate(username,warehouse,strongbox));
    }

    public void sendFaithMessage(Map<String, Integer> update, Map<String, Integer> faithPositions, boolean isActive) {
        sendMessage(new FaithTrackMessage(update,faithPositions,isActive));
    }

    public String getUsername() {
        return playerName;
    }

    public void leaderCardSetupOk() {
        sendMessage(new LeaderCardSetupDone());
    }


    public void startingResourceOk() {
        sendMessage(new StartingResourceDone());
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
