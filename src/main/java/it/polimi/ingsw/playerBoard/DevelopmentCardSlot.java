package it.polimi.ingsw.playerBoard;

import it.polimi.ingsw.exceptions.InvalidInsertException;
import it.polimi.ingsw.gameboard.DevelopmentCard;

import java.util.ArrayList;

public class DevelopmentCardSlot {
    private ArrayList<DevelopmentCard> slots = new ArrayList<>();
    private int cardCounter=0;

    /**
     * adding a card to the slot
     * @param card to add
     * @throws InvalidInsertException if it is impossible to add the card to the slot
     */
    public void addCardOnTop(DevelopmentCard card) throws InvalidInsertException {
        if (slots.get(this.cardCounter).getLevel() < card.getLevel()){
            this.slots.add(card);
            this.cardCounter++;
        }
        else throw new InvalidInsertException();
    }

    /**
     * @return of the currently active card
     */
    public DevelopmentCard lookTop(){
        return slots.get(this.cardCounter);
    }

    /**
     * @return of the specific card in place @param position
     */
    public DevelopmentCard getCard(int position){
        return slots.get(position);
    }

    /**
     *
     * @return true if the slot is full
     */
    public boolean isFull(){
        if (slots.get(this.cardCounter).getLevel()==3) return true;
        else return false;
    }

    /**
     *
     * @return of the number of cards
     */
    public int numOfCards(){return cardCounter;}

}
