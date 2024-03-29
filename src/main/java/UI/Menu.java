package UI;

import GAME.Board;
import GAME.State;
import PLAYER.Hybrid.Hybrid;
import PLAYER.MTS.SELECTION_HEURISTICS.*;
import PLAYER.MTS.MonteCarlo;
import PLAYER.HumanPlayer;
import PLAYER.Player;
import PLAYER.RULE_BASED_BOT.RuleBasedBot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Menu extends JPanel {

    public int boardSize = 3;
    public final int numberOfPlayers = 2;
    public int HumanOrComputer = 0; // default is human against human
    public int numberOfAiPlayers = 0;
    public JPanel panel;
    public Player whitePlayer = new HumanPlayer("White");
    public Player blackPlayer = new HumanPlayer("Black");
    public int offSetX;
    public int offSetY;

    private Menu() {
        menuBoardSize();
    }

    //SINGLETON DESIGN PATTERN
    //By using singleton method ,instead of creating new menus each time when we clicked on main menu button on Game Page,
    //we can use getInstance method. which will creates the menu for the first time and returns it ,if menu is created before it will return
    // previously created menu.
    private static Menu menu;

    public static Menu getInstance() {
        if (menu == null) {
            menu = new Menu();
            return menu;
        }
        return menu;
    }

    public JPanel getPanel() {
        return panel;
    }

    //
    public void menuBoardSize() {
        //GETTING SCREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() - 100);
        int height = (int) (screenSize.getHeight() - 100);
        offSetX = width / 2;
        offSetY = height / 2;

        //LOADING BACKGROUND
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File("src/main/java/UI/background.jpg"));
        } catch (IOException ignored) {
        }
        Image img = null;
        if (originalImage != null)
            img = originalImage.getScaledInstance(originalImage.getWidth() / 3, originalImage.getHeight() / 3, Image.SCALE_AREA_AVERAGING);
        Image finalImg = img;

        //SETS FRAME SIZE
        Show.frame.setPreferredSize(new Dimension(width, height));

        //CREATING PANEL
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalImg != null) {
                    int x = this.getParent().getWidth() / 2 - finalImg.getWidth(null) / 2;
                    int y = this.getParent().getHeight() / 2 - finalImg.getHeight(null) / 2;
                    g.drawImage(finalImg, x, y, this);
                } else
                    this.setBackground(new Color(92, 130, 117));
            }
        };
        panel.setLayout(null);
        Show.frame.add(panel);

        //LABEL BOARD SIZE
        JLabel label_boardSize = new JLabel("SIZE OF THE BOARD");
        if (Show.customFont_50f != null) label_boardSize.setFont(Show.customFont_50f);
        label_boardSize.setForeground(Color.LIGHT_GRAY);
        panel.add(label_boardSize);

        Dimension size_l1 = label_boardSize.getPreferredSize();
        label_boardSize.setBounds((width - size_l1.width) / 2, (height - size_l1.height) / 10 * 2, size_l1.width, size_l1.height);

        //LABEL PLAYERS
        JLabel label_Players = new JLabel("PLAYERS");
        if (Show.customFont_50f != null) label_Players.setFont(Show.customFont_50f);
        label_Players.setForeground(Color.LIGHT_GRAY);
        panel.add(label_Players);

        Dimension size_l2 = label_Players.getPreferredSize();
        label_Players.setBounds((width - size_l2.width) / 2, (height - size_l2.height) / 10 * 5, size_l2.width, size_l2.height);


        //BUTTONS FOR BOARD SIZE AND ACTION LISTENER

//        JButton buttonBoardSize1 = new JButton("1");
        JButton buttonBoardSize2 = new JButton("2");
        JButton buttonBoardSize3 = new JButton("3");
        JButton buttonBoardSize5 = new JButton("4");
        JButton buttonBoardSize7 = new JButton("5");

        JButton[] buttons = new JButton[]{ buttonBoardSize2, buttonBoardSize3, buttonBoardSize5, buttonBoardSize7};
//        JButton[] buttons = new JButton[]{ buttonBoardSize1,buttonBoardSize2, buttonBoardSize3, buttonBoardSize5, buttonBoardSize7};

        JPanel panel_white = new JPanel();
        panel_white.setForeground(Color.PINK);
        ButtonGroup whites = new ButtonGroup();
        JRadioButton humanW = new JRadioButton( "Human White" , new ImageIcon("src/UI/transparent_radio.png"));
        JRadioButton uctW = new JRadioButton(   "UCT White"  , new ImageIcon("src/UI/transparent_radio.png"));
        JRadioButton usb1W = new JRadioButton(  "UCB1 White"  , new ImageIcon("src/UI/transparent_radio.png"));
        whites.add(humanW);
        whites.add(uctW);
        whites.add(usb1W);
        panel_white.add(humanW);
        panel_white.add(uctW);
        panel_white.add(usb1W);
        humanW.setSelected(true);

        JPanel panel_black = new JPanel();
        ButtonGroup blacks = new ButtonGroup();
        JRadioButton humanB = new JRadioButton("Human Black", new ImageIcon("src/UI/transparent_radio.png"));
        JRadioButton uctB  = new JRadioButton("UCT Black", new ImageIcon("src/UI/transparent_radio.png"));
        JRadioButton ucb1B = new JRadioButton("UCB1 Black", new ImageIcon("src/UI/transparent_radio.png"));
        JRadioButton ucb1BNN = new JRadioButton("Hybrid Black", new ImageIcon("src/UI/transparent_radio.png"));
        blacks.add(humanB);
        blacks.add(uctB);
        blacks.add(ucb1B);
        blacks.add(ucb1BNN);
        panel_black.add(humanB);
        panel_black.add(uctB);
        panel_black.add(ucb1B);
        panel_black.add(ucb1BNN);
        humanB.setSelected(true);



        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds((width - buttons.length * 200 - 100) / 3 + 300 * i, (height - size_l1.height) / 10 * 3, 100, 100);
            buttons[i].setFont(Show.customFont_50f);
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);

            if (buttons[i].getText().equalsIgnoreCase(Integer.toString(boardSize))) {
                buttons[i].setForeground(Color.ORANGE);
            } else
                buttons[i].setForeground(Color.RED);

            buttons[i].setFocusPainted(false);
            panel.add(buttons[i]);
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(ucb1BNN.isSelected() ){
                        return;
                    }
                    for (JButton button : buttons) {
                        JButton clickedButton = (JButton) e.getSource();
                        if (button != clickedButton) {
                            button.setForeground(Color.RED);
                        }
                        clickedButton.setForeground(Color.ORANGE);
                        boardSize = Integer.parseInt(clickedButton.getText());
                    }
                }
            });
        }
        Player.counterForIDs = 0 ;
        //BUTTONS FOR PLAYER OPTIONS AND ACTION LISTENERS


        JRadioButton[] radioButtons = new JRadioButton[]{humanB,uctB ,ucb1B ,ucb1BNN,humanW,uctW ,usb1W };
//        JRadioButton[] radioButtons = new JRadioButton[]{humanB,uctB ,usb1B ,raveB,humanW,uctW ,usb1W ,raveW};
//        JRadioButton[] radioButtons = new JRadioButton[]{humanB,uctB ,usb1B ,humanW,uctW ,usb1W };
        JPanel[] player_panels = new JPanel[]{panel_white, panel_black};

        for (int i = 0; i < player_panels.length; i++) {
            player_panels[i].setBounds((width - 750) / 2 + 500 * i, (height - size_l1.height) / 10 * 6, 250, 170);
            player_panels[i].setFont(Show.customFont_40f);
            player_panels[i].setOpaque(false);
            panel.add(player_panels[i]);

        }
        for (JRadioButton radioButton  : radioButtons  ) {

            if(radioButton.isSelected())
                radioButton.setForeground(Color.ORANGE);
            else
                radioButton.setForeground(Color.RED);

            radioButton.setFocusPainted(false);
            radioButton.setOpaque(false);
            radioButton.setFont(Show.customFont_20f);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ((e.getSource()).equals(ucb1BNN)) {
                        for (JButton button : buttons) {
                            if (button != buttonBoardSize3) {
                                button.setForeground(Color.GRAY);
                            }
                        }
                        if (boardSize != 3) {
                            buttonBoardSize3.setForeground(Color.ORANGE);
                            boardSize = 3;
                        }
                    }else {
                        for (JButton button : buttons) {
                            if (button.getForeground().equals(Color.GRAY)) {
                                button.setForeground(Color.RED);
                            }
                        }
                    }
                    for (JRadioButton button : radioButtons) {
                        if (button.isSelected())
                            button.setForeground(Color.ORANGE);
                        else
                            button.setForeground(Color.RED);
                    }

                    if(radioButton.getText().contains("White")) {
                        switch (radioButton.getText()) {
                                case "Human White" -> whitePlayer = new HumanPlayer("White");
                                case "UCT White" -> whitePlayer = new MonteCarlo("White", new UCT());
                                case "UCB1 White" -> whitePlayer = new MonteCarlo("White", new UCB1());


                            }
                        }
                    if(radioButton.getText().contains("Black")) {
                        switch (radioButton.getText()) {
                            case "Human Black" -> blackPlayer = new HumanPlayer("Black");
                            case "UCT Black" -> blackPlayer = new MonteCarlo("Black", new UCT());
                            case "UCB1 Black" -> blackPlayer = new MonteCarlo("Black", new UCB1());
                            case "Hybrid Black" -> blackPlayer = new Hybrid("black", new UCB1NN());

                        }

                    }
                }
            });
        }


            //PLAY BUTTON AND ACTION LISTENER
        JButton playButton = new JButton("PLAY");
        playButton.setBounds((width - 300) / 2, (height - size_l1.height) / 10 * 8, 300, 80);

        playButton.setFont(Show.customFont_50f);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setForeground(Color.YELLOW);
        playButton.setFocusPainted(false);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Players ArrayList
                ArrayList<Player> players = new ArrayList<>() {};
                players = new ArrayList<>();
                players.add(whitePlayer);
                players.add(blackPlayer);
                Grid grid = new Grid(new State(new Board(boardSize, offSetX, offSetY), players));
                Show.frame.setContentPane(grid);
                Show.frame.revalidate();


            }
        });
        panel.add(playButton);

        //FRAME SETTINGS and VIEW THE MENU
        Show.frame.setTitle("OMEGA-5 ");
        Show.frame.setLocation(((int) screenSize.getWidth() - width) / 2, ((int) screenSize.getHeight() - height) / 3);
        Show.frame.setSize(width, height);
        Show.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Show.frame.setVisible(true);
    }


}

