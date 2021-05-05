package it.polimi.ingsw.controller;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.messages.clientToServer.ClientMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.leaderAction.DiscardLeaderCard;
import it.polimi.ingsw.model.actions.leaderAction.PlayLeaderCard;
import it.polimi.ingsw.model.actions.normalAction.BuyDevelopmentCard;
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

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller implements Observer<ClientMessage> {

    private ArrayList<LeaderCard> startingLeaderCards;
    private Game game;
    private int playerNumber;
    private ArrayList<String> nicknames;


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

    public void setPlayerNumber(int num){
        playerNumber= num;
    }
    public void addUsername(String nick){
        nicknames.add(nick);
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
