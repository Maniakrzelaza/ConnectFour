import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    Board sutBoard;

    @Test
    public void shouldHaveProperSizeWhenArgsAreProper() {
        sutBoard = new Board(7, 6);
        assertAll(
                () -> assertThat(sutBoard.getWidth()).isEqualTo(7),
                () -> assertThat(sutBoard.getHeight()).isEqualTo(6)
        );
    }

    @Test
    public void shouldThorowWhenArgsAreLessOrEqual0() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Board(0, -1);
        });
    }

    @Test
    public void shouldAddToColumnProperly() {
        sutBoard = new Board(7, 6);
        this.fillColumn(0);

        assertEquals(6, sutBoard.getBoardMatrix().get(0).size());
    }

    @Test
    public void shouldNotAddTokenToFullColumn() {
        sutBoard = new Board(7, 6);
        this.fillColumn(0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sutBoard.addTokenToBoard(new Token('G'), 0);
        });
    }

    @Test
    public void shouldCheckIfBoardIsFullProperly() {
        sutBoard = new Board(7, 6);
        this.fillColumn(0);
        this.fillColumn(1);
        this.fillColumn(2);
        this.fillColumn(3);
        this.fillColumn(4);
        this.fillColumn(5);
        this.fillColumn(6);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sutBoard.addTokenToBoard(new Token('G'), 0);
        });
    }

    @Test
    public void shouldDisplayProperly() {
        sutBoard = new Board(7, 6);
        this.fillColumn(0);
        sutBoard.addTokenToBoard(new Token('R'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('R'), 3);
        sutBoard.addTokenToBoard(new Token('R'), 3);
        sutBoard.addTokenToBoard(new Token('R'), 3);
        this.fillColumn(6);
        assertEquals(
                "G           G \n" +
                        "G           G \n" +
                        "G           G \n" +
                        "G     R     G \n" +
                        "G G   R     G \n" +
                        "G R G R     G ",
                sutBoard.toString()
        );
    }

    @AfterEach
    public void tearDown() {
        sutBoard = null;
    }

    private void fillColumn(int column) {
        sutBoard.addTokenToBoard(new Token('G'), column);
        sutBoard.addTokenToBoard(new Token('G'), column);
        sutBoard.addTokenToBoard(new Token('G'), column);
        sutBoard.addTokenToBoard(new Token('G'), column);
        sutBoard.addTokenToBoard(new Token('G'), column);
        sutBoard.addTokenToBoard(new Token('G'), column);
    }
}
