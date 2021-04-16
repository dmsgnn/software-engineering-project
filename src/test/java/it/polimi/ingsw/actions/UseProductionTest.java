package it.polimi.ingsw.actions;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.actions.leaderAction.PlayLeaderCard;
import it.polimi.ingsw.actions.normalAction.UseProduction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.gameboard.development.ProductionPower;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.leadercard.ability.ProductionAbility;
import it.polimi.ingsw.leadercard.ability.StoreAbility;
import it.polimi.ingsw.playerboard.DevelopmentCardSlot;
import it.polimi.ingsw.playerboard.Strongbox;
import it.polimi.ingsw.playerboard.depot.BaseDepot;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UseProductionTest {

   @Test
    public void correctActionTest() {
        Game game = new Game();
        try {
            game.setActivePlayer(new Player("Giorgio", 1, game));
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        }
       //cost doesn't matter for this test
       PlayLeaderCard play = new PlayLeaderCard(new LeaderCard(2, new StoreAbility(), new ColorRequirements(), Resource.SERVANTS),
               new HashMap<>(), new HashMap<>(), new HashMap<>());
       try {
           play.doAction(game.getActivePlayer().getPlayerBoard());
       } catch (InvalidActionException e) {
           e.printStackTrace();
       }

       PlayLeaderCard play2 = new PlayLeaderCard(new LeaderCard(2, new ProductionAbility(), new ColorRequirements(), Resource.SERVANTS),
               new HashMap<>(), new HashMap<>(), new HashMap<>());
       try {
           play2.doAction(game.getActivePlayer().getPlayerBoard());
       } catch (InvalidActionException e) {
           e.printStackTrace();
       }

        ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();
        try {

            depots.get(0).addResources(Resource.COINS);
            depots.get(1).addResources(Resource.STONES);
            depots.get(1).addResources(Resource.STONES);
            depots.get(2).addResources(Resource.SERVANTS);
            depots.get(2).addResources(Resource.SERVANTS);
            depots.get(2).addResources(Resource.SERVANTS);
            //card depot
            depots.get(3).addResources(Resource.SERVANTS);
            depots.get(3).addResources(Resource.SERVANTS);
            for(Resource resource : Resource.values()){
                //strongbox con 4 di ogni risorsa
                game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource, 4);
            }
        } catch (ZeroCapacityException | FullWarehouseException | WrongResourceException e) {
            e.printStackTrace();
        }

        //create devCards to use as test
        Map<Resource, Integer> prodCost = new HashMap<>();
        prodCost.put(Resource.SERVANTS, 3);
        prodCost.put(Resource.STONES, 2);
        Map<Resource, Integer> prodCost2 = new HashMap<>();
        prodCost2.put(Resource.COINS, 2);
        prodCost2.put(Resource.SHIELDS, 2);
        Map<Resource, Integer> resGain = new HashMap<>();
        resGain.put(Resource.SERVANTS, 1);
        resGain.put(Resource.STONES, 1);
        ProductionPower power = new ProductionPower(prodCost, resGain, 2);
        ProductionPower power2 = new ProductionPower(prodCost2, resGain, 2);
        DevelopmentCard card = new DevelopmentCard(new HashMap<>(), Color.GREEN,1, 1, power);
        DevelopmentCard card2 = new DevelopmentCard(new HashMap<>(), Color.GREEN,1, 1, power2);

        ArrayList<DevelopmentCardSlot> playerSlot = game.getActivePlayer().getPlayerBoard().getSlots();
        try {
            playerSlot.get(0).addCardOnTop(card, game.getActivePlayer().getPlayerBoard());
            playerSlot.get(1).addCardOnTop(card2, game.getActivePlayer().getPlayerBoard());
        } catch (InvalidInsertException e) {
            e.printStackTrace();
        }

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
        strongboxResources.put(Resource.COINS, 2);

        ArrayList<Resource> boardResources = new ArrayList<>();
        boardResources.add(Resource.SERVANTS);
        boardResources.add(Resource.COINS);
        boardResources.add(Resource.SHIELDS);

        UseProduction production = new UseProduction(new ArrayList<>(Arrays.asList(0,1)), new ArrayList<>(Collections.singletonList(0)),
                new ArrayList<>(Collections.singletonList(Resource.COINS)), boardResources, wareHouse, leaderDepotResources, strongboxResources);


        try {
            production.doAction(game.getActivePlayer().getPlayerBoard());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //controls if the updated resources inside warehouse are correct
        assertEquals(depots.get(0).getOccupied(), 0);
        assertEquals(depots.get(1).getOccupied(), 1);
        assertEquals(depots.get(2).getOccupied(), 2);
        assertEquals(depots.get(3).getOccupied(), 1);

        Map<Resource, Integer> map = new HashMap<>();
        map.put(Resource.SERVANTS, 3);
        map.put(Resource.SHIELDS, 3);
        map.put(Resource.STONES, 5);
        map.put(Resource.COINS, 3);
        Strongbox strongbox = game.getActivePlayer().getPlayerBoard().getStrongbox();
        //controls if the updated resources inside strongbox are correct
        assertEquals(strongbox.getResources(), map);

    }


    @Test
    public void validActionTestWrongPayment(){
        Game game = new Game();
        try {
            game.setActivePlayer(new Player("Giorgio", 1, game));
        } catch (ZeroCapacityException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<BaseDepot> depots = game.getActivePlayer().getPlayerBoard().getWarehouse().getDepots();
            depots.get(0).addResources(Resource.COINS);
            depots.get(1).addResources(Resource.STONES);
            depots.get(2).addResources(Resource.SERVANTS);
            for(Resource resource : Resource.values()){
                //strongbox con 2 di ogni risorsa
                game.getActivePlayer().getPlayerBoard().getStrongbox().addResource(resource, 2);
            }
        } catch (ZeroCapacityException | FullWarehouseException | WrongResourceException e) {
            e.printStackTrace();
        }
        Map<Resource, Integer> wareHouse = new HashMap<>();
        Map<Resource, Integer> leaderDepotResources = new HashMap<>();
        Map<Resource, Integer> strongboxResources = new HashMap<>();
        strongboxResources.put(Resource.SERVANTS, 0);
        strongboxResources.put(Resource.SHIELDS, 1);
        strongboxResources.put(Resource.COINS, 1);

        ArrayList<Resource> boardResources = new ArrayList<>();
        boardResources.add(Resource.SERVANTS);
        boardResources.add(Resource.COINS);
        boardResources.add(Resource.SHIELDS);

        UseProduction production = new UseProduction(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                                                        boardResources, wareHouse, leaderDepotResources, strongboxResources);

        assertFalse(production.validAction(game.getActivePlayer().getPlayerBoard()));
    }

}
