package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.model.Resource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.System.exit;

public class GUI extends Application implements UserInterface {
    private static ClientView clientView;

    private static Login logger;
    private static PlayersNumber players;
    private static Waiting waiting;
    private static StartingResourcesScene startingResources;
    private static LeaderCardSelection leaderCardSelection;
    private static MainBoard playerBoard;
    private static FullView fullView;


    private static Stage mainStage;
    private static Parent loginRoot;
    private static Parent playersRoot;
    private static Parent waitingRoot;
    private static Parent startingResourcesRoot;
    private static Parent leaderCardRoot;
    private static Parent playerBoardRoot;
    private static Parent fullViewRoot;


    public GUI() {
    }

    public void setClientView(ClientView clientView) {
        GUI.clientView = clientView;
    }

    public ClientView getClientView() {
        return clientView;
    }


    public void begin() {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("Masters of Renaissance");
        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/graphics/logo.png")).toExternalForm()));
        mainStage.setResizable(false);
        mainStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            exit(0);
        });

        Login.setGui(this);
        PlayersNumber.setGui(this);
        Waiting.setGui(this);
        StartingResourcesScene.setGui(this);
        LeaderCardSelection.setGui(this);
        MainBoard.setGui(this);
        FullView.setGui(this);

        startGui();
        fullView.initArray();

        clientView.startConnection();

        login();

    }




    @Override
    public void login() {
        Platform.runLater(()->{
            mainStage.setScene(new Scene(loginRoot, 1400,800));
            mainStage.show();
        });
    }

    @Override
    public void failedLogin(ArrayList<String> usedNames) {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(loginRoot);
            logger.takenUsername();
        });
    }

    @Override
    public void loginDone() {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(waitingRoot);
            waiting.setWaitingMessage("Correct nickname, other players are joining...");
        });
    }

    @Override
    public void handleDisconnection(String nickname) {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playerBoardRoot);
            playerBoard.setMessage("User " + nickname + " disconnected from the game");
        });
    }

    @Override
    public void handleReconnection(String nickname) {

    }

    @Override
    public void loser(String nickname) {

    }

    @Override
    public void manageError(Error errorType) {
        String error;
        switch (errorType){
            case STARTING_RESOURCES:
                error = "Wrong placement of starting resources!!!";
                break;
            case STARTING_LEADER_CARD:
                error = "Wrong cards selected!!!";
                break;
            case STARTING_MANAGE_RESOURCES:
                error = "Wrong starting resources placement!!!";
                break;
            case INVALID_ACTION:
                error = "Invalid action!!";
                break;
            case MANAGE_RESOURCES:
                error = "Wrong resources management!!!";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + errorType);
        }
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playerBoardRoot);
            playerBoard.setMessage(error);
        });
    }

    @Override
    public void winner(String nickname) {

    }

    @Override
    public void inGameLobby() {

    }

    @Override
    public void gameFull() {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void endLeadercardSetup() {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(waitingRoot);
            waiting.setWaitingMessage("Correct leadercards selection, waiting for other players...");
        });
    }

    @Override
    public void endStartingResourcesSetup() {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(waitingRoot);
            waiting.setWaitingMessage("Correct starting resources selection, waiting for other players...");
        });
    }

    @Override
    public void startGame() {

    }

    @Override
    public void playersNumber(int max) {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playersRoot);
        });
    }

    @Override
    public void startingLeaderCardsSelection(ArrayList<String> leaderCardID) {
        Platform.runLater(()-> {
            leaderCardSelection.selectLeaderCards(leaderCardID);
            mainStage.getScene().setRoot(leaderCardRoot);
        });
    }

    @Override
    public void startingResources(int amount) {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(startingResourcesRoot);
            startingResources.setAmount(amount);
        });
    }

    @Override
    public void chooseAction(ArrayList<Actions> possibleActions) {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playerBoardRoot);
            playerBoard.showActions(possibleActions);
        });
    }

    @Override
    public void marketAction() {

    }

    @Override
    public void useProductionAction() {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playerBoardRoot);
            playerBoard.productionAction();
        });
    }

    @Override
    public void buyCardAction() {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playerBoardRoot);
            playerBoard.buyDevCardAction();
        });
    }

    @Override
    public void playLeaderCardAction() {

    }

    @Override
    public void discardLeaderCardAction() {

    }

    @Override
    public void manageResources(ArrayList<Resource> resources) {

    }

    @Override
    public void updateBoard() {
        Platform.runLater(()-> {
            mainStage.getScene().setRoot(playerBoardRoot);
            playerBoard.update();
        });
    }

    public void switchView(){
        if(mainStage.getScene().getRoot().equals(playerBoardRoot)){
            Platform.runLater(()-> {
                mainStage.getScene().setRoot(fullViewRoot);
                fullView.showView();
            });
        }
        else{
            Platform.runLater(()-> {
                mainStage.getScene().setRoot(playerBoardRoot);
            });
        }
    }

    public void startGui() throws IOException {
        //login
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        loginRoot = loginLoader.load();
        logger = loginLoader.getController();

        //players number selection
        FXMLLoader playersNumberLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerNumber.fxml"));
        playersRoot = playersNumberLoader.load();
        players = playersNumberLoader.getController();

        //waiting
        FXMLLoader waitingLoader = new FXMLLoader(getClass().getResource("/fxml/Waiting.fxml"));
        waitingRoot = waitingLoader.load();
        waiting = waitingLoader.getController();

        //starting Resources
        FXMLLoader startingResLoader = new FXMLLoader(getClass().getResource("/fxml/StartingResources.fxml"));
        startingResourcesRoot = startingResLoader.load();
        startingResources = startingResLoader.getController();

        //leaderCard Selection
        FXMLLoader leaderLoader = new FXMLLoader(getClass().getResource("/fxml/LeaderCards.fxml"));
        leaderCardRoot = leaderLoader.load();
        leaderCardSelection = leaderLoader.getController();

        // playerBoard
        FXMLLoader playerBoardLoader = new FXMLLoader(getClass().getResource("/fxml/MainBoard.fxml"));
        playerBoardRoot = playerBoardLoader.load();
        playerBoard = playerBoardLoader.getController();

        // full view
        FXMLLoader fullViewLoader = new FXMLLoader(getClass().getResource("/fxml/FullView.fxml"));
        fullViewRoot = fullViewLoader.load();
        fullView = fullViewLoader.getController();
    }
}