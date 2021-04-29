package it.polimi.ingsw.actions;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.actions.leaderAction.PlayLeaderCard;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.leadercard.ability.ProductionAbility;
import it.polimi.ingsw.leadercard.ability.StoreAbility;
import it.polimi.ingsw.playerboard.Strongbox;
import it.polimi.ingsw.playerboard.depot.BaseDepot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ActionsTest {
    public class ActionTest extends Actions{

    }

    @Test
    public void payResourcesTest() {
        Game game = new Game();
        Actions action = new ActionTest();

        game.setActivePlayer(new Player("Giorgio", 1, game));

        //cost doesn't matter for this test
        ArrayList<LeaderCard> cards = new ArrayList<LeaderCard>();
        LeaderCard card11 = new LeaderCard("1",2, new StoreAbility(), new ColorRequirements(), Resource.SERVANTS);
        LeaderCard card22 = new LeaderCard("2",2, new ProductionAbility(), new ColorRequirements(), Resource.SERVANTS);

        cards.add(0,card11);
        cards.add(1,card22);
        PlayLeaderCard play = new PlayLeaderCard(cards, card11, new HashMap<>(), new HashMap<>(), new HashMap<>());
        game.getActivePlayer().setCardsHand(cards);
        try {
            play.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();
        depots.get(0).addResources(Resource.COINS);
        depots.get(1).addResources(Resource.STONES);
        depots.get(1).addResources(Resource.STONES);
        depots.get(2).addResources(Resource.SERVANTS);
        depots.get(2).addResources(Resource.SERVANTS);
        depots.get(2).addResources(Resource.SERVANTS);
        //card depot
        depots.get(3).addResources(Resource.SERVANTS);
        depots.get(3).addResources(Resource.SERVANTS);
        for (Resource resource : Resource.values()) {
                //strongbox 4 di ogni risorsa
                game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource, 4);
            }
        //payment to do
        Map<Resource, Integer> wareHouse = new HashMap<>();
        wareHouse.put(Resource.COINS, 1);
        wareHouse.put(Resource.STONES, 1);
        wareHouse.put(Resource.SERVANTS, 1);
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        leaderDepotResources.put(Resource.SERVANTS, 1);
        Map<Resource, Integer> strongboxResources = new HashMap<>();
        strongboxResources.put(Resource.SERVANTS, 3);
        strongboxResources.put(Resource.SHIELDS, 2);
        strongboxResources.put(Resource.STONES, 1);
        strongboxResources.put(Resource.COINS, 4);

        try {
            action.payResources(game.getActivePlayer().getPlayerBoard(), wareHouse, leaderDepotResources, strongboxResources);
        } catch (InsufficientResourcesException  | CantPayException e) {
            e.printStackTrace();
        }

        assertEquals(depots.get(0).getOccupied(), 0);
        assertEquals(depots.get(1).getOccupied(), 1);
        assertEquals(depots.get(2).getOccupied(), 2);
        assertEquals(depots.get(3).getOccupied(), 1);

        Map<Resource, Integer> map = new HashMap<>();
        map.put(Resource.SERVANTS, 1);
        map.put(Resource.SHIELDS, 2);
        map.put(Resource.STONES, 3);
        map.put(Resource.COINS, 0);
        Strongbox strongbox = game.getActivePlayer().getPlayerBoard().getStrongbox();
        assertEquals(strongbox.getResources(), map);
    }


    @Test
    public void payResourcesWrongStrongboxTest() {
        Game game = new Game();
        Actions action = new ActionTest();
        game.setActivePlayer(new Player("Giorgio", 1, game));


        for (Resource resource : Resource.values()) {
            //strongbox 2 di ogni risorsa
            game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource, 2);
        }
        //payment to do
        Map<Resource, Integer> wareHouse = new HashMap<>();
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        Map<Resource, Integer> strongboxResources = new HashMap<>();
        strongboxResources.put(Resource.SERVANTS, 3);
        strongboxResources.put(Resource.SHIELDS, 2);
        strongboxResources.put(Resource.STONES, 1);
        strongboxResources.put(Resource.COINS, 4);


        Assertions.assertThrows(CantPayException.class, () -> action.payResources(game.getActivePlayer().getPlayerBoard(), wareHouse, leaderDepotResources, strongboxResources));

    }
    @Test
    public void payResourcesWrongWarehouseTest() {
        Game game = new Game();
        Actions action = new ActionTest();

        game.setActivePlayer(new Player("Giorgio", 1, game));


        ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();
        depots.get(0).addResources(Resource.COINS);
        depots.get(1).addResources(Resource.STONES);
        depots.get(2).addResources(Resource.SERVANTS);
        depots.get(2).addResources(Resource.SERVANTS);

        //payment to do
        Map<Resource, Integer> wareHouse = new HashMap<>();
        wareHouse.put(Resource.COINS, 1);
        wareHouse.put(Resource.STONES, 2);
        wareHouse.put(Resource.SERVANTS, 1);
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        Map<Resource, Integer> strongboxResources = new HashMap<>();


        Assertions.assertThrows(CantPayException.class, () -> action.payResources(game.getActivePlayer().getPlayerBoard(), wareHouse, leaderDepotResources, strongboxResources));

    }

    @Test
    public void payResourcesWrongLeaderDepotTest() {
        Game game = new Game();
        Actions action = new ActionTest();

        game.setActivePlayer(new Player("Giorgio", 1, game));

        ArrayList<LeaderCard> cards = new ArrayList<LeaderCard>();
        LeaderCard card11 = new LeaderCard("1",2, new StoreAbility(), new ColorRequirements(), Resource.SERVANTS);
        LeaderCard card22 = new LeaderCard("2",2, new ProductionAbility(), new ColorRequirements(), Resource.SERVANTS);

        cards.add(0,card11);
        cards.add(1,card22);
        PlayLeaderCard play = new PlayLeaderCard(cards, card11, new HashMap<>(), new HashMap<>(), new HashMap<>());
        game.getActivePlayer().setCardsHand(cards);
        try {
            play.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();
        depots.get(0).addResources(Resource.COINS);
        depots.get(1).addResources(Resource.STONES);
        depots.get(2).addResources(Resource.SERVANTS);
        //card depot
        depots.get(3).addResources(Resource.SERVANTS);

        //payment to do
        Map<Resource, Integer> wareHouse = new HashMap<>();
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        leaderDepotResources.put(Resource.SERVANTS, 2);
        Map<Resource, Integer> strongboxResources = new HashMap<>();

        Assertions.assertThrows(CantPayException.class, () -> action.payResources(game.getActivePlayer().getPlayerBoard(), wareHouse, leaderDepotResources, strongboxResources));

    }


}
