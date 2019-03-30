import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Calc2 {
    @Test
    public void hamcrestTest(){
        assertThat(2, equalTo(2));
    }
}
