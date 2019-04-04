import java.util.Scanner;

public class Game {
    public Game() {
    }
    public void startGame() {
        int width;
        int height;

        try (Scanner reader = new Scanner(System.in)){
            System.out.println("What width of board?");
            width = reader.nextInt();
            System.out.println("What height of board?");
            height = reader.nextInt();
        }

        Board gameBoard = new Board(width, height);
    }

}
