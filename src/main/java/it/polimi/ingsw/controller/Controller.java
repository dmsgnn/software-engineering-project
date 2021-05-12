package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.MarbleColors;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.leaderAction.DiscardLeaderCard;
import it.polimi.ingsw.model.actions.leaderAction.PlayLeaderCard;
import it.polimi.ingsw.model.actions.normalAction.BuyDevelopmentCard;
import it.polimi.ingsw.model.actions.normalAction.UseProduction;
import it.polimi.ingsw.model.actions.normalAction.marketAction.ManageResources;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickColumn;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickRow;
import it.polimi.ingsw.model.actions.setupAction.AddStartingLeaderCards;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.server.ServerView;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private int playerCounter=0;
    private int currentServerView =0;
    private int currentActivePlayer =0;
    private Map<Integer,ArrayList<Boolean>> numOfActions;
    private Map<Integer,Actions> currentAction;
    private ArrayList<Resource> resourceArrayList;
    private ArrayList<MarbleColors> marbleColorsArrayList;
    private int marketIndex;
    private boolean isRowOrColumn;


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
        ArrayList<Boolean> booleans = new ArrayList<Boolean>();
        booleans.add(0,false);
        booleans.add(1,false);
        numOfActions= new HashMap<Integer,ArrayList<Boolean>>(){{
            for (int i = 0; i < playersNumber; i++) {
                put(i,booleans);
            }
        }};
        currentAction = new HashMap<Integer,Actions>(){{
            for (int i = 0; i < playersNumber; i++) {
                put(i,null);
            }
        }};
        // PARAMETERS FOR MARKET ACTION
        resourceArrayList = new ArrayList<>();
        marbleColorsArrayList = new ArrayList<>();
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
            game.setActivePlayer(game.getPlayers(i));
            ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
            ArrayList<String> leaderId = new ArrayList<>();
            startingLeaderCards.put(game.getActivePlayer().getNickname(),leaderCards);
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
        ArrayList<LeaderCard> trueCards = new ArrayList<>();
        int counter=0;
        int j=0;
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            if (game.getPlayers(i).getNickname().equals(username)){
                game.setActivePlayer(game.getPlayers(i));
                for(int k=0;k< serverViews.size();k++){
                    if (serverViews.get(k).getUsername().equals(username)) j=k;
                }
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
        }    catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            //solito errore
            e.printStackTrace();
        }
        sendStartingResource(username);


    }

    public synchronized void sendStartingResource(String username) {
        int i;
        for (i=0; i< game.getPlayersNumber();i++){
            if (game.getPlayers(i).getNickname().equals(username) ) break;
        }
        switch (i) {
            case 0: {
                getServerView(username).startingResourceMessage(0);
                break;

            }
            case 1: {
                getServerView(username).startingResourceMessage(1);
                break;
            }
            case 2: {
                getServerView(username).startingResourceMessage(1);
                game.getActivePlayer().getFaithTrack().increaseVictoryPoints(1);
                break;
            }
            case 3: {
                getServerView(username).startingResourceMessage(2);
                game.getActivePlayer().getFaithTrack().increaseVictoryPoints(1);
                break;
            }
            default:{
                System.out.println("error");
                break;
            }
        }

    }


    /**
     * assign the starting resources to the player
     */
    public synchronized void pickStartingResources(Map<Integer, ArrayList<Resource>> resources, String username) {
        int i;
        for (i=0;i<game.getPlayersNumber();i++) {
            if (game.getPlayers(i).getNickname().equals(username)) {
                game.setActivePlayer(game.getPlayers(i));
                break;
            }
        }
        switch (i){
            case 0: {
                if ((resources.get(1).size())+(resources.get(2).size())+(resources.get(3).size())!=0) {
                    sendStartingResource(username);
                }
                else{
                    getServerView(username).sendError("INVALID STARTING RESOURCES");
                    manageStartingResource(resources,username);
                }
                break;
            }
            case 1:
            case 2: {
                if ((resources.get(1).size())+(resources.get(2).size())+(resources.get(3).size())!=1) {
                    sendStartingResource(username);
                }
                else{
                    getServerView(username).sendError("INVALID STARTING RESOURCES");
                    manageStartingResource(resources,username);
                }
                break;
            }
            case 3:{
                if ((resources.get(1).size())+(resources.get(2).size())+(resources.get(3).size())!=2) {
                    sendStartingResource(username);
                }
                else{
                    getServerView(username).sendError("INVALID STARTING RESOURCES");
                    manageStartingResource(resources,username);
                }
                break;
            }
            default: {
                getServerView(username).sendError("INVALID STARTING RESOURCES");
                manageStartingResource(resources,username);
                break;
            }

        }





    }


    private synchronized void manageStartingResource(Map<Integer, ArrayList<Resource>> resources,String username){
        ManageResources manageResources = new ManageResources(resources, null,null,true);
        try {
            game.doAction(manageResources);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            // errore nel manage
            System.out.println( username + " invalid manage resources ");
            sendStartingResource(username);
            //e.printStackTrace();
        }
        //layout update
        ArrayList<String> leaderID= new ArrayList<>();
        for (int i=0; i<selectedCards.get(game.getActivePlayer().getNickname()).size();i++){
            leaderID.add(i,selectedCards.get(game.getActivePlayer().getNickname()).get(i).getId());
            this.leaderID.put(game.getActivePlayer().getNickname(),leaderID);
        }

        begin();

    }


    public void begin(){
        playerCounter++;
        if (playerCounter==game.getPlayersNumber()){
            for (ServerView serverView : serverViews) {
                serverView.sendSetupGameUpdate(this.leaderID, getWarehouse(), getFaith());
            }
            game.setActivePlayer(game.getPlayers(0));
            for (int i = 0; i < serverViews.size(); i++) {
                if (serverViews.get(i).getUsername().equals(game.getActivePlayer().getNickname())){
                    currentServerView =i;
                }
            }
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }


    //------------------ACTIONS------------------

    private ArrayList<Actions> getPossibleAction() {
        ArrayList<Actions> actions = new ArrayList<>();
        //SE NON E' STATA COMPIUTA ANCORA NESSUNA AZIONE
        if ((!numOfActions.get(currentActivePlayer).get(0))){
            //SE IL GIOCATORE HA DELLE LEADERCARDS ATTIVABILI
            if (game.getActivePlayer().getCardsHand().size() != 0) {
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
        //SE IL GIOCATORE HA FATTO UNA NORMAL ACTION
        else {
            //SE IL GIOCATORE HA DELLE LEADERCARDS ATTIVABILI
            if (game.getActivePlayer().getCardsHand().size() != 0) {
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
                numOfActions.get(currentActivePlayer).add(0, true);
                break;
            }
            case DISCARDLEADERCARD:
            case PLAYLEADERCARD:{
                numOfActions.get(currentActivePlayer).add(1, true);
                break;
            }
        }
    }


    public void startTurn(){
        serverViews.get(currentServerView).startTurn();
        serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
    }


    public void endTurn(){
        ArrayList<Boolean> booleans = new ArrayList<Boolean>(){{
            add(0,false);
            add(1,false);
        }};
        numOfActions.put(currentActivePlayer,booleans);
        currentActivePlayer++;
        if (currentActivePlayer>=playersNumber) currentActivePlayer=0;
        game.setActivePlayer(game.getPlayers(currentActivePlayer));
        for (int i = 0; i < playersNumber; i++) {
            if (serverViews.get(i).getUsername().equals(game.getActivePlayer().getNickname())){
                currentServerView=i;
            }
        }
        startTurn();
    }


    /**
     * attempt to discard a leaderCard
     */
    public void discardLeaderCard(String id ){
        if (currentAction.get(currentActivePlayer)==Actions.DISCARDLEADERCARD) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(0), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                try {
                    //DO ACTION
                    game.doAction(discardLeaderCard);
                    //RESET CURRENT ACTION TO NULL
                    currentAction.put(currentActivePlayer, null);
                    //UPDATE
                    serverViews.get(currentServerView).sendDiscardLeaderCardUpdate(game.getActivePlayer().getNickname(),id,game.getActivePlayer().getFaithTrack().getPosition());
                    // SEND POSSIBLE ACTIONS
                    serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
                } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                    serverViews.get(currentServerView).sendError("INVALID DISCARD LEADER CARD ACTION");
                    serverViews.get(currentServerView).sendActionResponse(Actions.DISCARDLEADERCARD);
                }
            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(1), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                try {
                    //DO ACTION
                    game.doAction(discardLeaderCard);
                    //RESET CURRENT ACTION TO NULL
                    currentAction.put(currentActivePlayer, null);
                    //UPDATE
                    serverViews.get(currentServerView).sendDiscardLeaderCardUpdate(game.getActivePlayer().getNickname(),id,game.getActivePlayer().getFaithTrack().getPosition());
                    // SEND POSSIBLE ACTIONS
                    serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
                } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                    serverViews.get(currentServerView).sendError("INVALID DISCARD LEADER CARD ACTION");
                    serverViews.get(currentServerView).sendActionResponse(Actions.DISCARDLEADERCARD);
                    e.printStackTrace();
                }
            } else {
                serverViews.get(currentServerView).sendError("INVALID CARD ID");
                serverViews.get(currentServerView).sendActionResponse(Actions.DISCARDLEADERCARD);
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            serverViews.get(currentServerView).sendActionResponse(Actions.DISCARDLEADERCARD);
        }
    }


    /**
     * attempt to play a leaderCard
     */
    public void playLeaderCard(String id, HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes){
        if (currentAction.get(currentActivePlayer)==Actions.PLAYLEADERCARD) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(0), warehouseDepotRes, strongboxRes, cardDepotRes);
                try {
                    //DO ACTION
                    game.doAction(playLeaderCard);
                    //RESET CURRENT ACTION TO NULL
                    currentAction.put(currentActivePlayer, null);
                    // SEND UPDATE
                    serverViews.get(currentServerView).sendPlayLeaderCardUpdate(game.getActivePlayer().getNickname(),id,getWarehouse().get(game.getActivePlayer().getNickname()),getStrongbox().get(game.getActivePlayer().getNickname()));
                    // SEND POSSIBLE ACTIONS
                    serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
                } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                    serverViews.get(currentServerView).sendError("INVALID PLAY LEADER CARD ACTION");
                    serverViews.get(currentServerView).sendActionResponse(Actions.PLAYLEADERCARD);
                }
            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(1), warehouseDepotRes, strongboxRes, cardDepotRes);
                try {
                    //DO ACTION
                    game.doAction(playLeaderCard);
                    //RESET CURRENT ACTION TO DO TO NULL
                    currentAction.put(currentActivePlayer, null);
                    // SEND UPDATE
                    serverViews.get(currentServerView).sendPlayLeaderCardUpdate(game.getActivePlayer().getNickname(),id,getWarehouse().get(game.getActivePlayer().getNickname()),getStrongbox().get(game.getActivePlayer().getNickname()));
                    // SEND POSSIBLE ACTIONS
                    serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
                } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                    serverViews.get(currentServerView).sendError("INVALID PLAY LEADER CARD ACTION");
                    serverViews.get(currentServerView).sendActionResponse(Actions.PLAYLEADERCARD);
                }
            } else {
                serverViews.get(currentServerView).sendError("INVALID CARD ID");
                serverViews.get(currentServerView).sendActionResponse(Actions.PLAYLEADERCARD);
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            serverViews.get(currentServerView).sendActionResponse(Actions.PLAYLEADERCARD);
        }
    }


    /**
     * pick the marbles from the market and turn them into resources
     */
    public void marketAction(int index, boolean isRow){
        if (currentAction.get(currentActivePlayer)==Actions.MARKETACTION) {
            ArrayList<Marbles> marbles = new ArrayList<>();
            ArrayList<Resource> resources = new ArrayList<>();
            if (isRow) {
                PickRow pickRow = new PickRow(index, marbles, game.getBoard().getMarketBoard());
                try {
                    //DO ACTION
                    game.doAction(pickRow);
                    //TAKE THE ARRAY OF MARBLES COLORS
                    for (Marbles marble : marbles) {
                        marble.drawEffect(resources, game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getExchangeBuff());
                    }
                    Map<Resource, Integer> temp1;
                    temp1 = temp;
                    for (Resource resource : resources) {
                        temp1.put(resource, temp1.get(resource) + 1);
                    }
                    newResources.put(game.getActivePlayer().getNickname(), temp1);
                    // SEND THE MANAGE RESOURCES
                    serverViews.get(currentServerView).sendResourceManageRequest(resources);
                    //SAVE PARAMETERS FOR MANAGE RESOURCES
                    resourceArrayList = resources;
                    marbleColorsArrayList = getRowOrColumn(index, true);
                    marketIndex= index;
                    isRowOrColumn= true;
                } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                    serverViews.get(currentServerView).sendError("INVALID MARKET ACTION");
                    serverViews.get(currentServerView).sendActionResponse(Actions.MARKETACTION);
                }
            } else {
                PickColumn pickColumn = new PickColumn(index, marbles, game.getBoard().getMarketBoard());
                try {
                    //DO ACTION
                    game.doAction(pickColumn);
                    //TAKE THE ARRAY OF MARBLES COLORS
                    for (Marbles marble : marbles) {
                        marble.drawEffect(resources, game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getExchangeBuff());
                    }
                    Map<Resource, Integer> temp1;
                    temp1 = temp;
                    for (Resource resource : resources) {
                        temp1.put(resource, temp1.get(resource) + 1);
                    }
                    newResources.put(game.getActivePlayer().getNickname(), temp1);
                    // SEND THE MANAGE RESOURCES
                    serverViews.get(currentServerView).sendResourceManageRequest(resources);
                    //SAVE PARAMETERS FOR MANAGE RESOURCES
                    resourceArrayList = resources;
                    marbleColorsArrayList = getRowOrColumn(index, false);
                    marketIndex= index;
                    isRowOrColumn= false;
                } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                    serverViews.get(currentServerView).sendError("INVALID MARKET ACTION");
                    serverViews.get(currentServerView).sendActionResponse(Actions.MARKETACTION);
                }
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            serverViews.get(currentServerView).sendActionResponse(Actions.MARKETACTION);
        }
    }

    /**
     * assign the new Resources to the player
     */
    public void manageResources(Map<Integer,ArrayList<Resource>> resources, Map<Resource,Integer> discRes){
        if (currentAction.get(currentActivePlayer)==Actions.MARKETACTION) {
            ManageResources manageResources = new ManageResources(resources, newResources.get(game.getActivePlayer().getNickname()), discRes, false);
            try {
                //DO ACTION
                game.doAction(manageResources);
                //RESET CURRENT ACTION TO NULL
                currentAction.put(currentActivePlayer,null);
                resourceArrayList= null;
                //UPDATE
                serverViews.get(currentServerView).sendMarketActionUpdate(game.getActivePlayer().getNickname(),game.getActivePlayer().getFaithTrack().getPosition(),getWarehouse().get(game.getActivePlayer().getNickname()),getStrongbox().get(game.getActivePlayer().getNickname()),marbleColorsArrayList,getFreeMarble(),marketIndex,isRowOrColumn);
                marbleColorsArrayList=null;
                //SEND POSSIBLE ACTIONS
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());

                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                serverViews.get(currentServerView).sendError("INVALID MANAGE RESOURCES ACTION");
                serverViews.get(currentServerView).sendResourceManageRequest(resourceArrayList);
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            serverViews.get(currentServerView).sendResourceManageRequest(resourceArrayList);
        }

    }



    /**
     * attempt to buy a developmentCard
     */
    public void buyDevelopmentCard(HashMap<Resource,Integer> depotResources, HashMap<Resource,Integer> strongboxResources, HashMap<Resource,Integer>cardDepotResources, Color color,int level,int slotNumber){
        if (currentAction.get(currentActivePlayer)==Actions.BUYDEVELOPMENTCARD) {
            BuyDevelopmentCard buyDevelopmentCard = new BuyDevelopmentCard(game.getBoard(), depotResources, strongboxResources, cardDepotResources, color, level, slotNumber);
            try {
                //DO ACTION
                game.doAction(buyDevelopmentCard);
                //RESET CURRENT ACTION TO NULL
                currentAction.put(currentActivePlayer,null);
                //UPDATE
                //serverViews.get(currentServerView).sendBuyDevelopmentCardUpdate(game.getActivePlayer().getNickname(),);
                // SEND POSSIBLE ACTIONS
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                serverViews.get(currentServerView).sendError("INVALID BUY DEVELOPMENT CARD ACTION");
                serverViews.get(currentServerView).sendActionResponse(Actions.BUYDEVELOPMENTCARD);
            }
        }
        else {
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            serverViews.get(currentServerView).sendActionResponse(Actions.BUYDEVELOPMENTCARD);
        }
    }


    /**
     * attempt to use the production
     */
    public void useProduction(HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes,
                              ArrayList<Resource> boardGain, ArrayList<Resource> leaderGain, ArrayList<Integer> devSlotIndex, ArrayList<Integer> leaderCardProdIndex  ){
        if (currentAction.get(currentActivePlayer)==Actions.USEPRODUCTION) {
            UseProduction useProduction = new UseProduction(devSlotIndex, leaderCardProdIndex, leaderGain, boardGain, warehouseDepotRes, cardDepotRes, strongboxRes);
            try {
                //DO ACTION
                game.doAction(useProduction);
                //RESET CURRENT ACTION TO NULL
                currentAction.put(currentActivePlayer,null);
                //UPDATE

                //SEND POSSIBLE ACTION
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                serverViews.get(currentServerView).sendError("INVALID USE PRODUCTION ACTION");
                serverViews.get(currentServerView).sendActionResponse(Actions.USEPRODUCTION);
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            serverViews.get(currentServerView).sendActionResponse(Actions.USEPRODUCTION);
        }
    }


    public void sendVaticanReport(){

    }


    //------------------TOOLS------------------

    public void PlayerReconnection(String username){
        int j=0;
        for (int i=0;i<serverViews.size(); i++){
            if (nicknames.get(i).equals(username)) j=i;
            else{
                //error
            }
        }
        serverViews.get(j).sendDevCardGrid(getDevCardGrid());
        serverViews.get(j).sendMarket(getMarket(),getFreeMarble());
        serverViews.get(j).sendReconnectionMessage(getDevCardSlots(),getFaithPositions(),getLeaderCardsPlayed(),
                getLeaderCards(username), getStrongbox(), getWarehouse());

    }


    private Map<String,ArrayList<String>> getDevCardSlots(){
        Map<String,ArrayList<String>> devCardSlots = new HashMap<>();
        for (int i=0;i<serverViews.size();i++){
            game.setActivePlayer(game.getPlayers(i));
            ArrayList<String> playerCards = new ArrayList<>();
            for (int j=0;j<game.getActivePlayer().getPlayerBoard().getSlots().size();j++){
                playerCards.add(j,game.getActivePlayer().getPlayerBoard().getSlots().get(j).lookTop().getId());
            }
            devCardSlots.put(game.getActivePlayer().getNickname(),playerCards);
        }
        return devCardSlots;
    }
    private Map<String,Integer> getFaithPositions(){
        Map<String,Integer> faithPositions = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            game.setActivePlayer(game.getPlayers(i));
            faithPositions.put(game.getActivePlayer().getNickname(),game.getActivePlayer().getFaithTrack().getPosition());
        }
        return faithPositions;
    }
    private Map<String, ArrayList<String>> getLeaderCardsPlayed(){
        Map<String, ArrayList<String>> leaderCards = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            game.setActivePlayer(game.getPlayers(i));
            ArrayList<String> id = new ArrayList<>();
            for (int j=0;j<game.getActivePlayer().getPlayerBoard().getLeaderCards().size();j++){
                id.add(j,game.getActivePlayer().getPlayerBoard().getLeaderCards().get(j).getId());
            }
            leaderCards.put(game.getActivePlayer().getNickname(),id);
        }
        return leaderCards;
    }
    private ArrayList<String> getLeaderCards(String username){
        ArrayList<String> leaderCards = new ArrayList<>();
        for (int i=0;i<serverViews.size();i++){
            if (game.getPlayers(i).getNickname().equals(username)){
                game.setActivePlayer(game.getPlayers(i));
                for (int j=0;j<game.getActivePlayer().getCardsHand().size();j++){
                    leaderCards.add(j,game.getActivePlayer().getCardsHand().get(j).getId());
                }
            }
        }
        return leaderCards;
    }
    private Map<String, Map<Resource,Integer>> getStrongbox(){
        Map<String, Map<Resource,Integer>> strongbox = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            game.setActivePlayer(game.getPlayers(i));
            Map<Resource,Integer> map = game.getActivePlayer().getPlayerBoard().getStrongbox().getResources();
            strongbox.put(game.getActivePlayer().getNickname(), map);
        }
        return strongbox;
    }
    private Map<String, Map<Integer, ArrayList<Resource>>> getWarehouse(){
        Map<String, Map<Integer, ArrayList<Resource>>> warehouse = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            game.setActivePlayer(game.getPlayers(i));
            Map<Integer, ArrayList<Resource>> depots = new HashMap<>();
            for (int j=0;j<game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().size();j++){
                ArrayList<Resource> resources = new ArrayList<>();
                for (int k=0;k<game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(j).getOccupied();k++) {
                   resources.add(k,game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(j).getResource());
                }
                depots.put(j,resources);
            }
            warehouse.put(game.getActivePlayer().getNickname(),depots);
        }
        return warehouse;
    }
    private Map<String,Integer> getFaith(){
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < serverViews.size(); i++) {
            game.setActivePlayer(game.getPlayers(i));
            map.put(game.getActivePlayer().getNickname(),game.getActivePlayer().getFaithTrack().getPosition());

        }
        return map;
    }
    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    public MarbleColors[][] getMarket(){
        MarbleColors[][] marbleColors = new MarbleColors[game.getBoard().getMarketBoard().getRows()][game.getBoard().getMarketBoard().getColumns()];
        Marbles[][] marbles = game.getBoard().getMarketBoard().getMarbleGrid();
        for (int i=0; i<game.getBoard().getMarketBoard().getRows();i++){
            for (int j=0; j<game.getBoard().getMarketBoard().getColumns();j++){
                marbleColors[i][j]= marbles[i][j].getColor();
            }

        }
        return marbleColors;
    }
    public MarbleColors getFreeMarble(){
        return game.getBoard().getMarketBoard().getFreeMarble().getColor();
    }

    public String[][] getDevCardGrid(){
        String[][] devCardGrid= new String[game.getBoard().getCardRows()][game.getBoard().getCardColumns()];
        for (int i=0;i<game.getBoard().getCardRows();i++){
            for (int j=0; j<game.getBoard().getCardColumns();j++){
                try {
                    devCardGrid[i][j] = game.getBoard().getCardGrid()[i][j].lookFirst().getId();
                } catch (NoCardsLeftException e) {
                    devCardGrid[i][j] = "null";
                }

            }
        }
        return devCardGrid;
    }
    public ServerView getServerView(String username){
        for (ServerView serverView: serverViews){
            if (serverView.getUsername().equals(username)) return serverView;
        }
        return null;
    }

    public ArrayList<String> getPlayers(){
        ArrayList<String> players = new ArrayList<>();
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            players.add(0,game.getPlayers(i).getNickname());
        }
        return players;
    }
    public ArrayList<MarbleColors> getRowOrColumn(int index, boolean isRow){
        ArrayList<Marbles> marbles= new ArrayList<>();
        ArrayList<MarbleColors> resources = new ArrayList<>();
        if (isRow)
        marbles= game.getBoard().getMarketBoard().getOneRow(index);
        for (int i = 0; i < marbles.size(); i++) {
            resources.add(i,marbles.get(i).getColor());
        }
        return resources;
    }



 }

