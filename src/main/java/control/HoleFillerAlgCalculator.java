package control;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import constants.GlobalConstants;
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
	
	private final float calcWeight(Pixel p1, Pixel p2, WeightingFunc weightFun, WeightingFuncParams weightParams) {
		float weight = -1;
		
		switch(weightFun) {
			case DEFAULT:
		        float euclideanDist = (float) sqrt(pow(p1.getX() - p2.getX(), 2) + (pow(p1.getY() - p2.getY(), 2)));
		        weight = (float)(1/((pow(euclideanDist, weightParams.getZ())) + weightParams.getEpsilon()));
				break;
		}
		
		return weight;
	}
	
	/**
	 * Finds all neighbors (including holes) of a pixel in an image
	 * @param p - the pixel to find its neighbors
	 * @param im - the image to find the neighbors in
	 * @param connectType - the pixel connectivity type
	 * @return list of the neighbors of p
	 */
    private List<Pixel> getNeighbors(Pixel p, Mat im, ConnectivityType connectType){
    	List<Pixel> neighbs = new ArrayList<Pixel>();
        int pX = p.getX();
        int pY = p.getY();
    	
    	switch(connectType) {
    		case FOUR_CONNECTED:
    	        int[] x = new int[]{pX-1, pX+1, pX, pX};
    	        int[] y = new int[]{pY, pY, pY-1, pY+1};
    	        
    	        for (int i = 0; i < x.length; i++) {
    	        	neighbs.add(new Pixel(x[i], y[i]));
    	        }
    	        
    			break;
    		case EIGHT_CONNECTED:
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
	 * @param connectType - the pixel connectivity type
	 * @return list of the neighbors of p, not including the hole neighbors
     */
    private List<Pixel> getNeighborsWithoutHoles(Pixel p, Mat im, ConnectivityType connectType){
    	List<Pixel> neighbs = getNeighbors(p, im, connectType);
    	List<Pixel> neighbsWithoutHoles = new ArrayList<Pixel>();
    	
    	for (Pixel pI : neighbs) {
    		double[] val = im.get(pI.getX(), pI.getY());
            if (val[0] != GlobalConstants.HOLE_INDICATOR){
            	neighbsWithoutHoles.add(pI);
            }
    	}
    	
    	return neighbsWithoutHoles;
    }
    
    private List<Pixel> findHolePixels(Mat im) {
    	Size size = im.size();
    	List<Pixel> holePixels = new ArrayList<Pixel>();
    	
        for (int i = 0; i < size.height; i++) {
            for (int j = 0; j < size.width; j++) {
                double[] val = im.get(i, j);
                if (val[0] == GlobalConstants.HOLE_INDICATOR){
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
     * @param connectType - the pixel connectivity type
     * @param holePixels - list of pixels that are holes in the image
     * @return list of the boundary pixels in the image
     */
    private List<Pixel> findBoundaryPixels(Mat im, ConnectivityType connectType, List<Pixel> holePixels){
        List<Pixel> boundaryPixels = new ArrayList<Pixel>();
        for (Pixel h: holePixels) {
        	boundaryPixels.addAll(getNeighborsWithoutHoles(h, im, connectType));
        }
        
        return boundaryPixels;
    }
    
    /**
     * Fills the hole pixels in the image.
     * @param im - the image to fill the hole pixels in
     * @param weightFunc - the weighting function for calculating weight between pixels
     * @param alg - the algorithm for the hole filling
     */
    public void fillHolePixels(Mat im, WeightingFunc weightFunc, HoleFillingAlgorithm alg)
    {
    	WeightingFuncParams weightParams = new WeightingFuncParams(GlobalConstants.Z_VALUE, 
    			GlobalConstants.EPSILON_VALUE);
    	ConnectivityType connectType = GlobalConstants.CONNECTIVITY_TYPE;
    	
    	switch(alg) {
    		case DEFAULT:
    			this.fillHolePixelsDefaultAlg(im, connectType, weightFunc, weightParams);
    			break;
    		case APPROXIMATE:
    			this.fillHolePixelsApproxAlg(im, connectType, weightFunc, weightParams);
    	}
    }
    
    /**
     * Fills the hole pixels in the image according to a standard algorithm.
     * run time: O(n^2), where n is the number of the pixels in the image
     * @param im - the image to fill the hole pixels in
     * @param connectType - the pixel connectivity type
     * @param weightFunc - the weighting function for calculating weight between pixels
     * @param weightParams - the weighting parameters for calculating the weighting function
     */
    private void fillHolePixelsDefaultAlg(Mat im, ConnectivityType connectType, WeightingFunc weightFunc,
    		WeightingFuncParams weightParams)
    {
    	List<Pixel> holePixels = this.findHolePixels(im);
        List<Pixel> boundaryPixels = findBoundaryPixels(im, connectType, holePixels);
        
        for (Pixel hole: holePixels) {
            double algNumerator = 0;
            double algDenominator = 0;
            for (Pixel boundaryPixel : boundaryPixels){
                float weight = abs(calcWeight(boundaryPixel, hole, weightFunc, weightParams));
                algDenominator += weight;
                
                double boundaryPixelVal = im.get(boundaryPixel.getX(), boundaryPixel.getY())[0];
                algNumerator += (weight * boundaryPixelVal);
            }
            im.put(hole.getX(), hole.getY(), (algNumerator/algDenominator));
        }    	
    }
    
    /**
     * Fills the hole pixels in the image according to an algorithm that approximates the result.
     * run time: O(n), where n is the number of the pixels in the image
     * @param im - the image to fill the hole pixels in
     * @param connectType - the pixel connectivity type
     * @param weightFunc - the weighting function for calculating weight between pixels
     * @param weightParams - the weighting parameters for calculating the weighting function
     */
    private void fillHolePixelsApproxAlg(Mat im, ConnectivityType connectType, WeightingFunc weightFunc,
    		WeightingFuncParams weightParams){
    	List<Pixel> holePixels = this.findHolePixels(im);
        
        for (Pixel hole: holePixels) {
            double algNumerator = 0;
            double algDenominator = 0;
            List<Pixel> neighborsWithoutHoles = getNeighborsWithoutHoles(hole, im, connectType);
            
            for (Pixel neighbor : neighborsWithoutHoles){
                float weight = abs(calcWeight(neighbor, hole, weightFunc, weightParams));
                algDenominator += weight;
                algNumerator += (weight * im.get(neighbor.getX(), neighbor.getY())[0]);
            }
            im.put(hole.getX(), hole.getY(), (algNumerator/algDenominator));
        }
    }
	
}
