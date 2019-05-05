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
            throw new NegativeArraySizeException();
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
        fakeList.add(new GameState(5, new Board()));
        fakeList.add(new GameState(2, new Board()));
        fakeList.add(new GameState(3, new Board()));
    }
}
