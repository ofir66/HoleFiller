package control;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import data.Pixel;
import data.WeightingFunc;
import data.WeightingParams;

public class HoleFillerAlgCalculator {

	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;

	public HoleFillerAlgCalculator(WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal) {
		this.weightingFunc = weightingFuncVal;
		this.weightingParams = weightingParamsVal;
	}
	
	protected final float calcWeight(Pixel p1, Pixel p2) {
		float weight = -1;
		
		switch(weightingFunc) {
			case DEFAULT:
		        float euclideanDist = (float) sqrt(pow(p1.getX() - p2.getX(), 2) + (pow(p1.getY() - p2.getY(), 2)));
		        weight = (float)(1/((pow(euclideanDist, weightingParams.getZ())) + weightingParams.getEpsilon()));
				break;
		}
		
		return weight;
	}
	
}
