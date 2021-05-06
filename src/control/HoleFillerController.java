package control;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import data.ConnectivityType;
import data.HoleFillerModel;
import data.WeightingParams;
import view.HoleFillerDisplay;

import java.io.File;


public class HoleFillerController {
	// Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    private final HoleFillerModel model;
    private final HoleFillerDisplay display;

    
    public HoleFillerController(HoleFillerModel modelVal, HoleFillerDisplay displayVal) {
    	this.model = modelVal;
    	this.display = displayVal;
    }
    
	private boolean validateInputImages(Mat src, Mat mask) {
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
    
    public void process() {
    	HoleFillerConversionHandler conversionHandler = new HoleFillerConversionHandler(model.getImg(), model.getMask());
    	HoleFillerAlgCalculator algCalculator = new HoleFillerAlgCalculator(model.getWeightingFunc(), model.getWeightingParams(),
    			model.getConnectivityType(), model.getHoleFillingAlgorithm());
    	
        Mat imageMat = conversionHandler.convertToGrayscale(model.getImg());
        Mat maskMat = conversionHandler.convertToGrayscale(model.getMask());
        Mat destMat = new Mat();
        
        WeightingParams weightingParams = model.getWeightingParams();
        ConnectivityType connectivityType = model.getConnectivityType();
        
    	String imagePath = model.getImg().getPath();
        String imageName = new File(imagePath).getName();
        
        if (!validateInputImages(imageMat, maskMat)) {
        	return;
        }
        
        display.printHoleFillStartMsg(model.getImg(), model.getMask(), weightingParams, connectivityType);
        
        conversionHandler.carveHoleUsingMask(imageMat, maskMat, destMat);
        algCalculator.fillHole(destMat, connectivityType, weightingParams);
        conversionHandler.reconvertNormalizedImage(destMat);
        display.saveImgToOutputFile(model.getOutputDir(), imageName, destMat);
        System.out.println("Result was saved in output folder");
    }
}


