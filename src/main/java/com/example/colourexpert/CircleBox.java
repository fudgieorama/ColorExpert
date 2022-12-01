package com.example.colourexpert;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Random;

public class CircleBox {
    private int redScore = 0;
    private int greenScore = 0;
    private int blueScore = 0;
    HashMap<Circle, Integer> colorCounter = new HashMap<>();

    // Getters
    public int getRedScore() {
        return redScore;
    }
    public int getGreenScore() {
        return greenScore;
    }
    public int getBlueScore() {
        return blueScore;
    }
    public HashMap<Circle, Integer> getColorCounter() {
        return colorCounter;
    }

    public void createCircleBox(Group root, int x, int y, int lowerRange, int higherRange) {
        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                createCircle(root, x, y, lowerRange, higherRange);
                x += 20;
            }
            x -= 20 * 3;
            y +=20;
        }
    }

    private void createCircle(Group root, double x, double y, int lowerRange, int higherRange) {
        int score = 0;

        AnchorPane anchorPane = new AnchorPane();
        Circle c = new Circle(x,y,10);
        c.setFill(getRandomColor(lowerRange, higherRange));
        anchorPane.getChildren().add(c);
        root.getChildren().add(anchorPane);
        colorCounter.put(c, score);
    }

    public Color getRandomColor(int lowerRange, int higherRange) {
        Random rand = new Random();
        int r = lowerRange + rand.nextInt(higherRange-lowerRange);
        int g = lowerRange + rand.nextInt(higherRange-lowerRange);
        int b = lowerRange + rand.nextInt(higherRange-lowerRange);
        Color color = Color.rgb(r,g,b,1);
        return color;
    }

    private boolean determineColorValue(int colorValue) {
        if (colorValue > 0 && colorValue < 50) {
            return true;
        } else {
            return false;
        }
    }
}
