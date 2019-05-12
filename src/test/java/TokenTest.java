import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TokenTest {
    private Token sutToken;

    @BeforeEach
    public void setUp(){
    }

    @Test
    public void shouldHaveProperColor(){
        sutToken = new Token('R');

        assertThat(sutToken.getColor()).isEqualTo('R');
    }

    @Test
    public void shouldThrowIfWrongColorIsGiven(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sutToken = new Token('N');
        });
    }

    @AfterEach
    public void tearDown(){
        sutToken = null;
    }
}
