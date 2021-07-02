package it.polimi.ingsw.controller;


import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.leaderAction.DiscardLeaderCard;
import it.polimi.ingsw.model.actions.leaderAction.PlayLeaderCard;
import it.polimi.ingsw.model.actions.normalAction.BuyDevelopmentCard;
import it.polimi.ingsw.model.actions.normalAction.UseProduction;
import it.polimi.ingsw.model.actions.normalAction.marketAction.ManageResources;
import it.polimi.ingsw.model.actions.normalAction.marketAction.MarketAction;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickColumn;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickRow;
import it.polimi.ingsw.model.actions.setupAction.AddStartingLeaderCards;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.playerboard.faithTrack.FaithTrack;
import it.polimi.ingsw.model.singleplayer.LorenzoAI;
import it.polimi.ingsw.server.ServerView;

import javax.naming.InsufficientResourcesException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * this class allows the correct execution of a game,
 * obtains information from the server about what is happening in the client
 * and then updates and modifies the model accordingly
 */
public class Controller implements Observer<ClientMessage> {

    private final Game game;
    private final ArrayList<String> nicknames;
    private final ArrayList<ServerView> serverViews;
    private final Map<String,ArrayList<LeaderCard>> startingLeaderCards;
    private final Map<String,ArrayList<LeaderCard>> selectedCards;
    private final Map<String,ArrayList<String>> leaderID;
    private final Map<String,Map<Resource,Integer>> newResources;
    private final Map<Resource,Integer> temp;
    private int playersNumber;
    private int beginCounter =0;
    private int currentServerView =0;
    private int currentActivePlayer;
    private final ArrayList<String> playersDisconnected;
    // ACTION TOOLS
    private final Map<Integer,Boolean> numOfActions;
    private final Map<Integer,Actions> currentAction;
    // MARKET ACTION TOOLS
    private ArrayList<Resource> resourceArrayList;
    private ArrayList<MarbleColors> marbleColorsArrayList;
    private int marketIndex;
    private boolean isRowOrColumn;
    // FAITH TOOLS
    private int numOfVaticanReport;
    private final Map<String,Integer> faithPositions;
    private final Map<String,Integer> startingResources;
    // RECONNECTION TOOLS
    private int resourceCounter;
    private final Map<String,Map<Integer,Boolean>> playerStatus;
    private boolean gameStarted;
    private boolean gameFinished;
    private final Map<String, Map<Integer,Boolean>> vaticanReportActivated;
    private final Map<String,Boolean> resourceManageOk;
    //STARTING MANAGE RESOURCES
    private boolean validStartingManage;


    public Controller(Game game, ArrayList<ServerView> serverViews) {
        this.temp = new HashMap<Resource,Integer>(){{
            put(Resource.COINS,0);
            put(Resource.SERVANTS,0);
            put(Resource.SHIELDS,0);
            put(Resource.STONES,0);

        }};
        this.game = game;

        this.serverViews = serverViews;
        this.newResources = new HashMap<>();
        nicknames= new ArrayList<>();
        for (int i=0;i<serverViews.size();i++){
            nicknames.add(i,game.getPlayers(i).getNickname());
        }
        startingLeaderCards= new HashMap<>();
        selectedCards= new HashMap<>();
        leaderID = new HashMap<>();
        playersNumber = game.getPlayersNumber();
        numOfActions= new HashMap<>();
        currentAction = new HashMap<>();
        // PARAMETERS FOR MARKET ACTION
        resourceArrayList = new ArrayList<>();
        marbleColorsArrayList = new ArrayList<>();

        //PARAMETERS FOR FAITHTRACK
        numOfVaticanReport = game.getNumVaticanReports();
        faithPositions = new HashMap<>();
        vaticanReportActivated = new HashMap<>();
        Map<Integer,Boolean> tempo = new HashMap<>();


        //PARAMETERS FOR STARTING RESOURCES
        this.startingResources = new HashMap<>();
        this.resourceCounter=0;
        //PARAMETERS FOR DISCONNECTION
        playersDisconnected = new ArrayList<>();
        Map<Integer,Boolean> temp2 = new HashMap<>();
        temp2.put(0,false);
        temp2.put(1,false);
        playerStatus = new HashMap<>();
        this.currentActivePlayer=0;
        gameFinished = false;
        resourceManageOk = new HashMap<>();

        for (int i = 0; i < playersNumber; i++) {
            String name = game.getPlayers(i).getNickname();
            playerStatus.put(name,temp2);
            startingResources.put(name,0);
            vaticanReportActivated.put(name,tempo);
            faithPositions.put(name,0);
            currentAction.put(i,null);
            numOfActions.put(i,false);
            resourceManageOk.put(name,false);
        }
    }


    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Extracts the information from the {@link ClientMessage} which will call a method on the controller.
     * @param message message from the client.
     */
    @Override
    public void update(ClientMessage message) {
        message.handleMessage(this);
    }


    //------------------GAME SETUP------------------

    /**
     * 1) START: send market and devCardGrid
     */
    public void startGame(){
        for (ServerView s: serverViews){
            newResources.put(s.getUsername(),temp);
            s.sendMarket(getMarket(),getFreeMarble());
            s.sendDevCardGrid(getDevCardGrid());

        }
        setupGame();
    }

    /**
     * 2) SETUP: send to each player leadercards
     */
    public void setupGame(){
        for (int i=0;i< game.getPlayersNumber();i++){
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
            ArrayList<String> leaderId = new ArrayList<>();
            String name = game.getActivePlayer().getNickname();
            if (name.equals("admin1")||name.equals("admin2")||name.equals("admin3")||name.equals("admin4")){
                for (Resource resource: Resource.values()) {
                    game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource,50);
                }
            }
            startingLeaderCards.put(name,leaderCards);
            for (LeaderCard l: leaderCards){
                leaderId.add(l.getId());
            }
            for (ServerView serverView : serverViews) {
                if (serverView.getUsername().equals(game.getActivePlayer().getNickname())) {
                    serverView.sendPlayers(getPlayers());
                    serverView.sendLeaderCards(leaderId);
                }
            }
        }
    }

    /**
     * assign the starting cards to the player
     */
    public synchronized void pickStartingLeaderCards(ArrayList<String> leaderId, String username){
        Player active = game.getActivePlayer();
        ArrayList<LeaderCard> trueCards = new ArrayList<>();
        int counter=0;
        ServerView serverView1= getServerView(username);
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            if (game.getPlayers(i).getNickname().equals(username)){
                Player player = game.getPlayers(i);
                game.setActivePlayer(player);
            }
        }
        for (String s : leaderId){
            for (LeaderCard leaderCard : startingLeaderCards.get(game.getActivePlayer().getNickname())) {
                if (s.equals(leaderCard.getId())) {
                    trueCards.add(counter, leaderCard);
                    counter++;
                }
            }
        }
        selectedCards.put(game.getActivePlayer().getNickname(),trueCards);
        AddStartingLeaderCards addStartingLeaderCards = new AddStartingLeaderCards(trueCards,game.getActivePlayer());
        try {
            game.doAction(addStartingLeaderCards);
            assert serverView1 != null;
            if(playersNumber>1) serverView1.leaderCardSetupOk();
            sendStartingResource(username);
            Map<Integer, Boolean> map = new HashMap<>();
            map.put(0,true);
            map.put(1,false);
            playerStatus.put(username, map);
        }    catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            assert serverView1 != null;
            serverView1.sendError(Error.STARTING_LEADER_CARD);
            ArrayList<String> id = new ArrayList<>();
            for (int i = 0; i < startingLeaderCards.size(); i++) {
                id.add(i,startingLeaderCards.get(username).get(i).getId());
            }
            Objects.requireNonNull(serverView1).sendLeaderCards(id);
        }
        if(isGameStarted()){
            game.setActivePlayer(active);
        }
    }






    /**
     * sends the starting resources to the player according to his turn
     * @param username of the current player
     */
    public synchronized void sendStartingResource(String username) {
        int i;
        int j=0;

        for (i=0; i< playersNumber;i++){
            String name = game.getPlayers(i).getNickname();
            if (name.equals(username) ) break;
        }
        switch (i) {
            case 0: {
                startingResources.put(username,0);
                j=0;
                break;
            }
            case 1: {
                startingResources.put(username,1);
                j=1;
                break;
            }
            case 2: {
                game.getActivePlayer().getFaithTrack().increasePosition();
                faithPositions.put(game.getActivePlayer().getNickname(),1);
                startingResources.put(username,1);
                j=1;
                break;
            }
            case 3: {
                game.getActivePlayer().getFaithTrack().increasePosition();
                faithPositions.put(game.getActivePlayer().getNickname(),1);
                startingResources.put(username,2);
                j=2;
                break;
            }
            default:{
                break;
            }
        }
        if(!isGameStarted()) {
            resourceCounter++;
            sendStartingCounter();
        }
        else{
            ServerView serverView = getServerView(username);
            assert serverView != null;
            serverView.startingResourceMessage(j);
        }
    }

    private void sendStartingCounter(){
        if (resourceCounter + playersDisconnected.size() ==playersNumber){
            faithTrackMessage();
            for (int i = 0; i < playersNumber; i++) {
                String name = game.getPlayers(i).getNickname();
                ServerView serverView = getServerView(name);
                assert serverView != null;
                int number = startingResources.get(name);
                if (!resourceManageOk.get(name)) {
                    Objects.requireNonNull(serverView).startingResourceMessage(number);
                    if (!playersDisconnected.contains(name)) {
                        resourceManageOk.put(name, true);
                    }
                }

            }
        }
    }


    /**
     * assign the starting resources to the player
     */
    public synchronized void pickStartingResources(Map<Integer, ArrayList<Resource>> resources, String username) {
        int i;
        int size=0;
        ServerView serverView = getServerView(username);
        Player active = game.getActivePlayer();
        for (i=0;i<game.getPlayersNumber();i++) {
            if (game.getPlayers(i).getNickname().equals(username)) {
                Player player = game.getPlayers(i);
                game.setActivePlayer(player);
                break;
            }
        }
        for (int j = 0; j < resources.size(); j++) {
            size+= resources.get(j).size();
        }
        switch (i){
            case 0: {
                if (size!=0){
                    Objects.requireNonNull(serverView).sendError(Error.STARTING_RESOURCES);
                    int number = startingResources.get(username);
                    serverView.startingResourceMessage(number);
                }
                else{
                    manageStartingResource(resources,username);
                    if(!gameStarted && playersNumber>1 && validStartingManage) {
                        Objects.requireNonNull(serverView).startingResourceOk();
                    }
                }
                break;
            }
            case 1:
            case 2: {
                if (((resources.get(0).size())+(resources.get(1).size())+(resources.get(2).size()))!=1) {
                    Objects.requireNonNull(serverView).sendError(Error.STARTING_RESOURCES);
                    int number = startingResources.get(username);
                    serverView.startingResourceMessage(number);
                }
                else{
                    manageStartingResource(resources,username);
                    if(!gameStarted && playersNumber>1 && validStartingManage) {
                        Objects.requireNonNull(serverView).startingResourceOk();
                    }

                }
                break;
            }
            case 3:{
                if ((resources.get(0).size())+(resources.get(1).size())+(resources.get(2).size())!=2) {
                    Objects.requireNonNull(serverView).sendError(Error.STARTING_RESOURCES);
                    int number = startingResources.get(username);
                    serverView.startingResourceMessage(number);
                }
                else{
                    manageStartingResource(resources,username);
                    if(!gameStarted && playersNumber>1 && validStartingManage) {
                        Objects.requireNonNull(serverView).startingResourceOk();
                    }
                }
                break;
            }
            default: {
                Objects.requireNonNull(serverView).sendError(Error.STARTING_RESOURCES);
                int number = startingResources.get(username);
                serverView.startingResourceMessage(number);
                break;
            }

        }
        if(isGameStarted()) {
            game.setActivePlayer(active);
        }
    }


    /**
     * manage the starting resources of the player
     * @param resources that have been chosen by the player
     * @param username of the current player
     */
    private synchronized void manageStartingResource(Map<Integer, ArrayList<Resource>> resources,String username){
        ManageResources manageResources = new ManageResources(resources, null,null,true, null);
        try {
            game.doAction(manageResources);
            validStartingManage = true;
            // LAYOUT UPDATE
            ArrayList<String> leaderID= new ArrayList<>();
            for (int i=0; i<selectedCards.get(game.getActivePlayer().getNickname()).size();i++) {
                leaderID.add(i, selectedCards.get(game.getActivePlayer().getNickname()).get(i).getId());
                this.leaderID.put(game.getActivePlayer().getNickname(), leaderID);
            }
            Map<Integer, Boolean> map = new HashMap<>();
            map.put(0,true);
            map.put(1,true);
            playerStatus.put(username, map);
            beginCounter++;
            if(!isGameStarted()) {
                begin();
            }
            else{
                ServerView serverView = getServerView(username);
                assert serverView != null;
                serverView.sendSetupGameUpdate(this.leaderID, getWarehouse(), getFaithPositions());
            }
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            // MANAGE ERROR
            validStartingManage = false;
            ServerView serverView= getServerView(username);
            assert serverView != null;
            serverView.sendError(Error.STARTING_RESOURCES);
            int number = startingResources.get(username);
            serverView.startingResourceMessage(number);
        }

    }


    /**
     * starts the first player's turn and sends him the possible actions to perform
     */
    public void begin(){
        if(playersDisconnected.size() == playersNumber) {
            return;
        }
        if (beginCounter + playersDisconnected.size() == playersNumber){
            gameStarted = true;
            for (ServerView serverView : serverViews) {
                serverView.sendSetupGameUpdate(this.leaderID, getWarehouse(), getFaithPositions());
            }
            Player player = game.getPlayers(currentActivePlayer);
            game.setActivePlayer(player);
            for (int i = 0; i < serverViews.size(); i++) {
                if (serverViews.get(i).getUsername().equals(game.getActivePlayer().getNickname())){
                    currentServerView =i;
                }
            }
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }


    //------------------ACTIONS------------------


    /**
     * check if the player has already performed any actions
     * @return the player's list of possible actions
     */
    private ArrayList<Actions> getPossibleAction() {
        ArrayList<Actions> actions = new ArrayList<>();
        //IF NO ACTION HAS BEEN TAKEN YET
        if ((!numOfActions.get(currentActivePlayer))){
            //IF THE PLAYER HAS ACTIVABLE LEADERCARDS
            if (!game.getActivePlayer().getCardsHand().isEmpty()) {
                actions.add(0, Actions.PLAYLEADERCARD);
                actions.add(1, Actions.DISCARDLEADERCARD);
                actions.add(2, Actions.BUYDEVELOPMENTCARD);
                actions.add(3, Actions.USEPRODUCTION);
                actions.add(4, Actions.MARKETACTION);
            }
            else {
                actions.add(0, Actions.BUYDEVELOPMENTCARD);
                actions.add(1, Actions.USEPRODUCTION);
                actions.add(2, Actions.MARKETACTION);
            }
        }
        //IF THE PLAYER HAS MADE A NORMAL ACTION
        else {
            //IF THE PLAYER HAS ACTIVABLE LEADERCARDS
            if (!game.getActivePlayer().getCardsHand().isEmpty()) {
                actions.add(0, Actions.PLAYLEADERCARD);
                actions.add(1, Actions.DISCARDLEADERCARD);
                actions.add(2,Actions.ENDTURN);
            }
            else{
                actions.add(0,Actions.ENDTURN);
            }

        }
        return actions;
    }


    /**
     * select the action chosen by the player
     */
    public void selectAction(Actions action){
        currentAction.put(currentActivePlayer,action);
        serverViews.get(currentServerView).sendActionResponse(action);
        switch (action){
            case MARKETACTION:
            case USEPRODUCTION:
            case BUYDEVELOPMENTCARD: {
                numOfActions.put(currentActivePlayer,true);
                break;
            }
            case DISCARDLEADERCARD:
            case PLAYLEADERCARD:{
                break;
            }
        }
    }


    /**
     * changes the active player, sends a notification to the other players and sends to the active player the possible actions to perform
     */
    public void startTurn(){
        String name = serverViews.get(currentServerView).getUsername();
        for(int i=0; i<serverViews.size(); i++){
            if(i!=currentServerView) {
                serverViews.get(i).startTurn(name);
            }
        }

        serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
    }



    /**
     * ends the player's turn and sets the new active player in the model
     */
    public synchronized void endTurn(){
        int counter =0;
        for (String username : playersDisconnected) {
            for (int j = 0; j < playersNumber; j++) {
                String name = game.getPlayers(j).getNickname();
                if (name.equals(username)) {
                    if (currentActivePlayer < j) counter++;
                }
            }
        }
        if ((gameFinished && (currentActivePlayer == playersNumber - (counter +1))) || (gameFinished && (playersNumber==1))) {
            if (playersNumber ==1) {
                boolean lorenzo = game.getLorenzo().checkEndGame();
                finalScore(lorenzo);
            }
            else {
                finalScore(false);
            }
        }
        else {
            numOfActions.put(currentActivePlayer, false);
            newResources.put(serverViews.get(currentServerView).getUsername(), temp);
            increaseActivePlayer(1);
            Player player = game.getPlayers(currentActivePlayer);
            game.setActivePlayer(player);
            for (int i = 0; i < playersNumber; i++) {
                if (serverViews.get(i).getUsername().equals(game.getActivePlayer().getNickname())) {
                    currentServerView = i;
                }
            }
            if (playersNumber == 1) {
                lorenzoAction();
            } else {
                startTurn();
            }
        }
    }


    /**
     * this method makes lorenzo draw the token in the model and invokes the serverview method to communicate it to the client
     */
    public void lorenzoAction(){
        String message = game.getLorenzo().drawToken();
        String[][] newGrid = getDevCardGrid();
        faithTrackMessage();
        LorenzoAI lorenzo = game.getLorenzo();
        serverViews.get(0).lorenzoUpdate(message, lorenzo.getTrack().getPosition(), newGrid);
        boolean lori = game.getLorenzo().checkEndGame();
        if (lori) {
            gameFinished = true;
            serverViews.get(0).endGameMessage();
            finalScore(true);
        }
        else startTurn();
    }


    /**
     * attempt to discard a leaderCard
     */
    public void discardLeaderCard(String id,String playerId){
        String username = game.getActivePlayer().getNickname();
        boolean correctName = playerId.equals(username);
        if ((currentAction.get(currentActivePlayer)==Actions.DISCARDLEADERCARD)&&(correctName)) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(0), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                doActionDiscardLeader(id,discardLeaderCard);

            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(1), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                doActionDiscardLeader(id,discardLeaderCard);

            } else {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else{
            if (correctName) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
    }



    private void doActionDiscardLeader(String id, DiscardLeaderCard discardLeaderCard){
        try {
            //DO ACTION
            game.doAction(discardLeaderCard);
            //RESET CURRENT ACTION TO NULL
            currentAction.put(currentActivePlayer, null);
            //UPDATE
            String username = game.getActivePlayer().getNickname();
            int points = faithPositions.get(username);
            faithPositions.put(username,points +1);
            faithTrackMessage();
            for (ServerView serverView : serverViews) {
                serverView.sendDiscardLeaderCardUpdate(username, id);
            }
            // SEND POSSIBLE ACTIONS
            if (endGame()) {
                for (ServerView serverView: serverViews){
                    gameFinished = true;
                    serverView.endGameMessage();
                }
            }
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }

    }


    /**
     * attempt to play a leaderCard
     */
    public void playLeaderCard(String id,String playerId){
        String username = game.getActivePlayer().getNickname();
        boolean correctName = playerId.equals(username);
        if ((currentAction.get(currentActivePlayer)==Actions.PLAYLEADERCARD)&&(correctName)) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(0));
                doActionPlayLeader(id, playLeaderCard);
            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(1));
                doActionPlayLeader(id, playLeaderCard);
            } else {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else{
            if (correctName) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
    }

    private void doActionPlayLeader(String id, PlayLeaderCard playLeaderCard) {
        try {
            //DO ACTION
            game.doAction(playLeaderCard);
            //RESET CURRENT ACTION TO DO TO NULL
            currentAction.put(currentActivePlayer, null);
            // SEND UPDATE
            String username = game.getActivePlayer().getNickname();
            Map<Integer, ArrayList<Resource>> warehouse = getWarehouse().get(username);
            for (ServerView serverView: serverViews) {
                serverView.sendPlayLeaderCardUpdate(username, id, warehouse);
            }
            // SEND POSSIBLE ACTIONS
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }


    /**
     * pick the marbles from the market and turn them into resources
     */
    public  synchronized void marketAction(int index, boolean isRow, ArrayList<Resource>exchange,String playerId){
        String username = game.getActivePlayer().getNickname();
        boolean correctName = playerId.equals(username);
        if ((currentAction.get(currentActivePlayer)==Actions.MARKETACTION)&&(correctName)) {
            ArrayList<Marbles> marbles = new ArrayList<>();
            ArrayList<Resource> resources = new ArrayList<>();
            if (isRow) {
                PickRow pickRow = new PickRow(index, marbles, game.getBoard().getMarketBoard());
                doActionMarketAction(pickRow, resources,index, true,exchange);
            } else {
                PickColumn pickColumn = new PickColumn(index, marbles, game.getBoard().getMarketBoard());
                doActionMarketAction(pickColumn, resources,index, false,exchange);
            }
        }
        else{
            if (correctName) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
    }


    private void doActionMarketAction(MarketAction marketAction, ArrayList<Resource> resources, int index, boolean isRow,ArrayList<Resource> exchange){
        try {
            //DO ACTION
            int currentPoints = game.getActivePlayer().getFaithTrack().getPosition();
            String name = game.getActivePlayer().getNickname();
            ArrayList<Marbles> marbles = marketAction.getMarbles();
            game.doAction(marketAction);
            //TAKE THE ARRAY OF MARBLES COLORS
            for (Marbles marble : marbles) {
                marble.drawEffect(resources, exchange);
            }
            if (currentPoints != game.getActivePlayer().getFaithTrack().getPosition()){
                currentPoints = game.getActivePlayer().getFaithTrack().getPosition();
                faithPositions.put(name,currentPoints);
            }
            Map<Resource, Integer> temp1 = new HashMap<>(temp);
            int count;
            for (Resource resource : resources) {
                count = temp1.get(resource);
                temp1.put(resource, count + 1);
            }
            newResources.put(game.getActivePlayer().getNickname(), temp1);
            // SEND THE MANAGE RESOURCES
            serverViews.get(currentServerView).sendResourceManageRequest(resources);
            //SAVE PARAMETERS FOR MANAGE RESOURCES
            resourceArrayList = resources;
            marbleColorsArrayList = getRowOrColumn(index, isRow);
            marketIndex= index;
            isRowOrColumn= isRow;
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            numOfActions.put(currentActivePlayer,false);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }

    /**
     * assign the new Resources to the player
     */
    public void manageResources(Map<Integer,ArrayList<Resource>> resources, Map<Resource,Integer> discRes){
        if (currentAction.get(currentActivePlayer)==Actions.MARKETACTION) {
            String username = game.getActivePlayer().getNickname();
            FaithTrack faithTrack= game.getActivePlayer().getFaithTrack();
            ManageResources manageResources = new ManageResources(resources, newResources.get(username), discRes, false,faithTrack);
            try {
                //DO ACTION
                game.doAction(manageResources);
                if (discRes!=null) {
                    int size = 0;
                    for (Resource resource : Resource.values()) {
                        size += discRes.get(resource);
                    }
                    for (int i = 0; i < playersNumber; i++) {
                        if (i != currentActivePlayer) {
                            String user= game.getPlayers(i).getNickname();
                            int increase = faithPositions.get(user) + size;
                            faithPositions.put(user, increase);
                        }
                    }
                }
                //RESET CURRENT ACTION TO NULL
                currentAction.put(currentActivePlayer,null);
                resourceArrayList= null;
                //UPDATE
                String nickname = game.getActivePlayer().getNickname();
                Map<Integer,ArrayList<Resource>> warehouse = getWarehouse().get(nickname);
                faithTrackMessage();
                for (ServerView serverView: serverViews) {
                    serverView.sendMarketActionUpdate(nickname, warehouse, marbleColorsArrayList, getFreeMarble(), marketIndex, isRowOrColumn);
                }
                marbleColorsArrayList=null;
                //SEND POSSIBLE ACTIONS
                if (endGame()){
                    gameFinished = true;
                    for (ServerView serverView: serverViews){
                        serverView.endGameMessage();
                    }
                }
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException  e) {
                serverViews.get(currentServerView).sendError(Error.MANAGE_RESOURCES);
                serverViews.get(currentServerView).sendResourceManageRequest(resourceArrayList);
            }
        }
        else{
            serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }



    /**
     * attempt to buy a developmentCard
     */
    public void buyDevelopmentCard(HashMap<Resource,Integer> depotResources, HashMap<Resource,Integer> strongboxResources, HashMap<Resource,Integer>cardDepotResources, Color color,int level,int slotNumber,String playerId){
        String username = game.getActivePlayer().getNickname();
        boolean correctName = playerId.equals(username);
        if ((currentAction.get(currentActivePlayer)==Actions.BUYDEVELOPMENTCARD)&&(correctName)) {
            String id;
            try {
                id = game.getBoard().viewCard(color, level).getId();
            } catch (NoCardsLeftException | WrongLevelException e) {
                id = null;
            }
            BuyDevelopmentCard buyDevelopmentCard = new BuyDevelopmentCard(game.getBoard(), depotResources, strongboxResources, cardDepotResources, color, level, slotNumber);
            try {
                //DO ACTION
                game.doAction(buyDevelopmentCard);
                //RESET CURRENT ACTION TO NULL
                currentAction.put(currentActivePlayer,null);
                //UPDATE

                String newId;
                try {
                    newId = game.getBoard().viewCard(color, level).getId();
                } catch (NoCardsLeftException | WrongLevelException e) {
                    newId = null;
                }
                Map<Integer,ArrayList<Resource>> warehouse = new HashMap<>(getWarehouse().get(username));
                Map<Resource,Integer> strongbox =  new HashMap<>(getStrongbox().get(username));
                for (ServerView serverView: serverViews){
                    serverView.sendBuyDevelopmentCardUpdate(username,id,slotNumber,newId,color,level,warehouse,strongbox);
                }
                // SEND POSSIBLE ACTIONS
                if (endGame()){
                    for (ServerView serverView: serverViews){
                        gameFinished = true;
                        serverView.endGameMessage();
                    }
                }
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                numOfActions.put(currentActivePlayer,false);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else {
            if (correctName) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
    }


    /**
     * attempt to use the production
     */
    public void useProduction(HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes,
                              ArrayList<Resource> boardGain, ArrayList<Resource> leaderGain, ArrayList<Integer> devSlotIndex, ArrayList<Integer> leaderCardProdIndex, String playerId ){
        String name = game.getActivePlayer().getNickname();
        boolean correctName = playerId.equals(name);
        if ((currentAction.get(currentActivePlayer)==Actions.USEPRODUCTION)&&(correctName)) {
            UseProduction useProduction = new UseProduction(devSlotIndex, leaderCardProdIndex, leaderGain, boardGain, warehouseDepotRes, cardDepotRes, strongboxRes);
            int currentPoints = game.getActivePlayer().getFaithTrack().getPosition();
            try {
                //DO ACTION
                game.doAction(useProduction);
                //RESET CURRENT ACTION TO NULL
                currentAction.put(currentActivePlayer,null);
                //UPDATE
                if (currentPoints != game.getActivePlayer().getFaithTrack().getPosition()){
                    currentPoints = game.getActivePlayer().getFaithTrack().getPosition();
                    faithPositions.put(name,currentPoints);
                }
                faithTrackMessage();
                Map<Integer,ArrayList<Resource>> warehouse = new HashMap<>(getWarehouse().get(name));
                Map<Resource,Integer> strongbox = new HashMap<>(getStrongbox().get(name));
                for (ServerView serverView: serverViews){
                    serverView.sendUseProductionUpdate(name,warehouse,strongbox);
                }
                //SEND POSSIBLE ACTION
                if (endGame()){
                    for (ServerView serverView: serverViews){
                        gameFinished = true;
                        serverView.endGameMessage();
                    }
                }
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                numOfActions.put(currentActivePlayer,false);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else{
            if (correctName) {
                serverViews.get(currentServerView).sendError(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
    }

    /**
     * this method checks the faithtrack status and sends the update message to the players
     */
    private synchronized void faithTrackMessage() {
        boolean isActive = game.getNumVaticanReports() != numOfVaticanReport;
        ArrayList<String> update = new ArrayList<>();
        int vaticanReportPos = 0;
        if (isActive){
            numOfVaticanReport = game.getNumVaticanReports();
            for (int i = 0; i < playersNumber; i++) {
                Player player = game.getPlayers(i);
                int position = player.getFaithTrack().getPosition();
                String username = player.getNickname();
                Map<Integer,Boolean> temp = vaticanReportActivated.get(username);
                switch (numOfVaticanReport){
                    case 1: {
                        if (position>=5){
                            update.add(username);
                            temp.put(8,true);
                        }
                        else {
                            temp.put(8,false);
                        }
                        vaticanReportPos=8;
                        vaticanReportActivated.put(username,temp);
                        break;
                    }
                    case 2: {
                        if (position>=12){
                            update.add(username);
                            temp.put(16,true);
                        }
                        else {
                            temp.put(16,false);
                        }
                        vaticanReportPos=16;
                        vaticanReportActivated.put(username,temp);
                        break;
                    }
                    case 3:{
                        if (position>=19){
                            update.add(username);
                            temp.put(24,true);
                        }
                        else {
                            temp.put(24,false);
                        }
                        vaticanReportPos=24;
                        vaticanReportActivated.put(username,temp);
                        break;
                    }
                    default: break;
                }
                faithPositions.put(username,position);
            }
        }

        for (int i = 0; i < playersNumber; i++) {
            String name = game.getPlayers(i).getNickname();
            int position = game.getPlayers(i).getFaithTrack().getPosition();
            faithPositions.put(name,position);
        }

        Map<String,Integer> faith = new HashMap<>(faithPositions);
        for (ServerView serverView: serverViews){
            serverView.sendFaithMessage(update,faith,isActive, vaticanReportPos);
        }

    }

    /**
     * @return true true if the game is over
     */
    public boolean endGame(){
        return game.endGame();
    }


    /**
     * this method calculates the final scores of the players and sends a message to the client with the final ranking
     * @param lorenzo is true when lorenzo wins
     */
    public void finalScore(boolean lorenzo){
        Map<String,Integer> score= new HashMap<>();
        String name;
        int playerPoints;
        if (playersNumber==1){
            playerPoints = game.getActivePlayer().getVictoryPoints();
            name = game.getActivePlayer().getNickname();
            score.put(name,playerPoints);
            Map<String,Integer>  sortedMapReverseOrder =  score.entrySet().
                    stream().
                    sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            serverViews.get(0).finalScoreMessage(sortedMapReverseOrder,lorenzo);
        }
        else{
            for (int i = 0; i < playersNumber; i++) {
                Player player = game.getPlayers(i);
                playerPoints = player.getVictoryPoints();
                name = player.getNickname();
                score.put(name,playerPoints);
            }
            Map<String,Integer>  sortedMapReverseOrder =  score.entrySet().
                    stream().
                    sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            for (ServerView serverView: serverViews){
                serverView.finalScoreMessage(sortedMapReverseOrder,lorenzo);
            }
        }

    }

    //------------------TOOLS------------------


    /**
     * This method communicates the disconnection of a player to all the others.
     * The method then evaluates the player's status and, if applicable, passes the turn to the next player
     * @param username of the player who logged out
     */
    public synchronized void playerDisconnection(String username){
        for (ServerView s: serverViews){
            s.disconnectionMessage(username);
        }
        playersDisconnected.add(0,username);
        if (playersDisconnected.size()==playersNumber){
            return;
        }
        String name = game.getPlayers(currentActivePlayer).getNickname();
        if(playerStatus.get(username).get(0) && playerStatus.get(username).get(1)) {
            beginCounter--;
        }

        if(!isGameStarted()) {
            if (name.equals(username)) {
                currentAction.put(currentActivePlayer, null);
                increaseActivePlayer(1);
            }
            if(resourceCounter+playersDisconnected.size()==playersNumber)
                sendStartingCounter();
        }
        else{
            if (name.equals(username)){
                endTurn();
            }
        }
        if(!isGameStarted()) {
            begin();
        }

    }

    /**
     * this method increases the value referring to the active player recursively, until an unconnected player is found
     * @param callNum is the number of times the method is called recursively
     */
    private void increaseActivePlayer(int callNum){
        currentActivePlayer++;
        if (currentActivePlayer>=playersNumber)
            currentActivePlayer=0;
        if(callNum==playersNumber)
            return;
        Player player = game.getPlayers(currentActivePlayer);
        if (playersDisconnected.contains(player.getNickname())) {
            increaseActivePlayer(callNum+1);
        }
    }

    /**
     * replaces the serverView of the disconnected player with the new one
     * @param serverView updated of the player you want to reconnect
     */
    public synchronized void playerReconnection(ServerView serverView){
        int i;
        Map<String,Integer> cardInHand = new HashMap<>();
        Map<String,Boolean> playerConnected = new HashMap<>();
        int number1;
        String name;
        Player temp;
        boolean temp1;
        for (int j = 0; j < playersNumber; j++) {
            temp = game.getPlayers(j);
            name = temp.getNickname();
            number1 = temp.getCardsHand().size();
            cardInHand.put(name,number1);
        }
        playersDisconnected.remove(serverView.getUsername());

        for (int k = 0; k < playersNumber; k++) {
            name = game.getPlayers(k).getNickname();
            temp1 = true;
            for (String s : playersDisconnected) {
                if (s.equals(name)) {
                    temp1 = false;
                    break;
                }
            }
            playerConnected.put(name,temp1);
        }


        String username = serverView.getUsername();
        for (ServerView s: serverViews){
            s.reconnectionMessage(username);
        }
        for (i = 0; i < serverViews.size(); i++) {
            if (serverViews.get(i).getUsername().equals(username)){
                serverViews.set(i,serverView);
                break;
            }
        }

        // standard reconnection messages
        serverView.sendPlayers(getPlayers());
        serverView.sendDevCardGrid(getDevCardGrid());
        serverView.sendMarket(getMarket(),getFreeMarble());
        Map<String, Map<Integer, Boolean>> tempo = new HashMap<>(vaticanReportActivated);
        boolean setupDone = playerStatus.get(username).get(0) && playerStatus.get(username).get(1);
        serverView.sendReconnectionMessage(username, getDevCardSlots(), getFaithPositions(), getLeaderCardsPlayed(),
                    getLeaderCards(username), getStrongbox(), getWarehouse(), cardInHand, playerConnected, tempo, setupDone);


        // starting leader cards not yet chosen
        if (!playerStatus.get(username).get(0)){
            ArrayList<String> starting= new ArrayList<>();
            for (int j = 0; j < startingLeaderCards.get(serverView.getUsername()).size(); j++) {
                starting.add(j,startingLeaderCards.get(username).get(j).getId());
            }
            serverView.sendLeaderCards(starting);
            return;
        }
        //starting resources not yet chosen
        else if (!playerStatus.get(username).get(1)){
            int number= startingResources.get(username);
            serverView.startingResourceMessage(number);
            return;
        }
        // if all players were disconnected
        else if (playersDisconnected.size()+1 == playersNumber){
            increaseActivePlayer(1);
            Player player = game.getPlayers(currentActivePlayer);
            game.setActivePlayer(player);
            for (int k = 0; k < serverViews.size(); k++) {
                if (serverViews.get(k).getUsername().equals(username)) currentServerView =k;
            }
            startTurn();
        }
    }


    /**
     * @return a map containing the development cards of all the players
     */
    private Map<String, Map<Integer, ArrayList<String>>> getDevCardSlots(){
        Map<String, Map<Integer, ArrayList<String>>> devCardSlots = new HashMap<>();
        for (int i=0;i<serverViews.size();i++){
            Player player = game.getPlayers(i);
            devCardSlots.put(player.getNickname(), player.getPlayerBoard().getDevSlotsCardId());
        }
        return devCardSlots;
    }


    /**
     * @return a map that contains the faith positions of all the players
     */
    private Map<String,Integer> getFaithPositions(){
        Map<String,Integer> faithPositions = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            faithPositions.put(player.getNickname(),player.getFaithTrack().getPosition());
        }
        return faithPositions;
    }


    /**
     * @return a map containing the leader cards played by all players
     */
    private Map<String, ArrayList<String>> getLeaderCardsPlayed(){
        Map<String, ArrayList<String>> leaderCards = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            ArrayList<String> id = new ArrayList<>();
            for (int j=0;j<player.getPlayerBoard().getLeaderCards().size();j++){
                id.add(j,player.getPlayerBoard().getLeaderCards().get(j).getId());
            }
            leaderCards.put(player.getNickname(),id);
        }
        return leaderCards;
    }


    /**
     * @param username of the player I want to know about
     * @return the leader cards of the selected player
     */
    private ArrayList<String> getLeaderCards(String username){
        ArrayList<String> leaderCards = new ArrayList<>();
        for (int i=0;i<serverViews.size();i++){
            if (game.getPlayers(i).getNickname().equals(username)){
                Player player = game.getPlayers(i);
                for (int j=0;j<player.getCardsHand().size();j++){
                    leaderCards.add(j,player.getCardsHand().get(j).getId());
                }
            }
        }
        return leaderCards;
    }


    /**
     * @return a map with the strongboxes of all the players
     */
    private Map<String, Map<Resource,Integer>> getStrongbox(){
        Map<String, Map<Resource,Integer>> strongbox = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            Map<Resource,Integer> map = player.getPlayerBoard().getStrongbox().getResources();
            strongbox.put(player.getNickname(), map);
        }

        return strongbox;
    }


    /**
     * @return a map with all players' warehouses
     */
    private Map<String, Map<Integer, ArrayList<Resource>>> getWarehouse(){
        Map<String, Map<Integer, ArrayList<Resource>>> warehouse = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            Map<Integer, ArrayList<Resource>> depots = new HashMap<>();
            for (int j=0;j<player.getPlayerBoard().getWarehouse().getDepots().size();j++){
                ArrayList<Resource> resources = new ArrayList<>();
                for (int k=0;k<player.getPlayerBoard().getWarehouse().getDepots().get(j).getOccupied();k++) {
                   resources.add(k,player.getPlayerBoard().getWarehouse().getDepots().get(j).getResource());
                }
                depots.put(j,resources);
            }
            warehouse.put(player.getNickname(),depots);
        }
        return warehouse;
    }


    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    /**
     * @return the market
     */
    private MarbleColors[][] getMarket(){
        MarbleColors[][] marbleColors = new MarbleColors[game.getBoard().getMarketBoard().getRows()][game.getBoard().getMarketBoard().getColumns()];
        Marbles[][] marbles = game.getBoard().getMarketBoard().getMarbleGrid();
        for (int i=0; i<game.getBoard().getMarketBoard().getRows();i++){
            for (int j=0; j<game.getBoard().getMarketBoard().getColumns();j++){
                marbleColors[i][j]= marbles[i][j].getColor();
            }

        }
        return marbleColors;
    }


    /**
     * @return the free marble
     */
    private MarbleColors getFreeMarble(){
        return game.getBoard().getMarketBoard().getFreeMarble().getColor();
    }


    /**
     * @return the matrix of dev card
     */
    private String[][] getDevCardGrid(){
        String[][] devCardGrid= new String[game.getBoard().getCardRows()][game.getBoard().getCardColumns()];
        for (int i=0;i<game.getBoard().getCardRows();i++){
            for (int j=0; j<game.getBoard().getCardColumns();j++){
                try {
                    devCardGrid[i][j] = game.getBoard().getCardGrid()[i][j].lookFirst().getId();
                } catch (NoCardsLeftException e) {
                    devCardGrid[i][j] = null;
                }

            }
        }
        return devCardGrid;
    }


    /**
     * @param username  of the player
     * @return return the serverview of the player
     */
    private ServerView getServerView(String username){
        for (ServerView serverView: serverViews){
            if (serverView.getUsername().equals(username)) return serverView;
        }
        return null;
    }

    /**
     * @return the players of the game
     */
    private ArrayList<String> getPlayers(){
        ArrayList<String> players = new ArrayList<>();
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            players.add(0,game.getPlayers(i).getNickname());
        }
        return players;
    }


    /**
     * @param index of the market
     * @param isRow true row false column
     * @return the marbles selected
     */
    private ArrayList<MarbleColors> getRowOrColumn(int index, boolean isRow){
        ArrayList<Marbles> marbles;
        ArrayList<MarbleColors> resources = new ArrayList<>();
        if (isRow) marbles= game.getBoard().getMarketBoard().getOneRow(index);
        else marbles= game.getBoard().getMarketBoard().getOneColumn(index);
        for (int i = 0; i < marbles.size(); i++) {
            resources.add(i,marbles.get(i).getColor());
        }
        return resources;
    }


 }

