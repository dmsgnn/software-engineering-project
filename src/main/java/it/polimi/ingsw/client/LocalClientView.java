package it.polimi.ingsw.client;

import it.polimi.ingsw.client.representations.ClientGameBoard;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.LocalController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalClientView extends View{
    private LocalController controller;

    public LocalClientView(UserInterface ui) {
        super(ui);
    }

    /**
     * called to send the login nickname
     * @param nickname selected from the player
     */
    public void sendLogin(String nickname){
        setNickname(nickname);
        ArrayList<String> user = new ArrayList<>();
        user.add(nickname);
        Game game = new Game(user);
        controller= new LocalController(game, user, this);
        controller.startGame();
    }

    //------------------GAME SETUP------------------

    /**
     * called to send the starting leadercards to the server
     * @param cards selected from the player
     */
    public void sendStartingCards(ArrayList<String> cards){
        controller.pickStartingLeaderCards(cards, getNickname());
    }



    //------------------ACTIONS------------------

    /**
     * called to send to the server the action the player decided to do this turn
     * @param action selected from the player
     */
    public void sendAction(Actions action){
        controller.selectAction(action);
    }



    /**
     * called when the player ends his turn
     */
    public void endTurn(){
        getUiType().endTurn();
        controller.endTurn();
    }

    /**
     * called from UI to send the market action parameters to the server
     * @param pos column or row picked by the player
     * @param rowOrCol true if pos is row, false if column
     * @param exchangeBuffResources resources obtained from white marbles if possible
     * @param username of this client
     */
    public void marketAction(int pos, boolean rowOrCol, ArrayList<Resource> exchangeBuffResources, String username){
        controller.marketAction(pos, rowOrCol, exchangeBuffResources, username);
    }

    /**
     * called from UI to send the production action parameters to the server
     * @param developmentCardSlotIndex indexes of the development cards the player wants to use for the production
     * @param leaderCardProdIndex indexes of the leader cards the player wants to use for the production
     * @param leaderCardProdGain resource gains for the leader card production
     * @param boardResources resource cost and gains for the board production
     * @param warehouseResources resources inside the warehouse that the player wants to pay
     * @param leaderDepotResources resources inside the leadercard depots that the player wants to pay
     * @param strongboxResources resources inside the strongbox that the player wants to pay
     * @param username of this client
     */
    public void useProduction(ArrayList<Integer> developmentCardSlotIndex, ArrayList<Integer> leaderCardProdIndex, ArrayList<Resource> leaderCardProdGain,
                              ArrayList<Resource> boardResources, HashMap<Resource, Integer> warehouseResources, HashMap<Resource, Integer> leaderDepotResources,
                              HashMap<Resource, Integer> strongboxResources, String username){
        controller.useProduction(warehouseResources, leaderDepotResources, strongboxResources,
                boardResources, leaderCardProdGain, developmentCardSlotIndex, leaderCardProdIndex, username);
    }

    /**
     * called from UI to send the buy development card action parameters to the server
     * @param color of the card
     * @param level of the card
     * @param devCardSlot slot to place the card in
     * @param warehouseDepotRes resources inside the warehouse that the player wants to pay
     * @param cardDepotRes resources inside the leadercard depots that the player wants to pay
     * @param strongboxRes resources inside the strongbox that the player wants to pay
     * @param username of this client
     */
    public void buyDevCard(Color color, int level, int devCardSlot, HashMap<Resource, Integer> warehouseDepotRes,
                           HashMap<Resource, Integer> cardDepotRes, HashMap<Resource, Integer> strongboxRes, String username){
        controller.buyDevelopmentCard(warehouseDepotRes,strongboxRes, cardDepotRes, color, level, devCardSlot, username);
    }

    /**
     * called from UI to send the play leadercard action parameters to the server
     * @param id of the selected card
     * @param username of this client
     */
    public void playLeaderCard(String id,String username){
        controller.playLeaderCard(id, username);
    }

    /**
     * called from UI to send the discard leadercard action parameters to the server
     * @param id of the selected card
     * @param username of this client
     */
    public void discardLeaderCard(String id,String username){
        controller.discardLeaderCard(id, username);
    }


    /**
     * called from UI to send the manage resources action parameters to the server
     * @param newWarehouse new warehouse configuration
     * @param discard resources that the player wants to discard
     */
    public void sendManageResourcesReply(Map<Integer, ArrayList<Resource>> newWarehouse, Map<Resource,Integer> discard){
        controller.manageResources(newWarehouse, discard);
    }

    //------------------UPDATES------------------


    /**
     * called to notify the final scores and the winner to the players
     * @param finalScores final victory points
     * @param lorenzoWin true if Lorenzo won the game, false if he lost or if the game is singleplayer
     */
    public void finalScoresUpdate(Map<String, Integer> finalScores, boolean lorenzoWin){
        getUiType().lorenzoScoreboard(getNickname(), finalScores.get(getNickname()), lorenzoWin);
    }
}
