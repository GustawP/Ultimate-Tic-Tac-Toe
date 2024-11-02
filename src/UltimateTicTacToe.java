import javax.swing.*;
import java.awt.*;

public class UltimateTicTacToe extends JFrame {
    private static final int SIZE = 3;
    public TicTacToeBoard[][] boards;
    public char currentPlayer;
    public JTextArea historyArea;
    public boolean playAgainstComputer;
    public char computerPlayer;
    public int difficultyLevel;
    public int nextBoardRow = -1;
    public int nextBoardCol = -1;
    public boolean settingsLocked = false;
    public JLabel gameStatusLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UltimateTicTacToe().showSettingsDialog());
    }

    public UltimateTicTacToe() {
        boards = new TicTacToeBoard[SIZE][SIZE];
        currentPlayer = 'X';
        historyArea = new JTextArea(10, 30);
        historyArea.setEditable(false);

        setTitle("Ultimate Tic Tac Toe");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(SIZE, SIZE));
        mainPanel.setPreferredSize(new Dimension(700, 700));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boards[i][j] = new TicTacToeBoard(this, i, j);
                mainPanel.add(boards[i][j]);
            }
        }

        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        historyScrollPane.setBorder(BorderFactory.createTitledBorder("Game History"));

        add(mainPanel, BorderLayout.CENTER);
        add(historyScrollPane, BorderLayout.EAST);
        gameStatusLabel = new JLabel("Player X's turn");
        add(gameStatusLabel, BorderLayout.SOUTH);

        setVisible(false);
    }

    public void showSettingsDialog() {
        JDialog settingsDialog = new JDialog(this, "Game Settings", true);
        settingsDialog.setSize(500, 400);
        settingsDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel gameModeLabel = new JLabel("Game mode:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        settingsDialog.add(gameModeLabel, gbc);

        JRadioButton humanButton = new JRadioButton("Human vs Human");
        JRadioButton computerButton = new JRadioButton("Human vs Computer");
        ButtonGroup gameModeGroup = new ButtonGroup();
        gameModeGroup.add(humanButton);
        gameModeGroup.add(computerButton);
        humanButton.setSelected(true);

        JPanel gameModePanel = new JPanel();
        gameModePanel.add(humanButton);
        gameModePanel.add(computerButton);
        gbc.gridx = 1;
        gbc.gridy = 0;
        settingsDialog.add(gameModePanel, gbc);

        JLabel startingPlayerLabel = new JLabel("Starting Player:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        settingsDialog.add(startingPlayerLabel, gbc);

        JRadioButton playerXButton = new JRadioButton("Player X");
        JRadioButton playerOButton = new JRadioButton("Player O");
        ButtonGroup startingPlayerGroup = new ButtonGroup();
        startingPlayerGroup.add(playerXButton);
        startingPlayerGroup.add(playerOButton);
        playerXButton.setSelected(true);

        JPanel startingPlayerPanel = new JPanel();
        startingPlayerPanel.add(playerXButton);
        startingPlayerPanel.add(playerOButton);
        gbc.gridx = 1;
        gbc.gridy = 1;
        settingsDialog.add(startingPlayerPanel, gbc);

        JLabel difficultyLabel = new JLabel("Difficulty level:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        settingsDialog.add(difficultyLabel, gbc);

        JRadioButton easyButton = new JRadioButton("Easy");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton hardButton = new JRadioButton("Hard");
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);
        easyButton.setSelected(true);

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);
        gbc.gridx = 1;
        gbc.gridy = 2;
        settingsDialog.add(difficultyPanel, gbc);

        difficultyLabel.setVisible(false);
        difficultyPanel.setVisible(false);

        humanButton.addActionListener(e -> {
            difficultyLabel.setVisible(false);
            difficultyPanel.setVisible(false);
            settingsDialog.pack();
        });

        computerButton.addActionListener(e -> {
            difficultyLabel.setVisible(true);
            difficultyPanel.setVisible(true);
            settingsDialog.pack();
        });

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            playAgainstComputer = computerButton.isSelected();
            computerPlayer = playerOButton.isSelected() ? 'X' : 'O';
            currentPlayer = playerXButton.isSelected() ? 'X' : 'O';
            if (easyButton.isSelected()) difficultyLevel = 1;
            if (mediumButton.isSelected()) difficultyLevel = 2;
            if (hardButton.isSelected()) difficultyLevel = 3;

            gameStatusLabel.setText("Player " + currentPlayer + "'s turn");
            settingsDialog.dispose();
            setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        settingsDialog.add(startButton, gbc);

        settingsDialog.setLocationRelativeTo(null);
        settingsDialog.setVisible(true);
    }

    public void checkOverallWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (boards[i][0].winner == boards[i][1].winner &&
                    boards[i][0].winner == boards[i][2].winner &&
                    boards[i][0].winner != ' ') {
                declareOverallWinner(boards[i][0].winner);
            }
            if (boards[0][i].winner == boards[1][i].winner &&
                    boards[0][i].winner == boards[2][i].winner &&
                    boards[0][i].winner != ' ') {
                declareOverallWinner(boards[0][i].winner);
            }
        }
        if (boards[0][0].winner == boards[1][1].winner &&
                boards[0][0].winner == boards[2][2].winner &&
                boards[0][0].winner != ' ') {
            declareOverallWinner(boards[0][0].winner);
        }
        if (boards[0][2].winner == boards[1][1].winner &&
                boards[0][2].winner == boards[2][0].winner &&
                boards[0][2].winner != ' ') {
            declareOverallWinner(boards[0][2].winner);
        }
    }

    public void declareOverallWinner(char winner) {
        JOptionPane.showMessageDialog(this, "Player " + winner + " wins the game!");
        resetGame();
    }

    public void resetGame() {
        currentPlayer = 'X';
        nextBoardRow = -1;
        nextBoardCol = -1;
        settingsLocked = false;
        gameStatusLabel.setText("Player X's turn");

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boards[i][j].resetBoard();
            }
        }
    }
}
