import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PlayerTest {
    Player sutPlayer;

    @Test
    public void shouldSetProperNameWith0Wins(){
        sutPlayer = new Player("name");
        assertAll(
                () -> assertThat(sutPlayer.getName()).contains("name"),
                () -> assertThat(sutPlayer.getNumberOfWins()).isEqualTo(0)
        );
    }

    @Test
    public void shouldChangeName(){
        sutPlayer = new Player("name");
        sutPlayer.setName("newName");
        assertThat(sutPlayer.getName()).startsWith("new");
    }

    @Test
    public void shouldChangeNumberOfWins(){
        sutPlayer = new Player("name");
        sutPlayer.setNumberOfWins(15);
        assertThat(sutPlayer.getNumberOfWins()).isGreaterThan(14);
    }

    @Test
    public void shouldChangeNumberOfWinsWhenPlayerWins(){
        sutPlayer = new Player("name", 5);
        sutPlayer.win();
        assertThat(sutPlayer.getNumberOfWins()).isGreaterThan(5);
    }

    @Test
    public void shouldReturnProperToStringValue(){
        sutPlayer = new Player("Adam", 5);

        assertThat(sutPlayer.toString()).isEqualTo("Name: Adam Wins: 5");
    }

    @AfterEach
    public void tearDown(){
        sutPlayer = null;
    }
}
