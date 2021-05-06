package holeFillerImpl;


import control.HoleFillerController;
import data.ConnectivityType;
import data.Directory;
import data.HoleFillerModel;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import data.WeightingFuncParams;
import imageProcessorInterface.ImageProcessor;
import view.HoleFillerDisplay;

/**
 * A wrapper that connects all the library layers (model, display, control) together.
 */
public class HoleFiller implements ImageProcessor{

	private final HoleFillerModel model;
	private final HoleFillerDisplay display;
	private final HoleFillerController controller;
	
	public HoleFiller(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image mainImgVal,
			Image maskImgVal, WeightingFunc weightingFuncVal, WeightingFuncParams weightingParamsVal,
			Directory inputDirVal, Directory outputDirVal) {
		this.model = new HoleFillerModel(connectivityTypeVal, holeFillingAlgorithmVal, mainImgVal, maskImgVal,
				weightingFuncVal, weightingParamsVal, inputDirVal, outputDirVal);
		this.display = new HoleFillerDisplay();
		this.controller = new HoleFillerController(model, display);
	}
	
	@Override
	public void process() {
		controller.process();
	}
	
	

}
