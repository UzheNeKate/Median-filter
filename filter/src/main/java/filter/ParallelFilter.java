package filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ParallelFilter {
    BufferedImage srcImg;
    Consumer<ArrayList<Float>> sort;

    public ParallelFilter(BufferedImage srcImg) {
        this(srcImg, (ArrayList<Float> l) -> l.sort(Float::compare));
    }

    public ParallelFilter(BufferedImage srcImg, Consumer<ArrayList<Float>>  sort) {
        this.srcImg = srcImg;
        this.sort = sort;
    }
    public BufferedImage filterImage(int radius, int threadCount) throws InterruptedException {
        assert (srcImg != null);
        assert (radius > 0);
        assert (threadCount > 0);

        FilterThread[] threads = new FilterThread[threadCount];
        int pixelCount = srcImg.getHeight() * srcImg.getWidth();
        int sliceSize = pixelCount / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int finishPos = (i == threadCount - 1 ? pixelCount : sliceSize * (i + 1));
            threads[i] = new FilterThread(srcImg, sliceSize * i, finishPos, radius, sort);
        }

        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            thread.join();
        }

        var filtered = new int[pixelCount];

        for (FilterThread thread : threads) {
            System.arraycopy(thread.getFiltered(), 0, filtered, thread.getStart(), thread.getFiltered().length);
        }

        return FilterUtil.createImageFromPixels(filtered, srcImg.getWidth(), srcImg.getHeight(), srcImg.getType());
    }
}
