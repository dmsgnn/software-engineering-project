package it.polimi.ingsw.model;

import it.polimi.ingsw.client.CLI.CLI;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.representations.MarbleColors;
import it.polimi.ingsw.model.exceptions.NoCardsLeftException;
import it.polimi.ingsw.model.gameboard.Gameboard;
import org.junit.jupiter.api.Test;

public class CLITest {

    @Test
    public void CLITestBoard(){
        Gameboard gameboard = new Gameboard(new Game());
        String[][] cardGrid = new String[3][4];
        MarbleColors freemarble = gameboard.getMarketBoard().getFreeMarble().getColor();
        MarbleColors[][] market = new MarbleColors[3][4];
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                market[i][j] = gameboard.getMarketBoard().getMarbleGrid()[i][j].getColor();
                try {
                    cardGrid[i][j] = gameboard.getCardGrid()[i][j].lookFirst().getId();
                } catch (NoCardsLeftException e) {
                    e.printStackTrace();
                }
            }
        }
        cardGrid[2][1]=null;
        CLI cli = new CLI();
        ClientView view = new ClientView(cli);
        cli.setClientView(view);
        view.marketSetup(market, freemarble);
        view.developmentCardGridSetup(cardGrid);
        //cli.updateBoard();
    }
}
