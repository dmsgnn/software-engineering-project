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
import java.awt.font.FontRenderContext;
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
    private int playerNumber;

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
        playerNumber=0;

    }



    public void startGame(){
        for (ServerView s: serverViews){
            newResources.put(s.getUsername(),temp);
            s.sendMarket(getMarket(),getFreeMarble());
            s.sendDevCardGrid(getDevCardGrid());
            setupGame();
        }
    }



    /**
     * select the action chosen by the player
     */
    public void selectAction(Actions action){
        switch (action){
            case MARKETACTION:{

            }
            case USEPRODUCTION:{

            }
            case PLAYLEADERCARD:{

            }
            case DISCARDLEADERCARD:{

            }
            case BUYDEVELOPMENTCARD:{

            }
        }
    }


    /**
     * assign the starting cards to the player
     */
    public void pickStartingLeaderCards(ArrayList<String> leaderId, String username){
        ArrayList<LeaderCard> trueCards = new ArrayList<>();
        int counter=0;
        int j=0;
        for (int i=0;i<serverViews.size();i++) {
            if (serverViews.get(i).getUsername().equals(username)) {
                game.setActivePlayer(game.getPlayers(i));
                j=i;
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

        if (serverViews.get(j).getUsername().equals(game.getActivePlayer().getNickname())) {
            switch (j) {
                case 0: {
                    serverViews.get(j).sendStartingResource(0);
                }
                case 1: {
                    serverViews.get(j).sendStartingResource(1);
                }
                case 2: {
                    serverViews.get(j).sendStartingResource(1);
                    game.getActivePlayer().getFaithTrack().increaseVictoryPoints(1);
                }
                case 3: {
                    serverViews.get(j).sendStartingResource(2);
                    game.getActivePlayer().getFaithTrack().increaseVictoryPoints(1);
                }
            }
        }
    }


    /**
     * assign the starting resources to the player
     */
    public void pickStartingResources(Map<Integer, ArrayList<Resource>> resources, String username) {
        for (int i=0;i<serverViews.size();i++) {
            if (serverViews.get(i).getUsername().equals(username)) {
                game.setActivePlayer(game.getPlayers(i));
            }

        }
        //controllo sul numero esatto di risorse
        ManageResources manageResources = new ManageResources(resources, null,null,true);
        try {
            game.doAction(manageResources);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            // mando errore nel fare l'azione
            e.printStackTrace();
        }
        //layout update
        ArrayList<String> leaderID= new ArrayList<>();
        for (int i=0; i<selectedCards.get(game.getActivePlayer().getNickname()).size();i++){
            leaderID.add(i,selectedCards.get(game.getActivePlayer().getNickname()).get(i).getId());
            this.leaderID.put(game.getActivePlayer().getNickname(),leaderID);
        }
        for (ServerView serverView : serverViews) {
            serverView.sendSetupGameUpdate(this.leaderID, getWarehouse(), getFaith());
        }

    }


    /**
     * assign the new Resources to the player
     */
    public void manageResources(Map<Integer,ArrayList<Resource>> resources, Map<Resource,Integer> discRes){
        ManageResources manageResources = new ManageResources(resources,newResources.get(game.getActivePlayer().getNickname()),discRes,false);
        try {
            game.doAction(manageResources);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            // errore azione
            e.printStackTrace();
        }

    }


    /**
     * attempt to buy a developmentCard
     */
    public void buyDevelopmentCard(HashMap<Resource,Integer> depotResources, HashMap<Resource,Integer> strongboxResources, HashMap<Resource,Integer>cardDepotResources, Color color,int level,int slotNumber){
        BuyDevelopmentCard buyDevelopmentCard = new BuyDevelopmentCard(game.getBoard(), depotResources,strongboxResources,cardDepotResources,color,level,slotNumber);
        try {
            game.doAction(buyDevelopmentCard);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
         //errore azione
            e.printStackTrace();
        }
    }


    /**
     * attempt to discard a leaderCard
     */
    public void discardLeaderCard(String id ){
        if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)) {
            DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(0),game.getActivePlayer().getCardsHand(),game.getActivePlayer().getFaithTrack());
            try {
                game.doAction(discardLeaderCard);
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
             //solito errore
                e.printStackTrace();
            }
        }
        else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)) {
            DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard(game.getActivePlayer().getCardsHand().get(1),game.getActivePlayer().getCardsHand(),game.getActivePlayer().getFaithTrack());
            try {
                game.doAction(discardLeaderCard);
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                //solito errore
                e.printStackTrace();
            }
        }
        else{
            // carta non riconosciuta
        }
    }


    /**
     * attempt to play a leaderCard
     */
    public void playLeaderCard(String id, HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes){
        if (game.getActivePlayer().getCardsHand().get(0).getId().equals(id)){
            PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(),game.getActivePlayer().getCardsHand().get(0),warehouseDepotRes,strongboxRes,cardDepotRes);
            try {
                game.doAction(playLeaderCard);
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
              //solito errore
                e.printStackTrace();
            }
        }
        else if (game.getActivePlayer().getCardsHand().get(1).getId().equals(id)){
            PlayLeaderCard playLeaderCard = new PlayLeaderCard(game.getActivePlayer().getCardsHand(),game.getActivePlayer().getCardsHand().get(1),warehouseDepotRes,strongboxRes,cardDepotRes);
            try {
                game.doAction(playLeaderCard);
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                //solito errore
                e.printStackTrace();
            }
        }
        else {
            //carta non riconosciuta
        }
    }


    /**
     * pick the marbles from the market and turn them into resources
     */
    public void marketAction(int index, boolean isRow){
        ArrayList<Marbles> marbles= new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        if (isRow){
            PickRow pickRow = new PickRow(index,marbles,game.getBoard().getMarketBoard());
            try {
                game.doAction(pickRow);
                for (Marbles marble : marbles) {
                    marble.drawEffect(resources, game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getExchangeBuff());
                }
                Map<Resource,Integer> temp1;
                temp1= temp;
                for (Resource resource : resources) {
                    temp1.put(resource, temp1.get(resource) + 1);
                }
                newResources.put(game.getActivePlayer().getNickname(),temp1);
                for (ServerView serverView : serverViews) {
                    if (serverView.getUsername().equals(game.getActivePlayer().getNickname())) {
                        serverView.sendResourceManageRequest(resources);
                    }

                }
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                //solito errore
                e.printStackTrace();
            }
        }
        else{
            PickColumn pickColumn = new PickColumn(index,marbles,game.getBoard().getMarketBoard());
            try {
                game.doAction(pickColumn);
                for (Marbles marble : marbles) {
                    marble.drawEffect(resources, game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().getExchangeBuff());
                }
                Map<Resource,Integer> temp1;
                temp1= temp;
                for (Resource resource : resources) {
                    temp1.put(resource, temp1.get(resource) + 1);
                }
                newResources.put(game.getActivePlayer().getNickname(),temp1);
                for (ServerView serverView : serverViews) {
                    if (serverView.getUsername().equals(game.getActivePlayer().getNickname())) {
                        serverView.sendResourceManageRequest(resources);
                    }
                }
            } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
                //solito errore
                e.printStackTrace();
            }
        }

    }


    /**
     * attempt to use the production
     */
    public void useProduction(HashMap<Resource,Integer> warehouseDepotRes,HashMap<Resource,Integer> cardDepotRes, HashMap<Resource,Integer> strongboxRes,
                              ArrayList<Resource> boardGain, ArrayList<Resource> leaderGain, ArrayList<Integer> devSlotIndex, ArrayList<Integer> leaderCardProdIndex  ){
        UseProduction useProduction = new UseProduction(devSlotIndex,leaderCardProdIndex,leaderGain,boardGain,warehouseDepotRes,cardDepotRes,strongboxRes);
        try {
            game.doAction(useProduction);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            //errore da gestire
            e.printStackTrace();
        }
    }


    public void sendVaticanReport(){

    }


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
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
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

    public void setupGame(){
        for (int i=0;i<serverViews.size();i++){
            game.setActivePlayer(game.getPlayers(i));
            ArrayList<LeaderCard> leaderCards = game.getActivePlayer().take4cards();
            ArrayList<String> leaderId = new ArrayList<>();
            startingLeaderCards.put(game.getActivePlayer().getNickname(),leaderCards);
            for (LeaderCard l: leaderCards){
                leaderId.add(l.getId());
            }
            serverViews.get(i).sendLeaderCards(leaderId);

        }

    }

    /**
     * Extracts the information from the {@link ClientMessage} which will call a method on the controller.
     * @param message message from the client.
     */
    @Override
    public void update(ClientMessage message) {
        message.handleMessage(this);

    }
}
