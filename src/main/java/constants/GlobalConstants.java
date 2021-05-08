package constants;

import data.ConnectivityType;

public class GlobalConstants {

	public static final double HOLE_INDICATOR = -1;
	public static final double MIN_LIBRARY_PIXEL_VALUE = 0;
	public static final double MAX_LIBRARY_PIXEL_VALUE = 1;
	
	public static final double MAX_GRAYSCALE_VALUE = 255;
	public static final double GRAYSCALE_THRESHOLD_VALUE = 255 / 2;
	
	public static final int Z_VALUE = 3;
	public static final float EPSILON_VALUE = 0.01f;
	public static final ConnectivityType CONNECTIVITY_TYPE = ConnectivityType.EIGHT_CONNECTED;
	
}
