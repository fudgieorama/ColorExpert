// **********************************************************************************
// Title: Color Expert
// Author: Sophia Isabel Ferrer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: ColourExpert.java
// Description:
// **********************************************************************************

package com.example.colourexpert;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
import java.util.ArrayList;
import java.util.Random;

public class ColourExpert extends Application {

    private Stage window = new Stage();
    private Scene start;
    private Scene about;
    private Scene main;
    private Group root1 = new Group();
    private Group root2 = new Group();
    private Group root3 = new Group();

    private int playerScore = 0;
    private int difficulty = 200;
    private ArrayList<String> colorsList = new ArrayList<>();
    private int index = 0;

    public int canvasWidth = 500;
    public int canvasHeight = 500;

    @Override
    public void start(Stage stage) throws IOException {

        window = stage;
        stage.setResizable(false); // User cannot resize the window
        stage.setTitle("ColourExpert");


        start = createStart(); // Creates the "start" scene
        about = createAbout(); // Creates the "about" scene
        main = createMain(); // Creates the main scene
//        chooseRound = createChooseRound();

        //Gradient Background
        start.setFill(new RadialGradient(
                0, 0, 0, 0, 1, true,   //sizing
                CycleMethod.NO_CYCLE,                       //cycling
                new Stop(0, getRandomColor(0,256)),    //colors (in hexadecimal)
                new Stop(1, getRandomColor(0,256)))
        );

        stage.setScene(start); // The first scene to show when the program runs
        stage.show();
    }

    private Scene createMain() {
        main = new Scene(root1, canvasWidth, canvasHeight);

        // Add the colors for the player to guess into an ArrayList
        colorsList.add("Red");
        colorsList.add("Green");
        colorsList.add("Blue");

        // Menu Bar + Listeners
        MenuBar menuBar = new MenuBar();
        VBox vbox = new VBox(menuBar);
        vbox.setPrefWidth(canvasWidth);

        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Edit");
        Menu menu3 = new Menu("Help");

        MenuItem quitItem = new MenuItem("Quit");
        MenuItem increaseDifficultyItem = new MenuItem("Increase difficulty");
        MenuItem decreaseDifficultyItem = new MenuItem("Decrease difficulty");
        MenuItem aboutItem = new MenuItem("About");

        menuBar.getMenus().add(menu1);
            menu1.getItems().add(quitItem);
                quitItem.setOnAction(e -> {
                    storePlayerScore(playerScore); // Stores the player's highest score before exiting
                    Platform.exit(); // Exits the application
                });
        menuBar.getMenus().add(menu2);
            menu2.getItems().add(increaseDifficultyItem);
                increaseDifficultyItem.setOnAction(e -> { // Decreases the difference in colors (more difficult)
                    updateDifficulty(1);
                });
            menu2.getItems().add(decreaseDifficultyItem);
                decreaseDifficultyItem.setOnAction(e -> { // Increases the difference in colors (less difficult)
                    updateDifficulty(0);
                });
        menuBar.getMenus().add(menu3);
            menu3.getItems().add(aboutItem);
                aboutItem.setOnAction(e -> {
                    switchScenes(about); // Goes back to "about" scene
                });

        // Setting up the label + buttons for the GUI
        setRandomIndex(); // Initializes the index with a random number from within the color list
        Label lbl1 = new Label("Which has more " + colorsList.get(index) + "?");
        lbl1.setFont(Font.font(20));
        lbl1.setTextFill(Color.WHITE);
        setLayout(lbl1, 150, 140);
        Button leftBtn = new Button("LEFT");
        Button rightBtn = new Button("RIGHT");
        setLayout(leftBtn, 100, 350);
        setLayout(rightBtn, 350, 350);
        newComparison(difficulty, index);

        // Event-handling for left button
        leftBtn.setOnAction(e -> Platform.runLater(() -> { // Multi-threading
            setRandomIndex();
            lbl1.setText("Which has more " + colorsList.get(index) + "?");
            newComparison(difficulty, index);
            System.out.println(difficulty);
        }));

        // Event-handling for right button
        rightBtn.setOnAction(e -> Platform.runLater(() -> {  // Multi-threading
            setRandomIndex();
            lbl1.setText("Which has more " + colorsList.get(index) + "?");
            newComparison(difficulty, index);
            System.out.println(difficulty);
        }));

        main.setFill(Color.BLACK); // Sets background to black
        root1.getChildren().addAll(leftBtn, rightBtn, lbl1, vbox);
        return main;
    }

    // Function to set variable 'index' based on the ArrayList<Integer> colorList
    private void setRandomIndex() {
        index = (int)(Math.random() * colorsList.size());
    }

    // Increases or decreases the "difficulty" variable
    public void updateDifficulty(int i) {
        int increment = 40;
        if (i == 0) {
            if (difficulty + increment >= 255)
                difficulty = 255;
            else
                difficulty += increment;
        } else if (i == 1) {
            if (difficulty - increment <= 0)
                difficulty = 5;
            else
                difficulty -= increment;
        }
    }

    // Efficiently sets the x-y layout of certain nodes
    public void setLayout(Control obj, int x, int y) {
        obj.setLayoutX(x);
        obj.setLayoutY(y);
    }

    // Creates two new color boxes
    public void newComparison(int difference, int color) {
        int higherRange = getHigherRange(difference);
        CircleBox c = new CircleBox(root1, 100, 200, getLowerRange(difference, higherRange), higherRange, color);
        System.out.println(c.getColorCounter());
        CircleBox c2 = new CircleBox(root1, 350, 200, getLowerRange(difference, higherRange), higherRange, color);
        System.out.println(c2.getColorCounter());
    }

    // Obtains the "higher range" of the difference in value between the box colors
    public int getHigherRange(int difference) {
        Random rand = new Random();
        int num = rand.nextInt(255);
        if (num <= difference) {
            while (num <= difference) {
                num++;
            }
            System.out.println(num);
            return num;
        }
        else {
            return 255;
        }
    }

    // Obtains the "lower range" of the difference in value between the box colors
    public int getLowerRange(int difference, int higherRange) {
        return higherRange - difference;
    }

    // Randomly chooses a color for each RGB value
    public Color getRandomColor(int lowerRange, int higherRange) {
        Random rand = new Random();
        int r = lowerRange + rand.nextInt(higherRange-lowerRange);
        int g = lowerRange + rand.nextInt(higherRange-lowerRange);
        int b = lowerRange + rand.nextInt(higherRange-lowerRange);
        return Color.rgb(r,g,b,1);
    }

    // Forms the 'start' scene
    private Scene createStart() {

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

    // Forms the 'about' scene
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