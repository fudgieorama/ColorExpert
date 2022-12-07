package com.example.colourexpert;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.security.Provider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CircleBox {
    ColourExpert main = new ColourExpert();
    private Set<Double> colorCounter = new HashSet<>();
    // Getter
    public Set<Double> getColorCounter() {
        return colorCounter;
    }

    // Constructor
    public CircleBox(Group root, int x, int y, int lowerRange, int higherRange, int guessingColor) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                createCircle(root, x, y, lowerRange, higherRange, guessingColor);
                x += 20;
            }
            x -= 20 * 3;
            y += 20;
        }
    }

    private Circle createCircle(Group root, double x, double y, int lowerRange, int higherRange, int guessingColor) {
        double score = 0;

        Circle c = new Circle(x, y, 10);
        Color color = main.getRandomColor(lowerRange, higherRange);
        score = determineColorValue(color, guessingColor);
        System.out.println(score);
        c.setFill(color);
        root.getChildren().add(c);
        colorCounter.add(score);
        return c;
    }

    private double determineColorValue(Color color, int guessingColor) {
        double colorValue = 0;
        if (guessingColor == 0) {
            colorValue = color.getRed();
        } else if (guessingColor == 1) {
            colorValue = color.getGreen();
        } else if (guessingColor == 2) {
            colorValue = color.getBlue();
        }
        return colorValue;
    }

    private double getCircleBoxScore() {
        double score = 0;
        Double sum = colorCounter.stream().mapToDouble(Double::doubleValue).sum();
        return (double) sum;
    }
}

