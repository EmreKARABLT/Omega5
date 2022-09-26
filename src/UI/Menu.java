package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel{

    public int boardSize = 3;
    public final int numberOfPlayers = 2;
    public int HumanOrComputer = 0; // default is human against human
    public int numberOfAiPlayers = 0;

    // method creates a menu pop-up and assigns the boardsize for the game
    // menuBoardSize calls menuNumberPlayers which calls menuHumanOrComputer
    public void menuBoardSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() - 100);
        int height = (int) (screenSize.getHeight() - 100);
        System.out.println(width + " " + height);
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File("src/UI/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image img = null;
        if(originalImage != null )
            img = originalImage.getScaledInstance( originalImage.getWidth()/3, originalImage.getHeight()/3, Image.SCALE_AREA_AVERAGING);

//create the font

        Font customFont_60f = null;
        Font customFont_50f = null;
        Font customFont_40f = null;
        try {
            //create the font to use. Specify the size!
            customFont_60f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(60f);
            customFont_50f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(50f);
            customFont_40f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont_50f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }





        //create pop-up
        JFrame frame = new JFrame(){};
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Image finalImg = img;
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalImg != null)
                {
                    int x = this.getParent().getWidth()/2 - finalImg.getWidth(null)/2;
                    int y = this.getParent().getHeight()/2 - finalImg.getHeight(null)/2;
                    g.drawImage(finalImg,x,y,this);
                }
        }};
        //        panel.setBackground(Color.GRAY);
        panel.setLayout(null);
        frame.add(panel);

        JLabel label_boardSize = new JLabel("SIZE OF THE BOARD");
        if(customFont_50f != null) label_boardSize.setFont(customFont_50f)  ;
        label_boardSize.setForeground(Color.LIGHT_GRAY);
        panel.add(label_boardSize);

        Dimension size_l1 = label_boardSize.getPreferredSize();
        label_boardSize.setBounds((width - size_l1.width) / 10 * 5, (height - size_l1.height) / 10 * 2, size_l1.width, size_l1.height);

        JLabel label_Players = new JLabel("PLAYERS");
        if(customFont_50f != null) label_Players.setFont(customFont_50f)  ;
        label_Players.setForeground(Color.LIGHT_GRAY);
        panel.add(label_Players);

        Dimension size_l2 = label_Players.getPreferredSize();
        label_Players.setBounds((width - size_l2.width) / 10 * 5, (height - size_l2.height) / 10 * 5, size_l2.width, size_l2.height);



        //buttons + press action + ASSIGNMENT of BoardSize to variable
        //variables for menuBoardSize method
        JButton buttonBoardSize3 = new JButton("3");
        JButton buttonBoardSize5 = new JButton("4");
        JButton buttonBoardSize7 = new JButton("5");

        JButton[] buttons = new JButton[]{buttonBoardSize3, buttonBoardSize5, buttonBoardSize7};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds( (width - 500 )/2 + 200 * i    , (height - size_l1.height) / 10 * 3, 100, 100);
            buttons[i].setFont( customFont_50f );
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
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


                    //Add MouseListener to move the component

                }
            });
        }


        JButton humanVhuman = new JButton();
        humanVhuman.setText("<html><center>HUMAN<br>vs<br>HUMAN</center></html>\n");
        JButton humanVcomputer = new JButton();
        humanVcomputer.setText("<html><center>HUMAN<br>vs<br>COMPUTER</center></html>\n");


        JButton[] buttons_player = new JButton[]{humanVhuman, humanVcomputer};

        for (int i = 0; i < buttons_player.length; i++) {

            buttons_player[i].setBounds( (width - 750 )/2 + 500 * i    , (height - size_l1.height) / 10 * 6, 250, 170);
            buttons_player[i].setFont( customFont_40f );
            buttons_player[i].setOpaque(false);
            buttons_player[i].setContentAreaFilled(false);
            buttons_player[i].setBorderPainted(false);
            buttons_player[i].setForeground(Color.RED);
            buttons_player[i].setFocusPainted(false);
            panel.add(buttons_player[i]);
            buttons_player[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0 ; j < buttons_player.length ; j++ ) {
                        JButton clickedButton = (JButton) e.getSource();
                        if(buttons_player[j] != clickedButton) {
                            buttons_player[j].setForeground(Color.RED);
                            if( j == 0 ){
                                HumanOrComputer = 0 ;
                            }else{
                                HumanOrComputer = 1 ;
                            }

                        }
                        clickedButton.setForeground(Color.ORANGE);
                    }
                    //Add MouseListener to move the component

                }
            });

        }
        // button location in pop-up
        JButton playButton = new JButton("PLAY");
        playButton.setBounds( (width - 300 )/2     , (height - size_l1.height) / 10 * 8, 300, 100);

        playButton.setFont( customFont_60f );
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setForeground(Color.YELLOW);
        playButton.setFocusPainted(false);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.getContentPane().removeAll();
                frame.setContentPane(new Grid(boardSize));
                frame.revalidate();
            }
        });
        panel.add(playButton);

        //make menu visible (must be at end)
        frame.setTitle("OMEGA-5 ");
        frame.setLocation(((int) screenSize.getWidth() - width) / 2, ((int) screenSize.getHeight() - height) / 3);
        frame.setSize(width, height);
        frame.setVisible(true);

    }


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
                HumanOrComputer = 0;

                Grid();
            }
        });
        JButton buttonHumanVsComputer = new JButton(new AbstractAction("Human vs AI") {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        Grid grid = new Grid(boardSize);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
