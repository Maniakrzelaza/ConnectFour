import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    public void testAdd(){
        assertEquals(54, 2 + 2);
    }
    @Test
    public void testAdd2(){
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testAddAssertJ(){
        assertThat("abc")
                .startsWith("a")
                .contains("b")
                .endsWith("c");
    }
}
