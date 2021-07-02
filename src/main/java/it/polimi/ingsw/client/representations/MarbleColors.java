package it.polimi.ingsw.client.representations;
/**
 * simplified version of the marbles
 */
public enum MarbleColors {
    WHITE("white"),
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow"),
    GREY("grey"),
    PURPLE("purple");

    private final String color;

    MarbleColors(String color) {
        this.color=color;
    }

    @Override
    public String toString() {
        return color;
    }
}
