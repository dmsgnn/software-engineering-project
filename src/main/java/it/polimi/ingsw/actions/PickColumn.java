package it.polimi.ingsw.actions;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.gameboard.Gameboard;
import it.polimi.ingsw.gameboard.Market;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.ArrayList;

public class PickColumn extends MarketAction{
    @Override
    public ArrayList<Resource> pickRowOrColumn(Market market, int column) throws InvalidIndexException {
        return market.pickColumn(column);
    }

    @Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException {
        if (!validAction(playerBoard)) throw new InvalidActionException();


    }

    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        return super.validAction(playerBoard);
    }
}
