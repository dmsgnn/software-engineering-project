package it.polimi.ingsw.gameboard.marble;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Resource;

import java.util.ArrayList;

public class RedMarble extends Marbles {
    private final Game game;
    public RedMarble(Game game){
        this.game=game;
    }

    @Override
    public void drawEffect(ArrayList<Resource> resources, ArrayList<Resource> exchangeResources) {
        game.getActivePlayer().getFaithTrack().increasePosition();
    }
}
