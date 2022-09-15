package UI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import PLAYER.Player;

public class PlayerDisplayInformation {
    private static final int width = 800;
    private static final int height = 600;

    /**
     * tmporary test field
     */
    public static void createTestField(){
        JFrame f=new JFrame();//creating instance of JFrame
        f.setSize(width,height);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

        addPlayer(f,0,0, Color.gray);
        addPlayer(f,width-215,0, Color.red);
        addPlayer(f,0,height-125, Color.green);
        addPlayer(f,width-215,height-125, Color.yellow);

        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void addPlayer(JFrame frame, int x, int y, Color color){
        JPanel player = new JPanel(new GridBagLayout());
        player.setBackground(color);

        player.setBounds(x, y, 200, 100);

        addPlayerDetails(player);

        frame.add(player);
    }

    public static void addPlayerDetails(JPanel player){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,5,5,5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel t1 = new JLabel("test1");
        t1.setFont(new Font("Serif", Font.PLAIN, 20));
        player.add(t1, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        JLabel t2 = new JLabel("test2");
        t2.setFont(new Font("Serif", Font.PLAIN, 20));
        player.add(t2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel t3 = new JLabel("test3");
        t3.setFont(new Font("Serif", Font.PLAIN, 20));
        player.add(t3, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        JLabel t4 = new JLabel("test4");
        t4.setFont(new Font("Serif", Font.PLAIN, 20));
        player.add(t4, constraints);
    }


    public static void main(String[] args) {
        createTestField();
    }
}
