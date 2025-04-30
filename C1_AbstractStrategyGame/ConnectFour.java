// Tristan Suwito
// 20 April 2025
// CSE 123
// C1: Abstract Strategy Games
// TA: Rushil Arun & Chris Ma
// This class is a subclass of AbstractStrategyGame and is meant to simulate the game 
// "Connect Four", a two-player strategy board game. The game is played on a 6x7 grid, where 
// players take turns dropping tokens into columns. The objective is to be the first player to 
// align four of their tokens consecutively either horizontally, vertically, or diagonally.
import java.util.*;

public class ConnectFour extends AbstractStrategyGame{
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final String PLAYER_ONE_TOKEN = "<3";
    public static final String PLAYER_TWO_TOKEN = ":)";
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;
    public static final int TIE = 0;
    public static final int GAME_IS_OVER = -1;
    public static final int GAME_NOT_OVER = -1;

    private boolean isOneTurn;
    private int[][] board;

    // Behavior:
    //   - This method constructs a new ConnectFour.
    // Parameters:
    // Returns:
    // Exceptions:
    public ConnectFour() {
        this.isOneTurn = true;
        this.board = new int[ROWS][COLUMNS];
    }

    // Behavior:
    //   - This method constructs and returns a String that provides details and instructions on
    //     how to play the game.
    // Parameters:
    // Returns:
    //   - Returns String provding details on how to interpret the game, how to make moves, the
    //     game end condition, and how to win.
    // Exceptions:
    @Override
    public String instructions() {
        String result = "";
        result += "Player 1 is \"<3\" and goes first. Player 2 is \":)\". Choose where to play\n";
        result += "by entering the numberof the row (displayed by the board). Spaces shown as\n";
        result += "\"()\" are empty. The game ends when one player marks four spaces in a row\n";
        result += "diagonally, horizontally, or vertically, in which that player wins, or when\n";
        result += "the board is full, in which casethe game end in a tie.";
        return result;
    }
    
    // Behavior:
    //   - This method constructs and returns a String representation of the current board. 
    // Parameters:
    // Returns:
    //   - Returns String of the current state of the Connect Four board.
    //     (No moves made = Blank/clean Board).
    //     Ex.
    //       | () | () | () | () | () | () | () |
    //       | () | () | () | () | () | () | () |
    //       | () | () | () | () | () | () | () |
    //       | () | () | () | () | () | () | () |
    //       | () | () | () | () | () | () | () |
    //       | () | () | () | () | () | () | () |
    //         1    2    3    4    5    6    7  
    // Exceptions:
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < ROWS; i++) {
            result += " |";
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == PLAYER_ONE) {
                    result += " " + PLAYER_ONE_TOKEN + " |";
                } else if (board[i][j] == PLAYER_TWO) {
                    result += " " + PLAYER_TWO_TOKEN + " |";
                } else {
                    result += " () |";
                }
            }
            result += "\n";
        }

        result += " ";
        for (int i = 0; i < COLUMNS; i++) {
            result += "  " + (i + 1) + "  ";
        }
        result += "\n";
        return result;
    }

    // Behavior:
    //   - This method returns the index of the winner of the game.  
    // Parameters:
    // Returns:
    //   - Returns index of the winner of the game.
    //      1 -> Player One <3
    //      2 -> Player Two :)
    //      0 -> a tie occurred 
    //     -1 -> the game is not over
    // Exceptions:
    @Override
    public int getWinner() {
        int winner = 0;
        for (int i = 0; i < COLUMNS; i++) {
            winner = checkColumnWinner(i);
            if (winner != GAME_NOT_OVER) {
                return winner;
            }
        }
        for (int i = 0; i < ROWS; i++) {
            winner = checkRowWinner(i);
            if (winner != GAME_NOT_OVER) {
                return winner;
            }
        }
        winner = checkDiagonalWinner();
        if (winner != GAME_NOT_OVER) {
            return winner;
        }
        return checkTie();
    }

    // Behavior:
    //   - This method returns the index of which player's turn it is.
    // Parameters:
    // Returns:
    //   - Returns the index of which player's turn it is.
    //      1 -> Player One <3
    //      2 -> Player Two :)
    //     -1 -> the game is over
    // Exceptions:
    @Override
    public int getNextPlayer() {
        if (isGameOver()) {
            return GAME_IS_OVER;
        }

        if (isOneTurn) {
            return PLAYER_ONE;
        } else {
            return PLAYER_TWO;
        }
    }

    // Behavior:
    //   - This method prompts the current player to make a move by selecting a column(1-7). The
    //     player's token is placed in the lowest available row of the selected column, updating
    //     the board and then switches to the next player's turn.
    // Parameters:
    //   - input: Scanner that allows player's inputted column move to be read.
    // Returns:
    // Exceptions:
    //   - If input is null, an IllegalArgumentException is thrown.
    //   - If input is not within the 1-7 range, an IllegalArgumentException is thrown.
    //   - If the inputted column is full, an IllegalArgumentException is thrown.
    @Override
    public void makeMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        int currPlayer = 0;
        if(isOneTurn) {
            currPlayer = PLAYER_ONE;
        } else {
            currPlayer = PLAYER_TWO;
        } 
        System.out.print("Column? ");
        int column = input.nextInt();
        if(column < 1 || column > COLUMNS) {
            throw new IllegalArgumentException("Please choose beween 1 and 7");
        }
        int colIndex = column - 1;
        if(isFullColumn(colIndex)) {
            throw new IllegalArgumentException("This column is full");
        }
        boolean placed = false;
        for (int i = ROWS - 1; i >= 0 && !placed; i--) {
            if (board[i][colIndex] == 0) {
                board[i][colIndex] = currPlayer;
                placed = true;
            }
        }
        isOneTurn = !isOneTurn;
    }

    // Behavior:
    //   - This is a helper method that determines whether the inputted column in full.
    // Parameters:
    //   - column: int of the column that is to be checked.
    // Returns:
    //   - Returns boolean of whether inputted column is full or not. True = full column.
    //     False = column is not full.
    // Exceptions:
    private boolean isFullColumn(int column) {
        for (int i = 0; i < ROWS; i++) {
            if (board[i][column] == 0) {
                return false;
            }
        }
        return true;
    }

    // Behavior:
    //   - This is a method checks if the game is a tie(full board) and returns an int that 
    //     represents if the board is full or not.
    // Parameters:
    // Returns:
    //   - Returns int represnting if the board is full or not.
    //     0 -> Full board/Tie game
    //     -1 -> Board not full/Game not over
    // Exceptions:
    public int checkTie() {
        int fullCount = 0;
        for (int i = 0; i < COLUMNS; i++) {
            if (isFullColumn(i)) {
                fullCount++;
            }
        }
        if (fullCount == COLUMNS) {
            return TIE;
        }
        return GAME_NOT_OVER;
    }

    // Behavior:
    //   - This is a method checks the board for a column winner(4 of the same consecutive tokens
    //     vertically) and returns an int based off if a player got a column winner or not.
    // Parameters:
    //   - column: int of the column that is being checked for a column winner.
    // Returns:
    //   - Returns int representing if a player got a column winner or not.
    //     1 -> Player One got a column winner
    //     2 -> Player Two got a column winner
    //     -1 -> No player got a column winner/Game not over
    // Exceptions:
    public int checkColumnWinner(int column) {
        int col = column;
        int consecutive = 1;
        int lastPlayer = 0;

        for (int i = 0; i < ROWS; i++ ) {
            int currentPlayer = board[i][col];
            if (currentPlayer == lastPlayer && currentPlayer != 0) {
                consecutive++;
                if (consecutive == 4) {
                    return currentPlayer;
                }
            }else {
                consecutive = 1;
            }
            lastPlayer = currentPlayer;
        }
        return GAME_NOT_OVER;
    }

    // Behavior:
    //   - This is a method checks the board for a row winner(4 of the same consecutive tokens
    //     horizontally) and returns an int based off if a player got a row winner or not.
    // Parameters:
    //   - column: int of the row that is being checked for a row winner.
    // Returns:
    //   - Returns int representing if a player got a row winner or not.
    //     1 -> Player One got a row winner
    //     2 -> Player Two got a row winner
    //     -1 -> No player got a row winner/Game not over
    // Exceptions:
    public int checkRowWinner(int row) {
        int consecutive = 1;
        int lastPlayer = 0;

        for (int i = 0; i < COLUMNS; i++) {
            int currentPlayer = board[row][i];
            if (currentPlayer == lastPlayer && currentPlayer != 0) {
                consecutive++;
                if (consecutive == 4) {
                    return currentPlayer;
                }
            } else {
                consecutive = 1;
            }
            lastPlayer = currentPlayer;
        }
        return GAME_NOT_OVER;
    }

    // Behavior:
    //   - This is a method checks the board for a diagonal winner(4 of the same consecutive tokens
    //     diagonally) and returns an int based off if a player got a diagonal winner or not.
    // Parameters:
    // Returns:
    //   - Returns int representing if a player got a diagonal winner or not.
    //     1 -> Player One got a diagonal winner
    //     2 -> Player Two got a diagonal winner
    //     -1 -> No player got a diagonal winner/Game not over
    // Exceptions:
    public int checkDiagonalWinner() {
        // Checks left to right diagonal (/)
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j <= COLUMNS - 4; j++) {
                int winner = checkDiagonalFrom(i, j, -1, +1);
                if (winner != GAME_NOT_OVER) {
                    return winner;
                }
            }
        }
        //Checks right to left diagonal (\)
        for (int i = 3; i < ROWS; i++) {
            for (int j = 3; j < COLUMNS; j++) {
                int winner = checkDiagonalFrom(i, j, -1, -1);
                if (winner != GAME_NOT_OVER) {
                    return winner;
                }
            }
        }
        return GAME_NOT_OVER;
    }

    // Behavior:
    //   - This is a helper method that checks the board for a diagonal winner(4 of the same 
    //     consecutive tokens diagonally) from a specific row and column and then moving in
    //     direction based on the given row step and column step. Returns an int based off if a 
    //     player got a diagonal winner or not.
    // Parameters:
    //   - startRow: int of the starting row index the diagonal check will begin on.
    //   - startCol: int of the starting column index the diagonal check will begin on.
    //   - rowStep: int of the amount of row and direction to check for each step.
    //     1 -> move down 1 index each step
    //     -1 -> move up 1 index each step
    //   - colStep: int of the amount of column and direction to check for each step.
    //     1 -> move right 1 index each step
    //     -1 -> move left 1 index each step
    // Returns:
    //   - Returns int representing if a player got a diagonal winner or not.
    //     1 -> Player One got a diagonal winner
    //     2 -> Player Two got a diagonal winner
    //    -1 -> No player got a diagonal winner/Game not over
    // Exceptions:
    public int checkDiagonalFrom(int startRow, int startCol, int rowStep, int colStep) {
        int consecutive = 1;
        int lastPlayer = 0;
        int row = startRow;
        int col = startCol;

        while (row >= 0 && col >= 0 && row < ROWS && col < COLUMNS) {
            int currentPlayer = board[row][col];
            if (lastPlayer == currentPlayer && currentPlayer != 0) {
                consecutive++;
                if (consecutive == 4) {
                    return currentPlayer;
                }
            } else {
                if(currentPlayer == 0) {
                    consecutive = 0;
                } else {
                    consecutive = 1;
                }
            }
            lastPlayer = currentPlayer;
            row += rowStep;
            col += colStep;
        }
        return GAME_NOT_OVER;
    }
}