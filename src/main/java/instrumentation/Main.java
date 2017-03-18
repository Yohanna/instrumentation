package instrumentation;

public class Main {

    public static void main(String[] args) {
        Instrumentation inst = Instrumentation.getInstance();

        inst.activate(true);

        try {
            inst.startTiming("main");
            long j = 0;
//            for (int i = 0; i < 100; i++) {
//                j += i;
//            }
            System.out.println("j = " + j);

            inst.stopTiming("main");
            inst.dump("temp");

        } catch (ActivationException e) {
            System.err.println(e.getStackTrace());
        }

    }
}
