import main.java.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FakeGameSaver implements Saver {
    List<GameState> fakeList;
    public FakeGameSaver(){
        fakeList = new ArrayList<>();
    }
    @Override
    public void saveGame(GameState gameState) {
        fakeList.add(gameState);
    }

    @Override
    public GameState loadGame() {
        if(fakeList.size() == 0){
            throw new IllegalArgumentException();
        }
        return fakeList.stream()
                .max(Comparator.comparingInt(GameState::getTurn))
                .get();
    }

    @Override
    public void deleteLastSession() {
        fakeList.clear();
    }
    public void seedData(){
        fakeList.add(new GameState(5, new Board(10, 15)));
        fakeList.add(new GameState(2, new Board(7,16)));
        fakeList.add(new GameState(3, new Board(18,19)));
    }

    public List<GameState> getFakeList() {
        return fakeList;
    }
}
