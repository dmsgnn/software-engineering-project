package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class FullView {
    private static View clientView;
    private static GUI gui;

    public static void setGui(GUI gui) {
        FullView.gui = gui;
        FullView.clientView = gui.getClientView();
    }

    // depot of player one
    @FXML
    private ImageView resource1Depot1Player1;
    @FXML
    private ImageView resource1Depot2Player1;
    @FXML
    private ImageView resource2Depot2Player1;
    @FXML
    private ImageView resource1Depot3Player1;
    @FXML
    private ImageView resource2Depot3Player1;
    @FXML
    private ImageView resource3Depot3Player1;

    // depot of player two
    @FXML
    private ImageView resource1Depot1Player2;
    @FXML
    private ImageView resource1Depot2Player2;
    @FXML
    private ImageView resource2Depot2Player2;
    @FXML
    private ImageView resource1Depot3Player2;
    @FXML
    private ImageView resource2Depot3Player2;
    @FXML
    private ImageView resource3Depot3Player2;

    // depot of player three
    @FXML
    private ImageView resource1Depot1Player3;
    @FXML
    private ImageView resource1Depot2Player3;
    @FXML
    private ImageView resource2Depot2Player3;
    @FXML
    private ImageView resource1Depot3Player3;
    @FXML
    private ImageView resource2Depot3Player3;
    @FXML
    private ImageView resource3Depot3Player3;

    // depot of player four
    @FXML
    private ImageView resource1Depot1Player4;
    @FXML
    private ImageView resource1Depot2Player4;
    @FXML
    private ImageView resource2Depot2Player4;
    @FXML
    private ImageView resource1Depot3Player4;
    @FXML
    private ImageView resource2Depot3Player4;
    @FXML
    private ImageView resource3Depot3Player4;

    //strongbox counter player one
    @FXML
    private Label strongboxShieldsCounterPlayer1;
    @FXML
    private Label strongboxServantsCounterPlayer1;
    @FXML
    private Label strongboxCoinsCounterPlayer1;
    @FXML
    private Label strongboxStonesCounterPlayer1;

    //strongbox counter player two
    @FXML
    private Label strongboxShieldsCounterPlayer2;
    @FXML
    private Label strongboxServantsCounterPlayer2;
    @FXML
    private Label strongboxCoinsCounterPlayer2;
    @FXML
    private Label strongboxStonesCounterPlayer2;

    //strongbox counter player three
    @FXML
    private Label strongboxShieldsCounterPlayer3;
    @FXML
    private Label strongboxServantsCounterPlayer3;
    @FXML
    private Label strongboxCoinsCounterPlayer3;
    @FXML
    private Label strongboxStonesCounterPlayer3;

    //strongbox counter player three
    @FXML
    private Label strongboxShieldsCounterPlayer4;
    @FXML
    private Label strongboxServantsCounterPlayer4;
    @FXML
    private Label strongboxCoinsCounterPlayer4;
    @FXML
    private Label strongboxStonesCounterPlayer4;

    //strongbox images player three
    @FXML
    private ImageView shields3;
    @FXML
    private ImageView servants3;
    @FXML
    private ImageView coins3;
    @FXML
    private ImageView stones3;

    //strongbox images player four
    @FXML
    private ImageView shields4;
    @FXML
    private ImageView servants4;
    @FXML
    private ImageView coins4;
    @FXML
    private ImageView stones4;

    //board of player three
    @FXML
    private ImageView board3;
    @FXML
    private Label player3Name;

    //board of player three
    @FXML
    private ImageView board4;
    @FXML
    private Label player4Name;

    //vatican report of player one
    @FXML
    private ImageView vatican1Player1;
    @FXML
    private ImageView vatican2Player1;
    @FXML
    private ImageView vatican3Player1;

    //vatican report of player two
    @FXML
    private ImageView vatican1Player2;
    @FXML
    private ImageView vatican2Player2;
    @FXML
    private ImageView vatican3Player2;

    //vatican report of player three
    @FXML
    private ImageView vatican1Player3;
    @FXML
    private ImageView vatican2Player3;
    @FXML
    private ImageView vatican3Player3;

    //vatican report of player four
    @FXML
    private ImageView vatican1Player4;
    @FXML
    private ImageView vatican2Player4;
    @FXML
    private ImageView vatican3Player4;

    //slots of player one
    @FXML
    private ImageView player1Slot1;
    @FXML
    private ImageView player1Slot2;
    @FXML
    private ImageView player1Slot3;
    @FXML
    public ImageView player1Slot1mid;
    @FXML
    public ImageView player1Slot2mid;
    @FXML
    public ImageView player1Slot3mid;
    @FXML
    public ImageView player1Slot1bot;
    @FXML
    public ImageView player1Slot2bot;
    @FXML
    public ImageView player1Slot3bot;

    //slots of player two
    @FXML
    private ImageView player2Slot1;
    @FXML
    private ImageView player2Slot2;
    @FXML
    private ImageView player2Slot3;
    @FXML
    public ImageView player2Slot1mid;
    @FXML
    public ImageView player2Slot2mid;
    @FXML
    public ImageView player2Slot3mid;
    @FXML
    public ImageView player2Slot1bot;
    @FXML
    public ImageView player2Slot2bot;
    @FXML
    public ImageView player2Slot3bot;

    //slots of player three
    @FXML
    private ImageView player3Slot1;
    @FXML
    private ImageView player3Slot2;
    @FXML
    private ImageView player3Slot3;
    @FXML
    public ImageView player3Slot1mid;
    @FXML
    public ImageView player3Slot2mid;
    @FXML
    public ImageView player3Slot3mid;
    @FXML
    public ImageView player3Slot1bot;
    @FXML
    public ImageView player3Slot2bot;
    @FXML
    public ImageView player3Slot3bot;

    //slots of player four
    @FXML
    private ImageView player4Slot1;
    @FXML
    private ImageView player4Slot2;
    @FXML
    private ImageView player4Slot3;
    @FXML
    public ImageView player4Slot1mid;
    @FXML
    public ImageView player4Slot2mid;
    @FXML
    public ImageView player4Slot3mid;
    @FXML
    public ImageView player4Slot1bot;
    @FXML
    public ImageView player4Slot2bot;
    @FXML
    public ImageView player4Slot3bot;

    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;

    // leader cards
    @FXML
    private ImageView player1Leader1;
    @FXML
    private ImageView player1Leader2;
    @FXML
    private ImageView player2Leader1;
    @FXML
    private ImageView player2Leader2;
    @FXML
    private ImageView player3Leader1;
    @FXML
    private ImageView player3Leader2;
    @FXML
    private ImageView player4Leader1;
    @FXML
    private ImageView player4Leader2;

    // cross
    @FXML
    private ImageView cross1;
    @FXML
    private ImageView cross2;
    @FXML
    private ImageView cross3;
    @FXML
    private ImageView cross4;

    //array of buttons
    private ArrayList<ImageView> resource1Depot1;
    private ArrayList<ImageView> resource1Depot2;
    private ArrayList<ImageView> resource2Depot2;
    private ArrayList<ImageView> resource1Depot3;
    private ArrayList<ImageView> resource2Depot3;
    private ArrayList<ImageView> resource3Depot3;
    private ArrayList<Label> strongboxShieldsCounter;
    private ArrayList<Label> strongboxServantsCounter;
    private ArrayList<Label> strongboxCoinsCounter;
    private ArrayList<Label> strongboxStonesCounter;
    private ArrayList<Label> playerName;
    private ArrayList<ImageView> leader1;
    private ArrayList<ImageView> leader2;
    private ArrayList<ImageView> vatican1;
    private ArrayList<ImageView> vatican2;
    private ArrayList<ImageView> vatican3;
    private ArrayList<ImageView> slot1;
    private ArrayList<ImageView> slot1Mid;
    private ArrayList<ImageView> slot1Bot;
    private ArrayList<ImageView> slot2;
    private ArrayList<ImageView> slot2Mid;
    private ArrayList<ImageView> slot2Bot;
    private ArrayList<ImageView> slot3;
    private ArrayList<ImageView> slot3Mid;
    private ArrayList<ImageView> slot3Bot;
    private ArrayList<ImageView> cross;




    /**
     * initializes the buttons array
     */
    public void initArray() {
        //depot
        resource1Depot1 = new ArrayList<>(Arrays.asList(resource1Depot1Player1, resource1Depot1Player2, resource1Depot1Player3, resource1Depot1Player4));
        resource1Depot2 = new ArrayList<>(Arrays.asList(resource1Depot2Player1, resource1Depot2Player2, resource1Depot2Player3, resource1Depot2Player4));
        resource2Depot2 = new ArrayList<>(Arrays.asList(resource2Depot2Player1, resource2Depot2Player2, resource2Depot2Player3, resource2Depot2Player4));
        resource1Depot3 = new ArrayList<>(Arrays.asList(resource1Depot3Player1, resource1Depot3Player2, resource1Depot3Player3, resource1Depot3Player4));
        resource2Depot3 = new ArrayList<>(Arrays.asList(resource2Depot3Player1, resource2Depot3Player2, resource2Depot3Player3, resource2Depot3Player4));
        resource3Depot3 = new ArrayList<>(Arrays.asList(resource3Depot3Player1, resource3Depot3Player2, resource3Depot3Player3, resource3Depot3Player4));
        //strongbox
        strongboxShieldsCounter = new ArrayList<>(Arrays.asList(strongboxShieldsCounterPlayer1, strongboxShieldsCounterPlayer2, strongboxShieldsCounterPlayer3, strongboxShieldsCounterPlayer4));
        strongboxServantsCounter = new ArrayList<>(Arrays.asList(strongboxServantsCounterPlayer1, strongboxServantsCounterPlayer2, strongboxServantsCounterPlayer3, strongboxServantsCounterPlayer4));
        strongboxCoinsCounter = new ArrayList<>(Arrays.asList(strongboxCoinsCounterPlayer1,strongboxCoinsCounterPlayer2 , strongboxCoinsCounterPlayer3,strongboxCoinsCounterPlayer4 ));
        strongboxStonesCounter = new ArrayList<>(Arrays.asList(strongboxStonesCounterPlayer1, strongboxStonesCounterPlayer2, strongboxStonesCounterPlayer3, strongboxStonesCounterPlayer4));

        playerName = new ArrayList<>(Arrays.asList(player1Name, player2Name, player3Name, player4Name));

        //leader
        leader1 = new ArrayList<>(Arrays.asList(player1Leader1, player2Leader1, player3Leader1, player4Leader1));
        leader2 = new ArrayList<>(Arrays.asList(player1Leader2, player2Leader2, player3Leader2, player4Leader2));
        //vatican
        vatican1 = new ArrayList<>(Arrays.asList(vatican1Player1, vatican1Player2, vatican1Player3, vatican1Player4));
        vatican2 = new ArrayList<>(Arrays.asList(vatican2Player1, vatican2Player2, vatican2Player3, vatican2Player4));
        vatican3 = new ArrayList<>(Arrays.asList(vatican3Player1, vatican3Player2, vatican3Player3, vatican3Player4));
        //dev slots
        slot1 = new ArrayList<>(Arrays.asList(player1Slot1, player2Slot1, player3Slot1, player4Slot1));
        slot1Mid = new ArrayList<>(Arrays.asList(player1Slot1mid, player2Slot1mid, player3Slot1mid, player4Slot1mid));
        slot1Bot = new ArrayList<>(Arrays.asList(player1Slot1bot, player2Slot1bot, player3Slot1bot, player4Slot1bot));
        slot2 = new ArrayList<>(Arrays.asList(player1Slot2, player2Slot2, player3Slot2, player4Slot2));
        slot2Mid = new ArrayList<>(Arrays.asList(player1Slot2mid, player2Slot2mid, player3Slot2mid, player4Slot2mid));
        slot2Bot = new ArrayList<>(Arrays.asList(player1Slot2bot, player2Slot2bot, player3Slot2bot, player4Slot2bot));
        slot3 = new ArrayList<>(Arrays.asList(player1Slot3, player2Slot3, player3Slot3, player4Slot3));
        slot3Mid = new ArrayList<>(Arrays.asList(player1Slot3mid, player2Slot3mid, player3Slot3mid, player4Slot3mid));
        slot3Bot = new ArrayList<>(Arrays.asList(player1Slot3bot, player2Slot3bot, player3Slot3bot, player4Slot3bot));
        cross = new ArrayList<>(Arrays.asList(cross1, cross2, cross3, cross4));

    }

    /**
     * show the boards of the players
     */
    public void showView(){
        allVisible();
        for(int i=0; i<clientView.getGameboard().getPlayerBoards().size(); i++)
            showPlayer(i);
        if(!(clientView.getGameboard().getPlayerBoards().size()>2))
            hideThirdPlayer();
        if(!(clientView.getGameboard().getPlayerBoards().size()>3))
            hideFourthPlayer();
    }

    /**
     * called from the back arrow button to go back to the main board scene
     */
    public void back(){
        gui.switchView();
    }

    /**
     * it shows all the things related to the player number
     * @param playerNum is the number of the player
     */
    private void showPlayer(int playerNum){
        ClientPlayerBoard board = clientView.getGameboard().getPlayerBoards().get(playerNum);
        playerName.get(playerNum).setText("Playerboard of "+ board.getPlayerNickname());
        if(board.isConnected())
            playerName.get(playerNum).setStyle("-fx-background-color: green; -fx-background-radius: 20;");
        else
            playerName.get(playerNum).setStyle("-fx-background-color: red; -fx-background-radius: 20;");
        //

        // set strongbox counter
        strongboxShieldsCounter.get(playerNum).setText(String.valueOf(board.getStrongbox().get(Resource.SHIELDS)));
        strongboxServantsCounter.get(playerNum).setText(String.valueOf(board.getStrongbox().get(Resource.SERVANTS)));
        strongboxStonesCounter.get(playerNum).setText(String.valueOf(board.getStrongbox().get(Resource.STONES)));
        strongboxCoinsCounter.get(playerNum).setText(String.valueOf(board.getStrongbox().get(Resource.COINS)));
        // show depots
        if(board.isDepotEmpty(0))
            resource1Depot1.get(playerNum).setVisible(false);
        else
            resource1Depot1.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(0).toString().toLowerCase() + ".png"));
        if(board.isDepotEmpty(1)){
            resource1Depot2.get(playerNum).setVisible(false);
            resource2Depot2.get(playerNum).setVisible(false);
        }
        else{
            if (board.getWarehouse().get(1).size() == 2) {
                resource1Depot2.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
                resource2Depot2.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
            }
            else{
                resource1Depot2.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
                resource2Depot2.get(playerNum).setVisible(false);
            }
        }
        if(board.isDepotEmpty(2)){
            resource1Depot3.get(playerNum).setVisible(false);
            resource2Depot3.get(playerNum).setVisible(false);
            resource3Depot3.get(playerNum).setVisible(false);
        }
        else{
            if (board.getWarehouse().get(2).size() == 3) {
                resource1Depot3.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                resource2Depot3.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                resource3Depot3.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
            }
            else if(board.getWarehouse().get(2).size() == 2){
                resource1Depot3.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                resource2Depot3.get(playerNum).setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                resource3Depot3.get(playerNum).setVisible(false);
            }
            else {
                resource1Depot3.get(playerNum).setImage(new Image("resources/punchboard/" + board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                resource2Depot3.get(playerNum).setVisible(false);
                resource3Depot3.get(playerNum).setVisible(false);
            }
        }
        // leader card
        if(board.getPlayedCards().size()==2){
            leader1.get(playerNum).setImage(new Image("graphics/leaderCards/"+ board.getPlayedCards().get(0) +".png"));
            leader2.get(playerNum).setImage(new Image("graphics/leaderCards/"+ board.getPlayedCards().get(1) +".png"));
        }
        else if(board.getPlayedCards().size()==1) {
            leader1.get(playerNum).setImage(new Image("graphics/leaderCards/" + board.getPlayedCards().get(0) + ".png"));
            if(board.getHandSize()==1)
                leader2.get(playerNum).setImage(new Image("resources/back/leaderBack.png"));
            else
                leader2.get(playerNum).setVisible(false);
        }
        else{
            if(board.getHandSize()==2) {
                leader1.get(playerNum).setImage(new Image("resources/back/leaderBack.png"));
                leader2.get(playerNum).setImage(new Image("resources/back/leaderBack.png"));
            }
            else if(board.getHandSize()==1) {
                leader1.get(playerNum).setImage(new Image("resources/back/leaderBack.png"));
                leader2.get(playerNum).setVisible(false);
            }
            else{
                leader1.get(playerNum).setVisible(false);
                leader2.get(playerNum).setVisible(false);
            }
        }
        //vatican report
        if(board.getVaticanReports().containsKey(8)) {
            if (board.getVaticanReports().get(8))
                vatican1.get(playerNum).setImage(new Image("resources/punchboard/vatican1Active.png"));
            else
                vatican1.get(playerNum).setVisible(false);
        }
        else {
            vatican1.get(playerNum).setImage(new Image("resources/punchboard/vatican1.png"));
        }
        if(board.getVaticanReports().containsKey(16)) {
            if (board.getVaticanReports().get(16))
                vatican2.get(playerNum).setImage(new Image("resources/punchboard/vatican2Active.png"));
            else
                vatican2.get(playerNum).setVisible(false);
        }
        else {
            vatican2.get(playerNum).setImage(new Image("resources/punchboard/vatican2.png"));
        }
        if(board.getVaticanReports().containsKey(24)) {
            if (board.getVaticanReports().get(24))
                vatican3.get(playerNum).setImage(new Image("resources/punchboard/vatican3Active.png"));
            else
                vatican3.get(playerNum).setVisible(false);
        }
        else {
            vatican3.get(playerNum).setImage(new Image("resources/punchboard/vatican3.png"));
        }
        // development card slots
        if(board.isSlotEmpty(0))
            slot1.get(playerNum).setVisible(false);
        else
            slot1.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(0, board.slotSize(0)) + ".png"));
        if(board.isSlotEmpty(1))
            slot2.get(playerNum).setVisible(false);
        else
            slot2.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(1, board.slotSize(1)) + ".png"));
        if(board.isSlotEmpty(2))
            slot3.get(playerNum).setVisible(false);
        else
            slot3.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(2, board.slotSize(2)) + ".png"));
        //middle slots
        if(board.slotSize(0)>=2) slot1Mid.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(0, board.slotSize(0)-1) + ".png"));
        if(board.slotSize(1)>=2) slot2Mid.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(1, board.slotSize(1)-1) + ".png"));
        if(board.slotSize(2)>=2) slot3Mid.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(2, board.slotSize(2)-1) + ".png"));
        //bottom slots
        if(board.slotSize(0)==3) slot1Bot.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(0, board.slotSize(0)-2) + ".png"));
        if(board.slotSize(1)==3) slot2Bot.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(1, board.slotSize(1)-2) + ".png"));
        if(board.slotSize(2)==3) slot3Bot.get(playerNum).setImage(new Image("graphics/devCards/" + board.slotCard(2, board.slotSize(2)-2) + ".png"));
        // cross
        double[] newPosition = getCrossPosition(board.getPlayerPosition());
        if(playerNum==0) {
            cross.get(playerNum).setLayoutX(newPosition[0]);
            cross.get(playerNum).setLayoutY(newPosition[1]);
        }
        else if(playerNum==1){
            cross.get(playerNum).setLayoutX(newPosition[0]+680);
            cross.get(playerNum).setLayoutY(newPosition[1]);
        }
        else if(playerNum==2){
            cross.get(playerNum).setLayoutX(newPosition[0]);
            cross.get(playerNum).setLayoutY(newPosition[1]+380);
        }
        else if(playerNum==3){
            cross.get(playerNum).setLayoutX(newPosition[0]+680);
            cross.get(playerNum).setLayoutY(newPosition[1]+380);
        }

    }

    /**
     * used to know the X and Y positions of the redCross related to the player position
     * @param position is the position of the player
     * @return a double array with, respectively, the X and Y position of the cross
     */
    private double[] getCrossPosition(int position){
        if(position == 1){
            return new double[] {95,121};
        }
        else if(position == 2){
            return new double[] {120,121};

        }
        else if(position == 3){
            return new double[] {120,96};
        }
        else if(position == 4){
            return new double[] {118,71};
        }
        else if(position == 5){
            return new double[] {143,71};
        }
        else if(position == 6){
            return new double[] {168,71};
        }
        else if(position == 7){
            return new double[] {193,71};
        }
        else if(position == 8){
            return new double[] {218,71};
        }
        else if(position == 9){
            return new double[] {242,71};
        }
        else if(position == 10){
            return new double[] {242,96};
        }
        else if(position == 11){
            return new double[] {242,121};
        }
        else if(position == 12){
            return new double[] {267,121};
        }
        else if(position == 13){
            return new double[] {292,121};
        }
        else if(position == 14){
            return new double[] {316,121};
        }
        else if(position == 15){
            return new double[] {340,121};
        }
        else if(position == 16){
            return new double[] {365,121};
        }
        else if(position == 17){
            return new double[] {365,96};
        }
        else if(position == 18){
            return new double[] {364,71};
        }
        else if(position == 19){
            return new double[] {389,71};
        }
        else if(position == 20){
            return new double[] {414,71};
        }
        else if(position == 21){
            return new double[] {438,71};
        }
        else if(position == 22){
            return new double[] {462,71};
        }
        else if(position == 23){
            return new double[] {487,71};
        }
        else if(position == 24){
            return new double[] {512,71};
        }

        else{
            return new double[] {70,121};

        }

    }

    /**
     * used to hide the image views related to the third player if the game is of two players
     */
    private void hideThirdPlayer(){
        cross3.setVisible(false);
        board3.setVisible(false);
        player3Name.setVisible(false);
        shields3.setVisible(false);
        coins3.setVisible(false);
        stones3.setVisible(false);
        servants3.setVisible(false);
        strongboxShieldsCounterPlayer3.setVisible(false);
        strongboxServantsCounterPlayer3.setVisible(false);
        strongboxStonesCounterPlayer3.setVisible(false);
        strongboxCoinsCounterPlayer3.setVisible(false);
    }


    /**
     * used to hide the image views related to the third player if the game is of three players
     */
    private void hideFourthPlayer(){
        cross4.setVisible(false);
        board4.setVisible(false);
        player4Name.setVisible(false);
        shields4.setVisible(false);
        coins4.setVisible(false);
        stones4.setVisible(false);
        servants4.setVisible(false);
        strongboxShieldsCounterPlayer4.setVisible(false);
        strongboxServantsCounterPlayer4.setVisible(false);
        strongboxStonesCounterPlayer4.setVisible(false);
        strongboxCoinsCounterPlayer4.setVisible(false);
    }

    /**
     * used to make the depot and leader cards image views all visible.
     * they will be modified in the show player method if needed.
     */
    private void allVisible(){
        for (ImageView image : resource1Depot1) {
            image.setVisible(true);
        }

        for (ImageView image : resource1Depot2) {
            image.setVisible(true);
        }

        for (ImageView image : resource1Depot3) {
            image.setVisible(true);
        }

        for (ImageView image : resource2Depot2) {
            image.setVisible(true);
        }

        for (ImageView image : resource2Depot3) {
            image.setVisible(true);
        }

        for (ImageView image : resource3Depot3) {
            image.setVisible(true);
        }

        for (ImageView image : slot1) {
            image.setVisible(true);
        }

        for (ImageView image : slot2) {
            image.setVisible(true);
        }

        for (ImageView image : slot3) {
            image.setVisible(true);
        }

        for (ImageView image : leader1) {
            image.setVisible(true);
        }

        for (ImageView image : leader2) {
            image.setVisible(true);
        }

    }

}
