import main.java.IRankingList;
import main.java.*;
import java.util.HashMap;

public class FakeRankingList implements IRankingList {
    private HashMap<String, Player> list;
    private HashMap<String, Player> fileList;

    public FakeRankingList(){
        list = new HashMap<String, Player>();
        fileList = new HashMap<String, Player>();
    }

    @Override
    public void loadPlayersFromCsv() {
        list.clear();
        for(Player player : fileList.values()){
            list.put(player.getName(), player);
        }
    }

    @Override
    public void saveListToCsv() {
        fileList.clear();
        for(Player player : list.values()){
            fileList.put(player.getName(), player);
        }
    }

    @Override
    public void addPlayer(Player player) {
        if(!list.containsKey(player.getName())){
            list.put(player.getName(), player);
        }
    }

    public String showPlayers(){
        StringBuilder result = new StringBuilder();
        int index = 0;
        for (Player player : this.list.values()) {
            result.append(index + ". " + player.toString() + "\n");
            index++;
        }
        return result.delete(result.length() - 1, result.length()).toString();
    }

    @Override
    public HashMap<String, Player> getList() {
        return this.list;
    }
    public void seedData(){
        fileList.put("Player1", new Player("Player1", 1));
        fileList.put("Player2", new Player("Player2", 2));
        fileList.put("Player3", new Player("Player3", 3));
        this.loadPlayersFromCsv();
    }
}
