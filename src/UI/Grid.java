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


    public Grid(int radius){

        this.board = new Board(radius);
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

        for (int i = 0; i < setOfCells.size(); i++) {
            g.drawString(setOfCells.get(i).getQ() + " " + setOfCells.get(i).getR(), (int)setOfCells.get(i).getX(), (int)setOfCells.get(i).getY());
//            g.drawString(setOfCells.get(i).toString(), (int)setOfCells.get(i).getX(), (int)setOfHexagons.get(i).getCoordinates().getY());
            //System.out.println(setOfCells.get(i).toString() + " " + (int)setOfCells.get(i).getX() + " " + (int)setOfCells.get(i).getY());
        }

        //repaint(1);

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
            Cell cell = getCellFromMouseClick( e.getX() , e.getY());
            int color = 0 ;
            if(cell != null ){

                if(SwingUtilities.isLeftMouseButton(e)){
                    color = 1;
                }else if (SwingUtilities.isRightMouseButton(e))
                    color = 2;

                cell.setColor(color);
                List<Hex> list = setOfHexagons.stream().filter(hex -> hex.getPolygon().contains(e.getX() , e.getY())).toList();

                if( list.size() > 0 ){
                    list.get(0).changeColor(color);
//                    System.out.println(board.numberOfPiecesConnectedToCell(cell.getColor(), cell));
                }
                System.out.println("----------------");
                repaint();

                //FOR TESTING
                for (int i = 1; i <= 2; i++) {
                    System.out.print(((i==1) ? "WHITE = " : "BLACK = ") + board.scoreOfAPlayer(i) + "\n");
                    board.setAllCellsToNotVisited();
                }
                if(SwingUtilities.isMiddleMouseButton(e)){
                    System.out.println();
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

