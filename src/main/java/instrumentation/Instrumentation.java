package instrumentation;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import static java.lang.System.nanoTime;

/**
 * Created by Yohanna on 2017-03-16.
 */
class ActivationException extends Exception {
    public ActivationException(String msg) {
        super(msg);
    }
}

public class Instrumentation {

    private String TABS = "";
    private Stack startTimesStack = new Stack();
    private double totalTime = 0;
    private ArrayList<String> log = new ArrayList();
    private static boolean ACTIVE = false;

    private static Instrumentation ourInstance = new Instrumentation();

    public static Instrumentation getInstance() {
        return ourInstance;
    }

    private Instrumentation() {
    }

    public void startTiming(String comment) throws ActivationException {
        if (!ACTIVE) {
            throw new ActivationException("Instrumentation is not active");
        }

        log.add(TABS + "STARTTIMING: " + comment);
        TABS = TABS.concat("| ");

        startTimesStack.push(nanoTime());
    }

    public void stopTiming(String comment) {

        long estimatedTime = nanoTime() - (Long) startTimesStack.pop();

        if (startTimesStack.empty()) {
            totalTime = estimatedTime;
//        System.out.format("TOTAL TIME: %fms",  TimeUnit.NANOSECONDS.toMillis((long)totalTime));
            log.add("TOTAL TIME: " + totalTime + "ns");
        }

        estimatedTime = TimeUnit.NANOSECONDS.toMillis(estimatedTime);

        TABS = removeIndentation(TABS);

        log.add(TABS + "STOPTIMING: " + comment + " " + Long.toString(estimatedTime) + "ms");
    }

    public void comment(String comment) {
        log.add("COMMENT: " + comment);
    }

    public void dump(String filename) {


        //TODO remove this
        for (String item : log) {
            System.out.println(item);
        }
    }

    public void activate(boolean onoff) {
        if (onoff) ACTIVE = true;
    }

    // Remove last 2 chars
    private static String removeIndentation(String str) {
        if (str != null && str.length() >= 2) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}

