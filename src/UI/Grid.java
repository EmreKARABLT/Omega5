package UI;

import GAME.Board;
import GAME.Cell;
import GAME.GameLoop;
import jdk.swing.interop.SwingInterOpUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {


    private LinkedList<Hex> setOfHexagons;
    private ArrayList<Cell> setOfCells;

    private final Board board ;
    private final int WIDTH  = 1000;
    private final int HEIGHT = 1000;
    public Players players;

    public int P = 0;
    public int pT = 0;
    int p;


    public Grid(int radius, int p, Human human, Bot bot){

        this.p = p;
        this.board = new Board(radius);

        this.players = new Players(p , 0, human, bot);
        this.setOfCells = board.getCells();
        this.setOfHexagons = createHexagons();

//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        HexListener listener = new HexListener();
        addMouseListener(listener);


    }



    public Grid(Board board){

        this.board = board;
        this.setOfCells = board.getCells();
        this.setOfHexagons = createHexagons();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        HexListener listener = new HexListener();
        addMouseListener(listener);

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

    public Cell getCellFromMouseClick( double x , double y ){
        return board.getCellFromPosition(x,y);
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        for (int i = 0; i < setOfHexagons.size(); i++) {
            g.setColor(setOfHexagons.get(i).getColor());
            g.fillPolygon(setOfHexagons.get(i).getPolygon());
            g.setColor(Color.BLACK);
            g.drawPolygon(setOfHexagons.get(i).getPolygon());
        }
        addMainMenuButton();

        g.drawString(players.getFinalScore().get(0) + " " ,900, 200);
        g.drawString(players.getFinalScore().get(1) + " " ,900, 300);
        g.drawString(players.getFinalScore().get(2) + " " ,900, 400);
        g.drawString(players.getFinalScore().get(3) + " " ,900, 100);

        repaint(1);

    }

    public void addMainMenuButton(){
        JButton backButton = new JButton("<");
        backButton.setFont(Show.customFont_40f);
        backButton.setBounds( 10 ,10,60, 60);

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
        super.add(backButton);
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
        return board;
    }

    public void botPlays(){

        for (int i = 0; i < players.totalPlayers; i++) {
            Cell cell = players.bots.selectCell(board);
            players.update();
            int color = players.getColor();
            cell.setColor(color);
            List<Hex> list = setOfHexagons.stream().filter(hex -> hex.getPolygon().contains(cell.getX() , cell.getY())).toList();
            if( list.size() > 0 ){
                list.get(0).changeColor(color);
            }
            repaint();
        }
    }

    public void humanPlays(Cell cell){
        int color = 0;
        if(cell != null ){
            if(cell.getColor() == 0){

                players.update();
                color = players.getColor();
                if(players.currentPlayer >= players.totalPlayers - 1){}
            }else{
                color = cell.getColor();
            }
            cell.setColor(color);
            List<Hex> list = setOfHexagons.stream().filter(hex -> hex.getPolygon().contains(cell.getX() , cell.getY())).toList();
            if( list.size() > 0 ){
                list.get(0).changeColor(color);
            }
            repaint();
        }
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

            if(!players.gameOver(board.getNumberOfEmptyCells())){

                if(players.botTurn()){
                    botPlays();
                }else{
                    Cell cell = getCellFromMouseClick( e.getX() , e.getY());
                    int color = 0 ;
                    humanPlays(cell);
                    if(players.color == 2){
                        players.update();
                    }

                }
            }
            else{
                System.out.println("GAME OVER");
            }



        }

        /**
         * Invoked when a mouse button has been released on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseReleased(MouseEvent e) {

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

