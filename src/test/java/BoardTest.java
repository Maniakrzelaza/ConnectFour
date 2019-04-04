import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BoardTest {
    Board sutBoard;

    @Test
    public void shouldHaveProperSizeWhenArgsAreProper(){
        sutBoard = new Board(7, 6);
        assertAll(
                () -> assertThat(sutBoard.getWidth()).isEqualTo(7),
                () -> assertThat(sutBoard.getHeight()).isEqualTo(6)
        );
    }
    @Test
    public void shouldHaveMatrixWithProperSizeWhenArgsAreProper(){
        sutBoard = new Board(8, 7);
        assertAll(
                () -> assertThat(sutBoard.getBoardMatrix().length).isEqualTo(8),
                () -> assertThat(sutBoard.getBoardMatrix()[1].length).isEqualTo(7)
        );
    }

    @Test
    public void shouldThorowWhenArgsAreLessOrEqual0(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(0, -1);
        });
    }

    @AfterEach
    public void tearDown(){
        sutBoard = null;
    }
}
