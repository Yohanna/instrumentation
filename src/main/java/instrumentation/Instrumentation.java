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

    private String TABS;
    private Stack titles = new Stack();
    private static long startTime;
    private long stopTime;
    private long totalTime = 0;
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

        titles.add(comment);

        log.add("STARTTIMING: " + comment);

//        TABS.concat("| ");

        startTime = nanoTime();
//        System.out.format("startTime = %d in Start\n", startTime);
    }

    public void stopTiming(String comment) {

//        System.out.format("startTime = %d in Stop\n", startTime);

        long stopTime = nanoTime();

//        System.out.format("stopTime = %d in Stop\n", stopTime);
        long estimatedTime = stopTime - startTime;

        estimatedTime = TimeUnit.NANOSECONDS.toMillis(estimatedTime);

        log.add("STOPTIMING: " + comment + " " + Long.toString(estimatedTime) + "ms");
//        log.add(TABS + "STOPTIMING: " + comment);
        TABS = removeIndentation(TABS);

    }

    public void comment(String comment) {
        log.add("COMMENT: " + comment);
    }

    public void dump(String filename) {
        // TODO write to a file

        // Print the log for now
        for (String item : log) {
            System.out.println(item);
        }
//        System.out.println(Arrays.toString(log.toArray()));

        System.out.format("TOTAL TIME: %sms", Long.toString(totalTime));
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

