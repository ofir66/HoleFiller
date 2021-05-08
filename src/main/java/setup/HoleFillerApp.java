package setup;

import org.opencv.core.Core;
import data.Directory;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import holeFillerImpl.HoleFiller;

/**
 * A class for initializing the hole filling library.
 */
public class HoleFillerApp {
    /**
     * Initializes the hole filling library.
     * the parameter args should contain the following 2 elements: <br>
     * args[0] = path to the main image. <br>
     * args[1] = path to the mask image (which describes the holes in the main image). <br>
     * @param args - command line arguments
     */
    public static void main(String[] args) {
    	// Load native library for opencv
    	String opencvDllPath = System.getProperty("user.dir") + OpencvInitConstants.DLL_PATH;
		System.load(opencvDllPath + Core.NATIVE_LIBRARY_NAME + ".dll");
		
    	Directory inputDir = new Directory("input");
    	Directory outputDir = new Directory("output");
    	HoleFillerArgsParser parser = new HoleFillerArgsParser();
    	
    	String mainImgPath = parser.parseMainImgPath(args, inputDir);
    	if (mainImgPath == CmdLineArg.INVALID_MAIN_IMG_ARG){
            return;
        }
    	
    	String maskImgPath = parser.parseMaskImgPath(args, inputDir);
    	if (maskImgPath == CmdLineArg.INVALID_MASK_IMG_ARG){
            return;
        }
    	
    	Image mainImg = new Image(mainImgPath);
    	Image maskImg = new Image(maskImgPath);
    	
    	HoleFiller holeFiller = new HoleFiller(HoleFillingAlgorithm.DEFAULT, 
    			mainImg, maskImg, WeightingFunc.DEFAULT, inputDir, outputDir);
    	
    	holeFiller.process();
    }
}
