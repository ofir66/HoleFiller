package data;

public class HoleFillerModel {
	
	private final ConnectivityType connectivityType;
	private final HoleFillingAlgorithm holeFillingAlgorithm;
	private final Image img;
	private final Image mask;
	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;
	
	public HoleFillerModel(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image imgVal,
			Image maskVal, WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal) {
		this.connectivityType = connectivityTypeVal;
		this.holeFillingAlgorithm = holeFillingAlgorithmVal;
		this.img = imgVal;
		this.mask = maskVal;
		this.weightingFunc = weightingFuncVal;
		this.weightingParams = weightingParamsVal;
	}

	
	public ConnectivityType getConnectivityType() {
		return connectivityType;
	}

	public HoleFillingAlgorithm getHoleFillingAlgorithm() {
		return holeFillingAlgorithm;
	}

	public Image getImg() {
		return img;
	}
	
	public Image getMask() {
		return mask;
	}

	public WeightingFunc getWeightingFunc() {
		return weightingFunc;
	}

	public WeightingParams getWeightingParams() {
		return weightingParams;
	}
}
