package UI;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.*;
import java.util.Arrays;
import java.util.LinkedList;

public class Show extends  JFrame{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Hexagon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid grid = new Grid( 4);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
