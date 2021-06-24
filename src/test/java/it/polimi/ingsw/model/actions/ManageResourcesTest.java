package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.actions.normalAction.marketAction.ManageResources;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManageResourcesTest {
    @Test
    @DisplayName("Test manage resources ")
    public void manage(){
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
        resources.put(0,first);
        resources.put(1,second);
        resources.put(2,third);
        Map<Resource,Integer> res = new HashMap<>();
        res.put(Resource.COINS,1);
        res.put(Resource.SERVANTS,2);
        res.put(Resource.SHIELDS,3);
        res.put(Resource.STONES,0);
        Map<Resource,Integer> discard= new HashMap<>();
        for (Resource resource:Resource.values()){
            discard.put(resource,0);
        }
        ManageResources manageResources = new ManageResources(resources,res,discard,false,game.getActivePlayer().getFaithTrack());
        try {
            manageResources.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            assertTrue(true);
        }
        assertEquals(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(0).getResource(),Resource.COINS);
        assertEquals(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(1).getResource(),Resource.SERVANTS);
        assertEquals(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(2).getResource(),Resource.SHIELDS);
        assertTrue(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(0).isFull());
        assertTrue(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(1).isFull());
        assertTrue(game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots().get(2).isFull());

    }
}
