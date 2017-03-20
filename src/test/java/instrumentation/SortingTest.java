package instrumentation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

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
    }

    @Test
    void testBubbleSort() {
        int[] temp = arrTest;

        BubbleSort2Algorithm sort = new BubbleSort2Algorithm();

        try {
            sort.sort(temp);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }

        Arrays.sort(arrTest);
        Assertions.assertArrayEquals(arrTest, temp);
    }

    @Test
    void testQuickSort() {
        int[] temp = arrTest;

        QSortAlgorithm quickSort = new QSortAlgorithm();

        try {
            quickSort.sort(arrTest);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }

        Arrays.sort(arrTest);
        Assertions.assertArrayEquals(arrTest, temp);

    }
}