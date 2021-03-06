package main.java;

public class ConnectFour {
    public static IScanner reader = new ScannerWrapper();
    public static IRankingList rankingList = new RankingList();
    public static Saver gameSaver = new GameSaver();
    public static Game game;

    public static void main(String args[]) {
        ConnectFour.menu();
    }

    public static void menu() {
        int option = 0;

        System.out.println("========Connect Four========");
        System.out.println("What do you want to do?");
        System.out.println("1. New Game");
        System.out.println("2. Ranking list");
        System.out.println("3. Add Player");
        System.out.println("4. Load Game");
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
                ConnectFour.menu();
                break;
            case 4:
                ConnectFour.loadGame();
                break;
            case 0:
                break;
            default:
                throw new IllegalArgumentException("There is no such option");
        }
    }

    public static void newGame() {
        if (rankingList.getList().values().size() >= 2) {
            game = new Game();
            game.prepareGame();
        } else {
            System.out.println("Not enough players to play. Create more players");
            ConnectFour.menu();
        }
    }

    public static void showRankingList() {
        System.out.println(rankingList.showPlayers());
        ConnectFour.menu();
    }

    public static void addPlayer() {
        System.out.println("Name? ");
        String name = "";
        name = reader.next();

        rankingList.addPlayer(new Player(name));
        rankingList.saveListToCsv();
    }

    public static void loadGameFromDb() {
        GameState loadedGameState = gameSaver.loadGame();
        if (rankingList.getList().values().size() >= 2) {
            game = new Game(loadedGameState);
            gameSaver.deleteLastSession();
        } else {
            System.out.println("Not enough players to play. Create more players");
            ConnectFour.menu();
        }
    }

    public static void loadGame() {
        loadGameFromDb();
        game.prepareLoadedGame();
    }
}
