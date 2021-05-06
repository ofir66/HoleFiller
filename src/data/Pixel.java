package data;

public class Pixel {
    private final int x;
    private final int y;

    public Pixel(int x, int y){
        this.x = x;
        this.y = y;
    }


    public boolean equals(Object other){
        if (other == this) {
            return true;
        }
        if (!(other instanceof Pixel)) {
            return false;
        }
        Pixel pOther = (Pixel) other;
        return this.x == pOther.x && this.y == pOther.y;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
}
