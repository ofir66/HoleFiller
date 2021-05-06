package old;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import data.Pixel;


/**
 * Calculates the weight of two Pixels base on the Euclidean Dist between them.
 * use the equation: W(a, b) = (||a-b||^z + epsilon)^-1
 */
public class WeightingDefaultFunc implements PixelsWeight{
    int z;
    float epsilon;
    
    public WeightingDefaultFunc(int z, float e){
        this.z = z;
        this.epsilon = e;
    }
    
    public float getWeight(Pixel p1, Pixel p2){
        float euclideanDist = (float) sqrt(pow(p1.getX() - p2.getX(), 2) + (pow(p1.getY() - p2.getY(), 2)));
        return (float)(1/((pow(euclideanDist, this.z)) + this.epsilon));
    }
}
