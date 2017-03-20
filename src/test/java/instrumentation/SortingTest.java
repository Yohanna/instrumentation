package instrumentation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Yohanna on 2017-03-17.
 */
class SortingTest {

    private static int[] bubbleSortArr = new int[10000];
    private static int[] quickSortArr;

    static Instrumentation inst = Instrumentation.getInstance();

    @BeforeAll
    static void populateArray() {
        inst.activate(true);
        inst.startTiming("populateArray");

        Random rand = new Random();
        for (int i = 0; i < bubbleSortArr.length; i++) {
            bubbleSortArr[i] = rand.nextInt(99999) + 1;
        }

        inst.stopTiming("populateArray");
        inst.dump("populateArray.log");

        // Save the sorted array so it can be used by the QuickSort method
        quickSortArr = Arrays.copyOf(bubbleSortArr, bubbleSortArr.length);
    }

    @Test
    void testBubbleSort() {
        inst.activate(true);
        inst.startTiming("BubbleSort");

        BubbleSort2Algorithm bubbleSort = new BubbleSort2Algorithm();

        try {
            bubbleSort.sort(bubbleSortArr);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }

        inst.stopTiming("BubbleSort");
        inst.dump("BubbleSort.log");
    }

    @Test
    void testQuickSort() {
        inst.activate(true);
        inst.startTiming("QuickSort");

        QSortAlgorithm quickSort = new QSortAlgorithm();

        try {
            // Use the same sorted array for fair comparison
            quickSort.sort(quickSortArr);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }

        inst.stopTiming("QuickSort");
        inst.dump("QuickSort.log");

    }
}