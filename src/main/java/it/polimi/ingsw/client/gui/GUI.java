package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.controller.Actions;
import it.polimi.ingsw.controller.Error;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Map;

public class GUI implements UserInterface {
    private ClientView clientView;

    @Override
    public void begin() {

    }

    @Override
    public String login() {
        return null;
    }

    @Override
    public String failedLogin(ArrayList<String> usedNames) {
        return null;
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
    public int playersNumber(int max) {
        return 0;
    }

    @Override
    public ArrayList<String> startingLeaderCardsSelection(ArrayList<String> leaderCardID) {
        return null;
    }

    @Override
    public Map<Integer, ArrayList<Resource>> startingResources(int amount) {
        return null;
    }

    @Override
    public Actions chooseAction(ArrayList<Actions> possibleActions) {
        return null;
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
