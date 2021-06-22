package it.polimi.ingsw.model.actions;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.normalAction.BuyDevelopmentCard;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.playerboard.PlayerBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BuyDevCardTest {

    @Test
    @DisplayName("Test the correct placement of the card in the slot")
    public void BuyDevCardPlacementTest() throws WrongLevelException, NoCardsLeftException, InvalidActionException, InsufficientResourcesException {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        PlayerBoard board = game.getActivePlayer().getPlayerBoard();
        HashMap<Resource, Integer> payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        DevelopmentCard card = game.getBoard().viewCard(Color.GREEN, 1);
        HashMap<Resource, Integer> depot = new HashMap<>();

        HashMap<Resource, Integer> cardDepot = new HashMap<>();

        game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.GREEN, 1, 0));
        assertEquals(card, board.getSlots().get(0).getCard(0));

    }

    @Test
    @DisplayName("Test that the user can't place a card of level one over another card of level one")
    public void BuyDevCardInvalidTest1() throws WrongLevelException, NoCardsLeftException, InvalidActionException, InsufficientResourcesException {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        PlayerBoard board = game.getActivePlayer().getPlayerBoard();
        //buy first card in slot 0
        HashMap<Resource, Integer> payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        DevelopmentCard card = game.getBoard().viewCard(Color.GREEN, 1);
        HashMap<Resource, Integer> depot = new HashMap<>();

        HashMap<Resource, Integer> cardDepot = new HashMap<>();

        game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.GREEN, 1, 0));
        assertEquals(card, board.getSlots().get(0).getCard(0));

        //trying to buy another card of level 1 and place it on the same slot -> Invalid Action
        DevelopmentCard oldCard = board.getSlots().get(0).getCard(0);
        DevelopmentCard triedCard = game.getBoard().viewCard(Color.YELLOW, 1);
        payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.YELLOW, 1).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.YELLOW, 1).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        card = game.getBoard().viewCard(Color.YELLOW, 1);
        depot = new HashMap<>();

        cardDepot = new HashMap<>();

        try {
            game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.YELLOW, 1, 0));
        } catch (InvalidActionException e){
            assertTrue(true);
        }
        assertEquals(oldCard, board.getSlots().get(0).getCard(0));
        assertEquals(triedCard, game.getBoard().viewCard(Color.YELLOW,1));
    }

    @Test
    @DisplayName("Test that the user can't place a card of level three over a card of level one")
    public void BuyDevCardInvalidTest2() throws WrongLevelException, NoCardsLeftException, InvalidActionException, InsufficientResourcesException {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        PlayerBoard board = game.getActivePlayer().getPlayerBoard();
        //buy first card in slot 0
        HashMap<Resource, Integer> payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        DevelopmentCard card = game.getBoard().viewCard(Color.GREEN, 1);
        HashMap<Resource, Integer> depot = new HashMap<>();

        HashMap<Resource, Integer> cardDepot = new HashMap<>();

        game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.GREEN, 1, 0));
        assertEquals(card, board.getSlots().get(0).getCard(0));

        //trying to buy a card of level 3 and place it on the slot where there is a card of level one -> Invalid Action
        DevelopmentCard oldCard = board.getSlots().get(0).getCard(0);
        DevelopmentCard triedCard = game.getBoard().viewCard(Color.YELLOW, 3);
        payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.YELLOW, 3).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.YELLOW, 3).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        card = game.getBoard().viewCard(Color.YELLOW, 3);
        depot = new HashMap<>();

        cardDepot = new HashMap<>();

        try {
            game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.YELLOW, 3, 0));
        } catch (InvalidActionException e){
            assertTrue(true);
        }
        assertEquals(oldCard, board.getSlots().get(0).getCard(0));
        assertEquals(triedCard, game.getBoard().viewCard(Color.YELLOW,3));
    }

    @Test
    @DisplayName("Test that the user can place a card of level two over a card of level one")
    public void BuyDevCardInvalidTest3() throws WrongLevelException, NoCardsLeftException, InvalidActionException, InsufficientResourcesException {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        PlayerBoard board = game.getActivePlayer().getPlayerBoard();
        //buy first card in slot 0
        HashMap<Resource, Integer> payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.GREEN, 1).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        DevelopmentCard card = game.getBoard().viewCard(Color.GREEN, 1);
        HashMap<Resource, Integer> depot = new HashMap<>();

        HashMap<Resource, Integer> cardDepot = new HashMap<>();

        game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.GREEN, 1, 0));
        assertEquals(card, board.getSlots().get(0).getCard(0));

        //trying to buy a card of level 2 and place it on the slot where there is the card of level one -> Valid Action
        DevelopmentCard oldCard = board.getSlots().get(0).getCard(0);
        DevelopmentCard triedCard = game.getBoard().viewCard(Color.YELLOW, 2);
        payment = new HashMap<>();
        for(Map.Entry<Resource,Integer> entry: game.getBoard().viewCard(Color.YELLOW, 2).getCardRequirements().entrySet()){
            if(entry.getValue()!=0)
                payment.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Resource,Integer> entry : game.getBoard().viewCard(Color.YELLOW, 2).getCardRequirements().entrySet()) {
            board.getStrongbox().addResource(entry.getKey(), entry.getValue());
        }
        card = game.getBoard().viewCard(Color.YELLOW, 2);
        depot = new HashMap<>();

        cardDepot = new HashMap<>();

        game.doAction(new BuyDevelopmentCard(game.getBoard(), depot, payment, cardDepot, Color.YELLOW, 2, 0));

        assertEquals(oldCard, board.getSlots().get(0).getCard(0));
        assertEquals(triedCard, board.getSlots().get(0).getCard(1));
        assertNotEquals(triedCard, game.getBoard().viewCard(Color.YELLOW,2));
    }

}
