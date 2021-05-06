package data;

import org.opencv.core.Mat;

public class Image {
	
	private final Mat img;

	public Image(Mat imgVal) {
		this.img = imgVal;
	}

	public Mat getImg() {
		return img;
	}
}
