import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingListTest {

    private RankingList sutRankingList;

    @BeforeEach
    public void setUp(){
        RankingList.FILE_NAME = "TestRankingList.csv";
    }

    @Test
    public void shouldHaveProperSize(){

        sutRankingList = new RankingList();

        HashMap<String, Player> list = sutRankingList.list;

        assertThat(list.values().size()).isEqualTo(3);
    }

    @Test
    public void shouldSavePlayers(){
        sutRankingList = new RankingList();

        sutRankingList.addPlayer(new Player("Harry", 51));

        sutRankingList.saveListToCsv();

        sutRankingList.loadPlayersFromCsv();

        assertThat(sutRankingList.list.values().size()).isEqualTo(4);
    }

    @Test
    public void shouldThrowWhenThereIsNoFile(){
        RankingList.FILE_NAME = "NoFile.csv";

        Assertions.assertThrows(NullPointerException.class, () -> {
            sutRankingList = new RankingList();
        });
    }

    @Test
    public void shouldShowPlayersListProperly(){
        sutRankingList = new RankingList();

        String list = sutRankingList.showPlayers();

        assertEquals("0. Name: Adam Wins: 5\n" +
                "1. Name: Robert Wins: 0\n" +
                "2. Name: Monika Wins: 78",list);
    }

    @AfterEach
    public void tearDown(){
        File file = new File(getClass().getClassLoader().getResource("TestRankingList.csv").getFile());
        try (PrintWriter writer = new PrintWriter(new File(file.getAbsolutePath()))) {
            writer.write("Adam,5\n" +
                    "Monika, 78\n" +
                    "Robert, 0");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
