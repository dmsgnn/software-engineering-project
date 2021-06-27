package it.polimi.ingsw.client;

import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.messages.clientToServer.*;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.utility.LeaderCardsParserXML;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class View {


    private UserInterface uiType;
    private String nickname; //nickname of the player who owns this client
    private ClientGameBoard gameboard;

    private final ArrayList<LeaderCard> leaderDeck;

    public View(UserInterface uiType) {
        this.uiType = uiType;
        leaderDeck = new LeaderCardsParserXML().leaderCardsParser();
        gameboard = new ClientGameBoard();
    }

    public UserInterface getUiType() {
        return uiType;
    }

    public void setUiType(UserInterface uiType) {
        this.uiType = uiType;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGameboard(ClientGameBoard gameboard) {
        this.gameboard = gameboard;
    }

    public Map<Resource, Integer> getMyStrongbox(){
        return gameboard.getOnePlayerBoard(nickname).getStrongbox();
    }

    public Map<Integer, ArrayList<Resource>> getMyWarehouse(){
        return gameboard.getOnePlayerBoard(nickname).getWarehouse();
    }

    /**
     * utility method to find a leadercard
     * @param id card that I want to find
     * @return the correct card
     */
    public LeaderCard findLeaderCard(String id){
        for(LeaderCard card : leaderDeck){
            if(card.getId().equals(id)) return card;
        }
        return null;
    }

    public ClientGameBoard getGameboard() {
        return gameboard;
    }

    public UserInterface getUi() {
        return uiType;
    }

    public String getNickname() {
        return nickname;
    }

    /**
     * called to do the login
     */
    public void login(){
        uiType.login();
    }

    /**
     * called to send the login nickname
     * @param nickname selected from the player
     */
    public void sendLogin(String nickname){
    }


    /**
     * called when the server sends an error message to the client
     * @param errorType error to manage
     */
    public void errorManagement(Error errorType){
        uiType.manageError(errorType);
    }

    //------------------GAME SETUP------------------

    /**
     * adds all the players to the game
     * @param players nicknames of the players
     */
    public void addPlayersToGameboard(ArrayList<String> players){
        gameboard.addPlayers(players);
        if(players.size()==1) gameboard.getOnePlayerBoard(nickname).setLorenzoPosition(0);
    }

    /**
     * informs the client about the starting market configuration
     * @param grid this is the market configuration
     * @param free this is the free marble that has to be used to perform the market action
     */
    public void marketSetup(MarbleColors[][] grid, MarbleColors free) {
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
     * called to send the selected game size to the server
     * @param num number of players
     */
    public void sendNumOfPlayers(int num){
    }

    /**
     * called to make the player chose the starting leadercards
     * @param leaderCardID IDs of the cards
     */
    public void selectStartingCards(ArrayList<String> leaderCardID){
        uiType.startingLeaderCardsSelection(leaderCardID);
    }

    /**
     * called to send the starting leadercards to the server
     * @param cards selected from the player
     */
    public void sendStartingCards(ArrayList<String> cards){
    }
    /**
     * called if the leadercard selection was correct
     */
    public void leaderCardsDone(){
        uiType.endLeadercardSetup();
    }

    /**
     * called to send the starting resources to the server
     * @param warehouse configuration
     */
    public void sendStartingResources(Map<Integer, ArrayList<Resource>> warehouse){
    }


    //------------------ACTIONS------------------

    /**
     * called to make the player decide what action he wants to perform
     * @param possibleActions list of the actions the player can choose from
     */
    public void pickAction(ArrayList<Actions> possibleActions){
        uiType.chooseAction(possibleActions);
    }

    /**
     * called to send to the server the action the player decided to do this turn
     * @param action selected from the player
     */
    public void sendAction(Actions action){
    }

    /**
     * called to make the player do the selected action
     * @param action what the player decided to do
     */
    public void doAction(Actions action){
        switch (action) {
            case MARKETACTION:
                uiType.marketAction();
                break;
            case USEPRODUCTION:
                uiType.useProductionAction();
                break;
            case BUYDEVELOPMENTCARD:
                uiType.buyCardAction();
                break;
            case PLAYLEADERCARD:
                uiType.playLeaderCardAction();
                break;
            case DISCARDLEADERCARD:
                uiType.discardLeaderCardAction();
                break;
            case ENDTURN:
                endTurn();
                break;
        }
    }

    /**
     * called when the player ends his turn
     */
    public void endTurn(){

    }

    /**
     * called from UI to send the market action parameters to the server
     * @param pos column or row picked by the player
     * @param rowOrCol true if pos is row, false if column
     * @param exchangeBuffResources resources obtained from white marbles if possible
     */
    public void marketAction(int pos, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources, String username){
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
                              ArrayList<Resource> boardResources, HashMap<Resource, Integer> warehouseResources, HashMap<Resource, Integer> leaderDepotResources,
                              HashMap<Resource, Integer> strongboxResources, String username){

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
                           HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes, String username){
    }

    /**
     * called from UI to send the play leadercard action parameters to the server
     * @param id of the selected card
     */
    public void playLeaderCard(String id,String username){
    }

    /**
     * called from UI to send the discard leadercard action parameters to the server
     * @param id of the selected card
     */
    public void discardLeaderCard(String id,String username){
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
    public void sendManageResourcesReply(Map<Integer, ArrayList<Resource>> newWarehouse, Map<Resource,Integer> discard){
    }

    //------------------UPDATES------------------

    /**
     * called to setup all playerboard with initial values
     * @param leaderCards hand of each player
     * @param resources warehouse of each player
     * @param faithTracks faith track position of each player
     */
    public void setupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> resources, Map<String,Integer> faithTracks){
        for (String nickname : leaderCards.keySet()) {
            if (nickname.equals(this.nickname))
                gameboard.getOnePlayerBoard(nickname).setHand(leaderCards.get(nickname));
        }
        for (String nickname : resources.keySet()) {
            gameboard.getOnePlayerBoard(nickname).setWarehouse(resources.get(nickname));
        }
        for (String nickname : faithTracks.keySet()) {
            gameboard.getOnePlayerBoard(nickname).setPlayerPosition(faithTracks.get(nickname));
        }
        uiType.updateBoard("");
        uiType.endTurn();
    }

    /**
     * called to update the board after Lorenzo's move
     * @param lorenzoPosition new Lorenzo's position
     * @param newCardGrid new development card grid
     */
    public void lorenzoUpdate(String message, int lorenzoPosition, String[][] newCardGrid){
        gameboard.getOnePlayerBoard(nickname).setLorenzoPosition(lorenzoPosition);
        getGameboard().initializeCards(newCardGrid);
        uiType.updateBoard(message);
    }

    /**
     * called to update every player faithtrack
     * @param vaticanPosition map of the players who activated the report in the specified position
     * @param position new faith track position of each player
     * @param report true if a vatican report has been activated, false otherwise
     */
    public void faithTrackUpdate(Map<String, Integer> vaticanPosition, Map<String, Integer> position, boolean report){
        for (String nickname : position.keySet()) {
            gameboard.getOnePlayerBoard(nickname).setPlayerPosition(position.get(nickname));
        }
        if (report) {
            int reportPos = 0;
            for(String nickname : vaticanPosition.keySet()){
                reportPos = vaticanPosition.get(nickname);
                gameboard.getOnePlayerBoard(nickname).updateVaticanReports(reportPos, true);
            }
            for(String nickname : gameboard.getPlayers()){
                if(!vaticanPosition.containsKey(nickname)) gameboard.getOnePlayerBoard(nickname).updateVaticanReports(reportPos, false);
            }
        }
    }

    /**
     * called to update the board after a buy development card action
     * @param nickname player that performed the action
     * @param id card bought
     * @param slot where to place the dev card
     * @param gridId new card for the grid
     * @param color of the new card
     * @param level of the new card
     * @param warehouse new warehouse of the player
     * @param strongbox new strongbox of the player
     */
    public void buyCardUpdate(String nickname, String id, int slot, String gridId, Color color, int level,
                              Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox){
        ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
        playerBoard.updateDevCardSlot(slot, id);
        playerBoard.setWarehouse(warehouse);
        playerBoard.setStrongbox(strongbox);
        gameboard.changeGridCard(gridId, color, level);
        if(!nickname.equals(this.nickname)) uiType.updateBoard(nickname + " bought the level " + level + " " + color + " card");
        else uiType.updateBoard("");
    }

    /**
     * called to update the board after a player discords a leader card
     * @param nickname of the player
     * @param id discarded card
     */
    public void discardLeaderCardUpdate(String nickname, String id){
        ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
        playerBoard.removeHandCard(id);
        if(!nickname.equals(this.nickname)) uiType.updateBoard(nickname + " discarded a leader card");
        else uiType.updateBoard("");
    }

    /**
     * called to update the board after a player plays a leader card
     * @param nickname of the player
     * @param id of the played card
     * @param warehouse new warehouse of the player
     */
    public void playLeaderCardUpdate(String nickname, String id, Map<Integer, ArrayList<Resource>> warehouse){
        ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
        playerBoard.setWarehouse(warehouse);
        playerBoard.removeHandCard(id);
        playerBoard.addPlayedCard(id, Objects.requireNonNull(findLeaderCard(id)));
        if(!nickname.equals(this.nickname))
            uiType.updateBoard(nickname + " played a leader card");
        else uiType.updateBoard("");
    }

    /**
     * called to update the board after use production action
     * @param nickname of the player
     * @param warehouse new warehouse of the player
     * @param strongbox new strongbox of the player
     */
    public void useProductionUpdate(String nickname, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox){
        ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
        playerBoard.setWarehouse(warehouse);
        playerBoard.setStrongbox(strongbox);
        if(!nickname.equals(this.nickname))
            uiType.updateBoard(nickname + " activated the production");
        else uiType.updateBoard("");
    }

    /**
     * called to update the board after use market action
     * @param nickname of the player
     * @param warehouse new warehouse of the player
     * @param newMarbles market line that has been updated
     * @param newFreeMarble new freemarble
     * @param pos row or column of newMarbles
     * @param rowOrCol true if row, false if column
     */
    public void marketActionUpdate(String nickname, Map<Integer, ArrayList<Resource>> warehouse,
                                   ArrayList<MarbleColors> newMarbles, MarbleColors newFreeMarble, int pos, boolean rowOrCol){
        ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
        playerBoard.setWarehouse(warehouse);
        gameboard.updateMarket(newMarbles, newFreeMarble, pos, rowOrCol);
        if(!nickname.equals(this.nickname))
            uiType.updateBoard(nickname + " took resources from the market");
        else uiType.updateBoard("");
    }

    /**
     * called to notify the players that this is the last round
     */
    public void endGame(){
        uiType.lastRound();
    }

    /**
     * called to notify the final scores and the winner to the players
     * @param finalScores final victory points
     * @param lorenzoWin true if Lorenzo won the game, false if he lost or if the game is singleplayer
     */
    public void finalScoresUpdate(Map<String, Integer> finalScores, boolean lorenzoWin){

    }


    /**
     * Called when a {@link ServerMessage} is received. The package is extracted and the method on the incoming
     * event overrides a method on ClientView
     * @param message Incoming message
     */
    public void update(ServerMessage message) {
    }

    //------------------SOCKET------------------

    /**
     * starts the connection of the client
     */
    public void startConnection() {
    }

    /**
     * closes the socket of the client
     */
    public void disconnect() {
    }
}
