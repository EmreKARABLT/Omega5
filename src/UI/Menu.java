package UI;

import GAME.State;
import PLAYER.MonteCarlo;
import PLAYER.HumanPlayer;
import PLAYER.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Menu extends JPanel{

    public int boardSize = 3;
    public final int numberOfPlayers = 2;
    public int HumanOrComputer = 0; // default is human against human
    public int numberOfAiPlayers = 0;
    public JPanel panel ;
    public Player whitePlayer =new HumanPlayer("White");
    public Player blackPlayer = new HumanPlayer("Black");

    private Menu(){
        menuBoardSize();
    }
    //SINGLETON DESIGN PATTERN
    //By using singleton method ,instead of creating new menus each time when we clicked on main menu button on Game Page,
    //we can use getInstance method. which will creates the menu for the first time and returns it ,if menu is created before it will return
    // previously created menu.
    private static Menu menu ;
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

        System.out.println("Check the first comment in Board and replace the following line with the variables in Board class");
        System.out.println("private int offsetX = " + width/2  +" , " + "offsetY = " + height/2 + ";");

        //LOADING BACKGROUND
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File("src/UI/background.jpg"));
        } catch (IOException ignored) {}
        Image img = null;
        if(originalImage != null )
            img = originalImage.getScaledInstance( originalImage.getWidth()/3, originalImage.getHeight()/3, Image.SCALE_AREA_AVERAGING);
        Image finalImg = img;

        //SETS FRAME SIZE
        Show.frame.setPreferredSize(new Dimension(width, height));

        //CREATING PANEL
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalImg != null)
                {
                    int x = this.getParent().getWidth()/2 - finalImg.getWidth(null)/2;
                    int y = this.getParent().getHeight()/2 - finalImg.getHeight(null)/2;
                    g.drawImage(finalImg,x,y,this);
                }
                else
                    this.setBackground(new Color(92, 130, 117));
        }};
        panel.setLayout(null);
        Show.frame.add(panel);

        //LABEL BOARD SIZE
        JLabel label_boardSize = new JLabel("SIZE OF THE BOARD");
        if(Show.customFont_50f != null) label_boardSize.setFont(Show.customFont_50f)  ;
        label_boardSize.setForeground(Color.LIGHT_GRAY);
        panel.add(label_boardSize);

        Dimension size_l1 = label_boardSize.getPreferredSize();
        label_boardSize.setBounds((width - size_l1.width) / 2, (height - size_l1.height) / 10 * 2, size_l1.width, size_l1.height);

        //LABEL PLAYERS
        JLabel label_Players = new JLabel("PLAYERS");
        if(Show.customFont_50f != null) label_Players.setFont(Show.customFont_50f)  ;
        label_Players.setForeground(Color.LIGHT_GRAY);
        panel.add(label_Players);

        Dimension size_l2 = label_Players.getPreferredSize();
        label_Players.setBounds((width - size_l2.width) / 2, (height - size_l2.height) / 10 * 5, size_l2.width, size_l2.height);


        //BUTTONS FOR BOARD SIZE AND ACTION LISTENERS
        JButton buttonBoardSize3 = new JButton(boardSize+"");
        JButton buttonBoardSize5 = new JButton("4");
        JButton buttonBoardSize7 = new JButton("5");

        JButton[] buttons = new JButton[]{buttonBoardSize3, buttonBoardSize5, buttonBoardSize7};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds( (width - 500 )/2 + 200 * i    , (height - size_l1.height) / 10 * 3, 100, 100);
            buttons[i].setFont( Show.customFont_50f );
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);

            if(i==0){
                buttons[i].setForeground(Color.ORANGE);
            }else
                buttons[i].setForeground(Color.RED);

            buttons[i].setFocusPainted(false);
            panel.add(buttons[i]);
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (JButton button: buttons ) {
                        JButton clickedButton = (JButton) e.getSource();
                        if(button != clickedButton){
                            button.setForeground(Color.RED);
                        }
                        clickedButton.setForeground(Color.ORANGE);
                        boardSize = Integer.parseInt(clickedButton.getText());
                    }
                }
            });
        }

        //BUTTONS FOR PLAYER OPTIONS AND ACTION LISTENERS
        JButton humanVhuman = new JButton();
        humanVhuman.setText("<html><center>HUMAN<br>vs<br>HUMAN</center></html>\n");
        JButton humanVcomputer = new JButton();
        humanVcomputer.setText("<html><center>HUMAN<br>vs<br>COMPUTER</center></html>\n");


        JButton[] buttons_player = new JButton[]{humanVhuman, humanVcomputer};

        for (int i = 0; i < buttons_player.length; i++) {

            buttons_player[i].setBounds( (width - 750 )/2 + 500 * i    , (height - size_l1.height) / 10 * 6, 250, 170);
            buttons_player[i].setFont( Show.customFont_40f );
            buttons_player[i].setOpaque(false);
            buttons_player[i].setContentAreaFilled(false);
            buttons_player[i].setBorderPainted(false);
            buttons_player[i].setFocusPainted(false);

            if(i==0){
                buttons_player[i].setForeground(Color.ORANGE);
            }else
                buttons_player[i].setForeground(Color.RED);

            panel.add(buttons_player[i]);
            buttons_player[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0 ; j < buttons_player.length ; j++ ) {
                        JButton clickedButton = (JButton) e.getSource();
                        if(clickedButton.getText().contains("COMPUTER")){
                            blackPlayer = new MonteCarlo("Black");
                        }
                        if (buttons_player[j] != clickedButton) {

                            buttons_player[j].setForeground(Color.RED);
                        }
                        clickedButton.setForeground(Color.ORANGE);
                    }
                }
            });

        }

        //PLAY BUTTON AND ACTION LISTENER
        JButton playButton = new JButton("PLAY");
        playButton.setBounds( (width - 300 )/2     , (height - size_l1.height) / 10 * 8, 300, 80);

        playButton.setFont( Show.customFont_60f );
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setForeground(Color.YELLOW);
        playButton.setFocusPainted(false);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //Players ArrayList
                ArrayList<Player> players = new ArrayList<>(){};
                Player.counterForIDs = 0 ;
                players.add(whitePlayer);
//                players.add(new RandomBot("Black") );
                players.add(blackPlayer);
                Grid grid = new Grid(new State(boardSize ,players ));
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
