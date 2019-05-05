import java.util.Scanner;

public class Game {
    public IScanner reader;
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean currentPlayerFlag = true;
    private boolean isOver = false;
    private Board gameBoard;
    private IRankingList rankingList;
    private Saver gameSaver;
    private int turn;
    public Game() {
        this.reader = new ScannerWrapper();
        this.rankingList = new RankingList();
        this.turn = 0;
        this.gameSaver = new GameSaver();
    }
    public Game(GameState gameState){
        this.reader = new ScannerWrapper();
        this.rankingList = new RankingList();
        this.gameSaver = new GameSaver();
        this.turn = gameState.getTurn();
        this.gameBoard = gameState.getBoard();
    }

    public void prepareGame(){
        this.prepareBoard();
        this.choosePlayers();
    }
    public void prepareBoard(){
        int width;
        int height;

        System.out.println("What width of board?");
        width = reader.nextInt();
        System.out.println("What height of board?");
        height = reader.nextInt();
        this.gameBoard = new Board(width, height);
    }

    public void prepareLoadedGame(){
        this.choosePlayers();
        this.startGame();
    }

    public void startGame(){
        int column =-1;
        Player currentPlayer = null;
        String line = "";
        while(!this.isOver){
            System.out.println(this.gameBoard.toString());
            currentPlayer = currentPlayerFlag ? firstPlayer : secondPlayer;
            do{
                System.out.println(currentPlayer.getName() + " Select column or s to save");
                if(reader.hasNextInt()){
                    column = reader.nextInt();
                    if(this.gameBoard.isLegalMove(column)){
                        System.out.println("Choose legal column");
                    }
                } else{
                    line = reader.next();
                    if(line.contains("s")){
                        this.saveGame();
                    }
                    if(line.contains("e")){
                        return;
                    }
                }

            } while (this.gameBoard.isLegalMove(column));
            this.gameBoard.addTokenToBoard(new Token(currentPlayer.getColor()), column);
            this.isOver = this.gameBoard.hasPlayerWon(column, this.gameBoard.getBoardMatrix().get(column).size() - 1, currentPlayer.getColor());
            currentPlayerFlag = !currentPlayerFlag;
            this.turn++;
        }
        System.out.println(currentPlayer.getName() + " has won");
        currentPlayer.win();
        rankingList.saveListToCsv();
        ConnectFour.menu();
    }

    public void saveGame(){
        gameSaver.saveGame(new GameState(this.turn, this.gameBoard));
    }

    public void choosePlayers() {
        System.out.println(rankingList.showPlayers());

        int player1;
        int player2;
        do {
            System.out.println("Choose Player 1?");
            player1 = reader.nextInt();
            if(player1 < 0 || player1 > rankingList.getList().values().size()){
                System.out.println("Player does not exists");
            }
        } while (player1 < 0 || player1 > rankingList.getList().values().size());

        do {
            System.out.println("Choose Player 2");
            player2 = reader.nextInt();
            if(player2 < 0 || player2 > rankingList.getList().values().size()){
                System.out.println("Player does not exists");
            }
            if(player1 == player2){
                System.out.println("Choose different player");
            }
        } while (player2 < 0 || player1 > rankingList.getList().values().size() || player1 == player2);

        firstPlayer = (Player) rankingList.getList().values().toArray()[player1];
        firstPlayer.setColor('G');
        secondPlayer = (Player) rankingList.getList().values().toArray()[player2];
        secondPlayer.setColor('R');
    }
    public Board getGameBoard(){
        return this.gameBoard;
    }
    public void setGameSaver(Saver gameSaver){ this.gameSaver = gameSaver; }
    public void setRankingList(RankingList rankingList){ this.rankingList = rankingList; }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getTurn() {
        return turn;
    }
}
