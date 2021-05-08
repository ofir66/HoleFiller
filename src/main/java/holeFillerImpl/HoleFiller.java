package holeFillerImpl;


import control.HoleFillerController;
import data.Directory;
import data.HoleFillerModel;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import imageProcessorInterface.ImageProcessor;
import view.HoleFillerDisplay;

/**
 * A wrapper that connects all the library layers (model, display, control) together.
 */
public class HoleFiller implements ImageProcessor{

	private final HoleFillerModel model;
	private final HoleFillerDisplay display;
	private final HoleFillerController controller;
	
	public HoleFiller(HoleFillingAlgorithm holeFillingAlgorithmVal, Image mainImgVal,
			Image maskImgVal, WeightingFunc weightingFuncVal,
			Directory inputDirVal, Directory outputDirVal) {
		this.model = new HoleFillerModel(holeFillingAlgorithmVal, mainImgVal, maskImgVal,
				weightingFuncVal, inputDirVal, outputDirVal);
		this.display = new HoleFillerDisplay();
		this.controller = new HoleFillerController(model, display);
	}
	
	public void process() {
		controller.process();
	}
	
	

}
