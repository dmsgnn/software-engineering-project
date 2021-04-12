package it.polimi.ingsw.actions;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.gameboard.Market;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.ArrayList;

public class PickRow extends MarketAction{
    @Override
    public ArrayList<Resource> pickRowOrColumn(Market market,int row) throws InvalidIndexException {
        return market.pickRow(row);
    }

    @Override
    public void doAction(PlayerBoard playerBoard) {

    }

    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        return true;
    }
}
