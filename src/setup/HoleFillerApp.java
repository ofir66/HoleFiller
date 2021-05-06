package setup;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import control.HoleFillerController;
import data.ConnectivityType;
import data.WeightingParams;

import java.io.File;

public class HoleFillerApp {
    // Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    
	private static boolean validateInputImages(Mat src, Mat mask) {
		if (src.empty()) {
			System.err.println("Error: format for image source is invalid");
			return false;
		}
		if (mask.empty()){
            System.err.println("Error: format for mask source is invalid");
            return false;
        }
        if (src.size().width != mask.size().width || src.size().height != mask.size().height){
            System.err.println("Error: image and mask sources doesn't have the same dimensions");
            return false;
        }
        
        return true;
	}

    public static void main(String[] args) {
    	HoleFillerArgsParser parser = new HoleFillerArgsParser(args);
    	
    	if (!parser.parseArgs()){
            return;
        }
    	
    	String imagePath = parser.getImagePath();
    	String maskPath = parser.getMaskPath();
    	WeightingParams weightingParams = parser.getWeightingParams();
    	int connectivityType = parser.getConnectivityType();
        
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
        if (connectivityType == ConnectivityType.C8.getConnectivityType()){
            HoleFillerController.fillHole(destMat, ConnectivityType.C8, weightingParams);
        } 
        else {
            HoleFillerController.fillHole(destMat, ConnectivityType.C4, weightingParams);
        }
        HoleFillerController.reconvertNormalizedImage(destMat);
        Imgcodecs.imwrite("output/" + imageName, destMat);
        System.out.println("Result was saved in output folder");
    }
}
