package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
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
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.utility.LeaderCardsParserXML;
import it.polimi.ingsw.utility.TokensParserXML;

import java.net.Socket;
import java.util.*;

import static java.lang.System.exit;

public class ClientView implements Observer<ServerMessage> {

    private ClientSocketHandler socket;
    private final UserInterface uiType;
    private String nickname; //nickname of the player owning this client
    private final String ip;
    private final int port;
    private final ClientGameBoard gameboard;
    

    private ArrayList<Actions> possibleActions = new ArrayList<>();

    private final Object lock = new Object();

    ArrayList<LeaderCard> leaderDeck = new LeaderCardsParserXML().leaderCardsParser();

    /**
     * for testing
     */
    public ClientView(UserInterface ui){
        this.uiType = ui;
        gameboard = new ClientGameBoard();
        ip = "";
        port = 0;
    }

    public ClientView(String ip, int port, UserInterface ui) {
        this.ip = ip;
        this.port = port;
        this.uiType = ui;
        gameboard = new ClientGameBoard();
    }

    public ArrayList<Actions> getPossibleActions() {
        return possibleActions;
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
    private LeaderCard findLeaderCard(String id){
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
    public void login(){ // needed?
        uiType.login();
    }

    /**
     * called to send the login nickname
     * @param nickname selected from the player
     */
    public void sendLogin(String nickname){
        socket.sendMessage(new LoginMessage(nickname));
    }

    /**
     * manages the Username Response message
     * @param nickname the player set
     */
    public void manageUsernameResponse(boolean isFree, String nickname, ArrayList<String> usedNicknames){
        if(isFree){
            this.nickname = nickname;
            uiType.loginDone();
        }
        else {
            uiType.failedLogin(usedNicknames);
        }
    }

    /**
     * called when the server sends an error message to the client
     * @param errorType error to manage
     */
    public void errorManagement(Error errorType){
        synchronized (lock){
            uiType.manageError(errorType);
        }
    }

    /**
     * called when one player disconnects from the game
     * @param nickname of who is disconnected
     */
    public void playerDisconnection(String nickname){
        gameboard.getOnePlayerBoard(nickname).setConnected(false);
    }

    //------------------GAME SETUP------------------

    /**
     * adds all the players to the game
     * @param players nicknames of the players
     */
    public void addPlayersToGameboard(ArrayList<String> players){
        synchronized (lock){
            gameboard.addPlayers(players);
            if(players.size()==1) gameboard.getOnePlayerBoard(nickname).setLorenzoPosition(0);
        }
    }

    /**
     * informs the client about the starting market configuration
     * @param grid this is the market configuration
     * @param free this is the free marble that has to be used to perform the market action
     */
    public void marketSetup(MarbleColors[][] grid, MarbleColors free) {
        synchronized (lock) {
            gameboard.initializeMarket(grid, free);
        }
    }

    /**
     * informs the client about the starting development card grid (only the cards on top of it)
     * @param cards initial card grid
     */
    public void developmentCardGridSetup(String[][] cards){
        synchronized (lock) {
            gameboard.initializeCards(cards);
        }
    }

    /**
     * called if this is the first player, asks the game size
     * @param maxNum maximum number of players
     */
    public void numOfPlayers(int maxNum){
        uiType.playersNumber(maxNum);
    }

    /**
     * called to send the selected game size to the server
     * @param num number of players
     */
    public void sendNumOfPlayers(int num){
        socket.sendMessage(new PlayerNumberReply(num));
    }

    /**
     * called to make the player chose the starting leadercards
     * @param leaderCardID IDs of the cards
     */
    public void selectStartingCards(ArrayList<String> leaderCardID){
        synchronized (lock) {
            uiType.startingLeaderCardsSelection(leaderCardID);
        }
    }

    /**
     * called to send the starting leadercards to the server
     * @param cards selected from the player
     */
    public void sendStartingCards(ArrayList<String> cards){
        socket.sendMessage(new LeaderCardsReply(cards, nickname));
    }

    /**
     * called if the leadercard selection was correct
     */
    public void leaderCardsDone(){
        synchronized (lock) {
            uiType.endLeadercardSetup();
        }
    }

    /**
     * called to make the player chose the starting resources
     * @param amount num of resources
     */
    public void startingResources(int amount){
        synchronized (lock) {
             uiType.startingResources(amount);
        }
    }

    /**
     * called to send the starting resources to the server
     * @param warehouse configuration
     */
    public void sendStartingResources(Map<Integer, ArrayList<Resource>> warehouse){
        socket.sendMessage(new ResourcesReply(warehouse, nickname));
    }

    /**
     * called if the starting resources selection was correct
     */
    public void startingResDone(){
        synchronized (lock) {
            uiType.endStartingResourcesSetup();
        }
    }


    //------------------ACTIONS------------------

    /**
     * called to make the player decide what action he wants to perform
     * @param possibleActions list of the actions the player can choose from
     */
    public void pickAction(ArrayList<Actions> possibleActions){
        synchronized (lock){
            uiType.chooseAction(possibleActions);
        }
    }

    /**
     * called to send to the server the action the player decided to do this turn
     * @param action selected from the player
     */
    public void sendAction(Actions action){
        socket.sendMessage(new ActionReply(action));
    }

    /*public void setPossibleActions(ArrayList<Actions> possibleActions){
        this.possibleActions=possibleActions;
    }

    public void selectAction(){
        Actions action = uiType.chooseAction(this.possibleActions);
        doAction(action);
    }*/

    /**
     * called to make the player do the selected action
     * @param action what the player decided to do
     */
    public void doAction(Actions action){
        synchronized (lock) {
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
    }

    /**
     * called when the player ends his turn
     */
    public void endTurn(){
        uiType.endTurn();
        socket.sendMessage(new EndTurn());
    }

    /**
     * called from UI to send the market action parameters to the server
     * @param pos column or row picked by the player
     * @param rowOrCol true if pos is row, false if column
     * @param exchangeBuffResources resources obtained from white marbles if possible
     */
    public void marketAction(int pos, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources){
        socket.sendMessage(new RowColumnSelection(pos, rowOrCol, exchangeBuffResources));
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
                              HashMap<Resource, Integer> strongboxResources){
        socket.sendMessage(new UseProductionParameters(warehouseResources, leaderDepotResources, strongboxResources,
                boardResources, leaderCardProdGain, developmentCardSlotIndex, leaderCardProdIndex));
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
        socket.sendMessage(new BuyDevelopmentCardParameters(color, level, devCardSlot, warehouseDepotRes, cardDepotRes, strongboxRes));
    }

    /**
     * called from UI to send the play leadercard action parameters to the server
     * @param id of the selected card
     */
    public void playLeaderCard(String id){
        socket.sendMessage(new PlayLeaderCardParameters(id));
    }

    /**
     * called from UI to send the discard leadercard action parameters to the server
     * @param id of the selected card
     */
    public void discardLeaderCard(String id){
        socket.sendMessage(new DiscardLeaderCardParameters(id));
    }

    /**
     * called to make the player position the acquired resources
     * @param resources new resources
     */
    public void manageResources(ArrayList<Resource> resources){
        synchronized (lock) {
            uiType.manageResources(resources);
        }
    }

    /**
     * called from UI to send the manage resources action parameters to the server
     * @param newWarehouse new warehouse configuration
     * @param discard resources that the player wants to discard
     */
    public void sendManageResourcesReply(Map<Integer, ArrayList<Resource>> newWarehouse, Map<Resource,Integer> discard){
        socket.sendMessage(new ResourcesManageReply(newWarehouse, discard));
    }

    //------------------UPDATES------------------

    /**
     * called to setup all playerboard with initial values
     * @param leaderCards hand of each player
     * @param resources warehouse of each player
     * @param faithTracks faith track position of each player
     */
    public void setupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> resources, Map<String,Integer> faithTracks){
        synchronized (lock) {
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
    }

    /**
     * called when a player disconnects from the game
     * @param nickname
     */
    public void playerDisconnected(String nickname){
        synchronized (lock){
            gameboard.getOnePlayerBoard(nickname).setConnected(false);
            uiType.handleDisconnection(nickname);
        }
    }

    /**
     * called when a player reconnects to the game
     * @param nickname
     */
    public void playerReconnected(String nickname){
        synchronized (lock){
            gameboard.getOnePlayerBoard(nickname).setConnected(true);
            uiType.handleReconnection(nickname);
        }
    }

    /**
     * called after a player reconnects to the game, the parameters contains all the informations regarding the game
     * @param username of this client
     * @param devCardSlots all dev cards bought by all the player
     * @param faithPositions all player's faith track position
     * @param leaderCardsPlayed all leader cards played by all player
     * @param leaderCards player hand
     * @param strongbox every strongbox
     * @param warehouse every warehouse
     */
    public void reconnectionUpdate(String username, Map<String, ArrayList<String>> devCardSlots, Map<String, Integer> faithPositions, Map<String,
            ArrayList<String>> leaderCardsPlayed, ArrayList<String> leaderCards, Map<String, Map<Resource, Integer>> strongbox,
                                   Map<String, Map<Integer, ArrayList<Resource>>> warehouse){
        synchronized (lock) {
            this.nickname=username;
            ArrayList<String> players = new ArrayList<>(devCardSlots.keySet());
            gameboard.addPlayers(players);
            for (String nickname : devCardSlots.keySet()) {
                gameboard.getOnePlayerBoard(nickname).setDevCardSlot(devCardSlots.get(nickname));
            }
            for (String nickname : faithPositions.keySet()) {
                gameboard.getOnePlayerBoard(nickname).setPlayerPosition(faithPositions.get(nickname));
            }
            for (String nickname : leaderCardsPlayed.keySet()) {
                gameboard.getOnePlayerBoard(nickname).setPlayedCards(leaderCardsPlayed.get(nickname));
            }
            gameboard.getOnePlayerBoard(nickname).setHand(leaderCards);
            for (String nickname : strongbox.keySet()) {
                gameboard.getOnePlayerBoard(nickname).setStrongbox(strongbox.get(nickname));
            }
            for (String nickname : warehouse.keySet()) {
                gameboard.getOnePlayerBoard(nickname).setWarehouse(warehouse.get(nickname));
            }
            uiType.updateBoard("Reconnected");
            uiType.endTurn();
        }
    }

    /**
     * called to update the board after Lorenzo's move
     * @param lorenzoPosition new Lorenzo's position
     * @param newCardGrid new development card grid
     */
    public void lorenzoUpdate(String message, int lorenzoPosition, String[][] newCardGrid){
        synchronized (lock) {
            gameboard.getOnePlayerBoard(nickname).setLorenzoPosition(lorenzoPosition);
            getGameboard().initializeCards(newCardGrid);
            uiType.updateBoard(message);
        }
    }

    /**
     * called to update every player faithtrack
     * @param vaticanPosition map of the players who activated the report in the specified position
     * @param position new faith track position of each player
     * @param report true if a vatican report has been activated, false otherwise
     */
    public void faithTrackUpdate(Map<String, Integer> vaticanPosition, Map<String, Integer> position, boolean report){
        synchronized (lock) {
            for (String nickname : position.keySet()) {
                gameboard.getOnePlayerBoard(nickname).setPlayerPosition(position.get(nickname));
                if (report) {
                    gameboard.getOnePlayerBoard(nickname).setVaticanReports(vaticanPosition.get(nickname), vaticanPosition.containsKey(nickname));
                }
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
        synchronized (lock) {
            ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
            playerBoard.updateDevCardSlot(slot, id);
            playerBoard.setWarehouse(warehouse);
            playerBoard.setStrongbox(strongbox);
            gameboard.changeGridCard(gridId, color, level);
            if(!nickname.equals(this.nickname)) uiType.updateBoard(nickname + "bought the level " + level + " " + color + " card");
            else uiType.updateBoard("");
        }
    }

    /**
     * called to update the board after a player discords a leader card
     * @param nickname of the player
     * @param id discarded card
     */
    public void discardLeaderCardUpdate(String nickname, String id){
        synchronized (lock) {
            ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
            playerBoard.removeHandCard(id);
            if(!nickname.equals(this.nickname)) uiType.updateBoard(nickname + " discarded a leader card");
            else uiType.updateBoard("");
        }
    }

    /**
     * called to update the board after a player plays a leader card
     * @param nickname of the player
     * @param id of the played card
     * @param warehouse new warehouse of the player
     * @param strongbox new strongbox of the player
     */
    public void playLeaderCardUpdate(String nickname, String id, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox){
        synchronized (lock) {
            ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
            playerBoard.removeHandCard(id);
            playerBoard.addPlayedCard(id, Objects.requireNonNull(findLeaderCard(id)));
            playerBoard.setWarehouse(warehouse);
            playerBoard.setStrongbox(strongbox);
            if(!nickname.equals(this.nickname))
                uiType.updateBoard(nickname + " played a leader card");
            else uiType.updateBoard("");
        }
    }

    /**
     * called to update the board after use production action
     * @param nickname of the player
     * @param warehouse new warehouse of the player
     * @param strongbox new strongbox of the player
     */
    public void useProductionUpdate(String nickname, Map<Integer, ArrayList<Resource>> warehouse, Map<Resource, Integer> strongbox){
        synchronized (lock) {
            ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
            playerBoard.setWarehouse(warehouse);
            playerBoard.setStrongbox(strongbox);
            if(!nickname.equals(this.nickname))
                uiType.updateBoard(nickname + " activated the production");
            else uiType.updateBoard("");
        }
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
        synchronized (lock) {
            ClientPlayerBoard playerBoard = gameboard.getOnePlayerBoard(nickname);
            playerBoard.setWarehouse(warehouse);
            gameboard.updateMarket(newMarbles, newFreeMarble, pos, rowOrCol);
            if(!nickname.equals(this.nickname))
                uiType.updateBoard(nickname + " took resources from the market");
            else uiType.updateBoard("");
        }
    }


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

    //------------------SOCKET------------------

    /**
     * starts the connection of the client
     */
    public void startConnection() {
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
        } catch (Exception e) {
            System.out.println("\n\nserver not available: ip or port number might be wrong\n\n");
            exit(0);
        }

        ClientSocketHandler proxy = new ClientSocketHandler(socket);
        proxy.addObserver(this);
        this.socket = proxy;
        new Thread(proxy).start();
    }

    /**
     * closes the socket of the client
     */
    public void disconnect() {
        socket.close();
    }

}
