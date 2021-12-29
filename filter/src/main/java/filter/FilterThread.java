package filter;

import lombok.Getter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FilterThread extends Thread {

    private final BufferedImage srcImg;
    private final int radius;
    @Getter
    private final int start, finish;
    @Getter
    private final int[] filtered;
    private Consumer<ArrayList<Float>> sort;

    public FilterThread(BufferedImage srcImg, int startPos, int finish, int radius, Consumer<ArrayList<Float>> sort) {
        this.srcImg = srcImg;
        this.start = startPos;
        this.finish = finish;
        filtered = new int[finish - startPos];
        this.radius = radius;
        this.sort = sort;
    }

    @Override
    public void run() {
        for (int k = start; k < finish; k++) {
            int i = k % srcImg.getWidth();
            int j = k / srcImg.getWidth();
            filtered[k - start] = FilterUtil.getMedian(srcImg, i, j, radius, sort);
        }
    }
}

