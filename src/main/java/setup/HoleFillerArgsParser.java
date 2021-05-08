package setup;

import java.io.File;

import data.Directory;
import view.HoleFillerDisplay;

/**
 * A class for parsing the command line arguments.
 */
public class HoleFillerArgsParser {	   
    
	private boolean assertPathExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
        	HoleFillerDisplay.printToStderr("The path: " + path + " doesn't exist, please enter a valid path");
            return false;
        }
		return true;
	}


	/**
	 * Finds the main image path from the command line arguments
	 * @param args - command line arguments
	 * @param inputDir - input directory where the main image at
	 * @return the main image path if its command line argument is valid and CmdLineArg.INVALID_MAIN_IMG_ARG otherwise
	 */
	public String parseMainImgPath(String[] args, Directory inputDir) {
		String mainImgPath = inputDir.getPath() + "/" + args[CmdLineArg.MAIN_IMG_PATH];
		
		if (!assertPathExists(mainImgPath)) {
			return CmdLineArg.INVALID_MAIN_IMG_ARG;
		}
		
		return mainImgPath;
	}
	
	/**
	 * Finds the mask image path from the command line arguments
	 * @param args - command line arguments
	 * @param inputDir - input directory where the mask image at
	 * @return the mask image path if its command line argument is valid and CmdLineArg.INVALID_MASK_IMG_ARG otherwise
	 */
	public String parseMaskImgPath(String[] args, Directory inputDir) {
		String maskImgPath = inputDir.getPath() + "/" + args[CmdLineArg.MASK_IMG_PATH];
		
		if (!assertPathExists(maskImgPath)) {
			return CmdLineArg.INVALID_MASK_IMG_ARG;
		}
		
		return maskImgPath;
	}
}
