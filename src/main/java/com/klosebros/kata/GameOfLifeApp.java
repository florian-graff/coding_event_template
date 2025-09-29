package com.klosebros.kata;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX Application for Conway's Game of Life.
 *
 * Features:
 * - Visual grid displaying alive/dead cells
 * - Start/Stop/Reset controls
 * - Speed adjustment slider
 * - Click to toggle cells
 * - Predefined patterns
 */
public class GameOfLifeApp extends Application {

    private static final int GRID_SIZE = 30;
    private static final int CELL_SIZE = 15;

    private final GameOfLife gameOfLife = new GameOfLife();
    private boolean[][] board;
    private Button[][] cellButtons;

    private Timeline timeline;
    private Button startStopButton;
    private Button resetButton;
    private Slider speedSlider;
    private Label generationLabel;
    private int generation = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Conway's Game of Life - JavaFX");

        initializeBoard();

        var root = new BorderPane();
        root.setTop(createControlPanel());
        root.setCenter(createGameGrid());
        root.setBottom(createStatusPanel());

        var scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        setupAnimation();
    }

    private void initializeBoard() {
        board = new boolean[GRID_SIZE][GRID_SIZE];
        cellButtons = new Button[GRID_SIZE][GRID_SIZE];

        // Initialize with a simple pattern (glider)
        setGliderPattern();
    }

    private VBox createControlPanel() {
        var controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));

        // Start/Stop and Reset buttons
        var buttonRow = new HBox(10);
        startStopButton = new Button("Start");
        startStopButton.setOnAction(e -> toggleAnimation());

        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetBoard());

        var patternsButton = new Button("Load Pattern");
        var patternsMenu = new MenuButton("Load Pattern");
        patternsMenu.getItems().addAll(
            createPatternMenuItem("Glider", this::setGliderPattern),
            createPatternMenuItem("Blinker", this::setBlinkerPattern),
            createPatternMenuItem("Beacon", this::setBeaconPattern),
            createPatternMenuItem("Clear", this::clearBoard)
        );

        buttonRow.getChildren().addAll(startStopButton, resetButton, patternsMenu);

        // Speed control
        var speedLabel = new Label("Speed:");
        speedSlider = new Slider(1, 20, 5);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(5);

        var speedRow = new HBox(10);
        speedRow.getChildren().addAll(speedLabel, speedSlider);

        controlPanel.getChildren().addAll(buttonRow, speedRow);
        return controlPanel;
    }

    private MenuItem createPatternMenuItem(String name, Runnable action) {
        var menuItem = new MenuItem(name);
        menuItem.setOnAction(e -> {
            stopAnimation();
            action.run();
            updateDisplay();
            generation = 0;
            generationLabel.setText("Generation: " + generation);
        });
        return menuItem;
    }

    private GridPane createGameGrid() {
        var gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(10));
        gridPane.setStyle("-fx-background-color: #333333;");

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                var cellButton = new Button();
                cellButton.setPrefSize(CELL_SIZE, CELL_SIZE);
                cellButton.setMinSize(CELL_SIZE, CELL_SIZE);
                cellButton.setMaxSize(CELL_SIZE, CELL_SIZE);

                final int r = row, c = col;
                cellButton.setOnAction(e -> toggleCell(r, c));

                cellButtons[row][col] = cellButton;
                gridPane.add(cellButton, col, row);
            }
        }

        updateDisplay();
        return gridPane;
    }

    private HBox createStatusPanel() {
        var statusPanel = new HBox(10);
        statusPanel.setPadding(new Insets(10));

        generationLabel = new Label("Generation: " + generation);
        var instructionLabel = new Label("Click cells to toggle • Use patterns menu • Adjust speed");
        instructionLabel.setStyle("-fx-text-fill: gray;");

        statusPanel.getChildren().addAll(generationLabel, new Region(), instructionLabel);
        HBox.setHgrow(statusPanel.getChildren().get(1), Priority.ALWAYS);

        return statusPanel;
    }

    private void setupAnimation() {
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> nextGeneration()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void toggleAnimation() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    private void startAnimation() {
        var speed = speedSlider.getValue();
        timeline.setRate(speed / 5.0); // Base speed adjustment
        timeline.play();
        startStopButton.setText("Stop");
    }

    private void stopAnimation() {
        timeline.stop();
        startStopButton.setText("Start");
    }

    private void nextGeneration() {
        board = gameOfLife.nextGeneration(board);
        generation++;
        updateDisplay();
        generationLabel.setText("Generation: " + generation);
    }

    private void updateDisplay() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                var button = cellButtons[row][col];
                if (board[row][col]) {
                    button.setStyle("-fx-background-color: #4CAF50; -fx-border-color: #333;");
                } else {
                    button.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #333;");
                }
            }
        }
    }

    private void toggleCell(int row, int col) {
        board[row][col] = !board[row][col];
        updateDisplay();
    }

    private void resetBoard() {
        stopAnimation();
        generation = 0;
        generationLabel.setText("Generation: " + generation);
        setGliderPattern();
        updateDisplay();
    }

    private void clearBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                board[row][col] = false;
            }
        }
    }

    private void setGliderPattern() {
        clearBoard();
        // Glider pattern
        board[1][2] = true;
        board[2][3] = true;
        board[3][1] = true;
        board[3][2] = true;
        board[3][3] = true;
    }

    private void setBlinkerPattern() {
        clearBoard();
        // Blinker pattern (oscillates)
        int centerRow = GRID_SIZE / 2;
        int centerCol = GRID_SIZE / 2;
        board[centerRow][centerCol - 1] = true;
        board[centerRow][centerCol] = true;
        board[centerRow][centerCol + 1] = true;
    }

    private void setBeaconPattern() {
        clearBoard();
        // Beacon pattern (period-2 oscillator)
        int startRow = GRID_SIZE / 2 - 1;
        int startCol = GRID_SIZE / 2 - 1;

        board[startRow][startCol] = true;
        board[startRow][startCol + 1] = true;
        board[startRow + 1][startCol] = true;

        board[startRow + 2][startCol + 2] = true;
        board[startRow + 2][startCol + 3] = true;
        board[startRow + 3][startCol + 2] = true;
        board[startRow + 3][startCol + 3] = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
