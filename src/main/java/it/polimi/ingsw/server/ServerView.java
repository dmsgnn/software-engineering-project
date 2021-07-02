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


    /**
     * reports the start of the next player's turn
     * @param name of the player
     */
    public void startTurn(String name){
        sendMessage(new StartTurn(name));
    }


    /**
     * send the market and the free marble to the client
     * @param marbles of the market
     */
    public void sendMarket(MarbleColors[][] marbles, MarbleColors freeMarble) {
        sendMessage(new MarketMarbles(marbles,freeMarble));
    }


    /**
     * send the development card grid to the client
     * @param devCardGrid  String matrix, which refer to card ids
     */
    public void sendDevCardGrid(String[][] devCardGrid) {
        sendMessage(new DevCardGrid(devCardGrid));
    }


    /**
     * send to the client all the information that a player needs to be able to rejoin the game
     * @param username of the players
     * @param devCardSlots of the players
     * @param faithPositions of the players
     * @param leaderCardsPlayed of all players
     * @param leaderCards of all players
     * @param strongbox of all players
     * @param warehouse of all players
     * @param cardsInHand of the current player
     * @param playersConnected players connected to the game
     * @param vaticanReportActivated of all players
     * @param gameStarted a boolean that communicates if the initial phase of the game has been passed
     */
    public void sendReconnectionMessage(String username, Map<String, Map<Integer, ArrayList<String>>> devCardSlots, Map<String, Integer> faithPositions,
                                        Map<String, ArrayList<String>> leaderCardsPlayed, ArrayList<String> leaderCards,
                                        Map<String, Map<Resource, Integer>> strongbox, Map<String, Map<Integer, ArrayList<Resource>>> warehouse, Map<String, Integer> cardsInHand, Map<String, Boolean> playersConnected, Map<String, Map<Integer, Boolean>> vaticanReportActivated, boolean gameStarted) {
        sendMessage(new ReConnectionUpdate(username, devCardSlots,faithPositions,leaderCardsPlayed,leaderCards,strongbox,warehouse, cardsInHand, playersConnected,vaticanReportActivated,gameStarted));
    }


    /**
     * sends to the client a request to perform a manage resources
     * @param resources to manage
     */
    public void sendResourceManageRequest(ArrayList<Resource>resources) {sendMessage(new ResourcesManageRequest(resources));
    }


    /**
     * sends the leader cards assigned to the player to the client
     * @param leaderCards of the player
     */
    public void sendLeaderCards(ArrayList<String> leaderCards){
        sendMessage(new LeaderCardsRequest(leaderCards));
    }


    /**
     * sends a request to perform an initial manage to the client
     * @param i nummber of resources to select
     */
    public void startingResourceMessage(int i) {
        sendMessage(new ResourcesRequest(i));
    }


    /**
     * sends an update that is used to update the client
     * @param leaderCards activated by all players
     * @param warehouse of all players
     * @param position  faith of all players
     */
    public void sendSetupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> warehouse, Map<String,Integer> position) {
        sendMessage(new SetupGameUpdate(leaderCards,warehouse,position));

    }


    /**
     * sends the actions that can be performed to the client
     * @param possibleActions that can be performed
     */
    public void sendPossibleActions(ArrayList<Actions> possibleActions) {
        sendMessage(new ActionRequest(possibleActions));
    }

    /**
     * sends the request to perform an action to the client
     * @param action to perform
     */
    public void sendActionResponse(Actions action) {
        sendMessage( new ActionResponse(action));
    }


    /**
     * sends all players in the game to the client
     * @param players array containing the names of all players
     */
    public void sendPlayers(ArrayList<String> players) {
        sendMessage(new Players(players));
    }


    /**
     * sends an error to the client
     * @param err enum error
     */
    public void sendError(Error err) {
        sendMessage(new ErrorMessage(err));
    }


    /**
     * sends an update after a leader card is played to the client
     * @param nickname of the player
     * @param id of the card
     * @param warehouse of the current player
     */
    public void sendPlayLeaderCardUpdate(String nickname, String id, Map<Integer, ArrayList<Resource>> warehouse) {
        sendMessage(new PlayLeaderCardUpdate(nickname,id,warehouse));
    }


    /**
     * sends an update after a leader card is discarded to the client
     * @param nickname of the player
     * @param id of the card
     */
    public void sendDiscardLeaderCardUpdate(String nickname, String id) {
        sendMessage(new DiscardLeaderCardUpdate(nickname,id));
    }


    /**
     * sends an update after a market action is made to the client
     * @param nickname of the player
     * @param integerArrayListMap warehouse
     * @param marbleColorsArrayList new row/column
     * @param freeMarble to add
     * @param marketIndex selected
     * @param isRowOrColumn boolean selection
     */
    public void sendMarketActionUpdate(String nickname, Map<Integer, ArrayList<Resource>> integerArrayListMap, ArrayList<MarbleColors> marbleColorsArrayList, MarbleColors freeMarble, int marketIndex, boolean isRowOrColumn) {
        sendMessage(new MarketActionUpdate(nickname, integerArrayListMap, marbleColorsArrayList,freeMarble,marketIndex,isRowOrColumn));
    }


    /**
     * sends an update after a market action is made to the client
     * @param username of the player
     * @param id of the card
     * @param slotNumber selected
     * @param newId to insert
     * @param color of the card
     * @param level of the card
     * @param warehouse of the player
     * @param strongbox of the player
     */
    public void sendBuyDevelopmentCardUpdate(String username, String id, int slotNumber, String newId, Color color, int level, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        sendMessage(new BuyDevelopmentCardUpdate(username,id,slotNumber,newId,color,level,warehouse,strongbox));
    }


    /**
     * sends an update after a production is made to the client
     * @param username of the player
     * @param warehouse of the player
     * @param strongbox of the player
     */
    public void sendUseProductionUpdate(String username, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox) {
        sendMessage(new UseProductionUpdate(username,warehouse,strongbox));
    }


    /**
     * sends a faithtrack update to the client
     * @param update for vatican report
     * @param faithPositions of all player
     * @param isActive if a Vatican report has been activated
     */
    public void sendFaithMessage(ArrayList<String> update, Map<String, Integer> faithPositions, boolean isActive, int vaticanReportPos) {
        sendMessage(new FaithTrackMessage(update,faithPositions,isActive, vaticanReportPos));
    }


    public String getUsername() {
        return playerName;
    }


    /**
     * communicates the end of the choice of the initial leader cards
     */
    public void leaderCardSetupOk() {
        sendMessage(new LeaderCardSetupDone());
    }


    /**
     * communicates the end of the choice of the initial resources
     */
    public void startingResourceOk() {
        sendMessage(new StartingResourceDone());
    }


    /**
     * sends lorenzo's update after he fetches a token to the client
     * @param message token-based
     * @param lorenzoPos faith
     * @param newGrid of the dev card
     */
    public void lorenzoUpdate(String message, int lorenzoPos, String[][] newGrid) {
        sendMessage(new LorenzoMessage(message, lorenzoPos, newGrid));
    }


    /**
     * notifies the client if a player has disconnected
     * @param username of the disconnected player
     */
    public void disconnectionMessage(String username) {
        sendMessage(new Disconnection(username));
    }


    /**
     * notifies if a player has reconnected to the client
     * @param username of the reconnected player
     */
    public void reconnectionMessage(String username) {
        sendMessage(new Reconnection(username));
    }


    /**
     * notifies the client if the game is about to end
     */
    public void endGameMessage() { sendMessage(new EndGameMessage());
    }


    /**
     * communicate the final scores of the match and if lorenzo won in case of singleplayer
     * @param score of the players
     * @param lorenzo true if lorenzo won
     */
    public void finalScoreMessage(Map<String, Integer> score,boolean lorenzo) {
        sendMessage(new FinalScoreMessage(score,lorenzo));
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
