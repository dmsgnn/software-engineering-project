package it.polimi.ingsw.model.actions.normalAction.marketAction;

import it.polimi.ingsw.model.actions.Actions;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.model.exceptions.InvalidIndexException;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.gameboard.marble.Marbles;
import it.polimi.ingsw.model.playerboard.PlayerBoard;

import java.util.ArrayList;

/**
 * market action
 */
public abstract class MarketAction extends Actions {
    protected int index;
    protected ArrayList<Marbles> marbles;
    protected Market market;

    public MarketAction(int index, ArrayList<Marbles> marbles, Market market) {
        this.index = index;
        this.marbles = marbles;
        this.market = market;
    }

    /**
     * takes the given column / row and determines the resources chosen by the player
     * @param market of the marbles
     * @param index of row / column
     * @return returns the resources that the player gets
     */
    public ArrayList<Marbles> pickRowOrColumn(Market market, int index) throws InvalidIndexException {
        return null;
    }

@Override
    public void doAction(PlayerBoard playerBoard) throws InvalidActionException, WrongLevelException, NoCardsLeftException {
        if (!validAction(playerBoard)) throw new InvalidActionException();
        else{
            try {
                marbles = pickRowOrColumn(market,index);
            } catch (InvalidIndexException e) {
                throw new InvalidActionException();
            }
        }

    }

    public ArrayList<Marbles> getMarbles() {
        return marbles;
    }

}


