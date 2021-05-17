package it.polimi.ingsw.model.playerboard.faithTrack;

import it.polimi.ingsw.model.Game;

public abstract class FaithTrack {
    protected int position;
    private final Game game;

    public FaithTrack(Game game){
        this.position = 0;
        this.game= game;
    }

    public int getPosition() {
        return position;
    }

    // only used for testing and for single incrementation
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * this method is used only when the player discards some resources.
     * it increases the position of every player (active player is not included)
     * and at every incrementation checks if someone can activates the vatican report.
     */
    public void increaseAllPositions() {
        if(game.isSoloMode()){
            game.getLorenzo().getTrack().increasePosition();
        }
        else {
            for (int i = 0; i < game.getPlayersNumber(); i++) {
                if (!game.getPlayers(i).equals(game.getActivePlayer())) {
                    game.getPlayers(i).getPlayerBoard().getFaithTrack().setPosition(game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition() + 1);
                }
            }
            for (int i = 0; i < game.getPlayersNumber(); i++) {
                if (!game.getPlayers(i).equals(game.getActivePlayer())) {
                    if (game.getPlayers(i).getPlayerBoard().getFaithTrack().vaticanReportCheck()) {
                        game.getPlayers(i).getPlayerBoard().getFaithTrack().vaticanReportActivation();
                    }
                }
            }
        }
    }

    /**
     * increases the position and check if the vatican report can be activated, in case activates it
     */
    public void increasePosition(){
        if(!endOfTrack()) {
            position += 1;
            if(vaticanReportCheck()){
                vaticanReportActivation();
            }
        }
    }

    /**
     * checks if the end of the track is reached
     * @return boolean true if the player has reached the end of the track
     */
    public boolean endOfTrack(){
        return position == 24;
    }

    /**
     * checks if the vatican report must be activated and in case calls the vaticanReportActivation method
     * @return boolean true if the vatican report must be activated
     */
    public boolean vaticanReportCheck() {
        if(position == 8 && game.getNumVaticanReports() == 0){
            game.setNumVaticanReports(1);
            return true;
        }
        else if(position == 16 && game.getNumVaticanReports() == 1){
            game.setNumVaticanReports(2);
            return true;
        }
        else if(position == 24 && game.getNumVaticanReports() == 2){
            game.setNumVaticanReports(3);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * increases victory points of every player depending on his position
     */
    public void vaticanReportActivation (){
        if(game.getNumVaticanReports() == 1){
        for(int i=0; i<game.getPlayersNumber(); i++){
            if(game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition() > 4 && game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition() < 9)
                  game.getPlayers(i).getPlayerBoard().getFaithTrack().increaseVictoryPoints(2);
            }
        }
        else if(game.getNumVaticanReports() == 2){
            for(int i=0; i<game.getPlayersNumber(); i++){
                if(game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition() > 11 && game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition() < 17)
                    game.getPlayers(i).getPlayerBoard().getFaithTrack().increaseVictoryPoints(2);
            }
        }
        else if(game.getNumVaticanReports() == 3){
            for(int i=0; i<game.getPlayersNumber(); i++){
                if(game.getPlayers(i).getPlayerBoard().getFaithTrack().getPosition() > 18)
                    game.getPlayers(i).getPlayerBoard().getFaithTrack().increaseVictoryPoints(4);
            }
        }
    }
}
