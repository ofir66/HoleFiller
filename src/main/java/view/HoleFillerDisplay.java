package view;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import constants.GlobalConstants;
import data.Directory;
import data.Image;

/**
 * A class for all the display issues of the hole filling library
 */
public class HoleFillerDisplay {
	
	public static void printToStdout(String msg) {
		System.out.println(msg);
	}
	
	public static void printToStderr(String msg) {
		System.err.println(msg);
	}
	
	public void printHoleFillStartMsg(Image mainImg, Image mskImg) {
    	String imagePath = mainImg.getPath();
    	String maskPath = mskImg.getPath();
        String imageName = new File(imagePath).getName();
        String maskName = new File(maskPath).getName();
		
		System.out.println("Fill hole for: " + imageName + "\n" + 
				   "Use mask: " + maskName + "\n" +
				   "z value: " + GlobalConstants.Z_VALUE + "\n" + 
				   "epsilon value: " + GlobalConstants.EPSILON_VALUE + "\n" + 
				   "connectivity value: " + GlobalConstants.CONNECTIVITY_TYPE + "\n");
	}
	
	public void saveImgToOutputFile(Directory outputDir, String outputFileName, Mat processedImg) {
		Imgcodecs.imwrite(outputDir.getPath() + "/" + outputFileName, processedImg);
		System.out.println("Result was saved in output folder");
	}
}
