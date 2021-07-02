package it.polimi.ingsw.model.actions.normalAction.marketAction;

import it.polimi.ingsw.model.exceptions.InvalidIndexException;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.ArrayList;

/**
 * market action if the player selected a column
 */
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

    @Override
    public ArrayList<Marbles> getMarbles() {
         return market.getOneColumn(index);
    }
}
