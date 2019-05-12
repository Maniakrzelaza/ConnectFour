import com.mongodb.WriteResult;
import main.java.Board;
import main.java.GameSaver;
import main.java.GameState;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.easymock.EasyMock.*;

public class EasyMockTests {

    GameSaver sutGameSaver = new GameSaver();

    MongoCollection gameStates;

    @Test
    public void GameSaverShouldCallSaveOnMongoCollection() {
        gameStates = mock(MongoCollection.class);

        expect(gameStates.save(anyObject()))
                .andReturn(new WriteResult(1, false, null));
        replay(gameStates);
        sutGameSaver = new GameSaver();
        sutGameSaver.gameStates = gameStates;
        GameState gameState = new GameState(5, new Board(10, 15));
        sutGameSaver.saveGame(gameState);
        verify(gameStates);
    }

    @Test
    public void GameSaverShouldThrowWhenMongoCollectionReturnsNull() {
        gameStates = niceMock(MongoCollection.class);
        FindOne mockFindOne = niceMock(FindOne.class);
        FindOne mockFindOne2 = niceMock(FindOne.class);
        expect(gameStates.findOne())
                .andReturn(mockFindOne);
        expect(mockFindOne.orderBy(anyString()))
                .andReturn(mockFindOne2);
        replay(gameStates);
        replay(mockFindOne);
        replay(mockFindOne2);
        sutGameSaver = new GameSaver();
        sutGameSaver.gameStates = gameStates;

        Assertions.assertThrows(IllegalArgumentException.class, () -> sutGameSaver.loadGame());
    }
}
