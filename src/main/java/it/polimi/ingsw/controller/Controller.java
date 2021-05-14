package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.MarbleColors;
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
import it.polimi.ingsw.server.ServerView;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    private final Map<Integer,Boolean> numOfActions;
    private final Map<Integer,Actions> currentAction;
    private ArrayList<Resource> resourceArrayList;
    private ArrayList<MarbleColors> marbleColorsArrayList;
    private int marketIndex;
    private boolean isRowOrColumn;
    private int numOfVaticanReport;
    private Map<String,Integer> faithPositions;


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
        numOfActions= new HashMap<Integer,Boolean>(){{
            for (int i = 0; i < playersNumber; i++) {
                put(i,false);
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

        //PARAMETERS FOR FAITHTRACK
        numOfVaticanReport = game.getNumVaticanReports();
        faithPositions = new HashMap<String,Integer>(){{
            for (int i = 0; i < playersNumber; i++) {
              put(game.getPlayers(i).getNickname(),0);
            }
        }};

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
                Objects.requireNonNull(getServerView(username)).startingResourceMessage(0);
                break;

            }
            case 1: {
                Objects.requireNonNull(getServerView(username)).startingResourceMessage(1);
                break;
            }
            case 2: {
                Objects.requireNonNull(getServerView(username)).startingResourceMessage(1);
                game.getActivePlayer().getFaithTrack().increaseVictoryPoints(1);
                faithPositions.put(game.getActivePlayer().getNickname(),1);
                break;
            }
            case 3: {
                Objects.requireNonNull(getServerView(username)).startingResourceMessage(2);
                game.getActivePlayer().getFaithTrack().increaseVictoryPoints(1);
                faithPositions.put(game.getActivePlayer().getNickname(),1);
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
        int size=0;
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
                    sendStartingResource(username);
                    Objects.requireNonNull(getServerView(username)).sendError("INVALID STARTING RESOURCES");
                }
                else{
                    manageStartingResource(resources,username);
                }
                break;
            }
            case 1:
            case 2: {
                if (((resources.get(0).size())+(resources.get(1).size())+(resources.get(2).size()))!=1) {
                    sendStartingResource(username);
                    Objects.requireNonNull(getServerView(username)).sendError("INVALID STARTING RESOURCES");
                }
                else{
                    manageStartingResource(resources,username);
                }
                break;
            }
            case 3:{
                if ((resources.get(0).size())+(resources.get(1).size())+(resources.get(2).size())!=2) {
                    sendStartingResource(username);
                    Objects.requireNonNull(getServerView(username)).sendError("INVALID STARTING RESOURCES");
                }
                else{
                    manageStartingResource(resources,username);
                }
                break;
            }
            default: {
                Objects.requireNonNull(getServerView(username)).sendError("INVALID STARTING RESOURCES");
                sendStartingResource(username);
                break;
            }

        }





    }


    private synchronized void manageStartingResource(Map<Integer, ArrayList<Resource>> resources,String username){
        ManageResources manageResources = new ManageResources(resources, null,null,true, null);
        try {
            game.doAction(manageResources);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            // MANAGE ERROR
            System.out.println( username + " invalid manage resources ");
            sendStartingResource(username);
        }
        // LAYOUT UPDATE
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
        //IF NO ACTION HAS BEEN TAKEN YET
        if ((!numOfActions.get(currentActivePlayer))){
            //IF THE PLAYER HAS ACTIVABLE LEADERCARDS
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
        //IF THE PLAYER HAS MADE A NORMAL ACTION
        else {
            //IF THE PLAYER HAS ACTIVABLE LEADERCARDS
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
                numOfActions.put(currentActivePlayer,true);
                break;
            }
            case DISCARDLEADERCARD:
            case PLAYLEADERCARD:{
                break;
            }
        }
    }


    public void startTurn(){
        serverViews.get(currentServerView).startTurn();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("wait failed");
        }
        serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
    }


    public void endTurn(){
        numOfActions.put(currentActivePlayer,false);
        newResources.put(serverViews.get(currentServerView).getUsername(),temp);
        currentActivePlayer++;
        if (currentActivePlayer>=playersNumber) currentActivePlayer=0;
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        for (int i = 0; i < playersNumber; i++) {
            if (serverViews.get(i).getUsername().equals(game.getActivePlayer().getNickname())){
                currentServerView=i;
            }
        }
        if(playersNumber==1){
            lorenzoAction();
        }
        else {
            startTurn();
        }
    }

    public void lorenzoAction(){
        game.getLorenzo().drawToken();
        startTurn();
    }


    /**
     * attempt to discard a leaderCard
     */
    public void discardLeaderCard(String id ){
        if (currentAction.get(currentActivePlayer)==Actions.DISCARDLEADERCARD) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(0), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                doActionDiscardLeader(id,discardLeaderCard);

            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(1), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                doActionDiscardLeader(id,discardLeaderCard);

            } else {
                serverViews.get(currentServerView).sendError("INVALID CARD ID");
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
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
                TimeUnit.SECONDS.sleep(1);
            }
            // SEND POSSIBLE ACTIONS
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException | InterruptedException e) {
            serverViews.get(currentServerView).sendError("INVALID DISCARD LEADER CARD ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }

    }


    /**
     * attempt to play a leaderCard
     */
    public void playLeaderCard(String id, HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes){
        if (currentAction.get(currentActivePlayer)==Actions.PLAYLEADERCARD) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(0), warehouseDepotRes, strongboxRes, cardDepotRes);
                doActionPlayLeader(id, playLeaderCard);
            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(1), warehouseDepotRes, strongboxRes, cardDepotRes);
                doActionPlayLeader(id, playLeaderCard);
            } else {
                serverViews.get(currentServerView).sendError("INVALID CARD ID");
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
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
            Map<Resource,Integer> strongbox = getStrongbox().get(username);
            for (ServerView serverView: serverViews) {
                serverView.sendPlayLeaderCardUpdate(username, id, warehouse , strongbox);
            }
            TimeUnit.SECONDS.sleep(1);
            // SEND POSSIBLE ACTIONS
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException | InterruptedException e) {
            serverViews.get(currentServerView).sendError("INVALID PLAY LEADER CARD ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
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
                doActionMarketAction(pickRow, resources,index, true);
            } else {
                PickColumn pickColumn = new PickColumn(index, marbles, game.getBoard().getMarketBoard());
                doActionMarketAction(pickColumn, resources,index, false);
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }


    private void doActionMarketAction(MarketAction marketAction, ArrayList<Resource> resources, int index, boolean isRow){
        try {
            //DO ACTION
            ArrayList<Marbles> marbles = marketAction.getMarbles();
            game.doAction(marketAction);
            //TAKE THE ARRAY OF MARBLES COLORS
            for (Marbles marble : marbles) {
                marble.drawEffect(resources, game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getExchangeBuff());
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
            serverViews.get(currentServerView).sendError("INVALID MARKET ACTION");
            currentAction.put(currentActivePlayer, null);
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
                TimeUnit.SECONDS.sleep(1);
                //SEND POSSIBLE ACTIONS
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException | InterruptedException e) {
                serverViews.get(currentServerView).sendError("INVALID MANAGE RESOURCES ACTION");
                serverViews.get(currentServerView).sendResourceManageRequest(resourceArrayList);
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }

    }



    /**
     * attempt to buy a developmentCard
     */
    public void buyDevelopmentCard(HashMap<Resource,Integer> depotResources, HashMap<Resource,Integer> strongboxResources, HashMap<Resource,Integer>cardDepotResources, Color color,int level,int slotNumber){
        if (currentAction.get(currentActivePlayer)==Actions.BUYDEVELOPMENTCARD) {
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
                //UPDATE FOR DA FARE
                String username = game.getActivePlayer().getNickname();
                String newId;
                try {
                    newId = game.getBoard().viewCard(color, level).getId();
                } catch (NoCardsLeftException | WrongLevelException e) {
                    newId = null;
                }
                Map<Integer,ArrayList<Resource>> warehouse = getWarehouse().get(username);
                Map<Resource,Integer> strongbox = getStrongbox().get(username);
                for (ServerView serverView: serverViews){
                    serverView.sendBuyDevelopmentCardUpdate(username,id,slotNumber,newId,color,level,warehouse,strongbox);
                }
                //serverViews.get(currentServerView).sendBuyDevelopmentCardUpdate(,);
                TimeUnit.SECONDS.sleep(1);
                // SEND POSSIBLE ACTIONS
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException | InterruptedException e) {
                serverViews.get(currentServerView).sendError("INVALID BUY DEVELOPMENT CARD ACTION");
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else {
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
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
                faithTrackMessage();
                currentAction.put(currentActivePlayer,null);
                //UPDATE
                String username  = game.getActivePlayer().getNickname();
                Map<Integer,ArrayList<Resource>> warehouse = getWarehouse().get(username);
                Map<Resource,Integer> strongbox = getStrongbox().get(username);

                for (ServerView serverView: serverViews){
                    serverView.sendUseProductionUpdate(username,warehouse,strongbox);
                }
                TimeUnit.SECONDS.sleep(1);
                //SEND POSSIBLE ACTION
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException | InterruptedException e) {
                serverViews.get(currentServerView).sendError("INVALID USE PRODUCTION ACTION");
                currentAction.put(currentActivePlayer, null);
                serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
            }
        }
        else{
            serverViews.get(currentServerView).sendError("INVALID ACTION");
            currentAction.put(currentActivePlayer, null);
            serverViews.get(currentServerView).sendPossibleActions(getPossibleAction());
        }
    }

    private void faithTrackMessage() {
        boolean isActive = game.getNumVaticanReports() != numOfVaticanReport;
        Map<String,Integer> update = new HashMap<>();
        if (isActive){
            numOfVaticanReport = game.getNumVaticanReports();
            for (int i = 0; i < playersNumber; i++) {
                Player player = game.getPlayers(i);
                game.setActivePlayer(player);
                int position = game.getActivePlayer().getFaithTrack().getPosition();
                String username = game.getActivePlayer().getNickname();
                if (position!=faithPositions.get(username)){
                    update.put(username,position);
                    faithPositions.put(username,position);
                }
            }

        }
        for (ServerView serverView: serverViews){
            serverView.sendFaithMessage(update,faithPositions,isActive);
        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);

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
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            ArrayList<String> playerCards = new ArrayList<>();
            for (int j=0;j<game.getActivePlayer().getPlayerBoard().getSlots().size();j++){
                if (game.getActivePlayer().getPlayerBoard().getSlots().get(j).lookTop()==null){
                    playerCards.add(j,"null");
                }
                else{
                    playerCards.add(j,game.getActivePlayer().getPlayerBoard().getSlots().get(j).lookTop().getId());
                }
            }
            devCardSlots.put(game.getActivePlayer().getNickname(),playerCards);
        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return devCardSlots;
    }
    private Map<String,Integer> getFaithPositions(){
        Map<String,Integer> faithPositions = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            faithPositions.put(game.getActivePlayer().getNickname(),game.getActivePlayer().getFaithTrack().getPosition());
        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return faithPositions;
    }
    private Map<String, ArrayList<String>> getLeaderCardsPlayed(){
        Map<String, ArrayList<String>> leaderCards = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            ArrayList<String> id = new ArrayList<>();
            for (int j=0;j<game.getActivePlayer().getPlayerBoard().getLeaderCards().size();j++){
                id.add(j,game.getActivePlayer().getPlayerBoard().getLeaderCards().get(j).getId());
            }
            leaderCards.put(game.getActivePlayer().getNickname(),id);
        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return leaderCards;
    }
    private ArrayList<String> getLeaderCards(String username){
        ArrayList<String> leaderCards = new ArrayList<>();
        for (int i=0;i<serverViews.size();i++){
            if (game.getPlayers(i).getNickname().equals(username)){
                Player player = game.getPlayers(i);
                game.setActivePlayer(player);
                for (int j=0;j<game.getActivePlayer().getCardsHand().size();j++){
                    leaderCards.add(j,game.getActivePlayer().getCardsHand().get(j).getId());
                }
            }
        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return leaderCards;
    }
    private Map<String, Map<Resource,Integer>> getStrongbox(){
        Map<String, Map<Resource,Integer>> strongbox = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            Map<Resource,Integer> map = game.getActivePlayer().getPlayerBoard().getStrongbox().getResources();
            strongbox.put(game.getActivePlayer().getNickname(), map);
        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return strongbox;
    }
    private Map<String, Map<Integer, ArrayList<Resource>>> getWarehouse(){
        Map<String, Map<Integer, ArrayList<Resource>>> warehouse = new HashMap<>();
        for (int i=0;i<serverViews.size();i++) {
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
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
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return warehouse;
    }
    private Map<String,Integer> getFaith(){
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < serverViews.size(); i++) {
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            map.put(game.getActivePlayer().getNickname(),game.getActivePlayer().getFaithTrack().getPosition());

        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return map;
    }
    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    private MarbleColors[][] getMarket(){
        MarbleColors[][] marbleColors = new MarbleColors[game.getBoard().getMarketBoard().getRows()][game.getBoard().getMarketBoard().getColumns()];
        Marbles[][] marbles = game.getBoard().getMarketBoard().getMarbleGrid();
        for (int i=0; i<game.getBoard().getMarketBoard().getRows();i++){
            for (int j=0; j<game.getBoard().getMarketBoard().getColumns();j++){
                marbleColors[i][j]= marbles[i][j].getColor();
            }

        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return marbleColors;
    }
    private MarbleColors getFreeMarble(){
        return game.getBoard().getMarketBoard().getFreeMarble().getColor();
    }

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
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return devCardGrid;
    }
    private ServerView getServerView(String username){
        for (ServerView serverView: serverViews){
            if (serverView.getUsername().equals(username)) return serverView;
        }
        return null;
    }

    private ArrayList<String> getPlayers(){
        ArrayList<String> players = new ArrayList<>();
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            players.add(0,game.getPlayers(i).getNickname());
        }
        return players;
    }
    private ArrayList<MarbleColors> getRowOrColumn(int index, boolean isRow){
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

