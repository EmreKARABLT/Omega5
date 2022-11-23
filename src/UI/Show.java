package UI;
import javax.swing.JFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Show extends JFrame {

    public static JFrame frame = new JFrame();
    //CREATING FONTS
    public static Font customFont_60f = null;
    public static Font customFont_50f = null;
    public static Font customFont_40f = null;
    public static Font customFont_25f = null;
    public static Font customFont_20f = null;

    public static void main(String[] args) {
        createFonts();
        Menu.getInstance();
    }
    public static void createFonts(){

        try {
            //create the font to use. Specify the size!
            Show.customFont_60f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(70f);
            Show.customFont_50f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(50f);
            Show.customFont_40f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(35f);
            Show.customFont_25f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(25f);
            Show.customFont_20f = Font.createFont(Font.TRUETYPE_FONT, new File("src/UI/RubikDirt-Regular.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Show.customFont_60f);
            ge.registerFont(Show.customFont_50f);
            ge.registerFont(Show.customFont_40f);
            ge.registerFont(Show.customFont_25f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            Show.customFont_60f = new Font("Arial", Font.BOLD, 60);
            Show.customFont_50f = new Font("Arial", Font.BOLD, 50);
            Show.customFont_40f = new Font("Arial", Font.BOLD, 40);
            Show.customFont_25f = new Font("Arial", Font.BOLD, 25);
        }
    }
}
