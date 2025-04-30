import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {
    
    @Test
    @DisplayName("TEST CASE - Construction")
    public void testConstruction() {
        AbstractStrategyGame g = new ConnectFour();

        assertEquals(ConnectFour.PLAYER_1, g.getNextPlayer(), "Player 1 not next player after construction");
        assertEquals(ConnectFour.GAME_NOT_OVER, g.getWinner(), "Winner incorrectly declared after construction");
        assertFalse(g.isGameOver(), "Game over immediately after construction");

    }

    @Test
    @DisplayName("TEST CASE - Diagonal Win")
    public void testDiagonalWinForPlayer1() {
        AbstractStrategyGame g = new ConnectFour();

        // Moves:
        // P1: col 1
        // P2: col 2
        // P1: col 2
        // P2: col 3
        // P1: col 3
        // P2: col 4
        // P1: col 3
        // P2: col 4
        // P1: col 4
        // P2: col 5
        // P1: col 4 

        int[] moves = {1, 2, 2, 3, 3, 4, 3, 4, 4, 5, 4};
        for (int move : moves) {
            g.makeMove(new Scanner("" + move));
        }

        assertTrue(g.isGameOver(), "Expected game to be over after diagonal win, but game is still ongoing.");
        assertEquals(ConnectFour.PLAYER_ONE, g.getWinner(), "Expected Player 1 to win with diagonal, but winner is incorrect.");
        assertEquals(ConnectFour.GAME_IS_OVER, g.getNextPlayer(), "Expected no next player after game over, but a next player was returned.");

    }

    @Test
    @DisplayName("TEST CASE - Column Win")
    public void testColumnWinPlayer2() {
        AbstractStrategyGame g = new ConnectFour();

        // Moves:
        // P1: col 1
        // P2: col 4
        // P1: col 2
        // P2: col 4
        // P1: col 5
        // P2: col 4
        // P1: col 6
        // P2: col 4 

        int[] moves = {1, 4, 2, 4, 5, 4, 6, 4};  
        for (int move : moves) {
            g.makeMove(new Scanner("" + move));
        }

        assertTrue(g.isGameOver(), "Expected game to be over after column win, but game is still ongoing.");
        assertEquals(ConnectFour.PLAYER_TWO, g.getWinner(), "Expected Player 2 to win with vertical column, but winner is incorrect.");
        assertEquals(ConnectFour.GAME_IS_OVER, g.getNextPlayer(), "Expected no next player after game over, but a next player was returned.");
    }

    @Test
    @DisplayName("TEST CASE - Row Win")
    public void testRowWinPlayer1() {
        AbstractStrategyGame g = new ConnectFour();

        // Moves:
        // P1: col 1
        // P2: col 6
        // P1: col 2
        // P2: col 6
        // P1: col 3
        // P2: col 5
        // P1: col 4

        int[] moves = {1, 6, 2, 6, 3, 5, 4};  
        for (int move : moves) {
            g.makeMove(new Scanner("" + move));
        }

        assertTrue(g.isGameOver(), "Expected game to be over after row win, but game is still ongoing.");
        assertEquals(ConnectFour.PLAYER_ONE, g.getWinner(), "Expected Player 1 to win with horizontal row, but winner is incorrect.");
        assertEquals(ConnectFour.GAME_IS_OVER, g.getNextPlayer(), "Expected no next player after game over, but a next player was returned.");
    }
}