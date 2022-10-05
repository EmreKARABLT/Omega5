package PLAYER;

public interface Player {
    boolean isBot();
    String getPlayerName();
    int getPlayerID();

    int getScore();
    void setScore(int score);

    int getCurrentPieceID();
    void setCurrentPieceID(int currentPieceID);



}
