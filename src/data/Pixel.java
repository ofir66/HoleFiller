package data;

public class Pixel {
    private final int x;
    private final int y;

    public Pixel(int x, int y){
        this.x = x;
        this.y = y;
    }


    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pixel)) {
            return false;
        }
        Pixel p = (Pixel) o;
        return this.x == p.x && this.y == p.y;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
}
