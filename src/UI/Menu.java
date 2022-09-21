package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu {

    public int BoardSize = 0;
    public int NumberPlayers = 0;
    public int HumanOrComputer = 0; // default is human against human

    // method creates a menu pop-up and assigns the boardsize for the game
    // menuBoardSize calls menuNumberPlayers which calls menuHumanOrComputer
    public void menuBoardSize(){
        //create pop-up
        JFrame frameBoardSize = new JFrame();

        // pop-up panel + border
        JPanel panelBoardSize = new JPanel();
        panelBoardSize.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panelBoardSize.setLayout(new BorderLayout());

        //pop-up options including title and close option
        frameBoardSize.add(panelBoardSize, BorderLayout.CENTER);
        frameBoardSize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameBoardSize.setTitle("Game Menu");
        frameBoardSize.setLocation(400,200);
        frameBoardSize.pack();
        frameBoardSize.setSize(400,200);

        //add label text to menu (aka the question)
        JLabel labelBoardSize = new JLabel("Please choose size of the board (radius):");
        panelBoardSize.add(labelBoardSize, BorderLayout.NORTH);

        //buttons + press action + ASSIGNMENT of BoardSize to variable
        //variables for menuBoardSize method
        JButton buttonBoardSize3 = new JButton(new AbstractAction("3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardSize = 3;
                frameBoardSize.dispose();
                menuHumanOrComputer();
            }
        });
        JButton buttonBoardSize5 = new JButton(new AbstractAction("5") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardSize = 5;
                frameBoardSize.dispose();
                menuHumanOrComputer();
            }
        });
        JButton buttonBoardSize7 = new JButton(new AbstractAction("7") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardSize = 7;
                frameBoardSize.dispose();
                menuHumanOrComputer();
            }
        });

        // button location in pop-up
        panelBoardSize.add(buttonBoardSize3, BorderLayout.WEST);
        panelBoardSize.add(buttonBoardSize5, BorderLayout.CENTER);
        panelBoardSize.add(buttonBoardSize7, BorderLayout.EAST);

        //make menu visible (must be at end)
        frameBoardSize.setVisible(true);
    }

    /*
    //method creates a menu pop-up and assigns the number for players for the game
    public void menuNumberPlayers(){

        //create pop-up
        JFrame frameNumberPlayers = new JFrame();

        // pop-up panel + border
        //variables for menuNumberPlayers method
        JPanel panelNumberPlayers = new JPanel();
        panelNumberPlayers.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panelNumberPlayers.setLayout(new GridLayout(3,2));

        //pop-up options including title and close option
        frameNumberPlayers.add(panelNumberPlayers);
        frameNumberPlayers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameNumberPlayers.setTitle("Game Menu");
        frameNumberPlayers.setLocation(400,200);
        frameNumberPlayers.pack();
        frameNumberPlayers.setSize(400,200);

        //add label to menu (aka the question)
        JLabel labelNumberPlayers = new JLabel("Please choose size the number of players:");
        panelNumberPlayers.add(labelNumberPlayers, BorderLayout.NORTH);

        //buttons + buttonlocation + press action + ASSIGNMENT of numberPlayers to variable
        //variables for menuNumberPlayers method
        JButton buttonNumberPlayers2 = new JButton(new AbstractAction("2") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberPlayers = 2;
                frameNumberPlayers.dispose();
                menuHumanOrComputer();
            }
        });
        JButton buttonNumberPlayers3 = new JButton(new AbstractAction("3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberPlayers = 3;
                frameNumberPlayers.dispose();
            }
        });
        JButton buttonNumberPlayers4 = new JButton(new AbstractAction("4") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberPlayers = 4;
                frameNumberPlayers.dispose();
            }
        });

        // button location in pop-up
        panelNumberPlayers.add(buttonNumberPlayers2, BorderLayout.CENTER);
        panelNumberPlayers.add(buttonNumberPlayers3, BorderLayout.CENTER);
        panelNumberPlayers.add(buttonNumberPlayers4, BorderLayout.EAST);

        // visibilty of frame (must be at end)
        frameNumberPlayers.setVisible(true);
    }

    */

    //method creates a menu pop-up and assigns how the game will be played (HvsH or HvsAI)
    // assignment is 0 if human vs human and assignment is 1 if Human vs AI
    public void menuHumanOrComputer() {
        //create pop-up
        JFrame frameHumanOrComputer = new JFrame();

        // pop-up panel + border
        //variables for menuHumanOrComputer method
        JPanel panelHumanOrComputer = new JPanel();
        panelHumanOrComputer.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
        panelHumanOrComputer.setLayout(new BorderLayout());

        //pop-up options including title and close option
        frameHumanOrComputer.add(panelHumanOrComputer, BorderLayout.CENTER);
        frameHumanOrComputer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHumanOrComputer.setTitle("Game Menu");
        frameHumanOrComputer.setLocation(400,200);
        frameHumanOrComputer.pack();
        frameHumanOrComputer.setSize(400,200);

        //add label to menu
        JLabel labelHumanOrComputer = new JLabel("Please choose how you want to play):");
        panelHumanOrComputer.add(labelHumanOrComputer, BorderLayout.NORTH);

        //add label to menu (aka the question)
        JButton buttonHumanVsHuman = new JButton(new AbstractAction("Human vs Human") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberPlayers = 2;
                HumanOrComputer = 0;
                frameHumanOrComputer.dispose();
                System.out.println(BoardSize + " " +NumberPlayers + " " +HumanOrComputer);
                Grid();
            }
        });
        JButton buttonHumanVsComputer = new JButton(new AbstractAction("Human vs AI") {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberPlayers = 2;
                HumanOrComputer = 1;
                frameHumanOrComputer.dispose();
                Grid();
            }
        });

        // button location in pop-up
        panelHumanOrComputer.add(buttonHumanVsHuman, BorderLayout.WEST);
        panelHumanOrComputer.add(buttonHumanVsComputer, BorderLayout.EAST);

        // make frame visible (must be at end)
        frameHumanOrComputer.setVisible(true);


    }

    public void Grid(){
        JFrame frame = new JFrame("Draw Hexagon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid grid = new Grid(BoardSize);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
