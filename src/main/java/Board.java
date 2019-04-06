import java.util.ArrayList;
import java.util.List;

public class Board {
    private int width;
    private int height;
    private List<List<Token>> boardMatrix;

    public Board(int width, int height) {
        if (width <= 7)
            this.width = 7;
        if (height <= 6)
            this.height = 6;
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;

        this.boardMatrix = new ArrayList<List<Token>>();

        for (int i = 0; i < this.width; i++) {
            this.boardMatrix.add(new ArrayList<Token>());
        }
    }

    public void addTokenToBoard(Token token, int posX) {
        if (posX < 0 || posX >= this.width || this.isColumnFull(posX) || isBoardFull()) {
            throw new IllegalArgumentException();
        } else {
            this.boardMatrix.get(posX).add(token);
        }
    }

    public boolean isColumnFull(int column) {
        return this.boardMatrix.get(column).size() == this.height;
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = this.height - 1; i >= 0; i--) {
            for (List<Token> list : this.boardMatrix) {
                try {
                    result.append(list.get(i).toString());
                } catch (IndexOutOfBoundsException e) {
                    result.append(" ");
                }
                result.append(" ");
            }
            result.append("\n");

        }

        return result.delete(result.length() - 1, result.length()).toString();
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
