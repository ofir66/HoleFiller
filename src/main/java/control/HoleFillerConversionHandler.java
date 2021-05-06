package control;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import constants.GlobalConstants;
import data.Image;

public class HoleFillerConversionHandler {
	
	public Mat convertToGrayscale(Image im) {
    	String path = im.getPath();
        return Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
	}
	
    /**
     * Creates a grayscale destination image which composed from a grayscale source image 
     * and holes determined by a grayscale mask image.
     * This function also normalizes the value of the pixels to be in the range [0,1].
     * @param mainImgMat - grayscale source image.
     * @param maskImgMat - grayScale mask image.
     * @param dst - grayscale destination image.
     */
    public void createHoleWithMask(Mat mainImgMat, Mat maskImgMat, Mat dst){
        Core.normalize(mainImgMat, dst, GlobalConstants.MIN_LIBRARY_PIXEL_VALUE, GlobalConstants.MAX_LIBRARY_PIXEL_VALUE, 
        		Core.NORM_MINMAX, CvType.CV_32FC1);
        Mat binaryMask = new Mat();
        Imgproc.threshold(maskImgMat, binaryMask, GlobalConstants.GRAYSCALE_THRESHOLD_VALUE, GlobalConstants.MAX_LIBRARY_PIXEL_VALUE, 
        		Imgproc.THRESH_BINARY_INV);
        
        /*
         * sets all values on dst that aren't HOLE_INDICATOR on binaryMask to be HOLE_INDICATOR
         * example: https://stackoverflow.com/questions/8971308/what-is-cvsetto-function/8973044
         */
        dst.setTo(new Scalar(GlobalConstants.HOLE_INDICATOR), binaryMask);
    }
    
    /**
     * Gets a grayscale image with values in the range [0,1]
     * and converts the values to the range [0,255].
     */
    public void reconvertNormalizedImage(Mat im){
        Core.multiply(im, new Scalar(GlobalConstants.MAX_GRAYSCALE_VALUE), im);
        im.convertTo(im, CvType.CV_8UC1);
    }

}
