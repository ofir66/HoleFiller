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
import data.WeightingFuncParams;

/**
 * A handler for calculating all algorithm related issues:
 * weight between pixels, finding boundary pixels and more.
 */
public class HoleFillerAlgCalculator {

	private final WeightingFunc weightingFunc;
	private final WeightingFuncParams weightingParams;
	private final ConnectivityType connectivityType;
	private final HoleFillingAlgorithm algorithm;

	
	public HoleFillerAlgCalculator(WeightingFunc weightingFuncVal, WeightingFuncParams weightingParamsVal, 
			ConnectivityType connectivityTypeVal, HoleFillingAlgorithm algorithmVal) {
		this.weightingFunc = weightingFuncVal;
		this.weightingParams = weightingParamsVal;
		this.connectivityType = connectivityTypeVal;
		this.algorithm = algorithmVal;
	}
	
	private final float calcWeight(Pixel p1, Pixel p2) {
		float weight = -1;
		
		switch(weightingFunc) {
			case DEFAULT:
		        float euclideanDist = (float) sqrt(pow(p1.getX() - p2.getX(), 2) + (pow(p1.getY() - p2.getY(), 2)));
		        weight = (float)(1/((pow(euclideanDist, weightingParams.getZ())) + weightingParams.getEpsilon()));
				break;
		}
		
		return weight;
	}
	
	/**
	 * Finds all neighbors (including holes) of a pixel in an image
	 * @param p - the pixel to find its neighbors
	 * @param im - the image to find the neighbors in
	 * @return list of the neighbors of p
	 */
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
    
    /**
     * Finds all neighbors of a pixel in an image, not including the hole neighbors
	 * @param p - the pixel to find its neighbors
	 * @param im - the image to find the neighbors in
	 * @return list of the neighbors of p, not including the hole neighbors
     */
    private List<Pixel> getNeighborsWithoutHoles(Pixel p, Mat im){
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
    
    private List<Pixel> findHolePixels(Mat im) {
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
     * Finds the boundary pixels of an image.
     * A boundary pixel is defined as pixel that is connected to a hole pixel, but is not in the hole itself.
     * @param im - the image to look for the boundary pixels
     * @param connectivityType - the pixel connectivity type
     * @param holePixels - list of pixels that are holes in the image
     * @return list of the boundary pixels in the image
     */
    private List<Pixel> findBoundaryPixels(Mat im, ConnectivityType connectivityType, List<Pixel> holePixels){
        List<Pixel> bounds = new ArrayList<Pixel>();
        for (Pixel h: holePixels) {
        	bounds.addAll(getNeighborsWithoutHoles(h, im));
        }
        
        return bounds;
    }
    
    /**
     * Fills the hole pixels in the image.
     * @param im - the image to fill the hole pixels in
     * @param connectivityType - the pixel connectivity type
     * @param weightingParams - the weighting parameters for calculating the hole filling algorithm
     */
    public void fillHolePixels(Mat im, ConnectivityType connectivityType, WeightingFuncParams weightingParams)
    {
    	switch(this.algorithm) {
    		case DEFAULT:
    			this.fillHolePixelsDefaultAlg(im, connectivityType, weightingParams);
    			break;
    		case APPROXIMATE:
    			this.fillHolePixelsApproxAlg(im, connectivityType, weightingParams);
    	}
    }
    
    /**
     * Fills the hole pixels in the image according to a standard algorithm.
     * run time: O(n^2), where n is the number of the pixels in the image
     * @param im - the image to fill the hole pixels in
     * @param connectivityType - the pixel connectivity type
     * @param weightingParams - the weighting parameters for calculating the hole filling algorithm
     */
    private void fillHolePixelsDefaultAlg(Mat im, ConnectivityType connectivityType, WeightingFuncParams weightingParams)
    {
    	List<Pixel> holePixels = this.findHolePixels(im);
        List<Pixel> bound = findBoundaryPixels(im, connectivityType, holePixels);
        
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
     * Fills the hole pixels in the image according to an algorithm that approximates the result.
     * run time: O(n), where n is the number of the pixels in the image
     * @param im - the image to fill the hole pixels in
     * @param connectivityType - the pixel connectivity type
     * @param weightingParams - the weighting parameters for calculating the hole filling algorithm
     */
    private void fillHolePixelsApproxAlg(Mat im, ConnectivityType connectivityType, WeightingFuncParams weightingParams){
    	List<Pixel> holePixels = this.findHolePixels(im);
        
        for (Pixel hole: holePixels) {
            double numeratorSum = 0;
            double denominatorSum = 0;
            List<Pixel> bound = getNeighborsWithoutHoles(hole, im);
            
            for (Pixel neighbor : bound){
                float weight = abs(calcWeight(neighbor, hole));
                denominatorSum += weight;
                numeratorSum += (weight * im.get(neighbor.getX(), neighbor.getY())[0]);
            }
            im.put(hole.getX(), hole.getY(), (numeratorSum/denominatorSum));
        }
    }
	
}
