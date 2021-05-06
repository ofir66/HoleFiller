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
    	HoleFillerArgsParser parser = new HoleFillerArgsParser(args, inputDir);
    	
    	if (!parser.parseArgs()){
            return;
        }
    	
    	Image mainImg = new Image(parser.getMainImgPath());
    	Image maskImg = new Image(parser.getMaskImgPath());
    	WeightingFuncParams weightingParams = parser.getWeightingParams();
    	ConnectivityType connectivityType = parser.getConnectivityType();
    	
    	HoleFiller holeFiller = new HoleFiller(connectivityType, HoleFillingAlgorithm.DEFAULT, 
    			mainImg, maskImg, WeightingFunc.DEFAULT, weightingParams, inputDir, outputDir);
    	
    	holeFiller.process();
    }
}
