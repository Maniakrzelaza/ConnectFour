import org.jongo.MongoCollection;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import main.java.*;
public class MockitoTests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;

    @Test
    public void connectFourShouldCallAddPlayerMethod() {
        //Arrange
        IScanner mockScanner = mock(ScannerWrapper.class);
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
        ScannerWrapper mockScanner = mock(ScannerWrapper.class);
        when(mockScanner.nextInt())
                .thenReturn(7, 6, 0, 1, 0);
        when(mockScanner.hasNextInt())
                .thenReturn(true)
                .thenReturn(false);
        when(mockScanner.next())
                .thenReturn("e");
        RankingList mockRankingList = mock(RankingList.class);
        HashMap<String, Player> fakeList = new HashMap<>();
        fakeList.put("Player1", new Player("Player1", 1));
        fakeList.put("Player2", new Player("Player2", 2));
        when(mockRankingList.getList())
                .thenReturn(fakeList);
        Game sutGame = new Game();
        sutGame.reader = mockScanner;
        sutGame.setRankingList(mockRankingList);

        //Act
        sutGame.prepareGame();
        sutGame.startGame();

        //Assert/Verify
        verify(mockScanner, times(5)).nextInt();
        assertAll(
                () -> assertThat(sutGame.getGameBoard().getHeight()).isEqualTo(6),
                () -> assertThat(sutGame.getGameBoard().getWidth()).isEqualTo(7)
        );
    }
    @Test
    public void gameShouldCallShowRankingList() {
        //Arrange
        setUpStreams();
        IScanner mockScanner = mock(ScannerWrapper.class);
        when(mockScanner.nextInt())
                .thenReturn(2)
                .thenReturn(0);
        RankingList spyRankingList = spy(RankingList.class);
        spyRankingList.getList().clear();
        spyRankingList.addPlayer(new Player("Player1", 1));
        spyRankingList.addPlayer(new Player("Player2", 2));

        ConnectFour.reader = mockScanner;
        ConnectFour.rankingList = spyRankingList;

        //Act
        ConnectFour.menu();

        //Assert/Verify
        verify(mockScanner, times(2)).nextInt();
        verify(spyRankingList, times(1)).showPlayers();
        assertThat(outContent.toString()).contains("0. Name: Player2 Wins: 2\n" +
                "1. Name: Player1 Wins: 1");
        restoreStreams();
    }
    @Test
    public void gameSaveShouldSaveProperly(){
        //Arrange
        GameSaver sutGameSaver = new GameSaver();
        MongoCollection mockGameStates = mock(MongoCollection.class);
        when(mockGameStates.save(any(Object.class))).thenAnswer(invocation -> null);
        sutGameSaver.gameStates = mockGameStates;

        //Act
        sutGameSaver.saveGame(new GameState(0, new Board(7,6)));

        //Assert/Verify
        verify(mockGameStates, times(1)).save(any(GameState.class));
    }

    @Test
    public void gameShouldSaveGame() {
        //Arrange
        ScannerWrapper mockScanner = mock(ScannerWrapper.class);
        when(mockScanner.nextInt())
                .thenReturn(7, 6, 0, 1, 0);
        when(mockScanner.next())
                .thenReturn("s")
                .thenReturn("e");
        GameSaver mockGameSaver = mock(GameSaver.class);
        doNothing().when(mockGameSaver).saveGame(any(GameState.class));
        RankingList mockRankingList = mock(RankingList.class);
        HashMap<String, Player> fakeList = new HashMap<>();
        fakeList.put("Player1", new Player("Player1", 1));
        fakeList.put("Player2", new Player("Player2", 2));
        when(mockRankingList.getList())
                .thenReturn(fakeList);
        Game sutGame = new Game();
        sutGame.reader = mockScanner;
        sutGame.setRankingList(mockRankingList);
        sutGame.setGameSaver(mockGameSaver);

        //Act
        sutGame.prepareGame();
        sutGame.startGame();
        //Assert/Verify
        verify(mockGameSaver, times(1)).saveGame(any(GameState.class));
    }

    @Test
    public void gameShouldNotStartWhenThereIsNotEnoughPlayers() {
        //Arrange
        setUpStreams();
        IScanner mockScanner = mock(ScannerWrapper.class);
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
        restoreStreams();
        assertThat(outContent.toString()).contains("Not enough players to play. Create more players");
    }

    @Test
    public void gameShouldEndWhenOnePlayerWins() {
        //Arrange
        setUpStreams();
        ScannerWrapper mockScanner = mock(ScannerWrapper.class);
        when(mockScanner.nextInt())
                .thenReturn(7, 6, 0, 1, 0, 1, 0, 1, 0, 1, 0);
        when(mockScanner.hasNextInt())
                .thenReturn(true);
        IScanner mockAppScanner = mock(ScannerWrapper.class);
        when(mockAppScanner.nextInt())
                .thenReturn(0);
        ConnectFour.reader = mockAppScanner;
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
        sutGame.startGame();

        //Assert/Verify
        verify(mockRankingList, times(1)).saveListToCsv();
        assertThat(outContent.toString()).contains("has won");
        restoreStreams();
    }

    @Test
    public void loadedGameShouldHaveProperValues() {
        //Arrange
        ScannerWrapper mockGameScanner = mock(ScannerWrapper.class);
        when(mockGameScanner.nextInt())
                .thenReturn( 0, 1);
        when(mockGameScanner.next())
                .thenReturn("e");
        when(mockGameScanner.hasNextInt())
                .thenReturn(false);
        RankingList mockRankingList = mock(RankingList.class);
        HashMap<String, Player> fakeList = new HashMap<>();
        fakeList.put("Player1", new Player("Player1", 1));
        fakeList.put("Player2", new Player("Player2", 2));
        when(mockRankingList.getList())
                .thenReturn(fakeList);
        Game sutGame = new Game(new GameState(4, new Board(10,15)));
        sutGame.reader = mockGameScanner;
        sutGame.setRankingList(mockRankingList);
        //Act
        sutGame.prepareLoadedGame();

        //Assert/Verify
        assertAll(
                () -> assertThat(sutGame.getGameBoard().getHeight()).isEqualTo(15),
                () -> assertThat(sutGame.getGameBoard().getWidth()).isEqualTo(10),
                () -> assertThat(sutGame.getTurn()).isEqualTo(4)
        );
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
