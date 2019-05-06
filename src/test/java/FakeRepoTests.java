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
    public void shouldShowPlayerList(){
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
    @Test
    public void shouldLoadPlayersWithProperData(){
        IRankingList fakeRankingList = new FakeRankingList();
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
        ((FakeRankingList) fakeRankingList).seedData();
        FakeScannerWrapper fakeScannerWrapper = new FakeScannerWrapper();
        Queue<Integer> inputs = new LinkedList<>();
        inputs.add(0);
        inputs.add(1);
        Queue<Boolean> haxNexts = new LinkedList<>();
        haxNexts.add(false);
        Queue<String> nexts = new LinkedList<>();
        nexts.add("e");
        fakeScannerWrapper.nextInts = inputs;
        fakeScannerWrapper.nexts = nexts;
        fakeScannerWrapper.hasNexts = haxNexts;
        ConnectFour.gameSaver = fakeGameSaver;
        ConnectFour.loadGameFromDb();
        ConnectFour.rankingList = fakeRankingList;
        ConnectFour.game.setRankingList(fakeRankingList);
        ConnectFour.game.reader = fakeScannerWrapper;
        ConnectFour.game.setGameSaver(fakeGameSaver);
        ConnectFour.game.prepareLoadedGame();

        assertThat(ConnectFour.game.firstPlayer.getName()).isEqualTo("Player2");
    }

    @Test
    public void shouldThrowExceptionWhenThereIsNoSavedGameStates(){
        IRankingList fakeRankingList = new FakeRankingList();
        fakeGameSaver = new FakeGameSaver();
        ((FakeRankingList) fakeRankingList).seedData();
        FakeScannerWrapper fakeScannerWrapper = new FakeScannerWrapper();
        Queue<Integer> inputs = new LinkedList<>();
        inputs.add(4);
        fakeScannerWrapper.nextInts = inputs;
        ConnectFour.gameSaver = fakeGameSaver;
        ConnectFour.rankingList = fakeRankingList;
        ConnectFour.reader = fakeScannerWrapper;

        Assertions.assertThrows(IllegalArgumentException.class, ConnectFour::menu);
    }
    @Test
    public void shouldThrowExceptionWhenWrongMenuOptionWas(){
        IRankingList fakeRankingList = new FakeRankingList();
        fakeGameSaver = new FakeGameSaver();
        ((FakeRankingList) fakeRankingList).seedData();
        FakeScannerWrapper fakeScannerWrapper = new FakeScannerWrapper();
        Queue<Integer> inputs = new LinkedList<>();
        inputs.add(7667);
        fakeScannerWrapper.nextInts = inputs;
        ConnectFour.reader = fakeScannerWrapper;

        Assertions.assertThrows(IllegalArgumentException.class, ConnectFour::menu);
    }

    @Test
    public void shouldTryToValidateColumn(){
        setUpStreams();
        IRankingList fakeRankingList = new FakeRankingList();
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
        ((FakeRankingList) fakeRankingList).seedData();
        FakeScannerWrapper fakeScannerWrapper = new FakeScannerWrapper();
        Queue<Integer> inputs = new LinkedList<>();
        inputs.add(0);
        inputs.add(1);
        inputs.add(100);
        Queue<Boolean> haxNexts = new LinkedList<>();
        haxNexts.add(true);
        haxNexts.add(false);
        Queue<String> nexts = new LinkedList<>();
        nexts.add("e");
        fakeScannerWrapper.nextInts = inputs;
        fakeScannerWrapper.nexts = nexts;
        fakeScannerWrapper.hasNexts = haxNexts;
        ConnectFour.gameSaver = fakeGameSaver;
        ConnectFour.loadGameFromDb();
        ConnectFour.rankingList = fakeRankingList;
        ConnectFour.game.setRankingList(fakeRankingList);
        ConnectFour.game.reader = fakeScannerWrapper;
        ConnectFour.game.setGameSaver(fakeGameSaver);
        ConnectFour.game.prepareLoadedGame();

        assertThat(outContent.toString()).containsOnlyOnce("Choose legal column");
        backToNormalState();
    }
    @Test
    public void shouldTryToValidatePlayerChoice(){
        setUpStreams();
        IRankingList fakeRankingList = new FakeRankingList();
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
        ((FakeRankingList) fakeRankingList).seedData();
        FakeScannerWrapper fakeScannerWrapper = new FakeScannerWrapper();
        Queue<Integer> inputs = new LinkedList<>();
        inputs.add(0);
        inputs.add(0);
        inputs.add(-5);
        inputs.add(1);
        inputs.add(0);
        Queue<String> nexts = new LinkedList<>();
        nexts.add("e");
        Queue<Boolean> hasNexts = new LinkedList<>();
        hasNexts.add(true);
        hasNexts.add(false);
        fakeScannerWrapper.nextInts = inputs;
        fakeScannerWrapper.nexts = nexts;
        fakeScannerWrapper.hasNexts = hasNexts;
        ConnectFour.gameSaver = fakeGameSaver;
        ConnectFour.loadGameFromDb();
        ConnectFour.rankingList = fakeRankingList;
        ConnectFour.game.setRankingList(fakeRankingList);
        ConnectFour.game.reader = fakeScannerWrapper;
        ConnectFour.game.setGameSaver(fakeGameSaver);
        ConnectFour.game.choosePlayers();

        assertAll(
                () -> assertThat(outContent.toString()).containsOnlyOnce("Choose different player"),
                () -> assertThat(outContent.toString()).containsOnlyOnce("Player does not exists")
        );
        backToNormalState();
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
