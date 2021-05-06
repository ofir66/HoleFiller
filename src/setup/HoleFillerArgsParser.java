package setup;

import java.io.File;

import data.ConnectivityType;
import data.Directory;
import data.WeightingParams;
import view.HoleFillerDisplay;

public class HoleFillerArgsParser {

    private String imagePath;
    private String maskPath;
    private WeightingParams weightingParams;
    private ConnectivityType connectivityType;
    private String[] args;
	
    private final int IMAGE_PATH = 0;
    private final int MASK_PATH = 1;
    private final int Z_VALUE = 2;
    private final int CONNECTIVITY_VALUE = 3;
    private final int EPSILON_VALUE = 4;
    private final Directory inputDir;
    
    public HoleFillerArgsParser(String[] argsVal, Directory inputDirVal) {
        imagePath = null;
        maskPath = null;
        weightingParams = null;
        connectivityType = ConnectivityType.C4;
    	args = argsVal;
    	inputDir = inputDirVal;
    }
    
	
    /**
     * @param args: should contain the following 5 elements:
     * args[0] = path to the image
     * args[1] = path to the image mask (which describes the hole)
     * args[2] = z value
     * args[3] = connectivityType - should be "4" or "8" only
     * args[4] = epsilon value
     * @return: true is parsing was successful and false otherwise
     * @throws: NumberFormatException if args[2] or args[3] or args[4] doesn't represent numeric value
     */
    public boolean parseArgs(){
    	int argsLen = args.length;
    	
    	if (argsLen != 5) {
    		HoleFillerDisplay.printToStderr("Wrong number of arguments, "
    							+ "please enter arguments list as described in the documentation for this method");
    		return false;
    	}
    	else {
    		int z = 0;
    		float epsilon = 0;
    		
    		for (int i = 0; i < argsLen; i++) {
    			switch(i) {
    				case IMAGE_PATH:
    					imagePath = this.inputDir.getPath() + "/" + args[i];
    					if (!assertPathExists(imagePath)) {
    						return false;
    					}
    					break;
    				case MASK_PATH:
    					maskPath = this.inputDir.getPath() + "/" + args[i];
    					if (!assertPathExists(maskPath)) {
    						return false;
    					}
    					break;
    				case Z_VALUE:
    	                try {
    	                    z = Integer.parseInt(args[i]);
    	                } catch (NumberFormatException e){
    	                	HoleFillerDisplay.printToStderr("Error - z value isn't an integer");
    	                    return false;
    	                }
    	                
    	                if (z <= 0){
    	                	HoleFillerDisplay.printToStderr("z value invalid - should be an integer>0");
    	                    return false;
    	                }
    	                
    	                break;
    				case CONNECTIVITY_VALUE:
    	                try {
    	                	int connectivityDegree = Integer.parseInt(args[i]);
    	                	if (connectivityDegree == 4) {
    	                		connectivityType = ConnectivityType.C4;
    	                	}
    	                	else {
    	                		connectivityType = ConnectivityType.C8;
    	                	}
    	                } catch (NumberFormatException e){
    	                	HoleFillerDisplay.printToStderr("Error - connectivity value isn't an integer");
    	                    return false;
    	                }
    	                if ( (connectivityType != ConnectivityType.C4) && 
    	                	 (connectivityType != ConnectivityType.C8) ) {
    	                	HoleFillerDisplay.printToStderr("Error - connectivity value must be 4 or 8, current value is: " 
             					   + connectivityType);
    	                	return false;
    	                }
    	                break;
    				case EPSILON_VALUE:
    	                try {
    	                    epsilon = Float.parseFloat(args[i]);
    	                } catch (NumberFormatException e){
    	                	HoleFillerDisplay.printToStderr("Error - epsilon value isn't a float");
    	                    return false;
    	                }
    	                
    	                if (epsilon <= 0){
    	                	HoleFillerDisplay.printToStderr("epsilon value is invalid - should be a float>0");
    	                    return false;
    	                }
    			}
    		}
    		
    		this.weightingParams = new WeightingParams(z, epsilon);
    	}
    	
    	return true;
    }
    
	private boolean assertPathExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
        	HoleFillerDisplay.printToStderr("The path: " + path + " doesn't exist, please enter a valid path");
            return false;
        }
		return true;
	}


	public String getImagePath() {
		return imagePath;
	}


	public String getMaskPath() {
		return maskPath;
	}

	public WeightingParams getWeightingParams() {
		return weightingParams;
	}

	public ConnectivityType getConnectivityType() {
		return connectivityType;
	}
}
