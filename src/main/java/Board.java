import java.util.ArrayList;
import java.util.List;

public class Board {
    private int width;
    private int height;
    private List<List<Token>> boardMatrix;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        if (width <= 7)
            this.width = 7;
        if (height <= 6)
            this.height = 6;
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }

        this.boardMatrix = new ArrayList<List<Token>>();

        for (int i = 0; i < this.width; i++) {
            this.boardMatrix.add(new ArrayList<Token>());
        }
    }
    public Board(){
    }

    public void addTokenToBoard(Token token, int posX) {
        if (this.isLegalMove(posX)) {
            throw new IllegalArgumentException();
        } else {
            this.boardMatrix.get(posX).add(token);
        }
    }

    public boolean isColumnFull(int column) {
        return this.boardMatrix.get(column).size() == this.height;
    }

    public boolean isLegalMove(int posX){
        return posX < 0 || posX >= this.width || this.isColumnFull(posX) || isBoardFull();
    }

    public boolean isBoardFull() {
        boolean result = true;
        for (List<Token> list : this.boardMatrix) {
            if (list.size() != this.height) {
                result = false;
            }
        }
        return result;
    }

    public boolean hasPlayerWon(int x, int y, Character ch){
        return this.isHorizontalWin(x, y, ch) ||
                this.isVerticalWin(x, y, ch) ||
                this.isDiagonalBottomToTop(x, y, ch) ||
                this.isDiagonalTopToBottom(x, y, ch);
    }

    public boolean isDiagonalTopToBottom(int x, int y, Character ch){
        int bigestSeriesCounter = 0;
        int tempCounter = 0;
        for(int i = -3; i < 4; i++){
            if(this.get(x + i, y - i).equals(ch)){
                tempCounter += 1;
                bigestSeriesCounter = Math.max(tempCounter, bigestSeriesCounter);
            } else {
                tempCounter = 0;
            }
        }
        return bigestSeriesCounter >= 4;
    }

    public boolean isDiagonalBottomToTop(int x, int y, Character ch){
        int bigestSeriesCounter = 0;
        int tempCounter = 0;
        for(int i = -3; i < 4; i++){
            if(this.get(x + i, y + i).equals(ch)){
                tempCounter += 1;
                bigestSeriesCounter = Math.max(tempCounter, bigestSeriesCounter);
            } else {
                tempCounter = 0;
            }
        }
        return bigestSeriesCounter >= 4;
    }

    public boolean isVerticalWin(int x, int y, Character ch){
        int topPosition = this.getMostTopPosition(x, y);
        int bottomPosition = this.getMostBottomPosition(x, y);
        int bigestSeriesCounter = 0;
        int tempCounter = 0;
        for(int i = bottomPosition; i <= topPosition; i++){
            if(this.get(x, i).equals(ch)){
                tempCounter += 1;
                bigestSeriesCounter = Math.max(tempCounter, bigestSeriesCounter);
            } else {
                tempCounter = 0;
            }
        }
        return bigestSeriesCounter >= 4;
    }

    public boolean isHorizontalWin(int x, int y, Character ch){
        int leftPosition = this.getMostLeftPosition(x, y);
        int rightPosition = this.getMostRightPosition(x, y);
        int bigestSeriesCounter = 0;
        int tempCounter = 0;
        for(int i = leftPosition; i <= rightPosition; i++){
            if(this.get(i, y).equals(ch)){
                tempCounter += 1;
                bigestSeriesCounter = Math.max(tempCounter, bigestSeriesCounter);
            } else {
                tempCounter = 0;
            }
        }
        return bigestSeriesCounter >= 4;
    }

    public Character get(int x, int y){
        try{
            return this.boardMatrix.get(x).get(y).getColor();
        } catch (IndexOutOfBoundsException e){
            return 'E';
        }
    }

    public int getMostLeftPosition(int x, int y){
        return x - 3 < 0 ? 0 : x - 3;
    }

    public int getMostRightPosition(int x, int y){
        return x + 3 >= this.width ? this.width - 1 : x + 3;
    }

    public int getMostTopPosition(int x, int y){
        return y + 3 >= this.height ? this.height - 1 : y + 3;

    }

    public int getMostBottomPosition(int x, int y){
        return y - 3 < 0 ? 0 : y - 3;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = this.height - 1; i >= 0; i--) {
            for (List<Token> list : this.boardMatrix) {
                try {
                    result.append("| " + list.get(i).toString());
                } catch (IndexOutOfBoundsException e) {
                    result.append("|  ");
                }
                result.append(" ");
            }
            result.append("|\n");

        }
        for(int i = 0; i < this.width; i++){
            result.append("| " + i + " ");
        }
        result.append("|");
        return result.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<List<Token>> getBoardMatrix() {
        return boardMatrix;
    }
}
