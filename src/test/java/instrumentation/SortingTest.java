package instrumentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Created by Yohanna on 2017-03-17.
 */
class SortingTest {

    private int[] arrTest = new int[100];

    @BeforeEach
    void populateArray() {
        Random rand = new Random();
        for (int i = 0; i < arrTest.length; i++) {
            arrTest[i] = rand.nextInt(99999) + 1;
        }
    }

    @Test
    void testBubbleSort() {
    }

    @Test
    void testQuickSort() {

    }
}