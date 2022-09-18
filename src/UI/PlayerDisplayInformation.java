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
    public static void createTestField() {
        JFrame f = new JFrame();
        f.setSize(width, height);
        f.setLayout(new GridBagLayout());
        f.getContentPane().setBackground(new Color(238, 238, 173));
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 3;
        constraints.gridheight = 3;
        setConstraints(constraints, 1, 1, 0.0, 0.0);
        f.add(fakeBoard(), constraints);
        constraints.gridwidth = 0;
        constraints.gridheight = 0;

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        setConstraints(constraints, 0, 0, 1.0, 0.0);
        f.add(addPlayer(Color.white, "White Guy"), constraints);

        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        setConstraints(constraints, 2, 0, 0.0, 0.0);
        f.add(addPlayer(Color.black, "Black Guy"), constraints);

        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        setConstraints(constraints, 0, 2, 0.0, 0.0);
        f.add(addPlayer(Color.red, "Red Guy"), constraints);

        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        setConstraints(constraints, 2, 2, 0.0, 1.0);
        f.add(addPlayer(Color.blue, "Blue Guy"), constraints);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Ceates a fake board
     * @return fake board
     */
    private static JPanel fakeBoard() {
        JPanel board = new JPanel();

        board.setPreferredSize(new Dimension(350, 350));

        board.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f), Color.MAGENTA));
        return board;
    }

    /**
     * adds a player with specified color and name
     * @param playerColor the color of the player
     * @param playerName the name of the player
     * @return Jpanel representing the player
     */
    private static JPanel addPlayer(Color playerColor, String playerName) {
        JPanel player = new JPanel(new GridBagLayout());
        player.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f), playerColor));
        player.setPreferredSize(new Dimension(200, 100));
        player.setBackground(Color.lightGray);

        addPlayerDetails(player, Color.white, 0, playerName);

        return player;
    }

    /**
     * Adds the details to the Jpanel of the player
     * @param player the player whos detials we are adding to
     * @param pieceColor the color of the current piece
     * @param playerScore the score of the player
     * @param playerName the name of the player
     */
    public static void addPlayerDetails(JPanel player, Color pieceColor, int playerScore, String playerName) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(7, 7, 7, 7);

        Font font = new Font("Serif", Font.PLAIN, 20);

        JLabel profileLabel = new JLabel("PFP");
        profileLabel.setFont(font);

        JLabel namePanel = new JLabel(playerName);
        namePanel.setFont(font);

        JLabel pieceColorPanel = new JLabel("                     ");
        pieceColorPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.0f), Color.black));
        pieceColorPanel.setBackground(pieceColor);
        pieceColorPanel.setOpaque(true);

        JLabel scorePanel = new JLabel("Score: " + playerScore);
        scorePanel.setFont(font);

        setConstraints(constraints, 0, 0, 0.0, 0.0);
        player.add(profileLabel, constraints);

        setConstraints(constraints, 1, 0, 1.0, 0.0);
        player.add(namePanel, constraints);

        setConstraints(constraints, 0, 1, 0.0, 1.0);
        player.add(pieceColorPanel, constraints);

        setConstraints(constraints, 1, 1, 1.0, 1.0);
        player.add(scorePanel, constraints);
    }


    public static void setConstraints(GridBagConstraints constraints, int gridX, int gridY, double weightX, double weightY) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
    }

    public static void main(String[] args) {
        createTestField();
    }
}
