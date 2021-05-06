package control;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import data.ConnectivityType;
import data.HoleFillerModel;
import data.WeightingParams;
import java.io.File;


public class HoleFillerController {
	// Load native library for opencv
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    private final HoleFillerModel model;
    private final HoleFillerAlgCalculator algCalculator;

    
    public HoleFillerController(HoleFillerModel modelVal) {
    	this.model = modelVal;
    	algCalculator = new HoleFillerAlgCalculator(model.getWeightingFunc(), model.getWeightingParams(),
    			model.getConnectivityType(), model.getHoleFillingAlgorithm());
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
    	String imagePath = model.getImg().getPath();
    	String maskPath = model.getMask().getPath();
        String imageName = new File(imagePath).getName();
        String maskName = new File(maskPath).getName();
        Mat imageMat = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);
        Mat maskMat = Imgcodecs.imread(maskPath, Imgcodecs.IMREAD_GRAYSCALE);
        
        WeightingParams weightingParams = model.getWeightingParams();
        ConnectivityType connectivityType = model.getConnectivityType();
        
        if (!validateInputImages(imageMat, maskMat)) {
        	return;
        }
        
        System.out.println("Fill hole for: " + imageName + "\n" + 
        				   "use mask: " + maskName + "\n" +
        				   "z value: " + weightingParams.getZ() + "\n" + 
        				   "epsilon value: " + weightingParams.getEpsilon() + "\n" + 
        				   "connectivity value: " + connectivityType.getConnectivityDegree() + "\n");
        
        
        Mat destMat = new Mat();
        carveHoleUsingMask(imageMat, maskMat, destMat);
        this.algCalculator.fillHole(destMat, connectivityType, weightingParams);
        reconvertNormalizedImage(destMat);
        Imgcodecs.imwrite("output/" + imageName, destMat);
        System.out.println("Result was saved in output folder");
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
    public void carveHoleUsingMask(Mat im, Mat mask, Mat dst){
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
     * Get GrayScale image with 0-1 values, CV_32FC1 type
     * and convert it to 0-255 scale and CV_8UC1 type.
     */
    public void reconvertNormalizedImage(Mat im){
        Core.multiply(im, new Scalar(255), im);
        im.convertTo(im, CvType.CV_8UC1);
    }
}


