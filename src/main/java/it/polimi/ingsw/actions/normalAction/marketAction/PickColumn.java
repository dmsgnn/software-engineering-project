package it.polimi.ingsw.actions.normalAction.marketAction;

import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.gameboard.Market;
import it.polimi.ingsw.gameboard.marble.Marbles;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.ArrayList;

public class PickColumn extends MarketAction {


    public PickColumn(int index, ArrayList<Marbles> marbles, Market market) {
        super(index, marbles, market);
    }

    @Override
    public ArrayList<Marbles> pickRowOrColumn(Market market, int index) throws InvalidIndexException {
        return market.pickColumn(index);
    }

    @Override
    public boolean validAction(PlayerBoard playerBoard) {
        return market.validColumn(index);
    }
}
