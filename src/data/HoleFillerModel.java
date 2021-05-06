package data;

public class HoleFillerModel {
	
	private final ConnectivityType connectivityType;
	private final HoleFillingAlgorithm holeFillingAlgorithm;
	private final Image img;
	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;
	
	public HoleFillerModel(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image imgVal,
			WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal) {
		this.connectivityType = connectivityTypeVal;
		this.holeFillingAlgorithm = holeFillingAlgorithmVal;
		this.img = imgVal;
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

	public WeightingFunc getWeightingFunc() {
		return weightingFunc;
	}

	public WeightingParams getWeightingParams() {
		return weightingParams;
	}
}
