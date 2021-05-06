package imageProcessorInterface;

public interface ImageProcessor<T,V> {
	
	public T process(V imgSrc);
}
