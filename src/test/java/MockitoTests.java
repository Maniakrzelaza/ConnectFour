import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoTests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;

    @Test
    public void ConnectFourShouldCallAddPlayerMethod() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(3)
                .thenReturn(0);
        ConnectFour.reader = mockScanner;
        ConnectFour.menu();
        verify(mockScanner, times(1)).next();
    }

    @Test
    public void GameShouldPrepareProperBoard() {
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(7)
                .thenReturn(6)
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0);
        when(mockScanner.next())
                .thenReturn("e");
        Game sutGame = new Game();
        sutGame.reader = mockScanner;
        sutGame.prepareGame();
        verify(mockScanner, times(4)).nextInt();
        assertAll(
                () -> assertThat(sutGame.getGameBoard().getHeight()).isEqualTo(6),
                () -> assertThat(sutGame.getGameBoard().getWidth()).isEqualTo(7)
        );
    }
    @Test
    public void GameShouldCallShowRankingList() {
        setUpStreams();
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(2)
                .thenReturn(0);

        ConnectFour.reader = mockScanner;
        ConnectFour.menu();
        verify(mockScanner, times(2)).nextInt();
        assertThat(outContent.toString()).contains("0. Name: Kacper Wins: 2\n" +
                "1. Name: null Wins: 0\n" +
                "2. Name: Robert Wins: 1");
        restoreStreams();
    }
    private void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    private void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
