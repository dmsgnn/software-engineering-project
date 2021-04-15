package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.Resource;
import java.util.ArrayList;

public class CardBuffs {
    private final ArrayList<Resource> exchangeBuff;
    private final ArrayList<Resource> discountBuff;
    private final ArrayList<Resource> productionBuff;
    private Resource activeDiscountBuff;
    private Resource activeExchangeBuff;

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


    public void setActiveDiscountBuff(Resource activeDiscountBuff) {
        this.activeDiscountBuff = activeDiscountBuff;
    }
    public void setActiveExchangeBuff(Resource activeExchangeBuff) {
        this.activeExchangeBuff = activeExchangeBuff;
    }

    /**
     * these methods add the resource parameter to the specific buff
     */
    public void addExchangeBuff(Resource resource)  {
        int first =0;
        if (exchangeBuff.isEmpty()) {
            first = 1;}

       exchangeBuff.add(resource);
        if (first==1) activeExchangeBuff = exchangeBuff.get(0);
    }
    public void addDiscountBuff(Resource resource) {
        int first =0;
        if (exchangeBuff.isEmpty()) {
            first = 1;
        }
        discountBuff.add(resource);
        if (first==1) activeDiscountBuff = discountBuff.get(0);
    }
    public void addProductionBuff(Resource resource) {
        productionBuff.add(resource);

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
