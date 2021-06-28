package it.polimi.ingsw.model.playerboard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.model.leadercard.ability.DiscountAbility;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerBoardTest {
    private final DiscountAbility discountAbility = new DiscountAbility();
    private final ColorRequirements colorRequirements = new ColorRequirements();
    private final Game game = new Game();
    private final PlayerBoard playerBoard = new PlayerBoard(new Player("user", 0, game), game);
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();


    /**
     * Generate a random Resource
     * @return a RANDOM resource from the enum
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }


    public PlayerBoardTest()  {}

    @Test
    public void addLeaderCardTest() {
        LeaderCard  leaderCard= new LeaderCard("1",1,discountAbility,colorRequirements, randomResource());
        playerBoard.addLeaderCard(leaderCard);
        assertEquals(leaderCard, playerBoard.getLeaderCards().get(0));
    }

    @Test
    public void boardProductionTest()  {
        Resource resource1 = randomResource();
        Resource resource2 = randomResource();
        Resource resource3 = randomResource();
        playerBoard.getWarehouse().getDepots().get(0).addResources(resource1);
        playerBoard.getWarehouse().getDepots().get(1).addResources(resource2);
        playerBoard.boardProduction(resource1,resource2,resource3);
        assertEquals(1,playerBoard.getStrongbox().getValue(resource3));
        
    }

    @Test
    public void slotTest(){
        Map<Integer, ArrayList<String>> slot = playerBoard.getDevSlotsCardId();
        assertEquals(0,slot.get(0).size());
        assertEquals(0,slot.get(1).size());
        assertEquals(0,slot.get(2).size());

    }


}
