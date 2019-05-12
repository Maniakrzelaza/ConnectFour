package main.java;

public class Token {
    private Character color;

    public Token(Character color){
        if(color.equals('R') || color.equals('G')){
            this.color = color;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public Token(){

    }

    public Character getColor() {
        return color;
    }

    @Override
    public String toString(){
        return color.toString();
    }
}
