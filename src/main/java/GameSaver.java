import org.jongo.Jongo;
import org.jongo.MongoCollection;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class GameSaver {
    MongoCollection gameStates;
    public GameSaver(){
        @SuppressWarnings({ "deprecation", "resource" })
        DB db = new MongoClient().getDB("saves");
        gameStates = new Jongo(db).getCollection("gameStates");
    }
    public void saveGame(GameState gameState){
        gameStates.save("{name: 'Joe', age: 18}");
       // gameStates.save(gameState);
    }
    public GameState loadGame(int name){
        return gameStates.findOne("{_id: #", name).as(GameState.class);
    }
}
