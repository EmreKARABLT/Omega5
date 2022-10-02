package UI;

public class Human {

    public int color;

    public Human(int color){
        this.color = color;
    }

    public boolean isHumanPlaying(int playerColor){
        return this.color == playerColor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
