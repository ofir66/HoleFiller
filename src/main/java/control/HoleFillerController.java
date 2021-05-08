package control;
import org.opencv.core.Mat;
import data.HoleFillerModel;
import data.HoleFillingAlgorithm;
import data.WeightingFunc;
import view.HoleFillerDisplay;

import java.io.File;

/**
 * A handler for controlling the logic in the hole filling library
 */
public class HoleFillerController {
    private final HoleFillerModel model;
    private final HoleFillerDisplay display;

    
    public HoleFillerController(HoleFillerModel modelVal, HoleFillerDisplay displayVal) {
    	this.model = modelVal;
    	this.display = displayVal;
    }
    
	private boolean validateInputImages(Mat src, Mat mask) {
		if (src.empty()) {
			HoleFillerDisplay.printToStderr("format for main image is invalid");
			return false;
		}
		if (mask.empty()){
			HoleFillerDisplay.printToStderr("format for mask image is invalid");
            return false;
        }
        if (src.size().width != mask.size().width || src.size().height != mask.size().height){
        	HoleFillerDisplay.printToStderr("main image and mask image don't have the same dimensions");
            return false;
        }
        
        return true;
	}
    
	/**
	 * Starts the hole filling process by using {@link HoleFillerConversionHandler} 
	 * and {@link HoleFillerAlgCalculator}.
	 */
    public void process() {
    	HoleFillerConversionHandler conversionHandler = new HoleFillerConversionHandler();
    	HoleFillerAlgCalculator algCalculator = new HoleFillerAlgCalculator();
    	
        Mat mainImgMat = conversionHandler.convertToGrayscale(model.getMainImg());
        Mat maskImgMat = conversionHandler.convertToGrayscale(model.getMaskImg());
        Mat destMat = new Mat();
        
        WeightingFunc weightFunc = model.getWeightingFunc();
        HoleFillingAlgorithm alg = model.getHoleFillingAlgorithm();
        
    	String mainImgPath = model.getMainImg().getPath();
        String mainImgName = new File(mainImgPath).getName();
        
        if (!validateInputImages(mainImgMat, maskImgMat)) {
        	return;
        }
        
        display.printHoleFillStartMsg(model.getMainImg(), model.getMaskImg());
        
        conversionHandler.createHoleWithMask(mainImgMat, maskImgMat, destMat);
        algCalculator.fillHolePixels(destMat, weightFunc, alg);
        conversionHandler.reconvertNormalizedImage(destMat);
        display.saveImgToOutputFile(model.getOutputDir(), mainImgName, destMat);
    }
}


