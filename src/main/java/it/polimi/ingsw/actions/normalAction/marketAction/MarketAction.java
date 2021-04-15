package it.polimi.ingsw.actions.normalAction.marketAction;

import it.polimi.ingsw.actions.Actions;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.exceptions.NoCardsLeftException;
import it.polimi.ingsw.exceptions.WrongLevelException;
import it.polimi.ingsw.gameboard.Market;
import it.polimi.ingsw.gameboard.marble.Marbles;
import it.polimi.ingsw.playerboard.PlayerBoard;

import java.util.ArrayList;

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

}


