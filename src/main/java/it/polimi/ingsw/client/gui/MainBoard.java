package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.representations.ClientPlayerBoard;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class MainBoard {
    private static ClientView clientView;
    private static GUI gui;

    // market
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

    //leader cards
    @FXML
    private ImageView leaderCard1;
    @FXML
    private ImageView leaderCard2;
    @FXML
    private Button leaderCardOne;
    @FXML
    private Button leaderCardTwo;

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

    //Production attributes
    private final ArrayList<Integer> prodDevSlots = new ArrayList<>();
    private final ArrayList<Integer> prodLeaderSlots = new ArrayList<>();
    private final ArrayList<Resource> leaderCardGains = new ArrayList<>();
    private final ArrayList<Resource> boardResProd = new ArrayList<>();

    //Payments
    private final ArrayList<Resource> warehousePayment = new ArrayList<>();
    private final ArrayList<Resource> leaderDepotPayment = new ArrayList<>();
    private final ArrayList<Resource> strongboxPayment = new ArrayList<>();

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
            double[] newBlackPosition = getCrossPosition(board.getPlayerPosition());
            if(board.getPlayerPosition()==board.getLorenzoPosition())
                blackCross.setLayoutX(newPosition[0]-10);
            blackCross.setLayoutY(newPosition[1]);
        }
        showVatican(board);
        MarbleColors[][] market = clientView.getGameboard().getMarket();
        String[][] grid = clientView.getGameboard().getCards();

        for(int i=0; i<3; i++) {
            for (int j = 0; j < 4; j++) {
                Image marbleImage = new Image("/graphics/Marble/" + market[i][j].toString().toLowerCase() + ".png");
                Image devCardImage = new Image("/graphics/devCards/" + grid[i][j] + ".png");
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
        String color = null;
        Image marbleImage = new Image("/graphics/Marble/" + clientView.getGameboard().getFreeMarble().toString().toLowerCase() + ".png");
        marble0.setImage(marbleImage);
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().size() >0)
            leaderCard1.setImage(new Image("/graphics/leaderCards/" + clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().get(0) + ".png"));
        else
            leaderCardOne.setVisible(false);
        if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().size() >1)
            leaderCard2.setImage(new Image("/graphics/leaderCards/" + clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getHand().get(1) + ".png"));
        else
            leaderCardTwo.setVisible(false);
    }

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
        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());
        if(board.isDepotEmpty(0))
            depotOneResourceOne.setVisible(false);
        else
            depot1Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(0).toString().toLowerCase() + ".png"));
        if(board.isDepotEmpty(1)){
            depotTwoResourceOne.setVisible(false);
            depotTwoResourceTwo.setVisible(false);
        }
        else{
            if (board.getWarehouse().get(1).size() == 2) {
                depot2Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
                depot2Resource2.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
            }
            else{
                depot2Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(1).toString().toLowerCase() + ".png"));
                depotTwoResourceTwo.setVisible(false);
            }
        }
        if(board.isDepotEmpty(2)){
            depotThreeResourceOne.setVisible(false);
            depotThreeResourceTwo.setVisible(false);
            depotThreeResourceThree.setVisible(false);
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
                depotThreeResourceThree.setVisible(false);
            }
            else
                depot3Resource1.setImage(new Image("resources/punchboard/"+ board.getWarehouseResource(2).toString().toLowerCase() + ".png"));
                depotThreeResourceTwo.setVisible(false);
                depotThreeResourceThree.setVisible(false);
        }
        //disable all
    }

    private void setStrongboxCounters(){
        // to do -> disable the buttons
        strongboxShieldsCounter.setText(String.valueOf(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getStrongbox().get(Resource.SHIELDS)));
        strongboxServantsCounter.setText(String.valueOf(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getStrongbox().get(Resource.SERVANTS)));
        strongboxStonesCounter.setText(String.valueOf(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getStrongbox().get(Resource.STONES)));
        strongboxCoinsCounter.setText(String.valueOf(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getStrongbox().get(Resource.COINS)));
    }

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
        // leader buttons

        // dev grid buttons

        // dev slots buttons

        // depot buttons

        // strongbox buttons

        //market buttons
    }

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
    public void productionAction(){
        currentAction = Actions.USEPRODUCTION;
        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());
        if(!board.getDevCardSlot().get(0).isEmpty()) devSlot1.setDisable(false);
        if(!board.getDevCardSlot().get(1).isEmpty()) devSlot2.setDisable(false);
        if(!board.getDevCardSlot().get(2).isEmpty()) devSlot3.setDisable(false);
        boardProduction.setDisable(false);
        if(board.getProductionBuff().containsKey(board.getPlayedCards().get(0))){
            leaderCardOne.setDisable(false);
        }
        if(board.getProductionBuff().containsKey(board.getPlayedCards().get(1))){
            leaderCardTwo.setDisable(false);
        }
    }

    public void startPayment(){
        //disattivo tutto
        ClientPlayerBoard board = clientView.getGameboard().getOnePlayerBoard(clientView.getNickname());
        if(!board.getWarehouse().get(0).isEmpty()){
            depotOneResourceOne.setDisable(false);
        }

        if(board.getWarehouse().get(1).size()==1){
            depotTwoResourceOne.setDisable(false);
        }
        else if(board.getWarehouse().get(1).size()==2){
            depotTwoResourceOne.setDisable(false);
            depotTwoResourceTwo.setDisable(false);
        }

        if(board.getWarehouse().get(2).size()==1){
            depotThreeResourceOne.setDisable(false);
        }
        else if(board.getWarehouse().get(2).size()==2){
            depotThreeResourceOne.setDisable(false);
            depotThreeResourceTwo.setDisable(false);
        }
        else if(board.getWarehouse().get(2).size()==3){
            depotThreeResourceOne.setDisable(false);
            depotThreeResourceTwo.setDisable(false);
            depotThreeResourceThree.setDisable(false);
        }

        if(board.getStrongbox().get(Resource.COINS) > 0) strongboxCoins.setDisable(false);
        if(board.getStrongbox().get(Resource.SERVANTS) > 0) strongboxServants.setDisable(false);
        if(board.getStrongbox().get(Resource.STONES) > 0) strongboxStones.setDisable(false);
        if(board.getStrongbox().get(Resource.SHIELDS) > 0) strongboxShields.setDisable(false);

    }

    public void slot1Action() {
        if(currentAction == Actions.USEPRODUCTION){
            prodDevSlots.add(0);
        }
    }

    public void slot2Action() {
        if(currentAction == Actions.USEPRODUCTION){
            prodDevSlots.add(1);
        }
    }

    public void slot3Action() {
        if(currentAction == Actions.USEPRODUCTION){
            prodDevSlots.add(2);
        }
    }

    public void useBoardProd() {

    }

    public void leaderOneAction() {
        if(currentAction == Actions.USEPRODUCTION){
            prodLeaderSlots.add(0);
        }
    }

    public void leaderTwoAction() {
        if(currentAction == Actions.USEPRODUCTION){
            if(clientView.getGameboard().getOnePlayerBoard(clientView.getNickname()).getProductionBuff().size()==1) prodLeaderSlots.add(0);
            else prodLeaderSlots.add(1);
        }
    }

    public void strShieldsAction() {
        strongboxPayment.add(Resource.SHIELDS);
        if(clientView.getMyStrongbox().get(Resource.SHIELDS)==0) strongboxShields.setDisable(true);
    }

    public void strCoinsAction() {
    }

    public void strServantsAction() {
    }

    public void strStonesAction() {
    }

    public void depotOneResourceOneAction() {
    }

    public void depotTwoResourceOneAction() {
    }

    public void depotTwoResourceTwoAction() {
    }

    public void depotThreeResourceOneAction() {
    }

    public void depotThreeResourceTwoAction() {
    }

    public void depotThreeResourceThreeAction() {
    }
}
