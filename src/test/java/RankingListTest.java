import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RankingListTest {

    private RankingList sutRankingList;

    @Test
    public void shouldHaveProperSize(){
        RankingList.FILE_NAME = "TestRankingList.csv";

        sutRankingList = new RankingList();

        HashMap<String, Player> list = sutRankingList.list;

        assertThat(list.values().size()).isEqualTo(3);
    }

    @Test
    public void shouldSavePlayers(){
        RankingList.FILE_NAME = "TestRankingList.csv";

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

    @AfterEach
    public void tearDown(){
        RankingList.FILE_NAME = "TestRankingList.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("../resources/TestRankingList.csv").getFile());
        try (PrintWriter writer = new PrintWriter(new File(file.getAbsolutePath()))) {
            writer.write("Adam,5\n" +
                    "Monika, 78\n" +
                    "Robert, 0");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
