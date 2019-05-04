public interface Saver{
    void saveGame(GameState gameState);
    GameState loadGame(int name);
}
