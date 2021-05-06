package holeFillerImpl;


import control.HoleFillerController;
import data.ConnectivityType;
import data.HoleFillerModel;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import data.WeightingParams;
import imageProcessorInterface.ImageProcessor;

public class HoleFiller implements ImageProcessor{

	private final HoleFillerModel model;
	private final HoleFillerController controller;
	
	public HoleFiller(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image imgVal,
			Image mask, WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal) {
		this.model = new HoleFillerModel(connectivityTypeVal, holeFillingAlgorithmVal, imgVal, mask,
				weightingFuncVal, weightingParamsVal);
		this.controller = new HoleFillerController(model);
	}
	
	@Override
	public void process() {
		controller.process();
	}
	
	

}
