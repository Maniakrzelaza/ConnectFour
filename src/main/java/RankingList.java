import java.io.*;
import java.util.HashMap;

public class RankingList {
    public HashMap<String, Player> list;

    public static String FILE_NAME = "RankingList.csv";

    public RankingList(){
        list = new HashMap<String, Player>();
        this.loadPlayersFromCsv();
    }

    public void loadPlayersFromCsv(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("../resources/" + FILE_NAME).getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Player player = new Player(values[0], Integer.parseInt(values[1].trim()));
                this.addPlayer(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveListToCsv(){
        ClassLoader classLoader = getClass().getClassLoader();
        try (PrintWriter writer = new PrintWriter(new File(classLoader.getResource("../resources/" + FILE_NAME).getFile()))) {

            StringBuilder sb = new StringBuilder();

            for(Player player : list.values()) {
                sb.append(player.getName());
                sb.append(',');
                sb.append(player.getNumberOfWins());
                sb.append('\n');
            }

            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void addPlayer(Player player){
        if(!list.containsKey(player.getName())){
            list.put(player.getName(), player);
        }
    }
}
