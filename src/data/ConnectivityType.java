package data;

/**
 * Represents the pixel connectivity value. 
 */
public enum ConnectivityType {
    FOUR_CONNECTED(4),
    EIGHT_CONNECTED(8);
	
	private final int connectivityDegree;
	
	ConnectivityType(final int newConnectivityDegree){
		connectivityDegree = newConnectivityDegree;
	}
	
	public int getConnectivityDegree() { return connectivityDegree; }
}
