package it.polimi.ingsw.leadercard;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.FullWarehouseException;
import it.polimi.ingsw.exceptions.WrongResourceException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.leadercard.Requirements.ResourceRequirements;
import it.polimi.ingsw.playerboard.PlayerBoard;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceRequirementsTest {
    private final  Game game = new Game();
    private final PlayerBoard playerBoard = new PlayerBoard(new Player("name,",1,game),game);
    private final ResourceRequirements requirements = new ResourceRequirements();

    //random
    private static final List<Resource> VALUES = Collections.unmodifiableList(Arrays.asList(Resource.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    public ResourceRequirementsTest() throws ZeroCapacityException {
    }


    /**
     * Generate a random Resource
     * @return a RANDOM resource from the enum
     */
    public static Resource randomResource(){
        return VALUES.get(random.nextInt(SIZE));
    }

    @Test
    public void reqTest() throws FullWarehouseException, ZeroCapacityException, WrongResourceException {
        Resource resource = randomResource();
        int quantity = random.nextInt(10);
        for (int i=0;i<quantity;i++) {
            if (!playerBoard.getWarehouse().getDepots().get(2).isFull()) playerBoard.getWarehouse().getDepots().get(2).addResources(resource);
            else{
                playerBoard.getStrongbox().addResource(resource,quantity-i);
            }
        }
        HashMap<Resource,Integer> req = new HashMap<Resource,Integer>(){{
            put(resource,quantity);
        }};
        requirements.setCardRequirements(req);
        assertTrue(requirements.checkRequirements(playerBoard));
    }





}
