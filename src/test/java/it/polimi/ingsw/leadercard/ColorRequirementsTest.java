package it.polimi.ingsw.leadercard;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.exceptions.InvalidInsertException;
import it.polimi.ingsw.exceptions.ZeroCapacityException;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.gameboard.LeaderDeck;
import it.polimi.ingsw.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.leadercard.Requirements.Requirements;
import it.polimi.ingsw.playerboard.PlayerBoard;
import it.polimi.ingsw.utility.LeaderCardsParserXML;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColorRequirementsTest {
    private final Game game= new Game();
    private final PlayerBoard playerBoard = new PlayerBoard(new Player("name",1,game),game);
    private final ColorRequirements colorRequirements = new ColorRequirements();
    private static final List<Color> VALUES = Collections.unmodifiableList(Arrays.asList(Color.values()));
    private static final int SIZE = VALUES.size();
    static Random random = new Random();

    public ColorRequirementsTest() throws ZeroCapacityException {
    }

    /**
     * Generate a random Color
     * @return a RANDOM color from the enum
     */
    public static Color randomColor(){
        return VALUES.get(random.nextInt(SIZE));
    }
    @Test
    public void reqTest() throws InvalidInsertException {
        Color color = randomColor();
        int levelCard=0;
        while (levelCard==0){
            levelCard = random.nextInt(3);
        }
        HashMap<Color,Integer> level = new HashMap<>();
        level.put(color,levelCard);
        HashMap<Color,Integer> qua = new HashMap<>();
        qua.put(color,levelCard);
        colorRequirements.setLevelCardRequirements(level);
        colorRequirements.setColorCardRequirements(qua);
        for (int i=0; i<levelCard;i++){
            playerBoard.getSlots().get(i).addCardOnTop(new DevelopmentCard(null,color,"",levelCard,levelCard,null),playerBoard);
        }
        assertTrue(colorRequirements.checkRequirements(playerBoard));
    }




}
