import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard extends JPanel {
    private static final int SIZE = 3;
    public JButton[][] cells;
    public int boardRow, boardCol;
    public char winner;
    private UltimateTicTacToe game;

    public TicTacToeBoard(UltimateTicTacToe game, int boardRow, int boardCol) {
        this.game = game;
        this.boardRow = boardRow;
        this.boardCol = boardCol;
        this.cells = new JButton[SIZE][SIZE];
        this.winner = ' ';

        setLayout(new GridLayout(SIZE, SIZE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new JButton("");
                cells[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                cells[i][j].addActionListener(new CellClickListener(game, this, i, j));
                add(cells[i][j]);
            }
        }
    }

    public void checkWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (cells[i][0].getText().equals(cells[i][1].getText()) &&
                    cells[i][0].getText().equals(cells[i][2].getText()) &&
                    !cells[i][0].getText().equals("")) {
                winner = cells[i][0].getText().charAt(0);
                setBoardResult(winner);
                return;
            }
            if (cells[0][i].getText().equals(cells[1][i].getText()) &&
                    cells[0][i].getText().equals(cells[2][i].getText()) &&
                    !cells[0][i].getText().equals("")) {
                winner = cells[0][i].getText().charAt(0);
                setBoardResult(winner);
                return;
            }
        }
        if (cells[0][0].getText().equals(cells[1][1].getText()) &&
                cells[0][0].getText().equals(cells[2][2].getText()) &&
                !cells[0][0].getText().equals("")) {
            winner = cells[0][0].getText().charAt(0);
            setBoardResult(winner);
            return;
        }
        if (cells[0][2].getText().equals(cells[1][1].getText()) &&
                cells[0][2].getText().equals(cells[2][0].getText()) &&
                !cells[0][2].getText().equals("")) {
            winner = cells[0][2].getText().charAt(0);
            setBoardResult(winner);
            return;
        }
        if (isFull() && winner == ' ') {
            setBoardResult('D');
        }
    }

    private void setBoardResult(char result) {
        removeAll();
        setLayout(new BorderLayout());
        JButton resultButton = new JButton();
        resultButton.setFont(new Font("Arial", Font.BOLD, 100));
        if (result == 'X' || result == 'O') {
            resultButton.setText(String.valueOf(result));
            resultButton.setBackground(result == 'X' ? Color.BLUE : Color.RED);
            game.historyArea.append("Player " + result + " won board (" + (boardRow + 1) + ", " + (boardCol + 1) + ")\n");
        } else {
            resultButton.setText("REMIS");
            resultButton.setBackground(Color.YELLOW);
            game.historyArea.append("Board (" + (boardRow + 1) + ", " + (boardCol + 1) + ") ended in a draw.\n");
        }
        resultButton.setEnabled(false);
        add(resultButton, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetBoard() {
        winner = ' ';
        removeAll();
        setLayout(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new JButton("");
                cells[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                cells[i][j].addActionListener(new CellClickListener(game, this, i, j));
                add(cells[i][j]);
            }
        }
        revalidate();
        repaint();
    }

    public void makeComputerMove() {
        List<JButton> availableCells = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j].getText().equals("")) {
                    availableCells.add(cells[i][j]);
                }
            }
        }
        if (!availableCells.isEmpty()) {
            JButton move = availableCells.get((int) (Math.random() * availableCells.size()));
            move.doClick();
        }
    }
}
