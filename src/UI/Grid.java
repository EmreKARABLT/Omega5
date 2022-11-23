package UI;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {


    private LinkedList<Hex> setOfHexagons;
    private ArrayList<Cell> setOfCells;
    private State state;
    private ArrayList<JLabel> scores = new ArrayList<>();
    private ArrayList<JLabel> names = new ArrayList<>();
    private ArrayList<JPanel> colorBars = new ArrayList<>();
    private ArrayList<Cell> lastWhites = new ArrayList<>();
    private ArrayList<Cell> lastBlacks = new ArrayList<>();

    public Grid(State state){
        this.state = state;
        this.setOfCells = state.getBoard().getCells();
        this.setOfHexagons = createHexagons();
        HexListener listener = new HexListener();
        addMouseListener(listener);
        createGamingPage();

    }
    public void createGamingPage(){
        this.setLayout(null);

        for (Player player :state.getPlayers()) { createPlayerPanels(player); }
        updateColorBars();
        super.add(mainMenuButton());
        repaint();
    }
    public void createPlayerPanels(Player player){
        Color color = null;
        JPanel colorBar = new JPanel();
        if(player.getPlayerID() == 0 ) color = Color.WHITE;
        if(player.getPlayerID() == 1 ) color = Color.BLACK;
        if(player.getPlayerID() == 2 ) color = Color.RED;
        if(player.getPlayerID() == 3 ) color = Color.BLUE;

        colorBar.setBackground(Color.WHITE);
        JLabel name = new JLabel(player.getPlayerName());
        JLabel score = new JLabel(""+player.getScore());

        name.setFont(Show.customFont_25f);
        score.setFont(Show.customFont_25f);

        name.setForeground(color);
        score.setForeground(color);

        int x = 0 , y  = 0;
        if(player.getPlayerID() == 0){ x = 20                              ;  y = 20                              ;}
        if(player.getPlayerID() == 1){ x = getBoard().getOffsetX()*2 - 220 ;  y = 20                              ;}
        if(player.getPlayerID() == 2){ x = 20                              ;  y = getBoard().getOffsetY()*20 - 120;}
        if(player.getPlayerID() == 3){ x = getBoard().getOffsetX()*2 - 220 ;  y = getBoard().getOffsetY()*20 - 120;}

        name.setBounds( x+20,y,200,50);
        score.setBounds(x+20,y+25,200,50);
        colorBar.setBounds(x+5,y+13,7,50);

        super.add(name);
        super.add(score);
        super.add(colorBar);

//        panel.setBackground();

        colorBars.add(colorBar);
        names.add(name);
        scores.add(score);
    }

    public LinkedList<Hex> createHexagons(){

        LinkedList<Hex> setOfHexagons = new LinkedList<>();

        for (Cell cell : setOfCells) {
            Hex hex = new Hex(cell);
            setOfHexagons.add(hex);
            hex.changeColor(cell.getColor());
        }

        repaint();


        return setOfHexagons;
    }


    public void updateScores(){
        for (int i = 0; i < scores.size(); i++) {
            Player p = state.getPlayers().get(i);
            scores.get(i).setText( getBoard().scoreOfAPlayer(p.getPlayerID())+"");
        }

    }
    public void updateColors(){
        for (Hex hex : setOfHexagons) {
            hex.changeColor(hex.getCell().getColor());
        }
    }
    public void updateColorBars() {
        for (int i = 0; i < colorBars.size(); i++) {
            int colorBarIndexToShow = state.getCurrentPlayer().getPlayerID();
            if (i == state.getCurrentPlayer().getPlayerID()) {
                colorBars.get(i).setVisible(true);
                Color color = null;
                int currentColor = state.getCurrentColor();
                if (currentColor == 0) color = Color.WHITE;
                if (currentColor == 1) color = Color.BLACK;
                if (currentColor == 2) color = Color.RED;
                if (currentColor == 3) color = Color.BLUE;

                colorBars.get(i).setBackground(color);

            } else {
                colorBars.get(i).setVisible(false);
            }
        }
    }


        public Cell getCellFromMouseClick( double x , double y ){
        return state.getBoard().getCellFromPosition(x,y);
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        this.setOpaque(false);
        for (int i = 0; i < setOfHexagons.size(); i++) {
            g.setColor(setOfHexagons.get(i).getColor());
            g.fillPolygon(setOfHexagons.get(i).getPolygon());
            g.setColor(new Color(92, 130, 117));
            g.drawPolygon(setOfHexagons.get(i).getPolygon());
        }
        Show.frame.setBackground(new Color(92, 130, 117));
        repaint();




//
//        for (int i = 0; i < setOfCells.size(); i++) {
//            g.drawString(setOfCells.get(i).getQ() + " " + setOfCells.get(i).getR(), (int)setOfCells.get(i).getX(), (int)setOfCells.get(i).getY());
//        }

    }
    public JButton mainMenuButton(){
        JButton backButton = new JButton("<");
        backButton.setFont(Show.customFont_40f);
        backButton.setBounds( 10 , getBoard().getOffsetY()-60,60, 60);

        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
//                Show.frame.getContentPane().removeAll();
                Show.frame.setContentPane(Menu.getInstance().getPanel());
                Show.frame.getRootPane().revalidate();
            }
        });
        return backButton;
    }



    public LinkedList<Hex> getSetOfHexagons() {
        return setOfHexagons;
    }

    public void setSetOfHexagons(LinkedList<Hex> setOfHexagons) {
        this.setOfHexagons = setOfHexagons;
    }



    public ArrayList<Cell> getSetOfCells() {
        return setOfCells;
    }

    public void setSetOfCells(ArrayList<Cell> setOfCells) {
        this.setOfCells = setOfCells;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Board getBoard() {
        return state.getBoard();
    }


    private class HexListener extends MouseAdapter {

        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        /**
         * Invoked when a mouse button has been pressed on a component.
         *
         * @param e the event to be processed
         */

        @Override
        public void mousePressed(MouseEvent e) {
            if(SwingUtilities.isRightMouseButton(e)){
                int color;

                Player player = state.getCurrentPlayer();
                color = player.getCurrentPieceID();
                if (color == 1) {
                    if(!lastWhites.isEmpty()) {
                        lastWhites.get(lastWhites.size() - 1).setColor(-1);
                        lastWhites.remove(lastWhites.size() - 1);
                    }
                } else if(color == 0){
                    if(!lastBlacks.isEmpty()) {
                        lastBlacks.get(lastBlacks.size() - 1).setColor(-1);
                        lastBlacks.remove(lastBlacks.size() - 1);
                    }
                }
                state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
                state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
                state.previousTurn();


                updateColors();
                updateScores();
                updateColorBars();
                repaint();
                return;
            }
            if(SwingUtilities.isLeftMouseButton(e)) {
                Cell cell = getCellFromMouseClick(e.getX(), e.getY());
                int color;

                Player player = state.getCurrentPlayer();
                color = player.getCurrentPieceID();

                if (!state.isGameOver() && cell != null && cell.isEmpty()) {

                    cell.setColor(color);
                    if(color == 0 )
                        state.addWhite(cell);

                    else
                        state.addBlack(cell);
                    state.getPlayers().get(color).setScore(state.getBoard().scoreOfAPlayer(color));
                    state.nextTurn();
                    updateColors();
                    updateScores();
                    updateColorBars();
                    repaint();
                }
            }
        }

        /**
         * Invoked when a mouse button has been released on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseReleased(MouseEvent e) {

            if(state.getCurrentPlayer().isBot()){
                ArrayList<Cell> moves = state.getCurrentPlayer().getMoves(state);

                moves.get(0).setColor(0);
                moves.get(1).setColor(1);
                state.addWhite(moves.get(0));
                state.addBlack(moves.get(1));
                state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
                state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
                state.nextTurn();
                state.nextTurn();

                updateColors();
                updateScores();
                updateColorBars();
                repaint();
            }

            if(state.isGameOver()) {
                StringBuilder s = new StringBuilder();
                int[] scores = new int[state.getNumberOfPlayers()];
                for (int i = 0; i < scores.length; i++) {
                    Player p = state.getPlayers().get(i);
                    scores[i] = p.getScore();
                    s.append(p.getPlayerName()).append(" has ").append(p.getScore()).append(" points. \n");
                }
                if(scores[0] == scores[1]){
                    s.append("TIE");
                }else if(scores[0] < scores[1])
                    s.append("BLACK WON!!");
                else
                    s.append("WHITE WON!!");

                String[] playagainbuttontext = {"Play again!"};
                int play_again = JOptionPane.showOptionDialog(
                        null,
                        s,
                        "End Frame -- Thank you for playing!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        playagainbuttontext,
                        0
                );

                //play_again==0 means you want to play again
                if (play_again == 0){
                    Show.frame.setContentPane(Menu.getInstance().getPanel());
                    Show.frame.getRootPane().revalidate();
                }
                else {
                    Show.frame.dispose();
                }
            }

        }

        /**
         * Invoked when the mouse enters a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * Invoked when the mouse exits a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

