package it.polimi.ingsw.actions;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.InvalidIndexException;
import it.polimi.ingsw.gameboard.Market;

import java.util.ArrayList;

public abstract class MarketAction extends Actions {
    public ArrayList<Resource> pickRowOrColumn(Market market,int rowOrColumn) throws InvalidIndexException {
        return null;
    };
}
