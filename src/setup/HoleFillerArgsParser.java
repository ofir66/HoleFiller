package setup;

import java.io.File;

import data.ConnectivityType;
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
	
	/**
	 * Finds the z value from the command line arguments
	 * @param args - command line arguments
	 * @return the z value if its command line argument is valid and CmdLineArg.INVALID_Z_VALUE otherwise
	 */
	public int parseZValue(String[] args) {
        int z;
        
		try {
            z = Integer.parseInt(args[CmdLineArg.Z_VALUE]);
        } catch (NumberFormatException e){
        	HoleFillerDisplay.printToStderr("Error - z value doesn't represent an integer, should be an integer>0");
        	z = CmdLineArg.INVALID_Z_VALUE;
        	return z;
        }
        
        if (z <= 0){
        	HoleFillerDisplay.printToStderr("z value invalid - should represent an integer>0");
        	z = CmdLineArg.INVALID_Z_VALUE;
        }
        
        return z;
	}
	
	/**
	 * Finds the pixel connectivity type from the command line arguments
	 * @param args - command line arguments
	 * @return the pixel connectivity type if its command line argument is valid 
	 * and CmdLineArg.INVALID_CONNECTIVITY_VALUE otherwise
	 */
	public ConnectivityType parseConnectivitiyValue(String[] args) {
		ConnectivityType connectivityType;
		int connectivityValue;
		
		try {
        	connectivityValue = Integer.parseInt(args[CmdLineArg.CONNECTIVITY_VALUE]);
        	if (connectivityValue == ConnectivityType.FOUR_CONNECTED.getConnectivityDegree()) {
        		connectivityType = ConnectivityType.FOUR_CONNECTED;
        	}
        	else if (connectivityValue == ConnectivityType.EIGHT_CONNECTED.getConnectivityDegree()) {
        		connectivityType = ConnectivityType.EIGHT_CONNECTED;
        	}
        	else {
            	HoleFillerDisplay.printToStderr("Error - connectivity value must be 4 or 8, current value is: " 
 					   + connectivityValue);
            	connectivityType = CmdLineArg.INVALID_CONNECTIVITY_VALUE;
        	}
        } catch (NumberFormatException e){
        	HoleFillerDisplay.printToStderr("Error - connectivity value doesn't represent an integer, "
        			+ "should represent an integer with value 4 or 8");
        	connectivityType = CmdLineArg.INVALID_CONNECTIVITY_VALUE;
        }

		return connectivityType;
	}
	
	/**
	 * Finds the epsilon value from the command line arguments
	 * @param args - command line arguments
	 * @return the epsilon value if its command line argument is valid and CmdLineArg.INVALID_EPSILON_VALUEe otherwise
	 */
	public float parseEpsilonValue(String[] args) {
        float epsilon;
		
		try {
            epsilon = Float.parseFloat(args[CmdLineArg.EPSILON_VALUE]);
        } catch (NumberFormatException e){
        	HoleFillerDisplay.printToStderr("Error - epsilon value doesn't represent a float, should represent a float > 0");
        	epsilon = CmdLineArg.INVALID_EPSILON_VALUE;
        	return epsilon;
        }
        
        if (epsilon <= 0){
        	HoleFillerDisplay.printToStderr("epsilon value is invalid - should be a float > 0");
        	epsilon = CmdLineArg.INVALID_EPSILON_VALUE;
        }
        
        return epsilon;
	}
}
