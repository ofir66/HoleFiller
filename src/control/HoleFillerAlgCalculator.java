package control;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import data.ConnectivityType;
import data.HoleFillingAlgorithm;
import data.Pixel;
import data.WeightingFunc;
import data.WeightingParams;

public class HoleFillerAlgCalculator {

	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;
	private final ConnectivityType connectivityType;
	private final HoleFillingAlgorithm algorithm;

	
	public HoleFillerAlgCalculator(WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal, 
			ConnectivityType connectivityTypeVal, HoleFillingAlgorithm algorithmVal) {
		this.weightingFunc = weightingFuncVal;
		this.weightingParams = weightingParamsVal;
		this.connectivityType = connectivityTypeVal;
		this.algorithm = algorithmVal;
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
    
    public List<Pixel> findHolePixels(Mat im) {
    	Size s = im.size();
    	List<Pixel> holePixels = new ArrayList<Pixel>();
    	
        for (int i = 0; i < s.height; i++) {
            for (int j = 0; j < s.width; j++) {
                double[] val = im.get(i, j);
                if (val[0] == (-1)){
                	holePixels.add(new Pixel(i, j));
                }
            }
        }
        
        return holePixels;
    }
    
    /**
     * Gets image that contains one hole with (-1) pixels value.
     * Returns all the pixels around the hole s.t each boundary pixel is
     * connected to the hole with the given type definition.
     * @param im image contains one hole.
     * @return A set with all the boundary pixels.
     */
    private List<Pixel> findHoleBoundary(Mat im, ConnectivityType t, List<Pixel> holePixels){
        List<Pixel> bounds = new ArrayList<Pixel>();
        for (Pixel h: holePixels) {
        	bounds.addAll(getBoundaryPixels(h, im));
        }
        
        return bounds;
    }
    
    /**
     * Fills a hole inside the given image, that marks by -1 pixels.
     * Defines the hole's pixels value as an weighted average of the pixels around the hole.
     * Use the Lib's default pixel weight object for the hole filling calculation.
     * Need z and epsilon values to define it.
     * The Lib's default equation: W(a, b) = (||a-b||^z + epsilon)^-1
     */
    public void fillHole(Mat im, ConnectivityType t, WeightingParams weightingParams)
    {
    	switch(this.algorithm) {
    		case DEFAULT:
    			this.fillHoleDefault(im, t, weightingParams);
    			break;
    		case APPROXIMATE:
    			this.fillHoleApprox(im, t, weightingParams);
    	}
    }
    
    public void fillHoleDefault(Mat im, ConnectivityType t, WeightingParams weightingParams)
    {
    	List<Pixel> holePixels = this.findHolePixels(im);
        List<Pixel> bound = findHoleBoundary(im, t, holePixels);
        
        for (Pixel hole: holePixels) {
            double numeratorSum = 0;
            double denominatorSum = 0;
            for (Pixel neighbor : bound){
                float weight = abs(calcWeight(neighbor, hole));
                denominatorSum += weight;
                numeratorSum += (weight * im.get(neighbor.getX(), neighbor.getY())[0]);
            }
            im.put(hole.getX(), hole.getY(), (numeratorSum/denominatorSum));
        }    	
    }
    
    /**
     * Q2
     * Approximate method that fo over all the nearest neighbors.
     */
    public void fillHoleApprox(Mat im, ConnectivityType t, WeightingParams weightingParams){
    	List<Pixel> holePixels = this.findHolePixels(im);
        
        for (Pixel hole: holePixels) {
            double numeratorSum = 0;
            double denominatorSum = 0;
            List<Pixel> bound = getBoundaryPixels(hole, im);
            
            for (Pixel neighbor : bound){
                float weight = abs(calcWeight(neighbor, hole));
                denominatorSum += weight;
                numeratorSum += (weight * im.get(neighbor.getX(), neighbor.getY())[0]);
            }
            im.put(hole.getX(), hole.getY(), (numeratorSum/denominatorSum));
        }
    }
	
}
