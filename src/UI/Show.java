package UI;
import javax.swing.JFrame;


public class Show extends  JFrame{

    public static void main(String[] args) {
        /*JFrame frame = new JFrame("Draw Hexagon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid grid = new Grid( 1);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/

        Menu menu = new Menu();
        menu.menuBoardSize();

    }

}
