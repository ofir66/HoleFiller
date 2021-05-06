package control;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import data.Image;

public class HoleFillerConversionHandler {
	
	public Mat convertToGrayscale(Image im) {
    	String path = im.getPath();
        return Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
	}
	
    /**
     * Carve an hole in the given image using the mask.
     * Normalize the image values to 0 - 1.
     * Set image values to -1 in the mask positions.
     * Convert to type:CV_32FC1
     * @param mainImgMat grayScale image (0-255).
     * @param maskImgMat grayScale image (0-255), (the black pixels represent the hole).
     * @param dst Save the result to dst.
     */
    public void carveHoleUsingMask(Mat mainImgMat, Mat maskImgMat, Mat dst){
        Core.normalize(mainImgMat, dst,0.0,1.0, Core.NORM_MINMAX, CvType.CV_32FC1);
        Mat binMask = new Mat();
        Imgproc.threshold(maskImgMat, binMask, 127, 1, Imgproc.THRESH_BINARY_INV);
        
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
