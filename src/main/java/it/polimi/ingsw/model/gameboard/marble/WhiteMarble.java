package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class WhiteMarble extends Marbles {

    private final Game game;
    public WhiteMarble(Game game){
        this.game=game;
    }
    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources) {
        if(game.getActivePlayer().getPlayerBoard().getLeaderCardBuffs().isExchangeBuffActive()){
            resources.add(exchangeResources.remove(0));
        }
    }
}