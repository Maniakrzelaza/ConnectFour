import java.util.Scanner;

public class ConnectFour {
    public static void main(String args[]){
        ConnectFour.menu();
    }

    public static void menu(){
        System.out.println("========Connect Four========");
        System.out.println("What do you want to do?");
        System.out.println("1. New Game");
        System.out.println("2. Ranking list");
        System.out.println("0. END");

        int option;

        try (Scanner reader = new Scanner(System.in)) {
            System.out.println("What width of board?");
            option = reader.nextInt();
        }

        switch (option){
            case 1:
                ConnectFour.newGame();
                break;
            case 2:
                ConnectFour.showRankingList();
                break;
            case 0:
                break;
        }
    }

    public static void newGame(){
        Game game = new Game();
        game.startGame();
    }

    public static void showRankingList(){
        RankingList rankingList = new RankingList();
        for(Player player : rankingList.list.values()){
            System.out.println(player.toString());
        }
    }
}
