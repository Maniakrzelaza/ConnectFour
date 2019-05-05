import org.jongo.MongoCollection;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class MockitoTests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;

    @Test
    public void connectFourShouldCallAddPlayerMethod() {
        //Arrange
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(3)
                .thenReturn(0);
        ConnectFour.reader = mockScanner;

        //Act
        ConnectFour.menu();

        //Assert/Verify
        verify(mockScanner, times(1)).next();
    }

    @Test
    public void gameShouldPrepareProperBoard() {
        //Arrange
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

        //Act
        sutGame.prepareGame();

        //Assert/Verify
        verify(mockScanner, times(4)).nextInt();
        assertAll(
                () -> assertThat(sutGame.getGameBoard().getHeight()).isEqualTo(6),
                () -> assertThat(sutGame.getGameBoard().getWidth()).isEqualTo(7)
        );
    }
    @Test
    public void gameShouldCallShowRankingList() {
        //Arrange
        setUpStreams();
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(2)
                .thenReturn(0);
        RankingList mockRankingList = mock(RankingList.class);
        when(mockRankingList.showPlayers())
                .thenReturn("0. Name: Kacper Wins: 2\n" +
                        "1. Name: null Wins: 0\n" +
                        "2. Name: Robert Wins: 1");

        ConnectFour.reader = mockScanner;
        ConnectFour.rankingList = mockRankingList;

        //Act
        ConnectFour.menu();

        //Assert/Verify
        verify(mockScanner, times(2)).nextInt();
        verify(mockRankingList, times(1)).showPlayers();
        assertThat(outContent.toString()).contains("0. Name: Kacper Wins: 2\n" +
                "1. Name: null Wins: 0\n" +
                "2. Name: Robert Wins: 1");
        restoreStreams();
    }
    @Test
    public void gameSaveShouldSaveProperly(){
        //Arrange
        GameSaver sutGameSaver = new GameSaver();
        MongoCollection mockGameStates = mock(MongoCollection.class);
        when(mockGameStates.save(any(Object.class))).thenAnswer(jiinvocation -> null);
        sutGameSaver.gameStates = mockGameStates;

        //Act
        sutGameSaver.saveGame(new GameState(0, new Board(7,6)));

        //Assert/Verify
        verify(mockGameStates, times(1)).save(any(GameState.class));
    }

    @Test
    public void gameShouldSaveGame() {
        //Arrange
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(7)
                .thenReturn(6)
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0);
        when(mockScanner.next())
                .thenReturn("s")
                .thenReturn("e");
        GameSaver mockGameSaver = mock(GameSaver.class);
        doNothing().when(mockGameSaver).saveGame(any(GameState.class));
        Game sutGame = new Game();
        sutGame.reader = mockScanner;
        sutGame.setGameSaver(mockGameSaver);

        //Act
        sutGame.prepareGame();

        //Assert/Verify
        verify(mockGameSaver, times(1)).saveGame(any(GameState.class));
    }

    @Test
    public void gameShouldNotStartWhenThereIsNotEnoughPlayers() {
        //Arrange
        setUpStreams();
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(1)
                .thenReturn(0);
        RankingList mockRankingList = mock(RankingList.class);
        when(mockRankingList.getList())
                .thenReturn(new HashMap<>());

        ConnectFour.reader = mockScanner;
        ConnectFour.rankingList = mockRankingList;

        //Act
        ConnectFour.menu();

        //Assert/Verify
        verify(mockScanner, times(2)).nextInt();
        verify(mockRankingList, times(1)).getList();
        assertThat(outContent.toString()).contains("Not enough players to play. Create more players");
        restoreStreams();
    }

    @Test
    public void gameShouldEndWhenOnePlayerWins() {
        //Arrange
        setUpStreams();
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(7)
                .thenReturn(6)
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(0);
        when(mockScanner.hasNextInt())
                .thenReturn(true);
        RankingList mockRankingList = mock(RankingList.class);
        doNothing().when(mockRankingList).saveListToCsv();
        HashMap<String, Player> fakeList = new HashMap<>();
        fakeList.put("Player1", new Player("Player1", 1));
        fakeList.put("Player2", new Player("Player2", 2));
        when(mockRankingList.getList())
                .thenReturn(fakeList);
        GameSaver mockGameSaver = mock(GameSaver.class);
        doNothing().when(mockGameSaver).saveGame(any(GameState.class));
        Game sutGame = new Game();
        sutGame.reader = mockScanner;
        sutGame.setRankingList(mockRankingList);
        sutGame.setGameSaver(mockGameSaver);

        //Act
        sutGame.prepareGame();

        //Assert/Verify
        verify(mockRankingList, times(1)).saveListToCsv();
        assertThat(outContent.toString()).contains("has won");
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
