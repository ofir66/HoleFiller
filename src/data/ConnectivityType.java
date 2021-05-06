package data;
public enum ConnectivityType {
    C4(4),
    C8(8);
	
	private final int connectivityDegree;
	
	ConnectivityType(final int newConnectivityDegree){
		connectivityDegree = newConnectivityDegree;
	}
	
	public int getConnectivityDegree() { return connectivityDegree; }
}
