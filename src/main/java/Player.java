package main.java;

public class Player {
    private String name;
    private int numberOfWins;
    private Character color;

    public Player(String name, int numberOfWins){
        this.name = name;
        this.numberOfWins = numberOfWins;
    }

    public Player(String name){
        this(name, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void win(){
        this.numberOfWins += 1;
    }

    @Override
    public String toString(){
        return "Name: " +  this.name + " Wins: " +  this.numberOfWins;
    }

    public Character getColor() {
        return color;
    }

    public void setColor(Character color) {
        this.color = color;
    }
}
