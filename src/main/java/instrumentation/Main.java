package instrumentation;

/**
 * Generate partial instrumentation logs for the test drive section of the project
 */
public class Main {

    public static void doSomeStuff() {
        Instrumentation inst = Instrumentation.getInstance();
        inst.activate(true);

        inst.startTiming("doSomeStuff()");
        System.out.println("Hello there !");
        inst.stopTiming("doSomeStuff()");
    }

    public static void overhead(boolean activate) {
        Instrumentation inst = Instrumentation.getInstance();
        inst.activate(activate);

        inst.startTiming("Without instrumentation class");
        int j = 0;
        for (int i = 0; i < 100000; i++) {
            if (i > 0) {
                j += i;
            }
        }

        inst.stopTiming("Without instrumentation class");
        inst.dump("without_instrumentation.log");
    }

    public static void main(String[] args) {

        // Generate logs for the test drive section of the project
        Instrumentation inst = Instrumentation.getInstance();
        inst.activate(true);

        inst.startTiming("main");

        inst.startTiming("loop");

        for (int i = 0; i < 5; i++) {
            doSomeStuff();
        }

        inst.stopTiming("loop");
        inst.comment("this is an example of a comment!");
        inst.stopTiming("main");

        inst.dump("test_drive.log");

        // Measure the instrumentation overhead
        inst.activate(true);
        inst.startTiming("With Instruemenation class");
        overhead(false);
        inst.activate(true); // Activate again since it was disabled in overhead()
        inst.stopTiming("With Instruemenation class");
        inst.dump("with_instrumentation.log");

        // Without instrumentation
        overhead(true);
    }
}
