package setup;
import org.opencv.core.Core;
import data.ConnectivityType;
import data.Directory;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import data.WeightingParams;
import holeFillerImpl.HoleFiller;


public class HoleFillerApp {
    // Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
    	Directory inputDir = new Directory("input");
    	Directory outputDir = new Directory("output");
    	HoleFillerArgsParser parser = new HoleFillerArgsParser(args, inputDir);
    	
    	if (!parser.parseArgs()){
            return;
        }
    	
    	Image mainImg = new Image(parser.getMainImgPath());
    	Image maskImg = new Image(parser.getMaskImgPath());
    	WeightingParams weightingParams = parser.getWeightingParams();
    	ConnectivityType connectivityType = parser.getConnectivityType();
    	
    	HoleFiller holeFiller = new HoleFiller(connectivityType, HoleFillingAlgorithm.DEFAULT, 
    			mainImg, maskImg, WeightingFunc.DEFAULT, weightingParams, inputDir, outputDir);
    	
    	holeFiller.process();
    }
}
