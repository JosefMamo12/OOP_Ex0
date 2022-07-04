package gui;

import ex0.Building;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    ElevatorDrawer ed;

    public MyFrame(Building b) {
        ed = new ElevatorDrawer(b);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        this.setLayout(null);
        this.setSize(new Dimension(1000, 800));
        this.setTitle("Elevators");
        this.add(ed);
        this.setVisible(true);

    }

}
