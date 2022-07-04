package gui;

import ex0.Building;

import javax.swing.*;
import java.awt.*;

public class ElevatorDrawer extends JPanel {
    private final Building _building;
    private final int elevNum;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 800;
    private static final int ELEVATOR_WIDTH = 50;
    private static final int ELEVATOR_HEIGHT = 20;
    private static final int BUILDING_WIDTH = 100;
    private static final int FLOOR_HEIGHT = 100;


    public ElevatorDrawer(Building b) {
        _building = b;
        elevNum = b.numberOfElevetors();
        this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setBackground(Color.white);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBuilding(g2d);
        drawElevator(g2d);


    }

    private void drawElevator(Graphics2D g) {
        final int paddleX = 200, paddleY = 100;
        int mid = SCREEN_WIDTH / 2;
        for (int i = 0; i < elevNum; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(mid - (paddleX * elevNum / 2) + i * paddleX, SCREEN_HEIGHT - ELEVATOR_HEIGHT - paddleY / 2 + 10, ELEVATOR_WIDTH, ELEVATOR_HEIGHT);


        }
    }

    private void drawBuilding(Graphics2D g2d) {
        int paddleX = 200, paddleY = 300;
        final int mid = SCREEN_WIDTH / 2;
        for (int i = 0; i < elevNum; i++) {
            for (int j = 0; j < _building.maxFloor(); j++) {
                g2d.drawRect(mid - (paddleX * elevNum / 2) + i * paddleX - 25, (SCREEN_HEIGHT + 10 - paddleY / 2) - j * 25, BUILDING_WIDTH, FLOOR_HEIGHT);

            }

        }
    }
}
