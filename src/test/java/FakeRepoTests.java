import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class FakeRepoTests {
    FakeGameSaver fakeGameSaver;
    @BeforeEach
    public void setUp(){
        fakeGameSaver = new FakeGameSaver();
        fakeGameSaver.seedData();
    }

    @Test
    public void shouldTest(){
        assertThat(2 + 2).isEqualTo(4);
    }

    @AfterEach
    public void tearDown(){
        fakeGameSaver = null;
    }
}
