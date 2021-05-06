package data;

public class WeightingParams {
	
	private final int z;
	private final float epsilon;
	
	public WeightingParams(int zVal, float epsilonVal) {
		this.z = zVal;
		this.epsilon = epsilonVal;
	}

	public int getZ() {
		return z;
	}

	public float getEpsilon() {
		return epsilon;
	}
}
