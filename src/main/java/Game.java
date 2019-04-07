import java.util.Scanner;

public class Game {
    Scanner reader;
    Player firstPlayer;
    Player secondPlayer;
    private boolean currentPlayerFlag = true;
    private boolean isOver = false;
    private Board gameBoard;
    private RankingList rankingList;

    public Game() {
        this.reader = new Scanner(System.in);
        this.rankingList = new RankingList();
    }

    public void prepareGame() {
        int width;
        int height;

        System.out.println("What width of board?");
        width = reader.nextInt();
        System.out.println("What height of board?");
        height = reader.nextInt();
        this.gameBoard = new Board(width, height);
        this.choosePlayers();
        this.startGame();
    }

    public void startGame(){
        int column;
        Player currentPlayer = null;
        while(!this.isOver){
            System.out.println(this.gameBoard.toString());
            if(currentPlayerFlag){
                currentPlayer = firstPlayer;
            } else {
                currentPlayer = secondPlayer;
            }
            do{
                System.out.println(currentPlayer.getName() + " Select column ");
                column = reader.nextInt();
                if(this.gameBoard.isLegalMove(column)){
                    System.out.println("Choose legal column");
                }
            } while (this.gameBoard.isLegalMove(column));
            this.gameBoard.addTokenToBoard(new Token(currentPlayer.getColor()), column);
            this.isOver = this.gameBoard.hasPlayerWon(column, this.gameBoard.getBoardMatrix().get(column).size() - 1, currentPlayer.getColor());
            currentPlayerFlag = !currentPlayerFlag;
        }
        System.out.println(currentPlayer.getName() + " has won");
        currentPlayer.win();
        rankingList.saveListToCsv();
        ConnectFour.menu();
    }

    public void choosePlayers() {
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
        firstPlayer.setColor('G');
        secondPlayer = (Player) rankingList.list.values().toArray()[player2];
        secondPlayer.setColor('R');
    }

}
