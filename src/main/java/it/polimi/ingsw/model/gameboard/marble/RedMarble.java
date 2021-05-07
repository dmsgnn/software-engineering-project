package it.polimi.ingsw.model.gameboard.marble;

import it.polimi.ingsw.client.MarbleColors;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;

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

    @Override
    public MarbleColors getColor() {
        return MarbleColors.RED;
    }
}
