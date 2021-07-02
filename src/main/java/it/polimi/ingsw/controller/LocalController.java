package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.LocalClientView;
import it.polimi.ingsw.client.representations.MarbleColors;
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

public class LocalController {

    private final LocalClientView clientView;

    private final Game game;

    private final Map<String,ArrayList<LeaderCard>> startingLeaderCards;
    private final Map<String,ArrayList<LeaderCard>> selectedCards;
    private final Map<String,ArrayList<String>> leaderID;
    private final Map<String,Map<Resource,Integer>> newResources;
    private final Map<Resource,Integer> temp;
    private final int playersNumber;
    private int currentActivePlayer;
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
    private final Map<String,Map<Integer,Boolean>> playerStatus;
    private boolean gameFinished;
    private Map<String, Map<Integer,Boolean>> vaticanReportActivated;

    public LocalController(Game game, ArrayList<String> user, LocalClientView clientView) {
        this.clientView = clientView;
        this.temp = new HashMap<Resource,Integer>(){{
            put(Resource.COINS,0);
            put(Resource.SERVANTS,0);
            put(Resource.SHIELDS,0);
            put(Resource.STONES,0);

        }};
        this.game = game;

        this.newResources = new HashMap<>();
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
        tempo.put(8,false);
        tempo.put(16,false);
        tempo.put(24,false);


        //PARAMETERS FOR STARTING RESOURCES
        this.startingResources = new HashMap<>();
        //PARAMETERS FOR DISCONNECTION
        Map<Integer,Boolean> temp2 = new HashMap<>();
        temp2.put(0,false);
        temp2.put(1,false);
        playerStatus = new HashMap<>();
        System.out.println(playerStatus);
        this.currentActivePlayer=0;
        gameFinished = false;

        for (int i = 0; i < playersNumber; i++) {
            String name = game.getPlayers(i).getNickname();
            System.out.println("player number " + (i+1) + ":" + name);
            playerStatus.put(name,temp2);
            startingResources.put(name,0);
            vaticanReportActivated.put(name,tempo);
            faithPositions.put(name,0);
            currentAction.put(i,null);
            numOfActions.put(i,false);
        }
    }

    /**
     * 1) START: send market and devCardGrid
     */
    public void startGame(){
        clientView.marketSetup(getMarket(),getFreeMarble());
        clientView.developmentCardGridSetup(getDevCardGrid());
        setupGame();
    }


    /**
     * 2) SETUP: send to each player leadercards
     */
    public void setupGame(){
        for (int i=0;i< game.getPlayersNumber();i++){
            Player player = game.getPlayers(0);
            game.setActivePlayer(player);
            ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
            ArrayList<String> leaderId = new ArrayList<>();
            startingLeaderCards.put(game.getActivePlayer().getNickname(),leaderCards);
            for (LeaderCard l: leaderCards){
                leaderId.add(l.getId());
            }
            clientView.addPlayersToGameboard(getPlayers());
            clientView.selectStartingCards(leaderId);
        }
    }

    /**
     * assign the starting cards to the player
     */
    public void pickStartingLeaderCards(ArrayList<String> leaderId, String username){
        ArrayList<LeaderCard> trueCards = new ArrayList<>();
        int counter=0;
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
            Map<Integer, Boolean> map = new HashMap<>();
            map.put(0,true);
            map.put(1,true);
            playerStatus.put(username, map);
            System.out.println(playerStatus);
            ArrayList<String> leaderID= new ArrayList<>();
            for (int i=0; i<selectedCards.get(game.getActivePlayer().getNickname()).size();i++) {
                leaderID.add(i, selectedCards.get(game.getActivePlayer().getNickname()).get(i).getId());
                this.leaderID.put(game.getActivePlayer().getNickname(), leaderID);
            }
            begin();
        }    catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            clientView.errorManagement(Error.STARTING_LEADER_CARD);
        }
    }


    /**
     * starts the first player's turn and sends him the possible actions to perform
     */
    public void begin(){
            clientView.setupGameUpdate(this.leaderID, getWarehouse(), getFaith());
            Player player = game.getPlayers(currentActivePlayer);
            game.setActivePlayer(player);
            clientView.pickAction(getPossibleAction());
    }


    //------------------ACTIONS------------------


    /**
     * check if the player has already performed any actions
     * @return the player's list of possible actions
     */
    public ArrayList<Actions> getPossibleAction() {
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
        clientView.doAction(action);
    }

    /**
     * ends the player's turn and sets the new active player in the model
     */
    public void endTurn(){
        if ((gameFinished && (currentActivePlayer == playersNumber -1)) || (gameFinished && (playersNumber==1))) {
            boolean lorenzo = game.getLorenzo().checkEndGame();
            finalScore(lorenzo);
        }
        else {
            numOfActions.put(currentActivePlayer, false);
            newResources.put(game.getPlayers(currentActivePlayer).getNickname(), temp);
            increaseActivePlayer(1);
            Player player = game.getPlayers(currentActivePlayer);
            game.setActivePlayer(player);
            lorenzoAction();
        }
    }

    /**
     * changes the active player, sends a notification to the other players and sends to the active player the possible actions to perform
     */
    public void startTurn(){
        clientView.pickAction(getPossibleAction());
    }


    /**
     * this method makes lorenzo draw the token in the model and invokes the serverview method to communicate it to the client
     */
    public void lorenzoAction(){
        String message = game.getLorenzo().drawToken();
        String[][] newGrid = getDevCardGrid();
        faithTrackMessage();
        LorenzoAI lorenzo = game.getLorenzo();
        clientView.lorenzoUpdate(message, lorenzo.getTrack().getPosition(), newGrid);
        boolean lori = game.getLorenzo().checkEndGame();
        if (lori) {
            gameFinished = true;
            finalScore(true);
            clientView.endGame();
        }
        else startTurn();
    }


    /**
     * attempt to discard a leaderCard
     */
    public void discardLeaderCard(String id,String playerId){
        String username = game.getActivePlayer().getNickname();
        if ((currentAction.get(currentActivePlayer)==Actions.DISCARDLEADERCARD)&&(playerId.equals(username))) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(0), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                doActionDiscardLeader(id,discardLeaderCard);

            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(1), game.getActivePlayer().getCardsHand(), game.getActivePlayer().getFaithTrack());
                doActionDiscardLeader(id,discardLeaderCard);

            } else {
                clientView.errorManagement(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                clientView.pickAction(getPossibleAction());
            }
        }
        else{
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
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
            clientView.discardLeaderCardUpdate(username, id);
            // SEND POSSIBLE ACTIONS
            if (endGame()) {
                    gameFinished = true;
                    clientView.endGame();
            }
            clientView.pickAction(getPossibleAction());
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
        }

    }


    /**
     * attempt to play a leaderCard
     */
    public void playLeaderCard(String id,String playerId){
        String username = game.getActivePlayer().getNickname();
        if ((currentAction.get(currentActivePlayer)==Actions.PLAYLEADERCARD)&&(playerId.equals(username))) {
            if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(0));
                doActionPlayLeader(id, playLeaderCard);
            } else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
                PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(), game.getActivePlayer().getCardsHand().get(1));
                doActionPlayLeader(id, playLeaderCard);
            } else {
                clientView.errorManagement(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                clientView.pickAction(getPossibleAction());
            }
        }
        else{
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
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
            clientView.playLeaderCardUpdate(username, id, warehouse);
            // SEND POSSIBLE ACTIONS
            clientView.pickAction(getPossibleAction());
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
        }
    }


    /**
     * pick the marbles from the market and turn them into resources
     */
    public void marketAction(int index, boolean isRow, ArrayList<Resource>exchange,String playerId){
        String username = game.getActivePlayer().getNickname();
        if ((currentAction.get(currentActivePlayer)==Actions.MARKETACTION)&&(playerId.equals(username))) {
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
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
        }
    }


    private void doActionMarketAction(MarketAction marketAction, ArrayList<Resource> resources, int index, boolean isRow, ArrayList<Resource> exchange){
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
            //SAVE PARAMETERS FOR MANAGE RESOURCES
            resourceArrayList = resources;
            marbleColorsArrayList = getRowOrColumn(index, isRow);
            marketIndex= index;
            isRowOrColumn= isRow;
            // SEND THE MANAGE RESOURCES
            clientView.manageResources(resources);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            numOfActions.put(currentActivePlayer,false);
            clientView.pickAction(getPossibleAction());
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
                if (game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().size()==4) {
                    System.out.println(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(3).getResource());
                    System.out.println(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(3).getOccupied());
                }
                game.doAction(manageResources);
                if (game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().size()==4) {
                    System.out.println(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(3).getResource());
                    System.out.println(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(3).getOccupied());
                }
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
                clientView.marketActionUpdate(nickname, warehouse, marbleColorsArrayList, getFreeMarble(), marketIndex, isRowOrColumn);
                marbleColorsArrayList=null;
                //SEND POSSIBLE ACTIONS
                if (endGame()){
                    gameFinished = true;
                  clientView.endGame();
                }
                clientView.pickAction(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException  e) {
                clientView.errorManagement(Error.MANAGE_RESOURCES);
                clientView.manageResources(resourceArrayList);
            }
        }
        else{
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
        }
    }



    /**
     * attempt to buy a developmentCard
     */
    public void buyDevelopmentCard(HashMap<Resource,Integer> depotResources, HashMap<Resource,Integer> strongboxResources, HashMap<Resource,Integer>cardDepotResources, Color color, int level, int slotNumber, String playerId){
        String username = game.getActivePlayer().getNickname();
        if ((currentAction.get(currentActivePlayer)==Actions.BUYDEVELOPMENTCARD)&&(playerId.equals(username))) {
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
                clientView.buyCardUpdate(username,id,slotNumber,newId,color,level,warehouse,strongbox);
                // SEND POSSIBLE ACTIONS
                if (endGame()){
                        gameFinished = true;
                        clientView.endGame();
                }
                clientView.pickAction(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                clientView.errorManagement(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                numOfActions.put(currentActivePlayer,false);
                clientView.pickAction(getPossibleAction());
            }
        }
        else {
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
        }
    }


    /**
     * attempt to use the production
     */
    public void useProduction(HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes,
                              ArrayList<Resource> boardGain, ArrayList<Resource> leaderGain, ArrayList<Integer> devSlotIndex, ArrayList<Integer> leaderCardProdIndex, String playerId ){
        String name = game.getActivePlayer().getNickname();
        if ((currentAction.get(currentActivePlayer)==Actions.USEPRODUCTION)&&(playerId.equals(name))) {
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
                clientView.useProductionUpdate(name,warehouse,strongbox);
                //SEND POSSIBLE ACTION
                if (endGame()){
                    gameFinished = true;
                    clientView.endGame();
                }
                clientView.pickAction(getPossibleAction());
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                clientView.errorManagement(Error.INVALID_ACTION);
                currentAction.put(currentActivePlayer, null);
                numOfActions.put(currentActivePlayer,false);
                clientView.pickAction(getPossibleAction());
            }
        }
        else{
            clientView.errorManagement(Error.INVALID_ACTION);
            currentAction.put(currentActivePlayer, null);
            clientView.pickAction(getPossibleAction());
        }
    }

    /**
     * this method checks the faithtrack status and sends the update message to the players
     */
    private void faithTrackMessage() {
        boolean isActive = game.getNumVaticanReports() != numOfVaticanReport;
        Map<String,Integer> update = new HashMap<>();
        if (isActive){
            numOfVaticanReport = game.getNumVaticanReports();
            for (int i = 0; i < playersNumber; i++) {
                Player player = game.getPlayers(i);
                int position = player.getFaithTrack().getPosition();
                String username = player.getNickname();
                switch (numOfVaticanReport){
                    case 1: {
                        if (position>=5){
                            update.put(username,8);
                            Map<Integer,Boolean> temp = vaticanReportActivated.get(username);
                            temp.put(8,true);
                            vaticanReportActivated.put(username,temp);
                        }
                        break;
                    }
                    case 2: {
                        if (position>=12){
                            update.put(username,16);
                            Map<Integer,Boolean> temp = vaticanReportActivated.get(username);
                            temp.put(16,true);
                            vaticanReportActivated.put(username,temp);
                        }
                        break;
                    }
                    case 3:{
                        if (position>=19){
                            update.put(username,24);
                            Map<Integer,Boolean> temp = vaticanReportActivated.get(username);
                            temp.put(24,true);
                            vaticanReportActivated.put(username,temp);
                        }
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
        clientView.faithTrackUpdate(update,faith,isActive);

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
            clientView.finalScoresUpdate(sortedMapReverseOrder,lorenzo);
        }
    }

    //------------------TOOLS------------------

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

    }

    /**
     * @return a map with the strongboxes of all the players
     */
    private Map<String, Map<Resource,Integer>> getStrongbox(){
        Map<String, Map<Resource,Integer>> strongbox = new HashMap<>();
        for (int i=0;i<playersNumber;i++) {
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
        for (int i=0;i<playersNumber;i++) {
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

    /**
     * @return a map that contains the faith positions of all the players
     */
    private Map<String,Integer> getFaith(){
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < playersNumber; i++) {
            Player player = game.getPlayers(i);
            game.setActivePlayer(player);
            map.put(game.getActivePlayer().getNickname(),game.getActivePlayer().getFaithTrack().getPosition());

        }
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return map;
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
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
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
        Player player = game.getPlayers(currentActivePlayer);
        game.setActivePlayer(player);
        return devCardGrid;
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
