import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Board sutBoard;

    @Test
    public void testAllWinConditions(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('R'), 2);
        sutBoard.addTokenToBoard(new Token('R'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('R'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 5);
        sutBoard.addTokenToBoard(new Token('R'), 5);
        sutBoard.addTokenToBoard(new Token('G'), 5);
        sutBoard.addTokenToBoard(new Token('G'), 5);

        assertThat(sutBoard.hasPlayerWon(5, 3, 'G'));
    }

    @Test
    public void shouldTellTrueWhenPlayerWonByDiagonalTopToBottom(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 4);

        assertTrue(sutBoard.isDiagonalTopToBottom(4, 0, 'G'));
    }

    @Test
    public void shouldTellFalseWhenPlayerWonByDiagonalTopToBottom(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('R'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 4);

        assertFalse(sutBoard.isDiagonalTopToBottom(4, 0, 'G'));
    }

    @Test
    public void shouldTellTrueWhenPlayerWonByDiagonalBottomToTop(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 1);

        assertTrue(sutBoard.isDiagonalBottomToTop(1, 0, 'G'));
    }

    @Test
    public void shouldTellFalseWhenPlayerWonByDiagonalBottomToTop(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 4);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('R'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 1);

        assertFalse(sutBoard.isDiagonalBottomToTop(1, 0, 'G'));
    }

    @Test
    public void shouldTellTrueWhenPlayerWonByVertical(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);

        assertTrue(sutBoard.isVerticalWin(1, 2, 'G'));
    }

    @Test
    public void shouldTellFalseWhenPlayerDidNotWonByVertical(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('R'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 1);

        assertFalse(sutBoard.isVerticalWin(1, 4, 'G'));
    }

    @Test
    public void shouldTellTrueWhenPlayerWonByHorizontall(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('G'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 4);

        assertTrue(sutBoard.isHorizontalWin(4, 0, 'G'));
    }

    @Test
    public void shouldTellFalseWhenPlayerDidNotWonByHorizontal(){
        sutBoard = new Board(7, 6);
        sutBoard.addTokenToBoard(new Token('G'), 1);
        sutBoard.addTokenToBoard(new Token('G'), 2);
        sutBoard.addTokenToBoard(new Token('R'), 3);
        sutBoard.addTokenToBoard(new Token('G'), 4);

        assertFalse(sutBoard.isHorizontalWin(4, 0, 'G'));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1, delimiter = ',')
    public void shouldComputeHighestLegalPosition(int width, int height, int posX, int posY, int result){
        sutBoard = new Board(width, height);
        assertThat(sutBoard.getMostTopPosition(posX, posY)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource({"7,6,3,4,5", "7,6,3,5,5", "7,6,1,4,5"})
    public void shouldHaveComputeHighestPositionOfWinMeasureWhenResultIsOutsideBoard(int  width, int height, int posX, int posY, int result){
        sutBoard = new Board(width, height);
        assertThat(sutBoard.getMostTopPosition(posX, posY)).isEqualTo(result);
    }

    @Test
    public void shouldHaveComputeHighestPositionOfWinMeasureWhenResultIsWithinBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostTopPosition(3, 0)).isEqualTo(3);
    }

    @Test
    public void shouldHaveComputeLowestPositionOfWinMeasureWhenResultIsOutsideBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostBottomPosition(3, 1)).isEqualTo(0);
    }

    @Test
    public void shouldHaveComputeLowestPositionOfWinMeasureWhenResultIsWithinBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostBottomPosition(3, 4)).isEqualTo(1);
    }

    @Test
    public void shouldHaveComputeMostLeftPositionOfWinMeasureWhenResultIsOutsideBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostLeftPosition(2, 1)).isEqualTo(0);
    }

    @Test
    public void shouldHaveComputeMostLeftPositionOfWinMeasureWhenResultIsWithinBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostLeftPosition(4, 4)).isEqualTo(1);
    }

    @Test
    public void shouldHaveComputeMostRightPositionOfWinMeasureWhenResultIsOutsideBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostRightPosition(1, 1)).isEqualTo(4);
    }

    @Test
    public void shouldHaveComputeMostRightPositionOfWinMeasureWhenResultIsWithinBoard(){
        sutBoard = new Board(7, 6);
        assertThat(sutBoard.getMostRightPosition(4, 4)).isEqualTo(6);
    }

    @Test
    public void shouldHaveProperSizeWhenArgsAreProper() {
        sutBoard = new Board(7, 6);
        assertAll(
                () -> assertThat(sutBoard.getWidth()).isEqualTo(7),
                () -> assertThat(sutBoard.getHeight()).isEqualTo(6)
        );
    }

    @Test
    public void shouldThrowWhenArgsAreLessOrEqual0() {
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
                "| G |   |   |   |   |   | G |\n" +
                        "| G |   |   |   |   |   | G |\n" +
                        "| G |   |   |   |   |   | G |\n" +
                        "| G |   |   | R |   |   | G |\n" +
                        "| G | G |   | R |   |   | G |\n" +
                        "| G | R | G | R |   |   | G |\n" +
                        "| 0 | 1 | 2 | 3 | 4 | 5 | 6 |",
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
