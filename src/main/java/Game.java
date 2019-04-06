import java.util.Scanner;

public class Game {
    Scanner reader;
    Player firstPlayer;
    Player secondPlayer;
    private boolean currentPlayer = true;

    public Game() {
        this.reader = new Scanner(System.in);
    }

    public void prepareGame() {
        int width;
        int height;

        System.out.println("What width of board?");
        width = reader.nextInt();
        System.out.println("What height of board?");
        height = reader.nextInt();

        Board gameBoard = new Board(width, height);
        this.choosePlayers();
    }

    public void startGame(){

    }

    public void doTurn(){

    }

    public void choosePlayers() {
        RankingList rankingList = new RankingList();
        System.out.println(rankingList.showPlayers());

        int player1;
        int player2;
        do {
            System.out.println("Choose Player 1?");
            player1 = reader.nextInt();
            if(player1 < 0 || player1 > rankingList.list.values().size()){
                System.out.println("Player does not exists");
            }
        } while (player1 < 0 || player1 > rankingList.list.values().size());


        do {
            System.out.println("Choose Player 2");
            player2 = reader.nextInt();
            if(player2 < 0 || player2 > rankingList.list.values().size()){
                System.out.println("Player does not exists");
            }
            if(player1 == player2){
                System.out.println("Choose different player");
            }
        } while (player2 < 0 || player1 > rankingList.list.values().size() || player1 == player2);

        firstPlayer = (Player) rankingList.list.values().toArray()[player1];
        secondPlayer = (Player) rankingList.list.values().toArray()[player2];
    }

}
