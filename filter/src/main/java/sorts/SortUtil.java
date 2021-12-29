package sorts;

import java.util.ArrayList;
import java.util.List;

public class SortUtil {
    public static void bubbleSort(ArrayList<Float> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    float tmp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, tmp);
                }
            }
        }
    }

    public static void insertSort(ArrayList<Float> list) {
        for (int i = 1; i < list.size(); i++) {
            float current = list.get(i);
            int j = i - 1;
            while (j >= 0 && current < list.get(j)) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, current);
        }
    }

    public static void heapSort(ArrayList<Float> list) {
        int n = list.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(list, n, i);

        for (int i = n - 1; i >= 0; i--) {
            var tmp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, tmp);

            heapify(list, i, 0);
        }
    }

    public static void heapify(List<Float> list, int n, int i){
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && list.get(l) > list.get(largest))
            largest = l;

        if (r < n && list.get(r) > list.get(largest))
            largest = r;

        if (largest != i) {
            var swap = list.get(i);
            list.set(i, list.get(largest));
            list.set(largest, swap);

            heapify(list, n, largest);
        }
    }
}
