package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.messages.clientToServer.*;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.net.Socket;
import java.util.*;

public class ClientView extends View implements Observer<ServerMessage>{

    private ClientSocketHandler socket;
    private final String ip;
    private final int port;

    private final Object lock = new Object();
    private boolean updated = false;
    private boolean faithUpdateReceived = false;
    private boolean setupDone = false;
    private boolean numOfPlayers = false;


    public ClientView(String ip, int port, UserInterface ui) {
        super(ui);
        this.ip = ip;
        this.port = port;
    }

    /**
     * called to send the login nickname
     * @param nickname selected from the player
     */
    public void sendLogin(String nickname){
        numOfPlayers=false;
        socket.sendMessage(new LoginMessage(nickname));
    }

    /**
     * called if this is the first player, asks the game size
     * @param maxNum maximum number of players
     */
    public void numOfPlayers(int maxNum){
        synchronized (lock) {
            while (!numOfPlayers) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getUiType().playersNumber(maxNum);
        }
    }

    /**
     * called to send the selected game size to the server
     * @param num number of players
     */
    public void sendNumOfPlayers(int num){
        socket.sendMessage(new PlayerNumberReply(num));
    }

    /**
     * manages the Username Response message
     * @param nickname the player set
     */
    public void manageUsernameResponse(boolean isFree, String nickname){
        synchronized (lock) {
            if (isFree) {
                setNickname(nickname);
                getUiType().loginDone();
                numOfPlayers=true;
                lock.notifyAll();
            } else {
                getUiType().failedLogin();
            }
        }
    }

    /**
     * called when the server sends an error message to the client
     * @param errorType error to manage
     */
    public void errorManagement(Error errorType){
        synchronized (lock){
            super.errorManagement(errorType);
            updated=true;
            lock.notifyAll();
        }
    }

    //------------------GAME SETUP------------------

    /**
     * adds all the players to the game
     * @param players nicknames of the players
     */
    public void addPlayersToGameboard(ArrayList<String> players){
        synchronized (lock){
            super.addPlayersToGameboard(players);
            lock.notifyAll();
        }
    }

    /**
     * informs the client about the starting market configuration
     * @param grid this is the market configuration
     * @param free this is the free marble that has to be used to perform the market action
     */
    public void marketSetup(MarbleColors[][] grid, MarbleColors free) {
        synchronized (lock) {
            super.marketSetup(grid, free);
        }
    }

    /**
     * informs the client about the starting development card grid (only the cards on top of it)
     * @param cards initial card grid
     */
    public void developmentCardGridSetup(String[][] cards){
        synchronized (lock) {
           super.developmentCardGridSetup(cards);
        }
    }


    /**
     * called to make the player chose the starting leadercards
     * @param leaderCardID IDs of the cards
     */
    public void selectStartingCards(ArrayList<String> leaderCardID){
        synchronized (lock) {
            super.selectStartingCards(leaderCardID);
        }
    }

    /**
     * called to send the starting leadercards to the server
     * @param cards selected from the player
     */
    public void sendStartingCards(ArrayList<String> cards){
        socket.sendMessage(new LeaderCardsReply(cards, getNickname()));
    }

    /**
     * called if the leadercard selection was correct
     */
    public void leaderCardsDone(){
        synchronized (lock) {
            super.leaderCardsDone();
        }
    }

    /**
     * called to make the player chose the starting resources
     * @param amount num of resources
     */
    public void startingResources(int amount){
        System.out.println("sono dentro  " +amount);
        synchronized (lock) {
            getUiType().startingResources(amount);
        }
    }

    /**
     * called to send the starting resources to the server
     * @param warehouse configuration
     */
    public void sendStartingResources(Map<Integer, ArrayList<Resource>> warehouse){
        socket.sendMessage(new ResourcesReply(warehouse, getNickname()));
    }

    /**
     * called if the starting resources selection was correct
     */
    public void startingResDone(){
        synchronized (lock) {
            getUiType().endStartingResourcesSetup();
        }
    }


    //------------------ACTIONS------------------

    /**
     * called to make the player decide what action he wants to perform
     * @param possibleActions list of the actions the player can choose from
     */
    public void pickAction(ArrayList<Actions> possibleActions){
        synchronized (lock){
            while(!updated || !setupDone) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.pickAction(possibleActions);
        }
    }

    /**
     * called to send to the server the action the player decided to do this turn
     * @param action selected from the player
     */
    public void sendAction(Actions action){
        if(!action.equals(Actions.ENDTURN)) updated=false;
        socket.sendMessage(new ActionReply(action));
    }

    /**
     * called to make the player do the selected action
     * @param action what the player decided to do
     */
    public void doAction(Actions action){
        synchronized (lock) {
            super.doAction(action);
        }
    }

    /**
     * called when the player ends his turn
     */
    public void endTurn(){
        getUiType().endTurn();
        socket.sendMessage(new EndTurn());
        if(getGameboard().getNumOfPlayers()==1) updated = false;
    }

    /**
     * called from UI to send the market action parameters to the server
     * @param pos column or row picked by the player
     * @param rowOrCol true if pos is row, false if column
     * @param exchangeBuffResources resources obtained from white marbles if possible
     * @param username of this client
     */
    public void marketAction(int pos, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources, String username){
        socket.sendMessage(new RowColumnSelection(pos, rowOrCol, exchangeBuffResources, username));
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
     * @param username of this client
     */
    public void useProduction(ArrayList<Integer> developmentCardSlotIndex, ArrayList<Integer> leaderCardProdIndex, ArrayList<Resource> leaderCardProdGain,
                              ArrayList<Resource> boardResources, HashMap<Resource, Integer> warehouseResources, HashMap<Resource, Integer> leaderDepotResources,
                              HashMap<Resource, Integer> strongboxResources, String username){
        socket.sendMessage(new UseProductionParameters(warehouseResources, leaderDepotResources, strongboxResources,
                boardResources, leaderCardProdGain, developmentCardSlotIndex, leaderCardProdIndex, username));
    }

    /**
     * called from UI to send the buy development card action parameters to the server
     * @param color of the card
     * @param level of the card
     * @param devCardSlot slot to place the card in
     * @param warehouseDepotRes resources inside the warehouse that the player wants to pay
     * @param cardDepotRes resources inside the leadercard depots that the player wants to pay
     * @param strongboxRes resources inside the strongbox that the player wants to pay
     * @param username of this client
     */
    public void buyDevCard(Color color, int level, int devCardSlot, HashMap<Resource, Integer> warehouseDepotRes,
                           HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes, String username){
        socket.sendMessage(new BuyDevelopmentCardParameters(color, level, devCardSlot, warehouseDepotRes, cardDepotRes, strongboxRes, username));
    }

    /**
     * called from UI to send the play leadercard action parameters to the server
     * @param id of the selected card
     * @param username of this client
     */
    public void playLeaderCard(String id,String username){
        socket.sendMessage(new PlayLeaderCardParameters(id, username));
    }

    /**
     * called from UI to send the discard leadercard action parameters to the server
     * @param id of the selected card
     * @param username of this client
     */
    public void discardLeaderCard(String id,String username){
        socket.sendMessage(new DiscardLeaderCardParameters(id, username));
    }

    /**
     * called to make the player position the acquired resources
     * @param resources new resources
     */
    public void manageResources(ArrayList<Resource> resources){
        synchronized (lock) {
            super.manageResources(resources);
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
     * method to block the action update if the faith update hasn't been received yet
     */
    private void waitForFaithUpdate(){
        while (!faithUpdateReceived) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        faithUpdateReceived=false;
    }

    /**
     * called to setup all playerboard with initial values
     * @param leaderCards hand of each player
     * @param resources warehouse of each player
     * @param faithTracks faith track position of each player
     */
    public void setupGameUpdate(Map<String,ArrayList<String>> leaderCards, Map<String,Map<Integer, ArrayList<Resource>>> resources, Map<String,Integer> faithTracks){
        synchronized (lock) {
            while(getGameboard().getPlayers().isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.setupGameUpdate(leaderCards, resources, faithTracks);
            setupDone=true;
            updated=true;
            lock.notifyAll();
        }
    }

    /**
     * called when another player begins his turn
     * @param player who is beginning his turn
     */
    public void turnNotification(String player){
        synchronized (lock) {
            if(!player.equals(getNickname())) {
                getUiType().startTurnNotification(player);
                updated=false;
            }
        }
    }

    /**
     * called when a player disconnects from the game
     * @param nickname of the player
     */
    public void playerDisconnected(String nickname){
        synchronized (lock){
            while (!getGameboard().getOnePlayerBoard(nickname).isConnected()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getGameboard().getOnePlayerBoard(nickname).setConnected(false);
            getUiType().handleDisconnection(nickname);
            updated=true;
            lock.notifyAll();
        }
    }

    /**
     * called when a player reconnects to the game
     * @param nickname of the player
     */
    public void playerReconnected(String nickname){
        synchronized (lock){
            while (getGameboard().getOnePlayerBoard(nickname).isConnected()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getGameboard().getOnePlayerBoard(nickname).setConnected(true);
            getUiType().handleReconnection(nickname);
            lock.notifyAll();
        }
    }

    /**
     * called after a player reconnects to the game, the parameters contains all the information regarding the game
     * @param username of this client
     * @param devCardSlots all dev cards bought by all the player
     * @param faithPositions all player's faith track position
     * @param leaderCardsPlayed all leader cards played by all player
     * @param leaderCards player hand
     * @param strongbox every strongbox
     * @param warehouse every warehouse
     * @param cardsInHand number of cards in each player's hand
     * @param playersConnected connection status of each player
     * @param vaticanReportActivated vatican report status for each player
     * @param setupDone true if the player has already selected the starting resources and leader cards, false otherwise
     */
    public void reconnectionUpdate(String username, Map<String, Map<Integer, ArrayList<String>>> devCardSlots, Map<String, Integer> faithPositions, Map<String,
            ArrayList<String>> leaderCardsPlayed, ArrayList<String> leaderCards, Map<String, Map<Resource, Integer>> strongbox,
                                   Map<String, Map<Integer, ArrayList<Resource>>> warehouse, Map<String, Integer> cardsInHand,
                                   Map<String, Boolean> playersConnected, Map<String, Map<Integer, Boolean>> vaticanReportActivated, boolean setupDone){
        synchronized (lock) {
            this.setupDone = setupDone;
            setNickname(username);
            while(getGameboard().getPlayers().isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (String nickname : devCardSlots.keySet()) {
                getGameboard().getOnePlayerBoard(nickname).setDevCardSlot(devCardSlots.get(nickname));
            }
            for (String nickname : faithPositions.keySet()) {
                getGameboard().getOnePlayerBoard(nickname).setPlayerPosition(faithPositions.get(nickname));
            }
            getGameboard().getOnePlayerBoard(getNickname()).setHand(leaderCards);
            for (String nickname : strongbox.keySet()) {
                getGameboard().getOnePlayerBoard(nickname).setStrongbox(strongbox.get(nickname));
            }
            for (String nickname : warehouse.keySet()) {
                getGameboard().getOnePlayerBoard(nickname).setWarehouse(warehouse.get(nickname));
            }
            for (String nickname : cardsInHand.keySet()){
                getGameboard().getOnePlayerBoard(nickname).setHandSize(cardsInHand.get(nickname));
            }
            for (String nickname : playersConnected.keySet()){
                getGameboard().getOnePlayerBoard(nickname).setConnected(playersConnected.get(nickname));
            }
            for (String nickname : leaderCardsPlayed.keySet()) {
                for(String id : leaderCardsPlayed.get(nickname)){
                    getGameboard().getOnePlayerBoard(nickname).addPlayedCard(id, Objects.requireNonNull(findLeaderCard(id)));
                }
            }
            for (String nickname : vaticanReportActivated.keySet()){
                getGameboard().getOnePlayerBoard(nickname).setVaticanReports(vaticanReportActivated.get(nickname));
            }
            if(setupDone){
                getUiType().updateBoard("Reconnected");
                getUiType().endTurn();
                updated=true;
                lock.notifyAll();
            }
        }
    }

    /**
     * called to update the board after Lorenzo's move
     * @param lorenzoPosition new Lorenzo's position
     * @param newCardGrid new development card grid
     */
    public void lorenzoUpdate(String message, int lorenzoPosition, String[][] newCardGrid){
        synchronized (lock) {
            waitForFaithUpdate();
            while (!setupDone){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.lorenzoUpdate(message, lorenzoPosition, newCardGrid);
            updated=true;
            lock.notifyAll();
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
            super.faithTrackUpdate(vaticanPosition, position, report);
            faithUpdateReceived=true;
            lock.notifyAll();
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
            while (!setupDone){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.buyCardUpdate(nickname, id, slot, gridId, color, level, warehouse, strongbox);
            updated=true;
            lock.notifyAll();
        }
    }

    /**
     * called to update the board after a player discords a leader card
     * @param nickname of the player
     * @param id discarded card
     */
    public void discardLeaderCardUpdate(String nickname, String id){
        synchronized (lock) {
            waitForFaithUpdate();
            while (!setupDone){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.discardLeaderCardUpdate(nickname, id);
            updated=true;
            lock.notifyAll();
        }
    }

    /**
     * called to update the board after a player plays a leader card
     * @param nickname of the player
     * @param id of the played card
     * @param warehouse new warehouse of the player
     */
    public void playLeaderCardUpdate(String nickname, String id, Map<Integer, ArrayList<Resource>> warehouse){
        synchronized (lock) {
            while (!setupDone){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.playLeaderCardUpdate(nickname, id, warehouse);
            updated=true;
            lock.notifyAll();
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
            waitForFaithUpdate();
            while (!setupDone){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.useProductionUpdate(nickname, warehouse, strongbox);
            updated=true;
            lock.notifyAll();
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
            waitForFaithUpdate();
            while (!setupDone){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.marketActionUpdate(nickname, warehouse, newMarbles, newFreeMarble, pos, rowOrCol);
            updated=true;
            lock.notifyAll();
        }
    }

    /**
     * called to notify the players that this is the last round
     */
    public void endGame(){
        synchronized (lock) {
            super.endGame();
        }
    }

    /**
     * called to notify the final scores and the winner to the players
     * @param finalScores final victory points
     * @param lorenzoWin true if Lorenzo won the game, false if he lost or if the game is singleplayer
     */
    public void finalScoresUpdate(Map<String, Integer> finalScores, boolean lorenzoWin){
        synchronized (lock){
            if(getGameboard().getNumOfPlayers()==1) {
                getUiType().lorenzoScoreboard(getNickname(), finalScores.get(getNickname()), lorenzoWin);
            }
            else {
                getUiType().scoreboard(finalScores);
            }
        }
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
            System.exit(0);
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
