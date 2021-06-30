package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.normalAction.marketAction.ManageResources;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.playerboard.depot.CardDepot;
import it.polimi.ingsw.model.playerboard.depot.WarehouseDepot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ManageResourcesTest {
    @Test
    @DisplayName("Test manage resources ")
    public void manage() {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> first = new ArrayList<>();
        ArrayList<Resource> second = new ArrayList<>();
        ArrayList<Resource> third = new ArrayList<>();
        first.add(Resource.COINS);
        second.add(Resource.SERVANTS);
        second.add(Resource.SERVANTS);
        third.add(Resource.SHIELDS);
        third.add(Resource.SHIELDS);
        third.add(Resource.SHIELDS);
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0, first);
        resources.put(1, second);
        resources.put(2, third);
        Map<Resource, Integer> res = new HashMap<>();
        res.put(Resource.COINS, 1);
        res.put(Resource.SERVANTS, 2);
        res.put(Resource.SHIELDS, 3);
        res.put(Resource.STONES, 0);
        Map<Resource, Integer> discard = new HashMap<>();
        for (Resource resource : Resource.values()) {
            discard.put(resource, 0);
        }
        ManageResources manageResources = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        try {
            manageResources.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            assertTrue(true);
        }
        assertEquals(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(0).getResource(), Resource.COINS);
        assertEquals(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(1).getResource(), Resource.SERVANTS);
        assertEquals(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(2).getResource(), Resource.SHIELDS);
        assertTrue(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(0).isFull());
        assertTrue(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(1).isFull());
        assertTrue(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(2).isFull());

    }

    @Test
    public void error1() {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> a1 = new ArrayList<>();
        ArrayList<Resource> a2 = new ArrayList<>();
        ArrayList<Resource> a3 = new ArrayList<>();
        ArrayList<Resource> a4 = new ArrayList<>();
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0, a1);
        resources.put(1, a2);
        resources.put(2, a3);
        resources.put(3, a4);
        Map<Resource, Integer> res = new HashMap<>();
        res.put(Resource.COINS, 1);
        res.put(Resource.SERVANTS, 2);
        res.put(Resource.SHIELDS, 3);
        res.put(Resource.STONES, 0);
        Map<Resource, Integer> discard = new HashMap<>();
        for (Resource resource : Resource.values()) {
            discard.put(resource, 0);
        }
        ManageResources manageResources = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertFalse(manageResources.validAction(game.getActivePlayer().getPlayerBoard()));

    }

    @Test
    public void error2() {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> a1 = new ArrayList<>();
        ArrayList<Resource> a2 = new ArrayList<>();
        ArrayList<Resource> a3 = new ArrayList<>();
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0, a1);
        resources.put(1, a2);
        resources.put(2, a3);
        Map<Resource, Integer> res = new HashMap<>();
        res.put(Resource.COINS, 1);
        res.put(Resource.SERVANTS, 2);
        res.put(Resource.SHIELDS, 3);
        res.put(Resource.STONES, 0);
        Map<Resource, Integer> discard = new HashMap<>();
        for (Resource resource : Resource.values()) {
            discard.put(resource, 0);
        }
        ManageResources manageResources = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertTrue(manageResources.validAction(game.getActivePlayer().getPlayerBoard()));
        ArrayList<Resource> a4 = new ArrayList<>();
        a4.add(Resource.COINS);
        resources.put(3, a4);
        game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().add(3, new CardDepot(2, 0, Resource.COINS));
        ManageResources manageResources1 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertTrue(manageResources1.validAction(game.getActivePlayer().getPlayerBoard()));
        a4.clear();
        resources.put(3, a4);
        game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().add(3, new CardDepot(2, 0, Resource.COINS));
        ManageResources manageResources3 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertTrue(manageResources3.validAction(game.getActivePlayer().getPlayerBoard()));
        a4.clear();
        a4.add(Resource.SERVANTS);
        resources.put(3, a4);
        game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().add(3, new CardDepot(2, 0, Resource.COINS));
        ManageResources manageResources6 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertFalse(manageResources6.validAction(game.getActivePlayer().getPlayerBoard()));
        a4.clear();
        resources.put(3, a4);
        ArrayList<Resource> a5 = new ArrayList<>();
        a4.add(Resource.COINS);
        resources.put(3, a4);
        a5.add(Resource.COINS);
        resources.put(4, a5);
        game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().add(4, new CardDepot(2, 0, Resource.COINS));
        ManageResources manageResources2 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertTrue(manageResources2.validAction(game.getActivePlayer().getPlayerBoard()));
        a5.clear();
        a4.clear();
        resources.put(4, a5);
        resources.put(3, a4);
        game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().add(4, new CardDepot(2, 0, Resource.COINS));
        ManageResources manageResources4 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertTrue(manageResources4.validAction(game.getActivePlayer().getPlayerBoard()));
        a4.add(Resource.SERVANTS);
        resources.put(3, a4);
        game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().add(4, new CardDepot(2, 0, Resource.COINS));
        ManageResources manageResources5 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertFalse(manageResources5.validAction(game.getActivePlayer().getPlayerBoard()));


    }

    @Test
    public void error3() {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> a1 = new ArrayList<>();
        ArrayList<Resource> a2 = new ArrayList<>();
        ArrayList<Resource> a3 = new ArrayList<>();
        a1.add(Resource.COINS);
        a2.add(Resource.SERVANTS);
        a2.add(Resource.SERVANTS);
        a3.add(Resource.SHIELDS);
        a3.add(Resource.SHIELDS);
        a3.add(Resource.SHIELDS);
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0, a1);
        resources.put(1, a2);
        resources.put(2, a3);
        Map<Resource, Integer> res = new HashMap<>();
        res.put(Resource.COINS, 1);
        res.put(Resource.SERVANTS, 2);
        res.put(Resource.SHIELDS, 3);
        res.put(Resource.STONES, 0);
        Map<Resource, Integer> discard = new HashMap<>();
        for (Resource resource : Resource.values()) {
            discard.put(resource, 1);
        }
        ManageResources manageResources = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertFalse(manageResources.validAction(game.getActivePlayer().getPlayerBoard()));
    }

    @Test
    public void error4() {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> a1 = new ArrayList<>();
        ArrayList<Resource> a2 = new ArrayList<>();
        ArrayList<Resource> a3 = new ArrayList<>();
        a1.add(Resource.COINS);
        a2.add(Resource.COINS);
        a2.add(Resource.COINS);
        a3.add(Resource.SHIELDS);
        a3.add(Resource.SHIELDS);
        a3.add(Resource.SHIELDS);
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0, a1);
        resources.put(1, a2);
        resources.put(2, a3);
        Map<Resource, Integer> res = new HashMap<>();
        res.put(Resource.COINS, 3);
        res.put(Resource.SERVANTS, 0);
        res.put(Resource.SHIELDS, 3);
        res.put(Resource.STONES, 0);
        Map<Resource, Integer> discard = new HashMap<>();
        for (Resource resource : Resource.values()) {
            discard.put(resource, 0);
        }
        ManageResources manageResources = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        assertFalse(manageResources.validAction(game.getActivePlayer().getPlayerBoard()));
    }

    @Test
    public void error5() {
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> a1 = new ArrayList<>();
        ArrayList<Resource> a2 = new ArrayList<>();
        ArrayList<Resource> a3 = new ArrayList<>();
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0, a1);
        resources.put(1, a2);
        resources.put(2, a3);
        Map<Resource, Integer> res = new HashMap<>();
        res.put(Resource.COINS, 1);
        res.put(Resource.SERVANTS, 2);
        res.put(Resource.SHIELDS, 3);
        res.put(Resource.STONES, 1);
        Map<Resource, Integer> discard = new HashMap<>();
        for (Resource resource : Resource.values()) {
            discard.put(resource, 1);
        }
        ManageResources manageResources = new ManageResources(resources, res, discard, true, game.getActivePlayer().getFaithTrack());
        assertTrue(manageResources.validAction(game.getActivePlayer().getPlayerBoard()));

        manageResources.manageResources(game.getActivePlayer().getPlayerBoard());
        assertTrue(true);

        ManageResources manageResources1 = new ManageResources(resources, res, discard, false, game.getActivePlayer().getFaithTrack());
        manageResources1.manageResources(game.getActivePlayer().getPlayerBoard());
        assertTrue(true);

    }

    @Test
    public void error6(){
        Game game = new Game();
        game.setActivePlayer(new Player("tester", 1, game));
        ArrayList<Resource> a1 = new ArrayList<>();
        a1.add(Resource.SHIELDS);
        a1.add(Resource.SHIELDS);
        a1.add(Resource.SHIELDS);
        a1.add(Resource.SHIELDS);
        ArrayList<Resource> a2 = new ArrayList<>();
        ArrayList<Resource> a3 = new ArrayList<>();
        Map<Integer, ArrayList<Resource>> resources = new HashMap<>();
        resources.put(0,a1);
        resources.put(1,a2);
        resources.put(2,a3);
        Map<Resource,Integer> res = new HashMap<>();
        res.put(Resource.COINS,1);
        res.put(Resource.SERVANTS,2);
        res.put(Resource.SHIELDS,4);
        res.put(Resource.STONES,0);
        Map<Resource,Integer> discard= new HashMap<>();
        for (Resource resource:Resource.values()){
            discard.put(resource,0);
        }
        ManageResources manageResources = new ManageResources(resources,res,discard,false,game.getActivePlayer().getFaithTrack());
        assertFalse(manageResources.validAction(game.getActivePlayer().getPlayerBoard()));


        assertThrows(InvalidActionException.class, () -> manageResources.doAction(game.getActivePlayer().getPlayerBoard()));

        ManageResources manageResources1 = new ManageResources(resources,res,null,false,game.getActivePlayer().getFaithTrack());
        manageResources1.manageResources(game.getActivePlayer().getPlayerBoard());
        assertTrue(true);


        assertFalse(game.getActivePlayer().isFirst());
    }



}
