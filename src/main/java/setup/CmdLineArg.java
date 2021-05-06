package setup;

import data.ConnectivityType;

class CmdLineArg {
	
    static final int MAIN_IMG_PATH = 0;
    static final int MASK_IMG_PATH = 1;
    static final int Z_VALUE = 2;
    static final int CONNECTIVITY_VALUE = 3;
    static final int EPSILON_VALUE = 4;
    
    static final String INVALID_MAIN_IMG_ARG = null;
    static final String INVALID_MASK_IMG_ARG = null;
    static final int INVALID_Z_VALUE = -1;
    static final ConnectivityType INVALID_CONNECTIVITY_VALUE = null;
    static final float INVALID_EPSILON_VALUE = -1;
}
