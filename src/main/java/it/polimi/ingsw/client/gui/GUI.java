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

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.System.exit;

public class GUI extends Application implements UserInterface {
    private ClientView clientView;

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Login.fxml")));
        stage.setTitle("Masters of Renaissance");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/graphics/logo.png")).toExternalForm()));
        stage.setScene(new Scene(root, 1400, 800));
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            exit(0);
        });
        clientView.startConnection();
    }

    @Override
    public void begin() {
        launch();
    }

    @Override
    public void login() {
    }

    @Override
    public void failedLogin(ArrayList<String> usedNames) {
    }

    @Override
    public void handleDisconnection(String nickname) {

    }

    @Override
    public void loser(String nickname) {

    }

    @Override
    public void manageError(Error errorType) {

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

    }

    @Override
    public void endStartingResourcesSetup() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void playersNumber(int max) {
    }

    @Override
    public void startingLeaderCardsSelection(ArrayList<String> leaderCardID) {
    }

    @Override
    public void startingResources(int amount) {
    }

    @Override
    public void chooseAction(ArrayList<Actions> possibleActions) {
    }

    @Override
    public void marketAction() {

    }

    @Override
    public void useProductionAction() {

    }

    @Override
    public void buyCardAction() {

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

    }

}