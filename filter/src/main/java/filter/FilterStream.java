package filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class FilterStream {
    BufferedImage srcImg;
    Consumer<ArrayList<Float>> sort;

    public FilterStream(BufferedImage srcImg) {
        this(srcImg, (ArrayList<Float> l) -> l.sort(Float::compare));
    }

    public FilterStream(BufferedImage srcImg, Consumer<ArrayList<Float>> sort) {
        this.srcImg = srcImg;
        this.sort = sort;
    }

    public BufferedImage filterImageStream(int radius) throws ExecutionException, InterruptedException {
        assert (srcImg != null);
        assert (radius > 0);

        int pixelCount = srcImg.getHeight() * srcImg.getWidth();

        int[] filtered = IntStream
                .range(0, pixelCount)
                .parallel()
                .map(k -> FilterUtil.getMedian(srcImg, k % srcImg.getWidth(), k / srcImg.getWidth(),
                        radius, sort)).toArray();

        return FilterUtil.createImageFromPixels(filtered, srcImg.getWidth(), srcImg.getHeight(), srcImg.getType());
    }
}