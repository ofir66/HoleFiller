package data;

public class HoleFillerModel {
	
	private final ConnectivityType connectivityType;
	private final HoleFillingAlgorithm holeFillingAlgorithm;
	private final Image mainImg;
	private final Image maskImg;
	private final WeightingFunc weightingFunc;
	private final WeightingParams weightingParams;
	private final Directory inputDir;
	private final Directory outputDir;
	
	
	public HoleFillerModel(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image mainImgVal,
			Image maskImgVal, WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal, 
			Directory inputDirVal, Directory outputDirVal) {
		this.connectivityType = connectivityTypeVal;
		this.holeFillingAlgorithm = holeFillingAlgorithmVal;
		this.mainImg = mainImgVal;
		this.maskImg = maskImgVal;
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

	public Image getMainImg() {
		return mainImg;
	}
	
	public Image getMaskImg() {
		return maskImg;
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
