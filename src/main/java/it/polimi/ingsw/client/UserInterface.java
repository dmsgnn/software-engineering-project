package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public interface UserInterface {

    /**
     * initialization
     */
    void begin();

    /**
     * login into a match
     */
    void login();

    /**
     * handles login errors
     */
    void failedLogin();

    /**
     * called when the login is done
     */
    void loginDone();

    /**
     * handles the disconnection of one player
     * @param nickname of the player that disconnected
     */
    void handleDisconnection(String nickname);

    /**
     * handles the reconnection of one player
     * @param nickname of the player that reconnected
     */
    void handleReconnection(String nickname);

    /**
     * Manages a losing player
     * @param nickname Name of loser
     */
    void loser(String nickname);

    /**
     * called to manage the error message
     * @param errorType contains the error message
     */
    void manageError(Error errorType);

    /**
     * called to notify that this is the last round
     */
    void lastRound();

    /**
     * called to show the scores of each player
     * @param finalScores score of each player
     */
    void scoreboard(Map<String, Integer> finalScores);

    /**
     * called to show the scores in single player
     * @param score player score
     * @param lorenzoHasWon true if Lorenzo won the game, false if he lost
     * @param nickname the nickname of the player
     */
    void lorenzoScoreboard(String nickname, int score, boolean lorenzoHasWon);

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
     * called when the player has correctly selected his starting cards
     */
    void endLeadercardSetup();

    /**
     * called when the player has correctly selected his starting resources
     */
    void endStartingResourcesSetup();

    /**
     * called to begin a game, it displays the necessary information to begin a game
     */
    void startGame();

    /**
     * called only for the first player to make him choose the number of players
     * @param max possible number of players
     */
    void playersNumber(int max);

    /**
     * called to allow the players to choose their starting leadercards
     * @param leaderCardID list of the cards ID that the player can choose from
     */
    void startingLeaderCardsSelection(ArrayList<String> leaderCardID);

    /**
     * called to make the players chose their starting resources
     * @param amount num of starting resources
     */
    void startingResources(int amount);

    /**
     * called to make the player choose the action to perform
     * @param possibleActions list of possible actions
     */
    void chooseAction(ArrayList<Actions> possibleActions);

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
    void updateBoard(String message);
}
