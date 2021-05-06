package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.messages.clientToServer.*;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ClientView implements Observer<ServerMessage> {

    //private ClientSocketHandler socket;
    private UserInterface uiType;
    private String nickname; //nickname of the player owning this client
    private final String ip;
    private final int port;
    private final ClientGameBoard gameboard;

    public ClientView(String ip, int port, UserInterface ui) {
        this.ip = ip;
        this.port = port;
        this.uiType = ui;
        gameboard = new ClientGameBoard();
    }

    public UserInterface getUi() {
        return uiType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    /**
     * Called when the game begins
     */
    public void startGame() {
        uiType.startGame();
    }

    /**
     * called to do the login
     */
    public void login(){
        String nickname = uiType.login();
        //socket.sendMessage(new UsernameReply(nickname));
    }

    /**
     * called from the server if the nickname sent with login is valid
     * @param nickname the player set
     */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * called when the server sends a response message to the client, invokes UI method only if it's an error message
     * @param message response message
     */
    public void responseMessage(String message){
        message.toLowerCase(Locale.ROOT);
        if(!message.equals("ok")) uiType.displayMessage(message);
    }

    //------------------GAME SETUP------------------


    /**
     * adds all the players to the game
     * @param players nicknames of the players
     */
    public void addPlayersToGameboard(ArrayList<String> players){
        gameboard.addPlayers(players);
    }

    /**
     * informs the client about the starting market configuration
     * @param grid this is the market configuration
     * @param free this is the free marble that has to be used to perform the market action
     */
    public void marketSetup(MarbleColors[][] grid, MarbleColors free){
        gameboard.initializeMarket(grid, free);
    }

    /**
     * informs the client about the starting development card grid (only the cards on top of it)
     * @param cards initial card grid
     */
    public void developmentCardGridSetup(String[][] cards){
        gameboard.initializeCards(cards);
    }

    /**
     * called if this is the first player, asks the game size
     * @param maxNum maximum number of players
     */
    public void numOfPlayers(int maxNum){
        int num = uiType.playersNumber(maxNum);
        //socket.sendMessage(new PlayerNumberReply(num));
    }

    /**
     * called to make the player chose the starting leadercards
     * @param leaderCardID IDs of the cards
     */
    public void selectStartingCards(ArrayList<String> leaderCardID){
        ArrayList<String> cards = uiType.startingLeaderCardsSelection(leaderCardID);
        //socket.sendMessage(new LeaderCardsReply(cards));
    }

    /**
     * called to make the player chose the starting resources
     * @param amount num of resources
     */
    public void startingResources(int amount){
        ArrayList<Resource> resources = uiType.startingResources(amount);
        //socket.sendMessage(new ResourcesReply(resources));
    }


    //------------------ACTIONS------------------


    /**
     * called to make the player decide what action he wants to perform
     * @param possibleActions list of the actions the player can choose from
     */
    public void pickAction(ArrayList<Actions> possibleActions){
        Actions action = uiType.chooseAction(possibleActions);
        //socket.sendMessage(new ActionReply(action));
    }

    /**
     * called to make the player do the selected action
     * @param action what the player decided to do
     */
    public void doAction(Actions action){
        switch (action){
            case MARKETACTION: uiType.marketAction();
            case USEPRODUCTION: uiType.useProductionAction();
            case BUYDEVELOPMENTCARD: uiType.buyCardAction();
            case PLAYLEADERCARD: uiType.playLeaderCardAction();
            case DISCARDLEADERCARD: uiType.discardLeaderCardAction();
        }
    }

    /**
     * called from UI to send the market action parameters to the server
     * @param pos column or row picked by the player
     * @param rowOrCol true if pos is row, false if column
     * @param exchangeBuffResources resources obtained from white marbles if possible
     */
    public void marketAction(int pos, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources){
        //socket.sendMessage(new RowColumnSelection(pos, rowOrCol, exchangeBuffResources));
    }

    /**
     * called from UI to send the production action parameters to the server
     * @param developmentCardSlotIndex indexes of the development cards the player wants to use for the production
     * @param leaderCardProdIndex indexes of the leader cards the player wants to use for the production
     * @param leaderCardProdGain resource gains for the leader card production
     * @param boardResources resource cost and gains for the board production
     * @param warehouseResources resources inside the warehouse that the player wants to pay
     * @param leaderDepotResources resources inside the leadercard depots that the player wants to pay
     * @param strongboxResources resources inside the strongbox that the player wants to pay
     */
    public void useProduction(ArrayList<Integer> developmentCardSlotIndex, ArrayList<Integer> leaderCardProdIndex, ArrayList<Resource> leaderCardProdGain,
                              ArrayList<Resource> boardResources, Map<Resource, Integer> warehouseResources, Map<Resource, Integer> leaderDepotResources,
                              Map<Resource, Integer> strongboxResources){
        /*socket.sendMessage(new UseProductionParameters(developmentCardSlotIndex, leaderCardProdIndex, leaderCardProdGain, boardResources,
                warehouseResources, leaderDepotResources, strongboxResources));*/
    }

    /**
     * called from UI to send the buy development card action parameters to the server
     * @param color of the card
     * @param level of the card
     * @param devCardSlot slot to place the card in
     * @param warehouseDepotRes resources inside the warehouse that the player wants to pay
     * @param cardDepotRes resources inside the leadercard depots that the player wants to pay
     * @param strongboxRes resources inside the strongbox that the player wants to pay
     */
    public void buyDevCard(Color color, int level, int devCardSlot, HashMap<Resource, Integer> warehouseDepotRes,
                           HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes){
        //socket.sendMessage(new BuyDevelopmentCardParameters(color, level, devCardSlot, warehouseDepotRes, cardDepotRes, strongboxRes));
    }

    /**
     * called from UI to send the play leadercard action parameters to the server
     * @param id of the selected card
     * @param warehouseDepotRes resources inside the warehouse that the player wants to pay
     * @param cardDepotRes resources inside the leadercard depots that the player wants to pay
     * @param strongboxRes resources inside the strongbox that the player wants to pay
     */
    public void playLeaderCard(String id, HashMap<Resource, Integer> warehouseDepotRes, HashMap<Resource,
                                Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes){
        //socket.sendMessage(new PlayLeaderCardParameters(id, warehouseDepotRes, cardDepotRes, strongboxRes));
    }

    /**
     * called from UI to send the discard leadercard action parameters to the server
     * @param id of the selected card
     */
    public void discardLeaderCard(String id){
        //socket.sendMessage(new DiscardLeaderCardParameters(id));
    }

    /**
     * called to make the player position the acquired resources
     * @param resources new resources
     */
    public void manageResources(ArrayList<Resource> resources){
        uiType.manageResources(resources);
    }

    /**
     * called from UI to send the manage resources action parameters to the server
     * @param newWarehouse new warehouse configuration
     * @param discard resources that the player wants to discard
     */
    public void sendManageResourcesReply(Map<Integer, ArrayList<Resource>> newWarehouse, ArrayList<Resource> discard){
        //socket.sendMessage(new ResourcesManageReply(newWarehouse, discard))
    }


    //------------------GAME OVER------------------


    /**
     * called to show the winner nickname
     * @param winner nickname of the winning player
     */
    public void winner(String winner){
        uiType.winner(winner);
    }

    /**
     * Called when a {@link ServerMessage} is received. The package is extracted and the method on the incoming
     * event overrides a method on ClientView
     * @param message Incoming message
     */
    @Override
    public void update(ServerMessage message) {
        message.handleMessage(this);
    }

}
