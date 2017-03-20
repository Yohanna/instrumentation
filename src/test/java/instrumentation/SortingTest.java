package instrumentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Arrays;

/**
 * Created by Yohanna on 2017-03-17.
 */
class SortingTest {

    private int[] arrTest = new int[10];

    @BeforeEach
    void populateArray() {
        Random rand = new Random();
        for (int i = 0; i < arrTest.length; i++) {
            arrTest[i] = rand.nextInt(99999) + 1;
        }

        System.out.println("Arr before: " + Arrays.toString(arrTest));
    }

    @Test
    void testBubbleSort() {
        BubbleSort2Algorithm sort = new BubbleSort2Algorithm();

        try {
            sort.sort(arrTest);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }


        System.out.println("Arr after: " + Arrays.toString(arrTest));
    }

    @Test
    void testQuickSort() {
        QSortAlgorithm quickSort = new QSortAlgorithm();

        try {
            quickSort.sort(arrTest);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }
}