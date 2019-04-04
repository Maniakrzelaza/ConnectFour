public class Token {
    private Character color;

    public Token(Character color){
        if(color.equals('R') || color.equals('G')){
            this.color = color;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Character getColor() {
        return color;
    }
}
