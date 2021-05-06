package data;

/**
 * A class for holding all the parameters needed by the weight function used in the hole filling library.
 */
public class WeightingFuncParams {
	
	private final int z;
	private final float epsilon;
	
	public WeightingFuncParams(int zVal, float epsilonVal) {
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
