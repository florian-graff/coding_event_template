//package com.klosebros.kata;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class GameOfLifeGUI extends JFrame {
//    private static final int GRID_SIZE = 30;
//    private static final int CELL_SIZE = 15;
//    private static final Color ALIVE_COLOR = Color.BLACK;
//    private static final Color DEAD_COLOR = Color.WHITE;
//
//    private GameOfLife game;
//    private JPanel gridPanel;
//    private JButton[][] cellButtons;
//    private JButton startStopButton;
//    private JSlider speedSlider;
//    private Timer animationTimer;
//    private boolean isRunning = false;
//    private boolean useToroidal = false;
//
//    public GameOfLifeGUI() {
//        initializeGame();
//        setupUI();
//        setupTimer();
//    }
//
//    private void initializeGame() {
//        boolean[][] initialGrid = new boolean[GRID_SIZE][GRID_SIZE];
//        game = new GameOfLife(initialGrid);
//    }
//
//    private void setupUI() {
//        setTitle("Conway's Game of Life");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Grid Panel
//        gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
//        gridPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//        cellButtons = new JButton[GRID_SIZE][GRID_SIZE];
//
//        for (int row = 0; row < GRID_SIZE; row++) {
//            for (int col = 0; col < GRID_SIZE; col++) {
//                JButton cell = new JButton();
//                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
//                cell.setBackground(DEAD_COLOR);
//                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
//
//                final int r = row, c = col;
//                cell.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        if (!isRunning) {
//                            toggleCell(r, c);
//                        }
//                    }
//                });
//
//                cellButtons[row][col] = cell;
//                gridPanel.add(cell);
//            }
//        }
//
//        // Control Panel
//        JPanel controlPanel = new JPanel(new FlowLayout());
//
//        startStopButton = new JButton("Start");
//        startStopButton.addActionListener(e -> toggleAnimation());
//        controlPanel.add(startStopButton);
//
//        JButton resetButton = new JButton("Reset");
//        resetButton.addActionListener(e -> resetGrid());
//        controlPanel.add(resetButton);
//
//        JButton stepButton = new JButton("Schritt");
//        stepButton.addActionListener(e -> {
//            if (!isRunning) {
//                nextGeneration();
//            }
//        });
//        controlPanel.add(stepButton);
//
//        // Speed Control
//        controlPanel.add(new JLabel("Geschwindigkeit:"));
//        speedSlider = new JSlider(1, 20, 5);
//        speedSlider.addChangeListener(e -> updateTimerSpeed());
//        controlPanel.add(speedSlider);
//
//        // Topology Toggle
//        JCheckBox toroidalCheckbox = new JCheckBox("Toroidal");
//        toroidalCheckbox.addActionListener(e -> {
//            useToroidal = toroidalCheckbox.isSelected();
//            recreateGame();
//        });
//        controlPanel.add(toroidalCheckbox);
//
//        // Preset Patterns
//        JButton blinkerButton = new JButton("Blinker");
//        blinkerButton.addActionListener(e -> loadBlinkerPattern());
//        controlPanel.add(blinkerButton);
//
//        JButton gliderButton = new JButton("Glider");
//        gliderButton.addActionListener(e -> loadGliderPattern());
//        controlPanel.add(gliderButton);
//
//        add(gridPanel, BorderLayout.CENTER);
//        add(controlPanel, BorderLayout.SOUTH);
//
//        pack();
//        setLocationRelativeTo(null);
//    }
//
//    private void setupTimer() {
//        animationTimer = new Timer(200, e -> nextGeneration());
//        updateTimerSpeed();
//    }
//
//    private void updateTimerSpeed() {
//        int speed = speedSlider.getValue();
//        int delay = 1100 - (speed * 50); // Schneller bei höherem Wert
//        animationTimer.setDelay(delay);
//    }
//
//    private void toggleCell(int row, int col) {
//        boolean[][] currentGrid = game.getGrid();
//        currentGrid[row][col] = !currentGrid[row][col];
//
//        if (useToroidal) {
//            game = new ToroidalGameOfLife(currentGrid);
//        } else {
//            game = new GameOfLife(currentGrid);
//        }
//        updateDisplay();
//    }
//
//    private void toggleAnimation() {
//        if (isRunning) {
//            animationTimer.stop();
//            startStopButton.setText("Start");
//            isRunning = false;
//        } else {
//            animationTimer.start();
//            startStopButton.setText("Stop");
//            isRunning = true;
//        }
//    }
//
//    private void resetGrid() {
//        if (isRunning) {
//            toggleAnimation();
//        }
//
//        boolean[][] emptyGrid = new boolean[GRID_SIZE][GRID_SIZE];
//        if (useToroidal) {
//            game = new ToroidalGameOfLife(emptyGrid);
//        } else {
//            game = new GameOfLife(emptyGrid);
//        }
//        updateDisplay();
//    }
//
//    private void nextGeneration() {
//        game = game.nextGeneration();
//        updateDisplay();
//    }
//
//    private void recreateGame() {
//        boolean[][] currentGrid = game.getGrid();
//        if (useToroidal) {
//            game = new ToroidalGameOfLife(currentGrid);
//        } else {
//            game = new GameOfLife(currentGrid);
//        }
//    }
//
//    private void updateDisplay() {
//        boolean[][] grid = game.getGrid();
//        for (int row = 0; row < GRID_SIZE; row++) {
//            for (int col = 0; col < GRID_SIZE; col++) {
//                Color color = grid[row][col] ? ALIVE_COLOR : DEAD_COLOR;
//                cellButtons[row][col].setBackground(color);
//            }
//        }
//    }
//
//    private void loadBlinkerPattern() {
//        if (isRunning) {
//            toggleAnimation();
//        }
//
//        boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
//        int center = GRID_SIZE / 2;
//
//        // Horizontaler Blinker in der Mitte
//        grid[center][center - 1] = true;
//        grid[center][center] = true;
//        grid[center][center + 1] = true;
//
//        if (useToroidal) {
//            game = new ToroidalGameOfLife(grid);
//        } else {
//            game = new GameOfLife(grid);
//        }
//        updateDisplay();
//    }
//
//    private void loadGliderPattern() {
//        if (isRunning) {
//            toggleAnimation();
//        }
//
//        boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
//        int startRow = 5;
//        int startCol = 5;
//
//        // Glider Pattern
//        grid[startRow][startCol + 1] = true;
//        grid[startRow + 1][startCol + 2] = true;
//        grid[startRow + 2][startCol] = true;
//        grid[startRow + 2][startCol + 1] = true;
//        grid[startRow + 2][startCol + 2] = true;
//
//        if (useToroidal) {
//            game = new ToroidalGameOfLife(grid);
//        } else {
//            game = new GameOfLife(grid);
//        }
//        updateDisplay();
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            new GameOfLifeGUI().setVisible(true);
//        });
//    }
//}
