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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;

public class ColourExpert extends Application {

    private Stage window = new Stage();
    private Scene start;
    private Scene about;
    private Scene main;

    private Group root1 = new Group();
    private Group root2 = new Group();
    private Group root3 = new Group();

    private ShapeBox<Circle> circleShapeBox = new ShapeBox<>();

    private int playerScore = 0;

    private int bestScore;

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

        stage.setScene(main); // The first scene to show when the program runs
        stage.show();
    }

    private Scene createMain() {

        // Stores the player's score by writing to a file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"));
            writer.write(playerScore);
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


        AnchorPane anchorPane = new AnchorPane();
        Circle circle = new Circle(1);
        circle.setLayoutX(50);
        circle.setLayoutY(50);
        anchorPane.getChildren().add(circle);
        root1.getChildren().add(anchorPane);
        main = new Scene(root1, canvasWidth, canvasHeight);
        return main;
    }

    private Scene createStart() {

        // Labels
        Label lbl1 = new Label("Color Expert");
        lbl1.setFont(Font.font(40));
        lbl1.setLayoutX(140);
        lbl1.setLayoutY(140);


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
        lbl1.setLayoutX(80);
        lbl1.setLayoutY(60);
        lbl2.setLayoutX(80);
        lbl2.setLayoutY(120);
        lbl2.setTextAlignment(TextAlignment.CENTER);
        lbl3.setLayoutX(70);
        lbl3.setLayoutY(200);

        // Button
        // Buttons to switch scenes
        Button btn1 = new Button("Let's Play!");
        btn1.setLayoutX(215);
        btn1.setLayoutY(380);
        btn1.setOnAction(e -> switchScenes(main));

        root2.getChildren().addAll(lbl1, lbl2, lbl3);
        root2.getChildren().add(btn1);
        about = new Scene(root2, canvasWidth, canvasHeight);

        return about;
    }

    public void switchScenes(Scene scene) {
        window.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}