package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.exceptions.DiscountBuffErrorException;
import it.polimi.ingsw.exceptions.ExchangeBuffErrorException;
import it.polimi.ingsw.exceptions.ProductionBuffErrorException;

import java.util.ArrayList;

public class CardBuffs {
    private final ArrayList<Resource> exchangeBuff;
    private final ArrayList<Resource> discountBuff;
    private final ArrayList<Resource> productionBuff;
    private Resource activeDiscountBuff;
    private Resource activeExchangeBuff;
    private Resource activeProductionBuff;

    public CardBuffs() {
        this.exchangeBuff = new ArrayList<Resource>();
        this.discountBuff = new ArrayList<Resource>();
        this.productionBuff = new ArrayList<Resource>();
    }

    //public void useLeaderCardProduction(Resource gain){}

    /**
     * these methods provide the buff currently selected as active
     */
    public Resource getActiveDiscountBuff() {
        return activeDiscountBuff;
    }
    public Resource getActiveExchangeBuff() {
        return activeExchangeBuff;
    }
    public Resource getActiveProductionBuff() {
        return activeProductionBuff;
    }


    public void setActiveDiscountBuff(Resource activeDiscountBuff) {
        this.activeDiscountBuff = activeDiscountBuff;
    }
    public void setActiveExchangeBuff(Resource activeExchangeBuff) {
        this.activeExchangeBuff = activeExchangeBuff;
    }
    public void setActiveProductionBuff(Resource activeProductionBuff) {
        this.activeProductionBuff = activeProductionBuff;
    }

    /**
     * these methods add the resource parameter to the specific buff
     */
    public void addExchangeBuff(Resource resource) throws ExchangeBuffErrorException {
        int first =0;
        if (exchangeBuff.isEmpty()) {
            first = 1;
        }
        if (!exchangeBuff.add(resource)) {
            throw new ExchangeBuffErrorException();
        }
        if (first==1) activeExchangeBuff = exchangeBuff.get(0);

    }
    public void addDiscountBuff(Resource resource) throws DiscountBuffErrorException {
        int first =0;
        if (exchangeBuff.isEmpty()) {
            first = 1;
        }
        if (!discountBuff.add(resource)) throw new DiscountBuffErrorException();
        if (first==1) activeDiscountBuff = discountBuff.get(0);
    }
    public void addProductionBuff(Resource resource) throws ProductionBuffErrorException {
        int first =0;
        if (exchangeBuff.isEmpty()) {
            first = 1;
        }
        if (!productionBuff.add(resource)) throw new ProductionBuffErrorException();
        if(first==1) activeProductionBuff= productionBuff.get(0);
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
