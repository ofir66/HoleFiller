package control;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import data.ConnectivityType;
import data.Pixel;
import data.WeightingFunc;
import data.WeightingParams;

public class HoleFillerAlgCalculator {

	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;
	private final ConnectivityType connectivityType;

	
	public HoleFillerAlgCalculator(WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal, 
			ConnectivityType connectivityTypeVal) {
		this.weightingFunc = weightingFuncVal;
		this.weightingParams = weightingParamsVal;
		this.connectivityType = connectivityTypeVal;
	}
	
	protected final float calcWeight(Pixel p1, Pixel p2) {
		float weight = -1;
		
		switch(weightingFunc) {
			case DEFAULT:
		        float euclideanDist = (float) sqrt(pow(p1.getX() - p2.getX(), 2) + (pow(p1.getY() - p2.getY(), 2)));
		        weight = (float)(1/((pow(euclideanDist, weightingParams.getZ())) + weightingParams.getEpsilon()));
				break;
		}
		
		return weight;
	}
	
    private List<Pixel> getNeighbors(Pixel p, Mat im){
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
    
    public List<Pixel> getBoundaryPixels(Pixel p, Mat im){
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
