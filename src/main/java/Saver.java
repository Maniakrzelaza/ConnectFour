public interface Saver{
    void saveGame(GameState gameState);
    GameState loadGame();
    void deleteLastSession();
}
