package it.polimi.ingsw.actions;

import it.polimi.ingsw.Game;

public abstract class NormalActions implements Action{

    @Override
    public void doAction(Game game) {
    }

    public boolean validAction(Game game){
        return true;
    }
}
