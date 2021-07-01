package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.CLI.CLI;
import it.polimi.ingsw.client.LocalClientView;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.FullPlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ControllerTest {
    private LocalController localController;
    private Game game;
    private clientStub view;
    private CLI cli;

    private class clientStub extends  LocalClientView {
        public clientStub(UserInterface ui) {
            super(ui);
        }

        @Override
        public void faithTrackUpdate(Map<String, Integer> vaticanPosition, Map<String, Integer> position, boolean report) {

        }

        @Override
        public void lorenzoUpdate(String message, int lorenzoPosition, String[][] newCardGrid) {
        }

        @Override
        public void pickAction(ArrayList<Actions> possibleActions) {

        }
    }


    @BeforeEach
    void before(){
        game = new Game();
        ArrayList<String> player = new ArrayList<>();
        cli = new CLI();
        view = new clientStub(cli);
        player.add("giocatore1");
        try {
            game.addPlayer(player.get(0));
        } catch (FullPlayerException e) {
            e.printStackTrace();
        }

        game.setActivePlayer(game.getPlayers(0));
        localController = new LocalController(game,player,view);
    }

    @Test
    public void nextPlayer(){
        String username = game.getActivePlayer().getNickname();
        localController.endTurn();
        String username1 = game.getActivePlayer().getNickname();
        assertEquals(username,username1);
    }
}
