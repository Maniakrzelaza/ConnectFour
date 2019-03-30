import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BoardTest {
    Board sutBoard;

    @Test
    public void testBoardSize(){
        sutBoard = new Board(7, 6);
        assertAll(
                () -> assertThat(sutBoard.getWidth()).isEqualTo(7),
                () -> assertThat(sutBoard.getHeight()).isEqualTo(6)
        );
    }

    @AfterEach
    public void tearDown(){
        sutBoard = null;
    }
}
