package data;
public enum ConnectivityType {
    C4(4),
    C8(8);
	
	private final int connectivityType;
	
	ConnectivityType(final int newConnectivityType){
		connectivityType = newConnectivityType;
	}
	
	public int getConnectivityType() { return connectivityType; }
}
