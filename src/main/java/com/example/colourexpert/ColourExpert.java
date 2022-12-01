// **********************************************************************************
// Title: Color Expert
// Author: Sophia Isabel Ferrer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: ColourExpert.java
// Description:
// **********************************************************************************

package com.example.colourexpert;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;

public class ColourExpert extends Application {

    private Stage window = new Stage();
    private Scene start;
    private Scene about;
    private Scene main;
    private Scene chooseRound;
    private Group root1 = new Group();
    private Group root2 = new Group();
    private Group root3 = new Group();
    private Group root4 = new Group();

    private int rounds = 0;
    private int playerScore = 0;
    private int bestScore;

    private boolean playerStartedGame = false;
    private boolean gameOver = false;

    public int canvasWidth = 500;
    public int canvasHeight = 500;

    @Override
    public void start(Stage stage) throws IOException {
//        Scene scene = new Scene(root1, 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        window = stage;
        stage.setResizable(false); // User cannot resize the window
        stage.setTitle("ColourExpert");


        start = createStart(); // Creates the "start" scene
        about = createAbout(); // Creates the "about" scene
        main = createMain(); // Creates the main scene
        chooseRound = createChooseRound();

        CircleBox c = new CircleBox();
        start.setFill(new RadialGradient(
                0, 0, 0, 0, 1, true,   //sizing
                CycleMethod.NO_CYCLE,                       //cycling
                new Stop(0, c.getRandomColor(0,256)),    //colors (in hexadecimal)
                new Stop(1, c.getRandomColor(0,256)))
        );

        stage.setScene(chooseRound); // The first scene to show when the program runs
        stage.show();
    }

    private Scene createChooseRound() {
        Button btn1 = new Button("5 rounds");
        Button btn2 = new Button("10 rounds");
        Button btn3 = new Button("20 rounds");
        Shape Circle = new Circle(6);
        btn1.setShape(Circle);
        btn2.setShape(Circle);
        btn3.setShape(Circle);
        btn1.setPrefSize(100,50);
        btn2.setPrefSize(100,50);
        btn3.setPrefSize(100,50);
        setLayout(btn1, 200, 150);
        setLayout(btn2, 200, 200);
        setLayout(btn3, 200, 250);

        btn1.setOnAction(e -> {
            setUpGame(5);
            System.out.println(rounds);
            switchScenes(main);
        });

        root4.getChildren().addAll(btn1, btn2, btn3);
        chooseRound = new Scene(root4, canvasWidth, canvasHeight);
        chooseRound.setFill(Color.BLACK);
        return chooseRound;
    }

    private Scene createMain() {
        Button btn1 = new Button("LEFT");
        Button btn2 = new Button("RIGHT");
        setLayout(btn1, 100, 350);
        setLayout(btn2, 350, 350);

        newComparison(200);

//        int i = 0;
//        while (playerStartedGame) {
//            newComparison(200);
//            System.out.println(rounds);
//        }

        root1.getChildren().addAll(btn1, btn2);
        main = new Scene(root1, canvasWidth, canvasHeight);
        main.setFill(Color.BLACK);
        return main;
    }

    public void setUpGame(int num) {
        playerStartedGame = true;
        rounds = num;
    }

    public void increment(int i) {
        i++;
    }

    public void setLayout(Control obj, int x, int y) {
        obj.setLayoutX(x);
        obj.setLayoutY(y);
    }

    // Creates two new game boxes
    public void newComparison(int difference) {
        int higherRange = getHigherRange(difference);
        CircleBox c = new CircleBox();
        c.createCircleBox(root1, 100, 200, getLowerRange(difference, higherRange), higherRange);
        System.out.println(c.getColorCounter());
        c.createCircleBox(root1, 350, 200, getLowerRange(difference, higherRange), higherRange);
        System.out.println(c.getColorCounter());
    }

    public int getHigherRange(int difference) {
        Random rand = new Random();
        int num = rand.nextInt(256);
        if (num <= difference) {
            while (num <= difference) {
                num++;
            }
            System.out.println(num);
            return num;
        } else if (num > difference) {
            System.out.println(num);
            return num;
        } else {
            return 256;
        }
    }

    public int getLowerRange(int difference, int higherRange) {
        int num = higherRange - difference;
        return num;
    }

    private Scene createStart() throws FileNotFoundException {

        // Labels
        Label lbl1 = new Label("Color Expert");
        lbl1.setFont(Font.font(40));
        setLayout(lbl1, 140, 140);


        // Button
        // Buttons to switch scenes
        Button btn1 = new Button("Start!");
        Shape Circle = new Circle(6);
        btn1.setShape(Circle);
        btn1.setPrefSize(100,50);
        btn1.setLayoutX(200);
        btn1.setLayoutY(220);
        btn1.setOnAction(e -> switchScenes(about));

        root3.getChildren().addAll(lbl1, btn1);
        start = new Scene(root3, canvasWidth, canvasHeight);

        return start;
    }

    private Scene createAbout() {

        // Labels
        Label lbl1 = new Label("Welcome to Color Expert!");
        Label lbl2 = new Label("OBJECTIVE:\n" +
                "Determine which picture has the most amount of a certain color.\n\n" +
                "HOW TO PLAY:\n");
        Label lbl3 = new Label("- Two groups of shapes will be shown on the screen,\n" +
                "   each with a random amount of colors.\n" +
                "- On the top of the screen will be an indicator of what color you are gauging.\n" +
                "- Lock in your answer with the corresponding button\n" +
                "at the bottom of the screen.\n" +
                "- Got it correct? You earn a point!\n" +
                "- The game ends once you have finished all the rounds.\n\n" +
                "                            Ready? Hit the button below!");

        lbl1.setFont(Font.font(30));
        setLayout(lbl1, 80, 60);
        setLayout(lbl2, 80, 120);
        setLayout(lbl3, 70, 200);
        lbl1.setTextFill(Color.WHITE);
        lbl2.setTextFill(Color.WHITE);
        lbl3.setTextFill(Color.WHITE);
        lbl2.setTextAlignment(TextAlignment.CENTER);

        // Button
        // Buttons to switch scenes
        Button btn1 = new Button("Let's Play!");
        setLayout(btn1,215,380);
        btn1.setOnAction(e -> switchScenes(main));

        root2.getChildren().addAll(btn1, lbl1, lbl2, lbl3);
        about = new Scene(root2, canvasWidth, canvasHeight);
        about.setFill(Color.BLACK);

        return about;
    }

    // Stores the player's score by writing to a file
    private void storePlayerScore(int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"));
            writer.write(score);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("score.txt"));
            System.out.println(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchScenes(Scene scene) {
        window.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}