package UI;
import javax.swing.JFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Show extends JFrame {
    /*
        CREATING JFRAME
        I fall into the sin of using static variables. I hope, the following explanation will make you to forgive me :)
        I wanted to implement a main menu button on the game screen to come back to main menu.
        To be able to do that I created some buttons ( Menu -> PLAY BUTTON -> Game  //// Game -> MENU BUTTON (<) -> Menu )
        and assigned action listeners to them. But the problem was, we had to use only one frame and switch between 2 JPanels ,
        which are our Grid and Menu classes ( both extend JPanel) . So I choose 'static way' to have just one global JFrame which I can use everywhere.
        And to be able to use custom fonts everywhere without implementing them again and again. I also put them as static variables.

        If you run into a trouble with these variable or if you have more elegant solutions text me!!
        Emre K.
     */

    public static JFrame frame = new JFrame();
    //CREATING FONTS
    public static Font customFont_60f = null;
    public static Font customFont_50f = null;
    public static Font customFont_40f = null;
    public static Font customFont_25f = null;

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
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Show.customFont_60f);
            ge.registerFont(Show.customFont_50f);
            ge.registerFont(Show.customFont_40f);
            ge.registerFont(Show.customFont_25f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
