package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
/**
 * class that represents the white marble
 */
public class WhiteMarble extends Marbles {

    private final Game game;
    public WhiteMarble(Game game){
        this.game=game;
    }

    /**
     * activates the effect of the marble
     * @param resources adds a resource to it if the player has the exchange buff active
     * @param exchangeResources resources to exchange fort white marbles, not use here
     */
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources) {
        if(game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().isExchangeBuffActive()){
            resources.add(exchangeResources.remove(0));
        }
    }

    @Override
    public MarbleColors getColor() {
        return MarbleColors.WHITE;
    }
}