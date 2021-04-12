package it.polimi.ingsw.actions;


import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.playerboard.PlayerBoard;

public abstract class Actions {


    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
    }

    public boolean validAction(PlayerBoard playerBoard){
        return true;
    }
}
