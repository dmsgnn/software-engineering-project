package it.polimi.ingsw;

public abstract class FaithTrack {
    private int position;
    private final Game game;

    public FaithTrack(Game game){
        this.position = 0;
        this.game= game;
    }

    public int getPosition() {
        return position;
    }

    // only used for testing
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * increases the position
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
    }
}
