package it.polimi.ingsw.client;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.messages.clientToServer.UsernameReply;
import it.polimi.ingsw.messages.serverToClient.ServerMessage;

import java.util.ArrayList;

public class ClientView implements Observer<ServerMessage> {

    //private ClientSocketHandler socket;
    private UserInterface uiType;
    private String nickname; //nickname of the player owning this client
    private final String ip;
    private final int port;
    private final ClientGameBoard gameboard;

    public ClientView(String ip, int port, UserInterface ui) {
        this.ip = ip;
        this.port = port;
        this.uiType = ui;
        gameboard = new ClientGameBoard();
    }

    public UserInterface getUi() {
        return uiType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void addPlayersToGameboard(ArrayList<String> players){
        gameboard.addPlayers(players);
    }

    /**
     * Called when the game begins
     */
    public void startGame() {
        uiType.startGame();
    }

    /**
     * informs the client about the starting market configuration
     * @param grid this is the market configuration
     * @param free this is the free marble that has to be used to perform the market action
     */
    public void marketSetup(MarbleColors[][] grid, MarbleColors free){
        gameboard.initializeMarket(grid, free);
    }

    /**
     * informs the client about the starting development card grid (only the cards on top of it)
     * @param cards initial card grid
     */
    public void developmentCardGridSetup(String[][] cards){
        gameboard.initializeCards(cards);
    }

    public void login(){
        String nickname = uiType.login();
        //socket.sendMessage(new UsernameReply(nickname));
    }

    /**
     * Called when a {@link ServerMessage} is received. The package is extracted and the method on the incoming
     * event overrides a method on ClientView
     * @param message Incoming message
     */
    @Override
    public void update(ServerMessage message) {
        message.handleMessage(this);
    }

}
