package setup;

import java.io.File;

import data.ConnectivityType;
import data.Directory;
import data.WeightingFuncParams;
import view.HoleFillerDisplay;

/**
 * A class for parsing the command line arguments.
 */
public class HoleFillerArgsParser {

    private String mainImgPath;
    private String maskImgPath;
    private WeightingFuncParams weightingParams;
    private ConnectivityType connectivityType;
    private String[] args;
    private final Directory inputDir;
	
    private final int MAIN_IMG_PATH = 0;
    private final int MASK_IMG_PATH = 1;
    private final int Z_VALUE = 2;
    private final int CONNECTIVITY_VALUE = 3;
    private final int EPSILON_VALUE = 4;
    
    public HoleFillerArgsParser(String[] argsVal, Directory inputDirVal) {
        mainImgPath = null;
        maskImgPath = null;
        weightingParams = null;
        connectivityType = ConnectivityType.FOUR_CONNECTED;
    	args = argsVal;
    	inputDir = inputDirVal;
    }
    
	
    /**
     * Parses the command line arguments, according to the structure described on {@link HoleFillerApp#main(String[] args)} javadoc
     * @return
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
    				case MAIN_IMG_PATH:
    					mainImgPath = this.inputDir.getPath() + "/" + args[i];
    					if (!assertPathExists(mainImgPath)) {
    						return false;
    					}
    					break;
    				case MASK_IMG_PATH:
    					maskImgPath = this.inputDir.getPath() + "/" + args[i];
    					if (!assertPathExists(maskImgPath)) {
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
    	                		connectivityType = ConnectivityType.FOUR_CONNECTED;
    	                	}
    	                	else {
    	                		connectivityType = ConnectivityType.EIGHT_CONNECTED;
    	                	}
    	                } catch (NumberFormatException e){
    	                	HoleFillerDisplay.printToStderr("Error - connectivity value isn't an integer");
    	                    return false;
    	                }
    	                if ( (connectivityType != ConnectivityType.FOUR_CONNECTED) && 
    	                	 (connectivityType != ConnectivityType.EIGHT_CONNECTED) ) {
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
    		
    		this.weightingParams = new WeightingFuncParams(z, epsilon);
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


	public String getMainImgPath() {
		return mainImgPath;
	}


	public String getMaskImgPath() {
		return maskImgPath;
	}

	public WeightingFuncParams getWeightingParams() {
		return weightingParams;
	}

	public ConnectivityType getConnectivityType() {
		return connectivityType;
	}
}
