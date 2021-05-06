package holeFillerImpl;

import org.opencv.core.Mat;

import control.HoleFillerController;
import data.ConnectivityType;
import data.HoleFillerModel;
import data.HoleFillingAlgorithm;
import data.Image;
import data.WeightingFunc;
import data.WeightingParams;
import imageProcessorInterface.ImageProcessor;

public class HoleFiller implements ImageProcessor<Mat,Mat>{

	private final HoleFillerModel model;
	private final HoleFillerController controller;
	
	public HoleFiller(ConnectivityType connectivityTypeVal, HoleFillingAlgorithm holeFillingAlgorithmVal, Image imgVal,
			WeightingFunc weightingFuncVal, WeightingParams weightingParamsVal) {
		this.model = new HoleFillerModel(connectivityTypeVal, holeFillingAlgorithmVal, imgVal, 
				weightingFuncVal, weightingParamsVal);
		this.controller = new HoleFillerController(model);
	}
	
	@Override
	public Mat process(Mat imgSrc) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
