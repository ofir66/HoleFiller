package setup;
import org.opencv.core.Core;
import data.ConnectivityType;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import data.WeightingParams;
import holeFillerImpl.HoleFiller;


public class HoleFillerApp {
    // Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
    	HoleFillerArgsParser parser = new HoleFillerArgsParser(args);
    	
    	if (!parser.parseArgs()){
            return;
        }
    	
    	Image image = new Image(parser.getImagePath());
    	Image mask = new Image(parser.getMaskPath());
    	WeightingParams weightingParams = parser.getWeightingParams();
    	ConnectivityType connectivityType = parser.getConnectivityType();
    	
    	HoleFiller holeFiller = new HoleFiller(connectivityType, HoleFillingAlgorithm.DEFAULT, 
    			image, mask, WeightingFunc.DEFAULT, weightingParams);
    	
    	holeFiller.process();
    }
}
