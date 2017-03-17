package instrumentation;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {
//        Instrumentation inst = new Instrumentation();

        long startTime = nanoTime();
        long j=0;

        for (int i = 0; i < 10000000; i++) {
           j += i;
        }

        System.out.println("j = " + j);

        long stopTime = nanoTime();

        long estimatedTime = stopTime - startTime;

        estimatedTime =  TimeUnit.NANOSECONDS.toMillis(estimatedTime);

        System.out.println("estimatedTime = " + estimatedTime + " ms");
    }
}
