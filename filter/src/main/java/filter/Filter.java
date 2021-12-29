package filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Filter {
    BufferedImage srcImg;
    Consumer<ArrayList<Float>> sort;

    public Filter(BufferedImage srcImg) {
        this(srcImg, (ArrayList<Float> l) -> l.sort(Float::compare));
    }

    public Filter(BufferedImage srcImg, Consumer<ArrayList<Float>> sort) {
        this.srcImg = srcImg;
        this.sort = sort;
    }

    public BufferedImage filterImage(int radius) {
        assert (srcImg != null);
        assert (radius > 0);

        int pixelCount = srcImg.getHeight() * srcImg.getWidth();

        var filtered = new int[pixelCount];
        for (int k = 0; k < pixelCount; k++) {
            int i = k % srcImg.getWidth();
            int j = k / srcImg.getWidth();
            filtered[k] = FilterUtil.getMedian(srcImg, i, j, radius, sort);
        }

        return FilterUtil.createImageFromPixels(filtered, srcImg.getWidth(), srcImg.getHeight(), srcImg.getType());
    }
}
