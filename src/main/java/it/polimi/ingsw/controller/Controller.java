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
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickColumn;
import it.polimi.ingsw.model.actions.normalAction.marketAction.PickRow;
import it.polimi.ingsw.model.actions.setupAction.AddStartingLeaderCards;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.server.ServerView;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller implements Observer<ClientMessage> {

    private ArrayList<LeaderCard> startingLeaderCards;
    private Game game;
    private int playerNumber;
    private ArrayList<String> nicknames;
    private ArrayList<ServerView> serverViews;

    public Controller(Game game, ArrayList<ServerView> serverViews) {
        this.game = game;
        this.serverViews = serverViews;
        nicknames= new ArrayList<>();
        for (int i=0;i<serverViews.size();i++){
            nicknames.add(i,game.getPlayers(i).getNickname());
        }
        playerNumber=0;

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
            for (int j=0; j<game.getBoard().getCardColumns();i++){
                try {
                    devCardGrid[i][j] = game.getBoard().getCardGrid()[i][j].lookFirst().getId();
                } catch (NoCardsLeftException e) {
                    devCardGrid[i][j] = "null";
                }

            }
        }
        return devCardGrid;
    }

    public void startGame(){
        Map<String,Color> colorMap = new HashMap<>();
        Map<String,Integer> levelMap = new HashMap<>();
        for (int i=0;i<game.getBoard().getCardRows();i++) {
            for (int j = 0; j < game.getBoard().getCardColumns(); j++) {
                for (int k = 0; k < game.getBoard().getCardGrid()[i][j].getDeck().size(); k++) {
                    colorMap.put(game.getBoard().getCardGrid()[i][j].getDeck().get(k).getId(), game.getBoard().getCardGrid()[i][j].getDeck().get(k).getColor());
                    levelMap.put(game.getBoard().getCardGrid()[i][j].getDeck().get(k).getId(), game.getBoard().getCardGrid()[i][j].getDeck().get(k).getLevel());
                }
            }
        }
        for (ServerView s: serverViews){
            s.sendAllCards(colorMap,levelMap);
            s.sendMarket(getMarket(),getFreeMarble());
            s.sendDevCardGrid(getDevCardGrid());

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
    public void pickStartingLeaderCards(ArrayList<String> leaderId){
        ArrayList<LeaderCard> trueCards = new ArrayList<>();
        int counter=0;
        for (String s : leaderId){
            for (LeaderCard leaderCard: startingLeaderCards){
                if (s.equals(leaderCard.getId())) {
                    trueCards.add(counter,leaderCard);
                    counter++;
                }
            }
        }
        AddStartingLeaderCards addStartingLeaderCards = new AddStartingLeaderCards(trueCards,game.getActivePlayer());
        try {
            game.doAction(addStartingLeaderCards);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            //solito errore
            e.printStackTrace();
        }

    }



    /**
     * assign the starting resources to the player
     */
    public void pickStartingResources(Map<Integer,ArrayList<Resource>> resources) {
        //controllo sul numero esatto di risorse
        ManageResources manageResources = new ManageResources(resources, null,null,true);
        try {
            game.doAction(manageResources);
        } catch (InvalidActionException | InsufficientResourcesException | WrongLevelException | NoCardsLeftException e) {
            // mando errore nel fare l'azione
            e.printStackTrace();
        }
    }


    /**
     * assign the new Resources to the player
     */
    public void manageResources(Map<Integer,ArrayList<Resource>> resources, HashMap<Resource,Integer> discRes, HashMap<Resource,Integer> newRes){
        ManageResources manageResources = new ManageResources(resources,newRes,discRes,false);
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
                //hai il nuovo array di resources da spedire
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
                //hai il nuovo array di resources da spedire
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

    public void setPlayerNumber(int num){
        playerNumber= num;
    }
    public void addUsername(String nick){
        nicknames.add(nick);
    }


    public void setupGame(){

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
