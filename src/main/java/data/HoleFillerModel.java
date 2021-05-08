package data;

/**
 * A class to hold all the data the hole filling library needs.
 */
public class HoleFillerModel {
	
	private final HoleFillingAlgorithm holeFillingAlgorithm;
	private final Image mainImg;
	private final Image maskImg;
	private final WeightingFunc weightingFunc;
	private final Directory inputDir;
	private final Directory outputDir;
	
	
	public HoleFillerModel(HoleFillingAlgorithm holeFillingAlgorithmVal, Image mainImgVal,
			Image maskImgVal, WeightingFunc weightingFuncVal, Directory inputDirVal, 
			Directory outputDirVal) {
		this.holeFillingAlgorithm = holeFillingAlgorithmVal;
		this.mainImg = mainImgVal;
		this.maskImg = maskImgVal;
		this.weightingFunc = weightingFuncVal;
		this.inputDir = inputDirVal;
		this.outputDir = outputDirVal;
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

	public Directory getInputDir() {
		return inputDir;
	}


	public Directory getOutputDir() {
		return outputDir;
	}

	
	
	
}
