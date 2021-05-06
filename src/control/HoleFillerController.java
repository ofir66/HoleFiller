package control;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import data.ConnectivityType;
import data.HoleFillerModel;
import data.Pixel;
import data.WeightingFunc;
import data.WeightingParams;
import old.WeightingDefaultFunc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;


public class HoleFillerController {
	// Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    private static List<Pixel> holePixels = new ArrayList<Pixel>();
    private final HoleFillerModel model;

    
    public HoleFillerController(HoleFillerModel modelVal) {
    	this.model = modelVal;
    }
    
    /**
     * Creates an NeighborGetter object and return it.
     * @param connectivityType the connectivity type of the getter obj.
     * @return instance of NeighborGetter.
     */
    private static BoundaryCalculator createNeighborsGetter(ConnectivityType connectivityType){
    	return new BoundaryCalculator(connectivityType);
    }

    /**
     * Carve an hole in the given image using the mask.
     * Normalize the image values to 0 - 1.
     * Set image values to -1 in the mask positions.
     * Convert to type:CV_32FC1
     * @param im grayScale image (0-255).
     * @param mask grayScale image (0-255), (the black pixels represent the hole).
     * @param dst Save the result to dst.
     */
    public static void carveHoleUsingMask(Mat im, Mat mask, Mat dst){
        Core.normalize(im, dst,0.0,1.0, Core.NORM_MINMAX, CvType.CV_32FC1);
        Mat binMask = new Mat();
        Imgproc.threshold(mask, binMask, 127, 1, Imgproc.THRESH_BINARY_INV);
        
        /*
         * set all values on dst that aren't -1 on binMask to be -1
         * example: https://stackoverflow.com/questions/8971308/what-is-cvsetto-function/8973044
         */
        dst.setTo(new Scalar(-1), binMask);
    }

    /**
     * Gets image that contains one hole with (-1) pixels value.
     * Returns all the pixels around the hole s.t each boundary pixel is
     * connected to the hole with the given type definition.
     * @param im image contains one hole.
     * @return A set with all the boundary pixels.
     */
    private static List<Pixel> findHoleBoundary(Mat im, ConnectivityType t){
        BoundaryCalculator ng = createNeighborsGetter(t);
        List<Pixel> bounds = new ArrayList<Pixel>();
        
        for (Pixel h: holePixels) {
        	bounds.addAll(ng.getNeighborsWithoutHoles(h, im));
        }
        
        return bounds;
    }
    
    public static void saveHolePixels(Mat im) {
    	Size s = im.size();
    	
        for (int i = 0; i < s.height; i++) {
            for (int j = 0; j < s.width; j++) {
                double[] val = im.get(i, j);
                if (val[0] == (-1)){
                	holePixels.add(new Pixel(i, j));
                }
            }
        }
    }

    /**
     * Fills a hole inside the given image, that marks by -1 pixels.
     * Defines the hole's pixels value as an weighted average of the pixels around the hole.
     * Use the Lib's default pixel weight object for the hole filling calculation.
     * Need z and epsilon values to define it.
     * The Lib's default equation: W(a, b) = (||a-b||^z + epsilon)^-1
     */
    public static void fillHole(Mat im, ConnectivityType t, WeightingParams weightingParams)
    {
    	HoleFillerAlgCalculator algCalculator = new HoleFillerAlgCalculator(WeightingFunc.DEFAULT, weightingParams);
        List<Pixel> bound = findHoleBoundary(im, t);
        
        for (Pixel hole: holePixels) {
            double numeratorSum = 0;
            double denominatorSum = 0;
            for (Pixel neighbor : bound){
                float weight = abs(algCalculator.calcWeight(neighbor, hole));
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
    public static void fillHoleQ2(Mat im, ConnectivityType t, WeightingParams weightingParams){
    	WeightingDefaultFunc wf = new WeightingDefaultFunc(weightingParams.getZ(), weightingParams.getEpsilon());
        BoundaryCalculator ng = createNeighborsGetter(t);
        
        for (Pixel hole: holePixels) {
            double numeratorSum = 0;
            double denominatorSum = 0;
            List<Pixel> bound = ng.getNeighborsWithoutHoles(hole, im);
            
            for (Pixel neighbor : bound){
                float weight = abs(wf.getWeight(neighbor, hole));
                denominatorSum += weight;
                numeratorSum += (weight * im.get(neighbor.getX(), neighbor.getY())[0]);
            }
            im.put(hole.getX(), hole.getY(), (numeratorSum/denominatorSum));
        }
    }

    /**
     * Get GrayScale image with 0-1 values, CV_32FC1 type
     * and convert it to 0-255 scale and CV_8UC1 type.
     */
    public static void reconvertNormalizedImage(Mat im){
        Core.multiply(im, new Scalar(255), im);
        im.convertTo(im, CvType.CV_8UC1);
    }
}


