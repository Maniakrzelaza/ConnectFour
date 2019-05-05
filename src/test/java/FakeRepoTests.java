import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FakeRepoTests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;
    FakeGameSaver fakeGameSaver;
    @BeforeAll
    public static void prepare(){
        ConnectFour.reader = new ScannerWrapper();
        ConnectFour.rankingList = new RankingList();
    }
    @BeforeEach
    public void setUp(){
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
    }

    @Test
    public void shouldLoadFromFakeRepo() {
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
        FakeRankingList fakeRankingList = new FakeRankingList();
        fakeRankingList.seedData();
        ConnectFour.gameSaver = fakeGameSaver;
        ConnectFour.rankingList = fakeRankingList;
        ConnectFour.loadGameFromDb();
        Game sutGame = ConnectFour.game;
        assertAll(
                () -> assertThat(sutGame.getGameBoard().getHeight()).isEqualTo(15),
                () -> assertThat(sutGame.getGameBoard().getWidth()).isEqualTo(10)
        );
    }
    @Test
    public void shouldSaveGameStateProperly(){
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
        System.out.println(fakeGameSaver.loadGame().getTurn());
        Game sutGame = new Game();
        sutGame.setGameSaver(fakeGameSaver);
        sutGame.setGameBoard(new Board());
        sutGame.saveGame();

        assertThat(fakeGameSaver.getFakeList()).hasSize(4);
    }
    @Test
    public void shouldTest(){
        setUpStreams();
        IRankingList fakeRankingList = new FakeRankingList();
        ((FakeRankingList) fakeRankingList).seedData();
        FakeScannerWrapper fakeScannerWrapper = new FakeScannerWrapper();
        Queue<Integer> inputs = new LinkedList<>();
        inputs.add(2);
        inputs.add(0);
        fakeScannerWrapper.nextInts = inputs;
        ConnectFour.rankingList = fakeRankingList;
        ConnectFour.reader = fakeScannerWrapper;
        ConnectFour.menu();
        assertThat(outContent.toString()).containsSubsequence("0. Name: Player2 Wins: 2\n" +
                "1. Name: Player1 Wins: 1\n" +
                "2. Name: Player3 Wins: 3");
        restoreStreams();
    }

    @AfterEach
    public void tearDown(){
        fakeGameSaver = null;
    }
    @AfterAll
    public static void backToNormalState(){
        ConnectFour.reader = new ScannerWrapper();
        ConnectFour.rankingList = new RankingList();
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
