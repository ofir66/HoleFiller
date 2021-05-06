package control;
import java.util.ArrayList;
import java.util.List;
import data.ConnectivityType;
import org.opencv.core.Mat;

import data.Pixel;

/**
 * Defines a template for Neighbor Getter obj, these objects gets pixel and
 * image and returns the neighbor pixels according to unique connectivity method.
 */
public class BoundaryCalculator{
	
	private final ConnectivityType connectivityType;
	
	public BoundaryCalculator(ConnectivityType connectivityTypeVal) {
		connectivityType = connectivityTypeVal;
	}
	
    public List<Pixel> getNeighbors(Pixel p, Mat im){
    	List<Pixel> neighbs = new ArrayList<Pixel>();
        int pX = p.getX();
        int pY = p.getY();
    	
    	switch(connectivityType) {
    		case C4:
    	        int[] x = new int[]{pX-1, pX+1, pX, pX};
    	        int[] y = new int[]{pY, pY, pY-1, pY+1};
    	        
    	        for (int i = 0; i < x.length; i++) {
    	        	neighbs.add(new Pixel(x[i], y[i]));
    	        }
    	        
    			break;
    		case C8:
    	        for (int i = pX-1; i < (pX+2); i++) {
    	            for (int j = pY-1; j < (pY+2); j++) {
    	            	if ( (i!=pX) || (j!=pY)  ) {
    	            		neighbs.add(new Pixel(i, j));	
    	            	}
    	            }
    	        }
    	}
    	
    	return neighbs;
    }
    
    public List<Pixel> getNeighborsWithoutHoles(Pixel p, Mat im){
    	List<Pixel> neighbs = getNeighbors(p, im);
    	List<Pixel> neighbsWithoutHoles = new ArrayList<Pixel>();
    	
    	for (Pixel pI : neighbs) {
    		double[] val = im.get(pI.getX(), pI.getY());
            if (val[0] != (-1)){
            	neighbsWithoutHoles.add(pI);
            }
    	}
    	
    	return neighbsWithoutHoles;
    }
}