package it.polimi.ingsw.model.actions.normalAction.marketAction;

import it.polimi.ingsw.model.exceptions.InvalidIndexException;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.ArrayList;

public class PickRow extends MarketAction {
    public PickRow(int index, ArrayList<Marbles> marbles, Market market) {
        super(index, marbles,market);
    }

    @Override
    public ArrayList<Marbles> pickRowOrColumn(Market market, int index) throws InvalidIndexException {
        return market.pickRow(index);
    }


    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        return market.validRow(index);
    }


    @Override
    public ArrayList<Marbles> getMarbles() {
        return market.getOneRow(index);
    }
}
