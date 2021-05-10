package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public interface UserInterface {

    /**
     * initialization
     */
    void start();

    /**
     * login into a match
     */
    String login();

    /**
     * handles login errors
     */
    String failedLogin(ArrayList<String> usedNames);

    /**
     * Manages a losing player
     * @param nickname Name of loser
     */
    void loser(String nickname);

    /**
     * called to display the error message to the player
     * @param message contains the error message
     */
    void displayMessage(String message);

    /**
     * Manages a winning player
     * @param nickname Name of winner
     */
    void winner(String nickname);

    /**
     * called if a player correctly joins a game
     */
    void inGameLobby();

    /**
     * called if the player tries to join a full game
     */
    void gameFull();

    /**
     * called when the player ends their turn
     */
    void endTurn();

    /**
     * called to begin a game, it displays the necessary information to begin a game
     */
    void startGame();

    /**
     * called only for the first player to make him choose the number of players
     * @param max possible number of players
     * @return chosen number
     */
    int playersNumber(int max);

    /**
     * called to allow the players to choose their starting leadercards
     * @param leaderCardID list of the cards ID that the player can choose from
     * @return the ID of the chosen leadercards
     */
    ArrayList<String> startingLeaderCardsSelection(ArrayList<String> leaderCardID);

    /**
     * called to make the players chose their starting resources
     * @param amount num of starting resources
     * @return list of resources
     */
    Map<Integer, ArrayList<Resource>> startingResources(int amount);

    /**
     * called to make the player choose the action to perform
     * @param possibleActions list of possible actions
     * @return action picked from the player
     */
    Actions chooseAction(ArrayList<Actions> possibleActions);

    /**
     * called to make the player perform the market action
     */
    void marketAction();

    /**
     * called to make the player perform the production action
     */
    void useProductionAction();

    /**
     * called to make the player perform the buyDevCard action
     */
    void buyCardAction();

    /**
     * called to make the player perform the play leadercard action
     */
    void playLeaderCardAction();

    /**
     * called to make the player perform the discard leadercard action
     */
    void discardLeaderCardAction();

    /**
     * called to make the player manage his resources during the market action
     * @param resources list of resources to manage
     */
    void manageResources(ArrayList<Resource> resources);

    /**
     * called to print the new board
     */
    void updateBoard();
}
