package it.polimi.ingsw.client;

public interface UserInterface {

    /**
     * initialization of the ui
     */
    void start();

    /**
     * login into a match
     */
    String login();

    /**
     * handles login errors
     */
    void failedLogin();

    /**
     * Manages a losing player
     * @param username Name of loser
     */
    void loser(String username);

    /**
     * Manages a winning player
     * @param username Name of winner
     */
    void winner(String username);

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
     * called to begin a game, it displays the necessary informations to begin a game
     */
    void startGame();

}
