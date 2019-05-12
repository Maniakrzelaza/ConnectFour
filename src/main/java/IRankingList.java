package main.java;

import java.util.HashMap;

public interface IRankingList{
    void loadPlayersFromCsv();
    void saveListToCsv();
    void addPlayer(Player player);
    String showPlayers();
    HashMap<String, Player> getList();
}
