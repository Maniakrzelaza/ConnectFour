import org.junit.jupiter.api.*;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FakeRepoTests {
    FakeGameSaver fakeGameSaver;
    @BeforeAll
    public static void prepare(){
        ConnectFour.reader = new Scanner(System.in);
        ConnectFour.rankingList = new RankingList();
    }
    @BeforeEach
    public void setUp(){
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
    }

    @Test
    public void shouldLoadFromFakeRepo() {
        ConnectFour.gameSaver = fakeGameSaver;
        ConnectFour.loadGameFromDb();
        Game sutGame = ConnectFour.game;
        assertAll(
                () -> assertThat(sutGame.getGameBoard().getHeight()).isEqualTo(15),
                () -> assertThat(sutGame.getGameBoard().getWidth()).isEqualTo(10)
        );
    }
    @Test
    public void shouldSaveGameStateProperly(){
        Game sutGame = ConnectFour.game;
        sutGame.setGameSaver(fakeGameSaver);
        sutGame.setGameBoard(new Board());
        sutGame.saveGame();

        assertThat(fakeGameSaver.getFakeList()).hasSize(4);
    }

    @AfterEach
    public void tearDown(){
        fakeGameSaver = null;
    }
    @AfterAll
    public static void backToNormalState(){
        ConnectFour.reader = new Scanner(System.in);
        ConnectFour.rankingList = new RankingList();
    }
}
