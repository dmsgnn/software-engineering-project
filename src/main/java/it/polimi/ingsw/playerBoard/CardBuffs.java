package it.polimi.ingsw.playerBoard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.DiscountBuffErrorException;
import it.polimi.ingsw.exceptions.ExchangeBuffErrorException;
import it.polimi.ingsw.exceptions.ProdctionBuffErrorException;

import java.util.ArrayList;

public class CardBuffs {
    private final ArrayList<Resource> exchangeBuff;
    private final ArrayList<Resource> discountBuff;
    private final ArrayList<Resource> productionBuff;

    public CardBuffs() {
        this.exchangeBuff = new ArrayList<Resource>();
        this.discountBuff = new ArrayList<Resource>();
        this.productionBuff = new ArrayList<Resource>();
    }

    //public void useLeaderCardProduction(Resource gain){}

    public void addExchangeBuff(Resource resource) throws ExchangeBuffErrorException {
        if (!exchangeBuff.add(resource)) throw new ExchangeBuffErrorException();
    }

    public void addDiscountBuff(Resource resource) throws DiscountBuffErrorException {
        if (!discountBuff.add(resource)) throw new DiscountBuffErrorException();
    }

    public void addProductionBuff(Resource resource) throws ProdctionBuffErrorException {
        if (!productionBuff.add(resource)) throw new ProdctionBuffErrorException();
    }

    public ArrayList<Resource> getExchangeBuff() {
        return exchangeBuff;
    }
    public ArrayList<Resource> getProductionBuff() {
        return productionBuff;
    }
    public ArrayList<Resource> getDiscountBuff() {
        return discountBuff;
    }

    public boolean isDiscountBuffActive(){
        return !discountBuff.isEmpty();
    }
    public boolean isExchangeBuffActive(){
        return !exchangeBuff.isEmpty();
    }
    public boolean isExtraProductionAvailable(){
        return !productionBuff.isEmpty();
    }


}
