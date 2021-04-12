package it.polimi.ingsw.playerboard;

import it.polimi.ingsw.exceptions.InvalidInsertException;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.gameboard.development.DevelopmentCard;

import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentCardSlot {
    private ArrayList<DevelopmentCard> slots = new ArrayList<>();
    private int cardCounter=0;
    HashMap<Color,Integer> map;
    int count;

    /**
     * adding a card to the slot
     * @param card to add
     * @throws InvalidInsertException if it is impossible to add the card to the slot
     */
    public void addCardOnTop(DevelopmentCard card, PlayerBoard playerBoard) throws InvalidInsertException {
        if (isEmpty()) {
            this.slots.add(card);
            this.cardCounter++;
            map = playerBoard.getColorRequirements().get(card.getLevel());
            count = map.get(card.getColor());
            map.put(card.getColor(),count+1);
            playerBoard.getColorRequirements().put(card.getLevel(),map);
        } else {
            if (slots.get(cardCounter-1).getLevel() < card.getLevel()) {
                this.slots.add(card);
                this.cardCounter++;
                map = playerBoard.getColorRequirements().get(card.getLevel());
                count = map.get(card.getColor());
                map.put(card.getColor(),count+1);
                playerBoard.getColorRequirements().put(card.getLevel(),map);
            } else throw new InvalidInsertException();
        }

    }

    /**
     * @return of the currently active card
     */
    public DevelopmentCard lookTop() {
        if (isEmpty()) return null;
        else {
            return slots.get(cardCounter - 1);
        }
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
        if (cardCounter==0) return false;
        if (slots.get(cardCounter-1).getLevel()==3) return true;
        else return false;
    }

    /**
     *
     * @return true if the slot is empty
     */
    public boolean isEmpty(){
        if (cardCounter==0) return true;
        else return false;
    }

    /**
     *
     * @return of the number of cards
     */
    public int numOfCards(){return cardCounter;}

}
