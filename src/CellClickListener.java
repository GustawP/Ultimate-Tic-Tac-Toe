import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellClickListener implements ActionListener {
    private UltimateTicTacToe game;
    private TicTacToeBoard board;
    private int cellRow, cellCol;

    public CellClickListener(UltimateTicTacToe game, TicTacToeBoard board, int cellRow, int cellCol) {
        this.game = game;
        this.board = board;
        this.cellRow = cellRow;
        this.cellCol = cellCol;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!game.settingsLocked) {
            game.settingsLocked = true;
        }
        if (board.cells[cellRow][cellCol].getText().equals("") && board.winner == ' ' &&
                (game.nextBoardRow == -1 || (board.boardRow == game.nextBoardRow && board.boardCol == game.nextBoardCol))) {
            board.cells[cellRow][cellCol].setText(String.valueOf(game.currentPlayer));
            game.historyArea.append("Player " + game.currentPlayer + " moved to board (" + (board.boardRow + 1) + ", " + (board.boardCol + 1) +
                    ") cell (" + (cellRow + 1) + ", " + (cellCol + 1) + ")\n");
            board.checkWinner();
            game.checkOverallWinner();
            game.currentPlayer = (game.currentPlayer == 'X') ? 'O' : 'X';
            game.gameStatusLabel.setText("Player " + game.currentPlayer + "'s turn");

            game.nextBoardRow = cellRow;
            game.nextBoardCol = cellCol;
            if (game.boards[game.nextBoardRow][game.nextBoardCol].winner != ' ' || game.boards[game.nextBoardRow][game.nextBoardCol].isFull()) {
                game.nextBoardRow = -1;
                game.nextBoardCol = -1;
            }

            if (game.playAgainstComputer && game.currentPlayer == game.computerPlayer) {
                game.boards[game.nextBoardRow][game.nextBoardCol].makeComputerMove();
            }
        }
    }
}
