package data;

public class HoleFillerModel {
	
	private final ConnectivityType connectivityType;
	private final HoleFillingAlgorithm holeFillingAlgorithm;
	private final Image img;
	private final Image mask;
	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;
	private final Directory inputDir;
	private final Directory outputDir;
	
	
	public HoleFillerModel(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image imgVal,
			Image maskVal, WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal, 
			Directory inputDirVal, Directory outputDirVal) {
		this.connectivityType = connectivityTypeVal;
		this.holeFillingAlgorithm = holeFillingAlgorithmVal;
		this.img = imgVal;
		this.mask = maskVal;
		this.weightingFunc = weightingFuncVal;
		this.weightingParams = weightingParamsVal;
		this.inputDir = inputDirVal;
		this.outputDir = outputDirVal;
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


	public Directory getInputDir() {
		return inputDir;
	}


	public Directory getOutputDir() {
		return outputDir;
	}

	
	
	
}
