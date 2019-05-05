import org.jongo.Jongo;
import org.jongo.MongoCollection;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class GameSaver implements Saver {
    public MongoCollection gameStates;
    public GameSaver(){
        @SuppressWarnings({ "deprecation", "resource" })
        DB db = new MongoClient().getDB("saves");
        gameStates = new Jongo(db).getCollection("gameStates");
    }
    public void saveGame(GameState gameState){
        gameStates.save(gameState);
    }
    public GameState loadGame(){
        GameState result = gameStates.findOne().orderBy("{turn: -1}").as(GameState.class);
        if(result == null){
            throw new IllegalArgumentException();
        }
        return result;
    }
    public void deleteLastSession(){
        gameStates.remove("{}");
    }
}
