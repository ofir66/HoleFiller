package setup;
import org.opencv.core.Core;
import data.ConnectivityType;
import data.Directory;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import data.WeightingFuncParams;
import holeFillerImpl.HoleFiller;

/**
 * A class for initializing the hole filling library.
 */
public class HoleFillerApp {
    // Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    /**
     * Initializes the hole filling library.
     * the parameter args should contain the following 5 elements: <br>
     * args[0] = path to the main image. <br>
     * args[1] = path to the mask image (which describes the holes in the main image). <br>
     * args[2] = z value. <br>
     * args[3] = connectivityType - should be "4" or "8" only. <br>
     * args[4] = epsilon value.
     * @param args - command line arguments
     */
    public static void main(String[] args) {
    	Directory inputDir = new Directory("input");
    	Directory outputDir = new Directory("output");
    	HoleFillerArgsParser parser = new HoleFillerArgsParser();
    	
    	String mainImgPath = parser.parseMainImgPath(args, inputDir);
    	if (mainImgPath == null){
            return;
        }
    	
    	String maskImgPath = parser.parseMaskImgPath(args, inputDir);
    	if (maskImgPath == null){
            return;
        }
    	
    	int z = parser.parseZValue(args);
    	if (z < 0){
            return;
        }
    	
    	ConnectivityType connectivityType = parser.parseConnectivitiyValue(args);
    	if (connectivityType == null) {
    		return;
    	}
    	
    	float epsilon = parser.parseEpsilonValue(args);
    	if (epsilon < 0) {
    		return;
    	}
    	
    	Image mainImg = new Image(mainImgPath);
    	Image maskImg = new Image(maskImgPath);
    	WeightingFuncParams weightingParams = new WeightingFuncParams(z, epsilon);
    	
    	HoleFiller holeFiller = new HoleFiller(connectivityType, HoleFillingAlgorithm.DEFAULT, 
    			mainImg, maskImg, WeightingFunc.DEFAULT, weightingParams, inputDir, outputDir);
    	
    	holeFiller.process();
    }
}
