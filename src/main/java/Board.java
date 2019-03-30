public class Board {
    private int width;
    private int height;
    public Board(int width, int height){
        if(width <= 7)
            this.width = 7;
        if(height <= 6)
            this.height = 6;
        if(width <=0 || height <= 0){
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
