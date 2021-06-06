package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.leadercard.LeaderCard;
import it.polimi.ingsw.model.leadercard.Requirements.ResourceRequirements;
import it.polimi.ingsw.utility.LeaderCardsParserXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainBoard {
    private static ClientView clientView;
    private static GUI gui;

    @FXML
    public ImageView boardRes1;
    @FXML
    public ImageView boardRes2;
    @FXML
    public ImageView boardRes3;

    // market
    @FXML
    private Button row1MarketButton;
    @FXML
    private Button row2MarketButton;
    @FXML
    private Button row3MarketButton;
    @FXML
    private Button column1MarketButton;
    @FXML
    private Button column2MarketButton;
    @FXML
    private Button column3MarketButton;
    @FXML
    private Button column4MarketButton;

    // manage
    @FXML
    private Button manage1;
    @FXML
    private Button manage2;
    @FXML
    private Button manage3;
    @FXML
    private Button manage4;
    @FXML
    private Button trashManage;
    @FXML
    private ImageView manageImage1;
    @FXML
    private ImageView manageImage2;
    @FXML
    private ImageView manageImage3;
    @FXML
    private ImageView manageImage4;
    @FXML
    private ImageView trashManageImage;

    //marbles
    @FXML
    private ImageView marble1;
    @FXML
    private ImageView marble2;
    @FXML
    private ImageView marble3;
    @FXML
    private ImageView marble4;
    @FXML
    private ImageView marble5;
    @FXML
    private ImageView marble6;
    @FXML
    private ImageView marble7;
    @FXML
    private ImageView marble8;
    @FXML
    private ImageView marble9;
    @FXML
    private ImageView marble10;
    @FXML
    private ImageView marble11;
    @FXML
    private ImageView marble12;
    @FXML
    private ImageView marble0;

    //generic resources
    @FXML
    public Button coinChoice;
    @FXML
    public Button servantChoice;
    @FXML
    public Button shieldChoice;
    @FXML
    public Button stoneChoice;

    //leader cards
    @FXML
    private ImageView leaderCard1;
    @FXML
    private ImageView leaderCard2;
    @FXML
    private Button leaderCardOne;
    @FXML
    private Button leaderCardTwo;
    @FXML
    private Label PlayLeaderActive1;
    @FXML
    private Label PlayLeaderActive2;

    //development cards
    @FXML
    private ImageView devCard1;
    @FXML
    private ImageView devCard2;
    @FXML
    private ImageView devCard3;
    @FXML
    private ImageView devCard4;
    @FXML
    private ImageView devCard5;
    @FXML
    private ImageView devCard6;
    @FXML
    private ImageView devCard7;
    @FXML
    private ImageView devCard8;
    @FXML
    private ImageView devCard9;
    @FXML
    private ImageView devCard10;
    @FXML
    private ImageView devCard11;
    @FXML
    private ImageView devCard12;
    @FXML
    private Button devCardOne;
    @FXML
    private Button devCardTwo;
    @FXML
    private Button devCardThree;
    @FXML
    private Button devCardFour;
    @FXML
    private Button devCardFive;
    @FXML
    private Button devCardSix;
    @FXML
    private Button devCardSeven;
    @FXML
    private Button devCardEight;
    @FXML
    private Button devCardNine;
    @FXML
    private Button devCardTen;
    @FXML
    private Button devCardEleven;
    @FXML
    private Button devCardTwelve;

    //actions
    @FXML
    private Button marketAction;
    @FXML
    private Button productionAction;
    @FXML
    private Button buyCardAction;
    @FXML
    private Button playCardAction;
    @FXML
    private Button discardCardAction;
    @FXML
    private Button endTurn;

    @FXML
    private Button changeView;

    //board production
    @FXML
    public Button boardProduction;

    //strongbox
    @FXML
    private Button strongboxShields;
    @FXML
    private Label strongboxShieldsCounter;
    @FXML
    private Button strongboxCoins;
    @FXML
    private Label strongboxCoinsCounter;
    @FXML
    private Button strongboxServants;
    @FXML
    private Label strongboxServantsCounter;
    @FXML
    private Button strongboxStones;
    @FXML
    private Label strongboxStonesCounter;

    //depots
    @FXML
    private Button depotOneResourceOne;
    @FXML
    private ImageView depot1Resource1;
    @FXML
    private Button depotTwoResourceOne;
    @FXML
    private ImageView depot2Resource1;
    @FXML
    private Button depotTwoResourceTwo;
    @FXML
    private ImageView depot2Resource2;
    @FXML
    private Button depotThreeResourceOne;
    @FXML
    private ImageView depot3Resource1;
    @FXML
    private Button depotThreeResourceTwo;
    @FXML
    private ImageView depot3Resource2;
    @FXML
    private Button depotThreeResourceThree;
    @FXML
    private ImageView depot3Resource3;

    //card slots
    @FXML
    public Button devSlot1;
    @FXML
    public ImageView devSlot1Im;
    @FXML
    public Button devSlot2;
    @FXML
    public ImageView devSlot2Im;
    @FXML
    public Button devSlot3;
    @FXML
    public ImageView devSlot3Im;

    //vatican
    @FXML
    public ImageView vatican1;
    @FXML
    public ImageView vatican2;
    @FXML
    public ImageView vatican3;
    @FXML
    public ImageView redCross;
    @FXML
    public ImageView blackCross;

    @FXML
    private Label message;



    private Actions currentAction;
    private ArrayList<LeaderCard>leaderDeck = new LeaderCardsParserXML().leaderCardsParser();

    //Production attributes
    private ArrayList<Integer> prodDevSlots;
    private ArrayList<Integer> prodLeaderSlots;
    private ArrayList<Resource> leaderCardGains;
    private ArrayList<Resource> boardResProd;
    private boolean boardOrLeaderChoice;

    //Payments
    private HashMap<Resource, Integer> warehousePayment;
    private HashMap<Resource, Integer> leaderDepotPayment;
    private HashMap<Resource, Integer> strongboxPayment;
    private boolean actionDone;

    //buy dev card attributes
    private Color color;
    private int level;
    private int slot;
    Image devCardImage;

    //MARKET attributes
    private Map<Integer, ArrayList<Resource>> warehouse = new HashMap<>();
    private ArrayList<Resource> res;
    private Resource first= null;
    private int num= -1;
    private Resource second= null;
    private Resource depot1;
    private Resource[] depot2 = new Resource[2];
    private Resource[] depot3 = new Resource[3];
    private Resource[] newResources = new Resource[4];
    private Boolean isNullVolunteer = false;
    private ArrayList<Resource> discardRes = new ArrayList<>();


    public static void setGui(GUI gui) {
        MainBoard.gui = gui;
        MainBoard.clientView = gui.getClientView();
    }

    public void changeView(){
        gui.switchView();
    }

    public void update(){
        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());
        buttonStatus();
        setStrongboxCounters();
        showDepots();
        // cross
        double[] newPosition = getCrossPosition(board.getPlayerPosition());
        redCross.setLayoutX(newPosition[0]);
        redCross.setLayoutY(newPosition[1]);
        if(clientView.getGameboard().getPlayerBoards().size()==1){
            double[] newBlackPosition = getCrossPosition(board.getLorenzoPosition());
            if(board.getPlayerPosition()==board.getLorenzoPosition()) {
                blackCross.setLayoutX(newBlackPosition[0] - 10);
            }
            else{
                blackCross.setLayoutX(newBlackPosition[0]);
            }
            blackCross.setLayoutY(newBlackPosition[1]);
        }
        showVatican(board);
        MarbleColors[][] market = clientView.getGameboard().getMarket();
        String[][] grid = clientView.getGameboard().getCards();

        for(int i = 0; i<3; i++){
            if(board.getDevCardSlot().get(i)!=null) {
                Image devCardImage = new Image("/graphics/devCards/" + board.getDevCardSlot().get(i) + ".png");
                if (i == 0) devSlot1Im.setImage(devCardImage);
                if (i == 1) devSlot2Im.setImage(devCardImage);
                if (i == 2) devSlot3Im.setImage(devCardImage);
            }
        }

        for(int i=0; i<3; i++) {
            for (int j = 0; j < 4; j++) {
                Image marbleImage = new Image("/graphics/Marble/" + market[i][j].toString().toLowerCase() + ".png");
                if (grid[i][j]!=null) {
                    devCardImage = new Image("/graphics/devCards/" + grid[i][j] + ".png");
                }
                if(i==0 && j==0) {
                    marble1.setImage(marbleImage);
                    devCard1.setImage(devCardImage);
                }
                if(i==0 && j==1) {
                    marble2.setImage(marbleImage);
                    devCard2.setImage(devCardImage);
                }
                if(i==0 && j==2) {
                    marble3.setImage(marbleImage);
                    devCard3.setImage(devCardImage);
                }
                if(i==0 && j==3) {
                    marble4.setImage(marbleImage);
                    devCard4.setImage(devCardImage);
                }
                if(i==1 && j==0) {
                    marble5.setImage(marbleImage);
                    devCard5.setImage(devCardImage);
                }
                if(i==1 && j==1) {
                    marble6.setImage(marbleImage);
                    devCard6.setImage(devCardImage);
                }
                if(i==1 && j==2) {
                    marble7.setImage(marbleImage);
                    devCard7.setImage(devCardImage);
                }
                if(i==1 && j==3) {
                    marble8.setImage(marbleImage);
                    devCard8.setImage(devCardImage);
                }
                if(i==2 && j==0) {
                    marble9.setImage(marbleImage);
                    devCard9.setImage(devCardImage);
                }
                if(i==2 && j==1) {
                    marble10.setImage(marbleImage);
                    devCard10.setImage(devCardImage);
                }
                if(i==2 && j==2) {
                    marble11.setImage(marbleImage);
                    devCard11.setImage(devCardImage);
                }
                if(i==2 && j==3) {
                    marble12.setImage(marbleImage);
                    devCard12.setImage(devCardImage);
                }
            }
        }
        Image marbleImage = new Image("/graphics/Marble/" + clientView.getGameboard().getFreeMarble().toString().toLowerCase() + ".png");
        marble0.setImage(marbleImage);
        // leader cards
        if(board.getPlayedCards().size()==0) {
            if (board.getHand().size() > 0)
                leaderCard1.setImage(new Image("/graphics/leaderCards/" + board.getHand().get(0) + ".png"));
            else
                leaderCardOne.setVisible(false);
            if (board.getHand().size()  > 1)
                leaderCard2.setImage(new Image("/graphics/leaderCards/" + board.getHand().get(1) + ".png"));
            else
                leaderCardTwo.setVisible(false);
        }
        else if(board.getPlayedCards().size()==1){
            leaderCard1.setImage(new Image("/graphics/leaderCards/" + board.getPlayedCards().get(0) + ".png"));
            PlayLeaderActive1.setStyle("-fx-background-color: green; -fx-background-radius: 20;");
            PlayLeaderActive1.setText("ACTIVE");

            if(board.getHand().size() >0)
                leaderCard2.setImage(new Image("/graphics/leaderCards/" + board.getHand().get(0) + ".png"));
            else
                leaderCardTwo.setVisible(false);
        }
        else{
            leaderCard1.setImage(new Image("/graphics/leaderCards/" + board.getPlayedCards().get(0) + ".png"));
            leaderCard2.setImage(new Image("/graphics/leaderCards/" + board.getPlayedCards().get(1) + ".png"));
            PlayLeaderActive1.setStyle("-fx-background-color: green; -fx-background-radius: 20;");
            PlayLeaderActive2.setStyle("-fx-background-color: green; -fx-background-radius: 20;");
            PlayLeaderActive1.setText("ACTIVE");
            PlayLeaderActive2.setText("ACTIVE");
        }
    }

    /**
     * reset the button to End Turn style
     */
    public void resetEndButton(){
        endTurn.setStyle("-fx-background-color: red; -fx-background-radius: 20;");
        endTurn.setText("END TURN");
    }
    /**
     * change the button to Done style
     */
    public void changeEndButton(){
        if(endTurn.getText().equals("END TURN")){
            endTurn.setStyle("-fx-background-color: green; -fx-background-radius: 20;");
            endTurn.setText("DONE");
        }
    }

    /**
     * used to know the X and Y positions of the redCross related to the player position
     * @param position is the position of the player
     * @return a double array with, respectively, the X and Y position of the cross
     */
    private double[] getCrossPosition(int position) {
        if (position == 1) {
            return new double[]{715, 350};
        } else if (position == 2) {
            return new double[]{750, 350};
        } else if (position == 3) {
            return new double[]{750, 316};
        } else if (position == 4) {
            return new double[]{748, 283};
        } else if (position == 5) {
            return new double[]{783, 283};
        } else if (position == 6) {
            return new double[]{818, 283};
        } else if (position == 7) {
            return new double[]{850, 283};
        } else if (position == 8) {
            return new double[]{885, 283};
        } else if (position == 9) {
            return new double[]{920, 283};
        } else if (position == 10) {
            return new double[]{920, 316};
        } else if (position == 11) {
            return new double[]{920, 350};
        } else if (position == 12) {
            return new double[]{955, 350};
        } else if (position == 13) {
            return new double[]{990, 350};
        } else if (position == 14) {
            return new double[]{1023, 350};
        } else if (position == 15) {
            return new double[]{1058, 350};
        } else if (position == 16) {
            return new double[]{1092,350 };
        } else if (position == 17) {
            return new double[]{1092,316 };
        } else if (position == 18) {
            return new double[]{1092, 283};
        } else if (position == 19) {
            return new double[]{1126, 283};
        } else if (position == 20) {
            return new double[]{1161, 283};
        } else if (position == 21) {
            return new double[]{1196, 283};
        } else if (position == 22) {
            return new double[]{1228, 283};
        } else if (position == 23) {
            return new double[]{1262, 283};
        } else if (position == 24) {
            return new double[]{1296, 283};
        } else {
            return new double[]{680, 350};

        }
    }

    private void showVatican(ClientPlayerBoard board){
        if(board.getVaticanReports().containsKey(8)) {
            if (board.getVaticanReports().get(8))
                vatican1.setImage(new Image("resources/punchboard/vatican1Active.png"));
            else
                vatican1.setVisible(false);
        }
        else {
            vatican1.setImage(new Image("resources/punchboard/vatican1.png"));
        }
        if(board.getVaticanReports().containsKey(16)) {
            if (board.getVaticanReports().get(16))
                vatican2.setImage(new Image("resources/punchboard/vatican2Active.png"));
            else
                vatican2.setVisible(false);
        }
        else {
            vatican2.setImage(new Image("resources/punchboard/vatican2.png"));
        }
        if(board.getVaticanReports().containsKey(24)) {
            if (board.getVaticanReports().get(24))
                vatican3.setImage(new Image("resources/punchboard/vatican3Active.png"));
            else
                vatican3.setVisible(false);
        }
        else {
            vatican3.setImage(new Image("resources/punchboard/vatican3.png"));
        }
    }

    private void showDepots(){
        depot1Resource1.setVisible(true);
        depot2Resource1.setVisible(true);
        depot2Resource2.setVisible(true);
        depot3Resource1.setVisible(true);
        depot3Resource2.setVisible(true);
        depot3Resource3.setVisible(true);
        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());
        if(board.isDepotEmpty(0))
            depot1Resource1.setVisible(false);
        else
            depot1Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(0).toString().toLowerCase() + ".png"));
        if(board.isDepotEmpty(1)){
            depot2Resource1.setVisible(false);
            depot2Resource2.setVisible(false);
        }
        else{
            if (board.getWarehouse().get(1).size() == 2) {
                depot2Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
                depot2Resource2.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
            }
            else{
                depot2Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
                depot2Resource2.setVisible(false);
            }
        }
        if(board.isDepotEmpty(2)){
            depot3Resource1.setVisible(false);
            depot3Resource2.setVisible(false);
            depot3Resource3.setVisible(false);
        }
        else{
            if (board.getWarehouse().get(2).size() == 3) {
                depot3Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                depot3Resource2.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                depot3Resource3.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
            }
            else if(board.getWarehouse().get(2).size() == 2){
                depot3Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                depot3Resource2.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                depot3Resource3.setVisible(false);
            }
            else {
                depot3Resource1.setImage(new Image("resources/punchboard/" + board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                depot3Resource2.setVisible(false);
                depot3Resource3.setVisible(false);
            }
        }
        //disable all
    }

    private void setStrongboxCounters(){
        // to do -> disable the buttons
        strongboxShieldsCounter.setText(String.valueOf(clientView.getMyStrongbox().get(Resource.SHIELDS)));
        strongboxServantsCounter.setText(String.valueOf(clientView.getMyStrongbox().get(Resource.SERVANTS)));
        strongboxStonesCounter.setText(String.valueOf(clientView.getMyStrongbox().get(Resource.STONES)));
        strongboxCoinsCounter.setText(String.valueOf(clientView.getMyStrongbox().get(Resource.COINS)));
    }

    /**
     * utility method to disable and make invisible a button
     * @param b button to disable/enable
     * @param status true to disable the button, false to enable it
     */
    private void disableButton(Button b, boolean status){
        b.setVisible(!status);
        b.setDisable(status);
    }

    /**
     * used whenever needed to reset all the button in a disabled state
     * to make a new action
     */
    private void buttonStatus(){
        // message label
        message.setVisible(false);
        //change view
        if(clientView.getGameboard().getPlayerBoards().size()>1) {
            changeView.setVisible(true);
            blackCross.setVisible(false);
        }
        else {
            changeView.setVisible(false);
            blackCross.setVisible(true);
        }
        // actions button
        marketAction.setVisible(false);
        marketAction.setDisable(true);
        productionAction.setVisible(false);
        productionAction.setDisable(true);
        buyCardAction.setVisible(false);
        buyCardAction.setDisable(true);
        playCardAction.setVisible(false);
        playCardAction.setDisable(true);
        discardCardAction.setVisible(false);
        discardCardAction.setDisable(true);
        endTurn.setVisible(false);
        endTurn.setDisable(true);
        // generic resource button
        disableButton(coinChoice, true);
        disableButton(stoneChoice, true);
        disableButton(servantChoice, true);
        disableButton(shieldChoice, true);
        // leader buttons
        leaderCardOne.setMouseTransparent(true);
        leaderCardTwo.setMouseTransparent(true);
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHandSize()==1 && clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getPlayedCards().size()==0)
            leaderCardTwo.setVisible(false);
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHandSize()==0 && clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getPlayedCards().size()==0) {
            leaderCardOne.setVisible(false);
            leaderCardTwo.setVisible(false);
        }
        // dev grid buttons
        devCardOne.setMouseTransparent(true);
        devCardTwo.setMouseTransparent(true);
        devCardThree.setMouseTransparent(true);
        devCardFour.setMouseTransparent(true);
        devCardFive.setMouseTransparent(true);
        devCardSix.setMouseTransparent(true);
        devCardSeven.setMouseTransparent(true);
        devCardEight.setMouseTransparent(true);
        devCardNine.setMouseTransparent(true);
        devCardTen.setMouseTransparent(true);
        devCardEleven.setMouseTransparent(true);
        devCardTwelve.setMouseTransparent(true);



        String[][] grid = clientView.getGameboard().getCards();
        if(grid[0][0] == null){
            devCardOne.setMouseTransparent(false);
        }
        if(grid[0][1] == null){
            devCardTwo.setVisible(false);
        }
        if(grid[0][2] == null){
            devCardThree.setVisible(false);
        }
        if(grid[0][3] == null){
            devCardFour.setVisible(false);
        }
        if(grid[1][0] == null){
            devCardFive.setVisible(false);
        }
        if(grid[1][1] == null){
            devCardSix.setVisible(false);
        }
        if(grid[1][2] == null){
            devCardSeven.setVisible(false);
        }
        if(grid[1][3] == null){
            devCardEight.setVisible(false);
        }
        if(grid[2][0] == null){
            devCardNine.setVisible(false);
        }
        if(grid[2][1] == null){
            devCardTen.setVisible(false);
        }
        if(grid[2][2] == null){
            devCardEleven.setVisible(false);
        }
        if(grid[2][3] == null){
            devCardTwelve.setVisible(false);
        }


        //
        // board production button
        disableButton(boardProduction, true);
        // dev slots buttons
        devSlot1.setMouseTransparent(true);
        devSlot2.setMouseTransparent(true);
        devSlot3.setMouseTransparent(true);
        // depot buttons
        depotOneResourceOne.setDisable(false);
        depotOneResourceOne.setMouseTransparent(true);
        depotTwoResourceOne.setDisable(false);
        depotTwoResourceOne.setMouseTransparent(true);
        depotTwoResourceTwo.setDisable(false);
        depotTwoResourceTwo.setMouseTransparent(true);
        depotThreeResourceOne.setDisable(false);
        depotThreeResourceOne.setMouseTransparent(true);
        depotThreeResourceTwo.setDisable(false);
        depotThreeResourceTwo.setMouseTransparent(true);
        depotThreeResourceThree.setDisable(false);
        depotThreeResourceThree.setMouseTransparent(true);
        // strongbox buttons
        strongboxCoins.setDisable(false);
        strongboxCoins.setMouseTransparent(true);
        strongboxServants.setDisable(false);
        strongboxServants.setMouseTransparent(true);
        strongboxShields.setDisable(false);
        strongboxShields.setMouseTransparent(true);
        strongboxStones.setDisable(false);
        strongboxStones.setMouseTransparent(true);
        //market buttons
        manage1.setDisable(true);
        manage2.setDisable(true);
        manage3.setDisable(true);
        manage4.setDisable(true);
        manage1.setVisible(false);
        manage2.setVisible(false);
        manage3.setVisible(false);
        manage4.setVisible(false);
        disableMarketButtons();
        trashManage.setVisible(false);
        trashManage.setDisable(true);
    }

    /**
     * updates the message
     * @param text new message
     */
    public void setMessage(String text){
        message.setText(text);
        message.setVisible(true);
    }

    public void showActions(ArrayList<Actions> actions){
        if(actions.contains(Actions.USEPRODUCTION)) {
            productionAction.setVisible(true);
            productionAction.setDisable(false);
        }
        if(actions.contains(Actions.BUYDEVELOPMENTCARD)) {
            buyCardAction.setVisible(true);
            buyCardAction.setDisable(false);
        }
        if(actions.contains(Actions.MARKETACTION)) {
            marketAction.setVisible(true);
            marketAction.setDisable(false);
        }
        if(actions.contains(Actions.PLAYLEADERCARD)) {
            playCardAction.setVisible(true);
            playCardAction.setDisable(false);
        }
        if(actions.contains(Actions.DISCARDLEADERCARD)) {
            discardCardAction.setVisible(true);
            discardCardAction.setDisable(false);
        }
        if(actions.contains(Actions.ENDTURN)) {
            endTurn.setVisible(true);
            endTurn.setDisable(false);
        }
    }

    /**
     * called to update the buttons when performing the production action
     */
    private void productionButtonsUpdate(){
        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());

        buttonStatus();
        setMessage("Select what you want to use for the production action");
        disableButton(endTurn, false);
        changeEndButton();

        if(board.getDevCardSlot().get(0)!=null && !prodDevSlots.contains(0)) devSlot1.setMouseTransparent(false);
        if(board.getDevCardSlot().get(1)!=null && !prodDevSlots.contains(1)) devSlot2.setMouseTransparent(false);
        if(board.getDevCardSlot().get(2)!=null && !prodDevSlots.contains(2)) devSlot3.setMouseTransparent(false);
        if(boardResProd.isEmpty()){
            disableButton(boardProduction, false);
        }
        if(!board.getPlayedCards().isEmpty()){
            if(!prodLeaderSlots.contains(0) && board.getProductionBuff().containsKey(board.getPlayedCards().get(0))){
                leaderCardOne.setMouseTransparent(false);
            }
            if(!prodLeaderSlots.contains(1) && board.getPlayedCards().size()>1 && board.getProductionBuff().containsKey(board.getPlayedCards().get(1))){
                leaderCardTwo.setMouseTransparent(false);
            }
        }
    }

    /**
     * called to prepare attributes and button when starting the action
     */
    public void productionAction(){
        prodDevSlots = new ArrayList<>();
        prodLeaderSlots = new ArrayList<>();
        leaderCardGains = new ArrayList<>();
        boardResProd = new ArrayList<>();
        actionDone=false;
        currentAction = Actions.USEPRODUCTION;
        productionButtonsUpdate();
    }

    /**
     * called to update the button status to start the payment phase
     */
    public void startPayment(){
        warehousePayment = new HashMap<>();
        leaderDepotPayment = new HashMap<>();
        strongboxPayment = new HashMap<>();

        buttonStatus();
        disableButton(endTurn, false);
        changeEndButton();
        if(currentAction == Actions.BUYDEVELOPMENTCARD)
            setMessage("You have selected the slot number "+slot+". Now choose the resources you want to use to pay.");
        else if(currentAction == Actions.USEPRODUCTION)
            setMessage("Choose the resources you want to use to pay.");


        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());
        if(!board.getWarehouse().get(0).isEmpty()){
            depotOneResourceOne.setMouseTransparent(false);
        }

        if(board.getWarehouse().get(1).size()==1){
            depotTwoResourceOne.setMouseTransparent(false);
        }
        else if(board.getWarehouse().get(1).size()==2){
            depotTwoResourceOne.setMouseTransparent(false);
            depotTwoResourceTwo.setMouseTransparent(false);
        }

        if(board.getWarehouse().get(2).size()==1){
            depotThreeResourceOne.setMouseTransparent(false);
        }
        else if(board.getWarehouse().get(2).size()==2){
            depotThreeResourceOne.setMouseTransparent(false);
            depotThreeResourceTwo.setMouseTransparent(false);
        }
        else if(board.getWarehouse().get(2).size()==3){
            depotThreeResourceOne.setMouseTransparent(false);
            depotThreeResourceTwo.setMouseTransparent(false);
            depotThreeResourceThree.setMouseTransparent(false);
        }

        if(board.getStrongbox().get(Resource.COINS) > 0) strongboxCoins.setMouseTransparent(false);
        if(board.getStrongbox().get(Resource.SERVANTS) > 0) strongboxServants.setMouseTransparent(false);
        if(board.getStrongbox().get(Resource.STONES) > 0) strongboxStones.setMouseTransparent(false);
        if(board.getStrongbox().get(Resource.SHIELDS) > 0) strongboxShields.setMouseTransparent(false);

    }

    /**
     * if the action is a Use Production the slot is added to the array of the slot production.
     * Otherwise the action must be a buy development card and the slot is saved,
     * the button status and the start payment methods are called
     */
    public void slot1Action() {
        if(currentAction == Actions.USEPRODUCTION){
            prodDevSlots.add(0);
            devSlot1.setDisable(true);
        }
        else{
            slot = 0;
            buttonStatus();
            startPayment();
        }
    }

    /**
     * if the action is a Use Production the slot is added to the array of the slot production.
     * Otherwise the action must be a buy development card and the slot is saved,
     * the button status and the start payment methods are called
     */
    public void slot2Action() {
        if(currentAction == Actions.USEPRODUCTION){
            prodDevSlots.add(1);
            devSlot2.setDisable(true);
        }
        else{
            slot = 1;
            buttonStatus();
            startPayment();
        }
    }

    /**
     * if the action is a Use Production the slot is added to the array of the slot production.
     * Otherwise the action must be a buy development card and the slot is saved,
     * the button status and the start payment methods are called
     */
    public void slot3Action() {
        if(currentAction == Actions.USEPRODUCTION){
            prodDevSlots.add(2);
            devSlot3.setDisable(true);
        }
        else{
            slot = 2;
            buttonStatus();
            startPayment();
        }
    }

    /**
     * begins the selection of what resources the players wants to pay/obtain for the board production
     */
    public void useBoardProd() {
        buttonStatus();
        setMessage("Select 2 resources that you want to pay and 1 resource that you want to gain");
        boardProduction.setDisable(true);
        boardOrLeaderChoice=true;
        disableButton(coinChoice, false);
        coinChoice.setStyle("-fx-background-color: #C8C5C5; ");
        disableButton(stoneChoice, false);
        stoneChoice.setStyle("-fx-background-color: #C8C5C5; ");
        disableButton(servantChoice, false);
        servantChoice.setStyle("-fx-background-color: #C8C5C5; ");
        disableButton(shieldChoice, false);
        shieldChoice.setStyle("-fx-background-color: #C8C5C5; ");
    }

    /**
     * if USEPRODUCTION adds the card to the production leader card index and begins the selection of what resources the players wants to obtain
     * if Discard Leader Card action the message with the leader card that the user wants to discard is sent.
     */
    public void leaderOneAction() {
        if(currentAction == Actions.USEPRODUCTION){
            boardOrLeaderChoice=false;
            prodLeaderSlots.add(0);
            buttonStatus();
            setMessage("Select what resource you want to gain");
            disableButton(coinChoice, false);
            disableButton(stoneChoice, false);
            disableButton(servantChoice, false);
            disableButton(shieldChoice, false);
        }
        else if(currentAction == Actions.DISCARDLEADERCARD){
            clientView.discardLeaderCard(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().get(0));
            buttonStatus();
        }
        else if (currentAction == Actions.PLAYLEADERCARD){
            doPlayCard(0);
        }
    }

    /**
     * if USEPRODUCTION adds the card to the production leader card slots and begins the selection of what resources the players wants to obtain
     * if Discard Leader Card action the message with the leader card that the user wants to discard is sent.
     */
    public void leaderTwoAction() {
        if(currentAction == Actions.USEPRODUCTION){
            boardOrLeaderChoice=false;
            if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getProductionBuff().size()==1) prodLeaderSlots.add(0);
            else prodLeaderSlots.add(1);
            buttonStatus();
            setMessage("Select what resource you want to gain");
            disableButton(coinChoice, false);
            disableButton(stoneChoice, false);
            disableButton(servantChoice, false);
            disableButton(shieldChoice, false);
        }
        else if(currentAction == Actions.DISCARDLEADERCARD){
            if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHandSize()==2)
                clientView.discardLeaderCard(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().get(1));
            else
                clientView.discardLeaderCard(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().get(0));
            buttonStatus();
        }
        else if (currentAction == Actions.PLAYLEADERCARD){
            if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHandSize()==2)
                doPlayCard(1);
            else
                doPlayCard(0);
        }
    }

    /**
     * adds one shield to strongboxPayment
     */
    public void strShieldsAction() {
        if(strongboxPayment.containsKey(Resource.SHIELDS)) strongboxPayment.put(Resource.SHIELDS, strongboxPayment.get(Resource.SHIELDS)+1);
        else strongboxPayment.put(Resource.SHIELDS, 1);
        int diff = clientView.getMyStrongbox().get(Resource.SHIELDS)-strongboxPayment.get(Resource.SHIELDS);
        strongboxStonesCounter.setText(String.valueOf(diff));
        if(diff==0) {
            strongboxShields.setDisable(true);
            strongboxShields.setMouseTransparent(true);
        }
    }

    /**
     * adds one coin to strongboxPayment
     */
    public void strCoinsAction() {
        if(strongboxPayment.containsKey(Resource.COINS)) strongboxPayment.put(Resource.COINS, strongboxPayment.get(Resource.COINS)+1);
        else strongboxPayment.put(Resource.COINS, 1);
        int diff = clientView.getMyStrongbox().get(Resource.COINS)-strongboxPayment.get(Resource.COINS);
        strongboxStonesCounter.setText(String.valueOf(diff));
        if(diff==0) {
            strongboxCoins.setDisable(true);
            strongboxCoins.setMouseTransparent(true);
        }
    }

    /**
     * adds one servant to strongboxPayment
     */
    public void strServantsAction() {
        if(strongboxPayment.containsKey(Resource.SERVANTS)) strongboxPayment.put(Resource.SERVANTS, strongboxPayment.get(Resource.SERVANTS)+1);
        else strongboxPayment.put(Resource.SERVANTS, 1);
        int diff = clientView.getMyStrongbox().get(Resource.SERVANTS)-strongboxPayment.get(Resource.SERVANTS);
        strongboxStonesCounter.setText(String.valueOf(diff));
        if(diff==0) {
            strongboxServants.setDisable(true);
            strongboxServants.setMouseTransparent(true);
        }
    }

    /**
     * adds one stone to strongboxPayment
     */
    public void strStonesAction() {
        if(strongboxPayment.containsKey(Resource.STONES)) strongboxPayment.put(Resource.STONES, strongboxPayment.get(Resource.STONES)+1);
        else strongboxPayment.put(Resource.STONES, 1);
        int diff = clientView.getMyStrongbox().get(Resource.STONES)-strongboxPayment.get(Resource.STONES);
        strongboxStonesCounter.setText(String.valueOf(diff));
        if(diff==0) {
            strongboxStones.setDisable(true);
            strongboxStones.setMouseTransparent(true);
        }
    }

    public void depotOneResourceOneAction() {
        if (currentAction==Actions.MANAGE){
            if (first==null) {
                if (!isNullVolunteer){
                    first = depot1;
                    depot1Resource1.setVisible(true);
                    num = 5;
                    if (first==null) isNullVolunteer = true;
                }
                else{
                    second = depot1;
                    depot1Resource1.setImage(null);
                    depot1Resource1.setVisible(false);
                    depot1=null;
                    isNullVolunteer=false;
                    setSecondResource();
                }
            }
            else {
                second = depot1;
                depot1Resource1.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
                depot1Resource1.setVisible(true);
                depot1 = first;
                first = null;
                setSecondResource();
            }
        }
        else{
            Resource depotRss = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouseResource(0);
            if(warehousePayment.containsKey(depotRss))
                warehousePayment.put(depotRss, warehousePayment.get(depotRss)+1);
            else warehousePayment.put(depotRss, 1);

            depotOneResourceOne.setDisable(true);
            depotOneResourceOne.setMouseTransparent(true);
        }
    }

    public void depotTwoResourceOneAction() {
        if (currentAction==Actions.MANAGE){
            if (first==null) {
                if (!isNullVolunteer){
                    first = depot2[0];
                    depot2Resource1.setVisible(true);
                    num = 6;
                    if (first==null) isNullVolunteer = true;
                }
                else{
                    second = depot2[0];
                    depot2Resource1.setImage(null);
                    depot2[0] = null;
                    isNullVolunteer = false;
                    setSecondResource();
                }
            }
            else {
                second = depot2[0];
                depot2Resource1.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
                depot2Resource1.setVisible(true);
                depot2[0] = first;
                first = null;
                setSecondResource();
            }

        }
        else{
            Resource depotRss = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouseResource(1);
            if(warehousePayment.containsKey(depotRss))
                warehousePayment.put(depotRss, warehousePayment.get(depotRss)+1);
            else warehousePayment.put(depotRss, 1);

            depotTwoResourceOne.setDisable(true);
            depotTwoResourceOne.setMouseTransparent(true);
        }
    }

    public void depotTwoResourceTwoAction() {
        if (currentAction==Actions.MANAGE){
            if (first==null) {
                if (!isNullVolunteer){
                    first = depot2[1];
                    depot2Resource2.setVisible(true);
                    num = 7;
                    if (first==null) isNullVolunteer = true;
                }
                else{
                    second = depot2[1];
                    depot2Resource2.setImage(null);
                    depot2[1] = null;
                    isNullVolunteer = false;
                    setSecondResource();
                }
            }
            else {
                second = depot2[1];
                depot2Resource2.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
                depot2Resource2.setVisible(true);
                depot2[1] = first;
                first = null;
                setSecondResource();
            }

        }
        else {
            Resource depotRss = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouseResource(1);
            if(warehousePayment.containsKey(depotRss))
                warehousePayment.put(depotRss, warehousePayment.get(depotRss)+1);
            else warehousePayment.put(depotRss, 1);

            depotTwoResourceTwo.setDisable(true);
            depotTwoResourceTwo.setMouseTransparent(true);
        }
    }

    public void depotThreeResourceOneAction() {
        if (currentAction==Actions.MANAGE){
            if (first==null) {
                if (!isNullVolunteer){
                    first = depot3[0];
                    depot3Resource1.setVisible(true);
                    num = 8;
                    if (first==null) isNullVolunteer = true;
                }
                else{
                    second = depot3[0];
                    depot3Resource1.setImage(null);
                    depot3[0] = null;
                    isNullVolunteer = false;
                    setSecondResource();
                }
            }
            else {
                second = depot3[0];
                depot3Resource1.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
                depot3Resource1.setVisible(true);
                depot3[0] = first;
                first = null;
                setSecondResource();
            }
        }
        else {
            Resource depotRss = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouseResource(2);
            if(warehousePayment.containsKey(depotRss))
                warehousePayment.put(depotRss, warehousePayment.get(depotRss)+1);
            else warehousePayment.put(depotRss, 1);

            depotThreeResourceOne.setDisable(true);
            depotThreeResourceOne.setMouseTransparent(true);
        }
    }

    public void depotThreeResourceTwoAction() {
        if (currentAction==Actions.MANAGE){
            if (first==null) {
                if (!isNullVolunteer){
                    first = depot3[1];
                    depot3Resource2.setVisible(true);
                    num = 9;
                    if (first==null) isNullVolunteer = true;
                }
                else{
                    second = depot3[1];
                    depot3Resource2.setImage(null);
                    depot3[1] = null;
                    isNullVolunteer = false;
                    setSecondResource();
                }
            }
            else {
                second = depot3[1];
                depot3Resource2.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
                depot3Resource2.setVisible(true);
                depot3[1] = first;
                first = null;
                setSecondResource();
            }
        }
        else {
            Resource depotRss = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouseResource(2);
            if(warehousePayment.containsKey(depotRss))
                warehousePayment.put(depotRss, warehousePayment.get(depotRss)+1);
            else warehousePayment.put(depotRss, 1);

            depotThreeResourceTwo.setDisable(true);
            depotThreeResourceTwo.setMouseTransparent(true);
        }
    }

    public void depotThreeResourceThreeAction() {
        if (currentAction==Actions.MANAGE){
            if (first==null) {
                if (!isNullVolunteer){
                    first = depot3[2];
                    depot3Resource3.setVisible(true);
                    num = 10;
                    if (first==null) isNullVolunteer = true;
                }
                else{
                    second = depot3[2];
                    depot3Resource3.setImage(null);
                    depot3[2] = null;
                    isNullVolunteer = false;
                    setSecondResource();
                }
            }
            else {
                second = depot3[2];
                depot3Resource3.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
                depot3Resource3.setVisible(true);
                depot3[2] = first;
                first = null;
                setSecondResource();
            }
        }
        else {
            Resource depotRss = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouseResource(2);
            if(warehousePayment.containsKey(depotRss))
                warehousePayment.put(depotRss, warehousePayment.get(depotRss)+1);
            else warehousePayment.put(depotRss, 1);

            depotThreeResourceThree.setDisable(true);
            depotThreeResourceThree.setMouseTransparent(true);
        }
    }

    /**
     * does the the correct methods base on what is the current action
     */
    public void endOrDoneButtonAction() {
        if(currentAction == Actions.USEPRODUCTION && !actionDone){
            actionDone = true;
            startPayment();
        }
        else if(currentAction == Actions.USEPRODUCTION){
            clientView.useProduction(prodDevSlots, prodLeaderSlots, leaderCardGains, boardResProd, warehousePayment, leaderDepotPayment, strongboxPayment);
            devSlot1.setDisable(false);
            devSlot2.setDisable(false);
            devSlot3.setDisable(false);
            boardRes1.setVisible(false);
            boardRes2.setVisible(false);
            boardRes3.setVisible(false);
            boardProduction.setDisable(false);
            resetEndButton();
            buttonStatus();
            currentAction = null;
        }
        else if(currentAction == Actions.BUYDEVELOPMENTCARD){
            clientView.buyDevCard(color, level, slot, warehousePayment, leaderDepotPayment, strongboxPayment);
            resetEndButton();
            buttonStatus();
            currentAction = null;
        }
        else if (currentAction == Actions.MANAGE){
            manageActionDone();
            resetEndButton();
            buttonStatus();
            currentAction = null;

        }
        else{
            clientView.sendAction(Actions.ENDTURN);
            buttonStatus();
        }

    }

    /**
     * calls the client view method to inform the server that the player wants to perform the production action
     */
    public void sendProduction() {
        clientView.sendAction(Actions.USEPRODUCTION);
    }

    /**
     * ends the selection of a "?" resource
     */
    private void endSelection(){
        if(!boardOrLeaderChoice || boardResProd.size() == 3){
            productionButtonsUpdate();
        }
    }

    private void placeBoardProdRss(Resource rss){
        if(boardResProd.size()==1) {
            boardRes1.setImage(new Image("resources/punchboard/"+ rss.toString().toLowerCase() +".png"));
            boardRes1.setVisible(true);
        }
        else if(boardResProd.size()==2) {
            boardRes2.setImage(new Image("resources/punchboard/"+ rss.toString().toLowerCase() +".png"));
            boardRes2.setVisible(true);
        }
        else if(boardResProd.size()==3) {
            boardRes3.setImage(new Image("resources/punchboard/"+ rss.toString().toLowerCase() +".png"));
            boardRes3.setVisible(true);
        }
    }

    /**
     * adds 1 coin to the corresponding production list
     */
    public void pickCoin() {
        if(boardOrLeaderChoice) {
            boardResProd.add(Resource.COINS);
            placeBoardProdRss(Resource.COINS);
        }
        else leaderCardGains.add(Resource.COINS);
        endSelection();
    }

    /**
     * adds 1 servant to the corresponding production list
     */
    public void pickServant() {
        if(boardOrLeaderChoice) {
            boardResProd.add(Resource.SERVANTS);
            placeBoardProdRss(Resource.SERVANTS);
        }
        else leaderCardGains.add(Resource.SERVANTS);
        endSelection();
    }

    /**
     * adds 1 shield to the corresponding production list
     */
    public void pickShield() {
        if(boardOrLeaderChoice){
            boardResProd.add(Resource.SHIELDS);
            placeBoardProdRss(Resource.SHIELDS);
        }
        else leaderCardGains.add(Resource.SHIELDS);
        endSelection();
    }

    /**
     * adds 1 stone to the corresponding production list
     */
    public void pickStone() {
        if(boardOrLeaderChoice) {
            boardResProd.add(Resource.STONES);
            placeBoardProdRss(Resource.STONES);
        }
        else leaderCardGains.add(Resource.STONES);
        endSelection();
    }

    // ---------- BUY DEV CARD ACTION ----------

    /**
     * called when the user click the buy development card action from GUI
     * it sends the message to the servers and tells the users its choice
     */
    public void buyDevCardSelection(){
        clientView.sendAction(Actions.BUYDEVELOPMENTCARD);
        buttonStatus();
        setMessage("You have choosen the buy development card action, please select one card");
        //disable other action buttons
    }

    /**
     * used to enable only the allowed button.
     * if a deck of the grid is empty the button won't be enabled
     */
    public void buyDevCardAction(){
        currentAction = Actions.BUYDEVELOPMENTCARD;
        String[][] grid = clientView.getGameboard().getCards();
        if(grid[0][0] != null){
            devCardOne.setMouseTransparent(false);
        }
        if(grid[0][1] != null){
            devCardTwo.setMouseTransparent(false);
        }
        if(grid[0][2] != null){
            devCardThree.setMouseTransparent(false);
        }
        if(grid[0][3] != null){
            devCardFour.setMouseTransparent(false);
        }
        if(grid[1][0] != null){
            devCardFive.setMouseTransparent(false);
        }
        if(grid[1][1] != null){
            devCardSix.setMouseTransparent(false);
        }
        if(grid[1][2] != null){
            devCardSeven.setMouseTransparent(false);
        }
        if(grid[1][3] != null){
            devCardEight.setMouseTransparent(false);
        }
        if(grid[2][0] != null){
            devCardNine.setMouseTransparent(false);
        }
        if(grid[2][1] != null){
            devCardTen.setMouseTransparent(false);
        }
        if(grid[2][2] != null){
            devCardEleven.setMouseTransparent(false);
        }
        if(grid[2][3] != null){
            devCardTwelve.setMouseTransparent(false);
        }
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev1Action(){
        color = Color.PURPLE;
        level = 3;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev2Action(){
        color = Color.YELLOW;
        level = 3;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev3Action(){
        color = Color.GREEN;
        level = 3;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev4Action(){
        color = Color.BLUE;
        level = 3;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev5Action(){
        color = Color.PURPLE;
        level = 2;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev6Action(){
        color = Color.YELLOW;
        level = 2;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev7Action(){
        color = Color.GREEN;
        level = 2;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev8Action(){
        color = Color.BLUE;
        level = 2;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev9Action(){
        color = Color.PURPLE;
        level = 1;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev10Action(){
        color = Color.YELLOW;
        level = 1;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev11Action(){
        color = Color.GREEN;
        level = 1;
        buttonStatus();
        enableSlot();
    }

    /**
     * set the color and level of the card the user wants to buy, related to its position
     * it also resets the button status and call the method for the next phase
     */
    public void dev12Action(){
        color = Color.BLUE;
        level = 1;
        buttonStatus();
        enableSlot();
    }

    /**
     * used to enable the buttons of the development card slots
     */
    public void enableSlot(){
        setMessage("You have selected the " + color.toString().toLowerCase() + " card of level "+ level+". Now please select one slot.");
        devSlot1.setMouseTransparent(false);
        devSlot2.setMouseTransparent(false);
        devSlot3.setMouseTransparent(false);
    }

    // ---------- DISCARD LEADER CARD ACTION ----------


    /**
     * used to send the message to the server about the selected action
     */
    public void discardLeaderAction(){
        clientView.sendAction(Actions.DISCARDLEADERCARD);
        buttonStatus();
        setMessage("You have chosen the discard leader card action, please select one card");
        //disable other action buttons
    }

    /**
     * used to enable the needed button for the discard leader card action
     */
    public void discardAction(){
        currentAction=Actions.DISCARDLEADERCARD;
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getPlayedCards().size()==1)
            leaderCardTwo.setMouseTransparent(false);
        else{
            leaderCardOne.setMouseTransparent(false);
            leaderCardTwo.setMouseTransparent(false);
        }
    }

    // ---------- PLAY LEADER CARD ACTION ----------

    /**
     * used to send the message to the server about the selected action
     */
    public void playLeaderCard(){
        clientView.sendAction(Actions.PLAYLEADERCARD);
        buttonStatus();
        setMessage("\"You have chosen the play leader card action, please select one card\"");
    }

    /**
     * used to enable the needed button for the play leader card action
     */
    public void playLeaderAction(){
        currentAction = Actions.PLAYLEADERCARD;
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getPlayedCards().size()==1)
            leaderCardTwo.setMouseTransparent(false);
        else{
            leaderCardOne.setMouseTransparent(false);
            leaderCardTwo.setMouseTransparent(false);
        }
    }

    private void doPlayCard(int cardSlot) {
        String id = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().get(cardSlot);
        buttonStatus();
        clientView.playLeaderCard(id);
    }



    // ---------- MARKET ACTION ----------

    public void marketAction(){
        clientView.sendAction(Actions.MARKETACTION);
        disableActions();
        setMessage("pick a row or column in the market");
    }

    public void doMarketAction(){
        activeMarketButtons();

    }

    private void activeMarketButtons(){
        row1MarketButton.setDisable(false);
        row2MarketButton.setDisable(false);
        row3MarketButton.setDisable(false);
        column1MarketButton.setDisable(false);
        column2MarketButton.setDisable(false);
        column3MarketButton.setDisable(false);
        column4MarketButton.setDisable(false);
        row1MarketButton.setVisible(true);
        row2MarketButton.setVisible(true);
        row3MarketButton.setVisible(true);
        column1MarketButton.setVisible(true);
        column2MarketButton.setVisible(true);
        column3MarketButton.setVisible(true);
        column4MarketButton.setVisible(true);
    }

    private void disableMarketButtons(){
        row1MarketButton.setDisable(true);
        row2MarketButton.setDisable(true);
        row3MarketButton.setDisable(true);
        column1MarketButton.setDisable(true);
        column2MarketButton.setDisable(true);
        column3MarketButton.setDisable(true);
        column4MarketButton.setDisable(true);
        row1MarketButton.setVisible(false);
        row2MarketButton.setVisible(false);
        row3MarketButton.setVisible(false);
        column1MarketButton.setVisible(false);
        column2MarketButton.setVisible(false);
        column3MarketButton.setVisible(false);
        column4MarketButton.setVisible(false);

    }

    public void row1MarketButton(){
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(0, true,resource);
        disableMarketButtons();
    }
    public void row2MarketButton(){
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(1, true,resource);
        disableMarketButtons();
    }
    public void row3MarketButton(){
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(2, true,resource);
        disableMarketButtons();
    }
    public void column1MarketButton(){
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(0, false,resource);
        disableMarketButtons();
    }
    public void column2MarketButton() {
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(1, false, resource);
        disableMarketButtons();
        marketAction.setDisable(true);
        marketAction.setVisible(false);
    }
    public void column3MarketButton(){
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(2, false,resource);
        disableMarketButtons();
    }
    public void column4MarketButton(){
        ArrayList<Resource> resource = new ArrayList<>();
        clientView.marketAction(3, false,resource);
        disableMarketButtons();
    }

    public void disableActions(){
        marketAction.setDisable(true);
        marketAction.setVisible(false);
        buyCardAction.setDisable(true);
        buyCardAction.setVisible(false);
        discardCardAction.setVisible(false);
        discardCardAction.setDisable(true);
        productionAction.setDisable(true);
        productionAction.setVisible(false);
        playCardAction.setDisable(true);
        playCardAction.setVisible(false);
        endTurn.setVisible(false);
        endTurn.setDisable(true);
    }

    public void manageResources(ArrayList<Resource> resources) {
        buttonStatus();
        setMessage("choose your new warehouse configuration");
        showDepots();
        currentAction = Actions.MANAGE;
        changeEndButton();
        warehouse= clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getWarehouse();
        res= resources;

        if (warehouse.get(0).isEmpty()){
            depot1 = null;
        }
        else{
            depot1 = warehouse.get(0).get(0);
        }


        if (warehouse.get(1).isEmpty()){
            depot2[0] = null;
            depot2[1] = null;
        }
        else if(warehouse.get(1).size()==1){
            depot2[0] = warehouse.get(1).get(0);
            depot2[1]= null;
        }
        else{
            depot2[0] = warehouse.get(1).get(0);
            depot2[1] = warehouse.get(1).get(1);
        }



        if (warehouse.get(2).isEmpty()){
            depot3[0] = null;
            depot3[1] = null;
            depot3[2] = null;
        }
        else if(warehouse.get(2).size()==1){
            depot3[0] =warehouse.get(2).get(0);
            depot3[1] = null;
            depot3[2] = null;
        }
        else if (warehouse.get(2).size() == 2){
            depot3[0] = warehouse.get(2).get(0);
            depot3[1] = warehouse.get(2).get(1);
            depot3[2] = null;
        }
        else{
            depot3[0] = warehouse.get(2).get(0);
            depot3[1] = warehouse.get(2).get(1);
            depot3[2] = warehouse.get(2).get(2);
        }

        if (res.isEmpty()){
            newResources[0] = null;
            newResources[1] = null;
            newResources[2] = null;
            newResources[3] = null;
        }
        else{
            if (res.size()==4){
                newResources[0] =res.get(0);
                newResources[1] =res.get(1);
                newResources[2] =res.get(2);
                newResources[3] =res.get(3);
            }
            else if (res.size() ==3){
                newResources[0] = res.get(0);
                newResources[1] = res.get(1);
                newResources[2] = res.get(2);
                newResources[3] = null;
            }
            else if (res.size() == 2){
                newResources[0] = res.get(0);
                newResources[1] = res.get(1);
                newResources[2] = null;
                newResources[3] = null;
            }
            else{
                newResources[0]= res.get(0);
                newResources[1]= null;
                newResources[2]= null;
                newResources[3]= null;
            }

        }







        if(newResources[0] == null){
            //fine manage resources;
        }
        else {
            final String s = "resources/punchboard/" + newResources[0].toString().toLowerCase() + ".png";
            if (newResources[1]== null){
                manageImage1.setImage(new Image(s));
                manage1.setVisible(true);
                manage1.setDisable(false);
            }
            else {
                String s1 = "resources/punchboard/" + newResources[1].toString().toLowerCase() + ".png";
                if (newResources[2] == null) {
                    manageImage1.setImage(new Image(s));
                    manageImage2.setImage(new Image(s1));

                }
                else {
                    String s2 = "resources/punchboard/" + newResources[2].toString().toLowerCase() + ".png";
                    manageImage1.setImage(new Image(s));
                    manageImage2.setImage(new Image(s1));
                    manageImage3.setImage(new Image(s2));
                    if (newResources[3] == null){
                        manage3.setVisible(true);
                        manage3.setDisable(false);
                    }
                    else{
                        manageImage4.setImage(new Image("resources/punchboard/" + newResources[3].toString().toLowerCase() + ".png"));
                        manage3.setVisible(true);
                        manage3.setDisable(false);
                        manage4.setVisible(true);
                        manage4.setDisable(false);
                    }

                }
                manage1.setVisible(true);
                manage1.setDisable(false);
                manage2.setVisible(true);
                manage2.setDisable(false);
            }
        }


        activateWarehouseButton();
        endTurn.setVisible(true);
        endTurn.setDisable(false);
        endTurn.setMouseTransparent(false);



    }

    private void activateWarehouseButton() {
        trashManage.setVisible(true);
        trashManage.setDisable(false);
        depotOneResourceOne.setMouseTransparent(false);
        depotOneResourceOne.setDisable(false);
        depotTwoResourceOne.setDisable(false);
        depotTwoResourceTwo.setDisable(false);
        depotThreeResourceOne.setDisable(false);
        depotThreeResourceTwo.setDisable(false);
        depotThreeResourceThree.setDisable(false);
        depotTwoResourceOne.setMouseTransparent(false);
        depotTwoResourceTwo.setMouseTransparent(false);
        depotThreeResourceOne.setMouseTransparent(false);
        depotThreeResourceTwo.setMouseTransparent(false);
        depotThreeResourceThree.setMouseTransparent(false);
        depotOneResourceOne.setVisible(true);
        depotTwoResourceOne.setVisible(true);
        depotTwoResourceTwo.setVisible(true);
        depotThreeResourceOne.setVisible(true);
        depotThreeResourceTwo.setVisible(true);
        depotThreeResourceThree.setVisible(true);

    }

    public void trashManage(){
        if (first != null){
            second = null;
            int a = discardRes.size();
            discardRes.add(a,first);
            first = null;
            setSecondResource();
        }
        else{
            if (isNullVolunteer){
                isNullVolunteer = false;
            }
        }
    }







    public void manage1(){
        if (first==null) {
            if (!isNullVolunteer) {
                first = newResources[0];
                num = 1;
                if (first== null) isNullVolunteer= true;
            }
            else{
                second = newResources[0] ;
                manageImage1.setImage(null);
                newResources[0] = null;
                isNullVolunteer = false;
                setSecondResource();
            }
        }
        else {
            second = newResources[0];
            manageImage1.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
            newResources[0] = first;
            first = null;
            setSecondResource();

        }
    }



    public void manage2(){
        if (first==null) {
            if (!isNullVolunteer) {
                first = newResources[1];
                num = 2;
                if (first == null) isNullVolunteer = true;
            }
            else{
                second = newResources[1] ;
                manageImage2.setImage(null);
                newResources[1] = null;
                isNullVolunteer = false;
                setSecondResource();
            }
        }
        else {
            second = newResources[1];
            manageImage2.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
            newResources[1] = first;
            first = null;
            setSecondResource();

        }
    }
    public void manage3(){
        if (first==null) {
            if (!isNullVolunteer) {
                first = newResources[2];
                num = 3;
                if (first == null) isNullVolunteer = true;
            }
            else{
                second = newResources[2] ;
                manageImage3.setImage(null);
                newResources[2] = null;
                isNullVolunteer = false;
                setSecondResource();
            }
        }
        else {
            second = newResources[2];
            manageImage3.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
            newResources[2] = first;
            first = null;
            setSecondResource();

        }
    }
    public void manage4(){
        if (first==null) {
            if (!isNullVolunteer) {
                first = newResources[3];
                num = 4;
                if (first == null) isNullVolunteer = true;
            }
            else{
                second = newResources[3] ;
                manageImage4.setImage(null);
                newResources[3] = null;
                isNullVolunteer = false;
                setSecondResource();
            }
        }
        else {
            second = newResources[3];
            manageImage4.setImage(new Image("resources/punchboard/" + first.toString().toLowerCase() + ".png"));
            newResources[3]= first;
            first = null;
            setSecondResource();

        }
    }
    private void setSecondResource(){
        switch (num){
            case 1:{
                if (second ==null){
                    manageImage1.setImage(null);
                }
                else {
                    manageImage1.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                newResources[0] = second;
                second = null;
                break;
            }
            case 2:{
                if (second == null){
                    manageImage2.setImage(null);
                }
                else {
                    manageImage2.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                newResources[1]= second;
                second = null;
                break;
            }
            case 3:{
                if (second == null){
                    manageImage3.setImage(null);
                }
                else {
                    manageImage3.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                newResources[2]= second;
                second = null;
                break;
            }
            case 4:{
                if (second ==null){
                    manageImage4.setImage(null);
                }
                else {
                    manageImage4.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                newResources[3]= second;
                second = null;
                break;
            }
            case 5:{
                if (second==null){
                    depot1Resource1.setImage(null);
                }
                else {
                    depot1Resource1.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                ArrayList<Resource> temp = new ArrayList<>();
                depot1 = second;
                second = null;
                break;
            }
            case 6:{
                if(second==null){
                    depot2Resource1.setImage(null);
                }
                else {
                    depot2Resource1.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                depot2[0] = second;
                second = null;
                break;
            }
            case 7:{
                if (second==null){
                    depot2Resource2.setImage(null);
                }
                else {
                    depot2Resource2.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                depot2[1] = second;
                second = null;
                break;
            }
            case 8:{
                if (second==null){
                    depot3Resource1.setImage(null);
                }
                else {
                    depot3Resource1.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                depot3[0] = second;
                second = null;
                break;
            }
            case 9:{
                if (second==null){
                    depot3Resource2.setImage(null);
                }
                else {
                    depot3Resource2.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                depot3[1] = second;
                second = null;
                break;
            }
            case 10:{
                if (second==null){
                    depot3Resource3.setImage(null);
                }
                else {
                    depot3Resource3.setImage(new Image("resources/punchboard/" + second.toString().toLowerCase() + ".png"));
                }
                depot3[2] = second;
                second = null;
                break;
            }
        }
    }

    private void manageActionDone(){
        ArrayList<Resource> first = new ArrayList<>();
        ArrayList<Resource> second = new ArrayList<>();
        ArrayList<Resource> third = new ArrayList<>();
        Map<Resource,Integer> discardRes = new HashMap<>();
        int coins=0;
        int stones=0;
        int shields = 0;
        int servants =0;
        Map<Integer,ArrayList<Resource>> ware = new HashMap<>();
        int counter=0;

        if (depot1!= null) first.add(0,depot1);

        for (Resource resource : depot2) {
            if (resource != null) {
                second.add(counter, resource);
                counter++;
            }
        }
        counter =0;
        for (Resource resource: depot3){
            if (resource!=null){
                third.add(counter,resource);
                counter++;
            }
        }

        for (Resource resource: newResources){
            if (resource!=null){
                if (resource==Resource.COINS) coins++;
                else if (resource== Resource.SERVANTS) servants++;
                else if (resource == Resource.STONES) stones++;
                else shields++;
            }
        }
        discardRes.put(Resource.COINS,coins);
        discardRes.put(Resource.SHIELDS,shields);
        discardRes.put(Resource.SERVANTS,servants);
        discardRes.put(Resource.STONES,stones);
        for (Resource resource: this.discardRes){
            int a =discardRes.get(resource);
            discardRes.put(resource,a+1);

        }

        ware.put(0,first);
        ware.put(1,second);
        ware.put(2,third);
        buttonStatus();
        disableWarehouseButtons();
        warehouse=null;
        res=null;
        this.first=null;
        num = -1;
        this.second = null;
        depot1=null;
        depot2[0] = null;
        depot2[1] = null;
        depot3[0] = null;
        depot3[1] = null;
        depot3[2] = null;
        newResources[0] = null;
        newResources[1] = null;
        newResources[2] = null;
        newResources[3] = null;
        isNullVolunteer = false;
        this.discardRes.clear();
        clientView.sendManageResourcesReply(ware,discardRes);

    }
    private void disableWarehouseButtons(){
        depotOneResourceOne.setMouseTransparent(true);
        depotTwoResourceOne.setMouseTransparent(true);
        depotTwoResourceTwo.setMouseTransparent(true);
        depotThreeResourceOne.setMouseTransparent(true);
        depotThreeResourceTwo.setMouseTransparent(true);
        depotThreeResourceThree.setMouseTransparent(true);
        manage4.setDisable(true);
        manage3.setDisable(true);
        manage2.setDisable(true);
        manage1.setDisable(true);
        manage4.setVisible(false);
        manage3.setVisible(false);
        manage2.setVisible(false);
        manage1.setVisible(false);




    }
}
