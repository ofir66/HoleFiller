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
        
    	/*
    	
        String imageName = new File(imagePath).getName();
        String maskName = new File(maskPath).getName();
        
        Mat imageMat = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);
        Mat maskMat = Imgcodecs.imread(maskPath, Imgcodecs.IMREAD_GRAYSCALE);
        
        if (!validateInputImages(imageMat, maskMat)) {
        	return;
        }
        
        System.out.println("Fill hole for: " + imageName + "\n" + 
        				   "use mask: " + maskName + "\n" +
        				   "z value: " + weightingParams.getZ() + "\n" + 
        				   "epsilon value: " + weightingParams.getEpsilon() + "\n" + 
        				   "connectivity value: " + connectivityType + "\n");
        
        
        Mat destMat = new Mat();
        HoleFillerController.carveHoleUsingMask(imageMat, maskMat, destMat);
        HoleFillerController.saveHolePixels(destMat);
        if (connectivityType == ConnectivityType.C8.getConnectivityDegree()){
            HoleFillerController.fillHole(destMat, ConnectivityType.C8, weightingParams);
        } 
        else {
            HoleFillerController.fillHole(destMat, ConnectivityType.C4, weightingParams);
        }
        HoleFillerController.reconvertNormalizedImage(destMat);
        Imgcodecs.imwrite("output/" + imageName, destMat);
        System.out.println("Result was saved in output folder");
        
        */
    }
}
