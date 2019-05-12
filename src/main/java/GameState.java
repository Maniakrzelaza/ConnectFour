package main.java;

public class GameState {
    private int turn;
    private Board board;

    public GameState(int turn, Board board){
        this.setTurn(turn);
        this.setBoard(board);
    }
    public GameState(){

    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
