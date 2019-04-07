import java.util.Scanner;

public class ConnectFour {
    public static Scanner reader = new Scanner(System.in);

    public static void main(String args[]) {
        ConnectFour.menu();
    }

    public static void menu() {
        int option;

        System.out.println("========Connect Four========");
        System.out.println("What do you want to do?");
        System.out.println("1. New Game");
        System.out.println("2. Ranking list");
        System.out.println("3. Add Player");
        System.out.println("0. END");

        System.out.println("What do you want to do?");
        option = reader.nextInt();

        switch (option) {
            case 1:
                ConnectFour.newGame();
                break;
            case 2:
                ConnectFour.showRankingList();
                break;
            case 3:
                ConnectFour.addPlayer();
                break;
            case 0:
                break;
        }
    }

    public static void newGame() {
        if(new RankingList().list.values().size() >= 2){
            Game game = new Game();
            game.prepareGame();
        } else {
            System.out.println("Not enough players to play. Create more players");
            ConnectFour.menu();
        }
    }

    public static void showRankingList() {
        System.out.println(new RankingList().showPlayers());
        ConnectFour.menu();
    }

    public static void addPlayer() {
        System.out.println("Name? ");
        String name = "";
        name = reader.next();

        RankingList rankingList = new RankingList();
        rankingList.addPlayer(new Player(name));
        rankingList.saveListToCsv();
        ConnectFour.menu();
    }
}
